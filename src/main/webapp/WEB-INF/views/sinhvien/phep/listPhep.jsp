<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid p-0">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle">Phép cá nhân</h1>
		<a class="btn btn-primary" href="/sinhvien/phep/add"
			style="float: right;"> <i class="fas fa-plus"></i><span class="an-themmoi">Thêm mới</span>
		</a>
	</div>
	
	<div class="card">
		<div class="card-body">
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

$(document).ready(function(){
	loadMore();
});
     
function loadMore() {
	ajaxRuning = 1;
	$.ajax({
		async: true,
		url: "/sinhvien/phep/load",
		type: "GET",
		dataType: "html",
		data: {},
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
function huyPhep(phepId) {
    $.confirm({
        'title': "",
        'message': "Bạn có muốn hủy phép này không",
        'buttons': {
            'Có': {
                'class': 'blue',
                'action': function() {
                    $.ajax({
                        url: "/sinhvien/phep/huy",
                        type: "POST",
                        dataType: "html",
                        data: {
                            "phepId": phepId,
                        },
                        beforeSend: function() {
           				 	$(".uni-loading-top").show();
	           			},
	           			complete: function () {
	           				$(".uni-loading-top").hide();
	           			},
                        success: function(data) {
                        	location.reload();
                        }
                    });
                },
            },
            'Không': {
                'class': 'blue',
                'action': function() {
                }
            }
        }
    });
}
</script>
