<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid p-0">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle">Lịch dạy</h1>
		<button class="btn btn-primary viewFind" style="float: right; margin-left: 5px;"> 
			<i class="fas fa-search"></i><span class="an-themmoi">Hiển thị tìm kiếm</span>
		</button>
	</div>
	
	<div class="card">
		<div class="card-body">
			<div class="viewForm box">
				<div class="row">
					<div class="col-lg-12" >
						<label for="name" class="mr-sm-2">Thời gian :</label>
						<div class="div-date">
		                    <div class="col-d-4">
		                       <input type="date" name="tuNgay" id="tuNgay" class="form-ip" value="" placeholder="Từ ngày">
		                    </div>
		                    <div class="col-d-2">
		                       <i class="fas fa-arrow-right"></i>
		                    </div>
		                    <div class="col-d-4">
		                       <input type="date" name="denNgay" id="denNgay" class="form-ip" value="" placeholder="Đến ngày">
		                    </div>
		               	</div>
					</div>
				</div>
				<div style="display: flow-root;">
					<button type="button" onclick="btnsearch();" class="btn btn-primary"
						style="margin-top: 10px; margin-bottom: 10px; float: right;">
						<span class="glyphicon glyphicon-search"></span> Tìm kiếm
					</button>
				</div>
			</div>
			
			<div class="buoc-3">
				<span id="countCN" style="display: none;"></span>
				<ul id="ulCN" class="ul-cn"></ul>
			</div>
		</div>
	</div>
</div>
<div class="uni-loading" style="display: none;"></div>
<div class="uni-loading-top" style="display: none"></div>
<a href="javascript:void(0)" id="loadMore"  ></a>
<script type="text/javascript">
var ajaxRuning = 0;
var tuNgay = "";
var denNgay = "";

$(document).ready(function(){
	loadMore();
});

function getDate(){
	var curr = new Date(); // get current date
	var first = curr.getDate() - curr.getDay(); // First day is the day of the month - the day of the week
	var last = first + 6; // last day is the first day + 6
	tuNgay = $("#tuNgay").val();
	denNgay = $("#denNgay").val();
	if(tuNgay == ''){
		var date = new Date(curr.setDate(first));
		var d = date.getDate().toString();
        var m = (date.getMonth() + 1 ).toString();
        tuNgay = date.getFullYear()+ '-' +(m[1]?m:"0"+m[0]) + '-' + (d[1]?d:"0"+d[0]) ;
		$("#tuNgay").val(tuNgay);
	}
	
	if(denNgay == ''){
		var date = new Date(curr.setDate(last));
		var d = date.getDate().toString();
        var m = (date.getMonth() + 1 ).toString();
        denNgay = date.getFullYear()+ '-' +(m[1]?m:"0"+m[0]) + '-' + (d[1]?d:"0"+d[0]) ;
		$("#denNgay").val(denNgay);
	}
	console.log("tungay: " + tuNgay);
	console.log("denngay: " + denNgay);
}
     
function loadMore() {
	ajaxRuning = 1;
	getDate();
	$.ajax({
		async: true,
		url: "/giangvien/tkb/load",
		type: "GET",
		dataType: "html",
		data: {			
			'tuNgay' : tuNgay,
			'denNgay' : denNgay
		},
		beforeSend: function() {
			 $(".uni-loading").show();
		},
		complete: function () {
			$(".uni-loading").hide();
			ajaxRuning = 0;
		},
		success: function(results) {
			test = results;
			if(results==""){
				$("#countDS").val("1");
			}
			if (results) {
				$("#ulCN").append(test);
			}
		},
		error: function () {
			console.log("Error");
		}
	});	
}

function btnsearch(){
	if (ajaxRuning == 0) {
		getDate();
		$("#ulCN").html("");
		loadMore();
	}
}
</script>
