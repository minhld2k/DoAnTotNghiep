<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String addOrEditUrl = "/thongbao/addOrEdit";
	User _user = (User) session.getAttribute("USERLOGIN");
%>
<div class="container-fluid p-0">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle">Danh sách thông báo</h1>
		<button class="btn btn-primary viewFind" style="float: right; margin-left: 5px;"> 
			<i class="fas fa-search"></i><span class="an-themmoi">Hiển thị tìm kiếm</span>
		</button>
		<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "ADD_THONGBAO")){ %>
			<a class="btn btn-primary" onclick="chonTruongHop();"
				style="float: right;"> <i class="fas fa-plus"></i><span class="an-themmoi">Thêm mới</span>
			</a>
		<% } %>
	</div>
	
	<div class="card">
		<div class="card-body">
			<div class="viewForm box">
				<div class="row">
					<div class="col-lg-12" >
						<label for="name" class="mr-sm-2">Nội dung tìm kiếm:</label>
						<input type="text" name="title" id="title" class="form-control" value=""/>
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
	
	<!-- Modal -->
	 <div class="div-modal">
	     <div class="background-modal"></div>
		 <div class="content-modal">
		      <a class="close-md" onclick="closeModal();"><i class="far fa-times-circle"></i></a>
			  <h1 class="tile-h1">Chọn trường hợp</h1>
			  <div class="row-dm">
			     <select class="form-ip" id="truongHopId" name="truongHopId">
			     <% if(_user.getNhomid() == 2){ %>
			     	<option value="1">Thông báo đến sinh viên</option>
	                <option value="4">Thông báo đến lớp</option>
			     <% }else{ %>
	                <option value="1">Thông báo đến sinh viên</option>
	                <option value="2">Thông báo đến giảng viên</option>
	                <option value="3">Thông báo đến khóa</option>
	                <option value="4">Thông báo đến lớp</option>
			     <% } %>
				</select>
			  </div>
			  <div class="row-dm" style="text-align: center;">
			     <button class="btn-xemdv" style="float: inherit;" type="button" onclick="add();" name="xem" id="btnLuu">Chọn</button>
			  </div>
		 </div>
	 </div>
</div>
<div class="uni-loading" style="display: none;"></div>
<div class="uni-loading-top" style="display: none"></div>
<a href="javascript:void(0)" id="loadMore"  ></a>
<script type="text/javascript">
var title = '';
var ajaxRuning = 0;

$(document).ready(function(){
	loadMore(title);
	$('.content-body').scroll(function() {
		if(ajaxRuning == 0 && checkLoadMore()) {
			var count = $(".ul-cn li").length;
			var countDVC = parseInt($("#countCN").text()) || 0;
			if(countDVC > 0 && countDVC > count) {
				ajaxRuning = 1;
				setTimeout(function() {
					loadMore(title);
				}, 5);
			}
		}
	});
});
    
function closeModal(){
   $('body').removeClass('open-modal');
}

function chonTruongHop(){
	$('body').addClass('open-modal');
}

function add(){
	closeModal();
	var truongHopId = $("#truongHopId").val();
	var url = '<%= addOrEditUrl%>';
	location.href = url + "?truongHopId=" + truongHopId;
}
function loadMore(title) {
	ajaxRuning = 1;
	var count = $(".ul-cn li").length;
	$.ajax({
		async: true,
		url: "/thongbao/load",
		type: "GET",
		dataType: "html",
		data: {
			'count' : count,			
			'title' : title,
			'loaiDS' : '1'
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
		title = $('#title').val();
		$("#ulCN").html("");
		loadMore(title);
	}
}
</script>
