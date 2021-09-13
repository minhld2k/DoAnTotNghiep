<%@page import="com.doan.totnghiep.dto.UserDetailDTO"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	UserDetailDTO user = (UserDetailDTO) request.getAttribute("UserDetail");
	long id = 0 ;
	String hoTen = "";
	String email = "";
	String phone = "";
	String diaChi = "";
	int gioiTinh = 1;
	long nhomId = 1;
	if(user != null && user.getId() > 0){
		id = user.getId();
		hoTen = user.getHoTen();
		email = user.getEmail();
		phone = user.getPhone();
		diaChi = user.getDiaChi();
		nhomId = user.getNhomId();
		gioiTinh = user.getGioiTinh();
	}
%>
<form action="/phongdaotao/user/save" method="post" id="formAdd">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle"><%= session.getAttribute("title") %></h1>
	</div>
	<div class="modal-body">

		<div class="form-group">
			<input type="hidden" class="form-control" id="idAdd" name="id" value="<%= id%>">
		</div>
	
		<div class="form-group">
			<label for="name" class="col-form-label">Họ tên<strong>*</strong>
			</label> <input type="text" class="form-control" id="hoTen" name="hoTen" value="<%=hoTen%>" <%= id > 0 ? "" : "onblur='genUserName()'" %>>
		</div>
		
		<div class="form-group">
			<label for="name" class="col-form-label">Email</label> 
			<input type="text" class="form-control" id="email" name="email" value="<%=email%>" />
		</div>
		
		<div class="form-group">
			<label for="name" class="col-form-label">Số điện thoại</label> 
			<input type="text" class="form-control" id="phone" name="phone" value="<%=phone%>" />
		</div>
		
		<div class="form-group">
			<label for="name" class="col-form-label">Địa chỉ</label> 
			<textarea rows="2" class="form-control" id="diaChi" name="diaChi"><%=diaChi%></textarea>
		</div>
		
		<div class="form-group">
			<label for="name" class="col-form-label">Giới tính</label> 
			<div>
				<label class="form-check form-check-inline">
            		<input class="form-check-input" type="radio" name="gioiTinh" <%= gioiTinh == 0 ?"checked":"" %> value="0">Nữ
          		</label>
				<label class="form-check form-check-inline">
	            	<input class="form-check-input" type="radio" name="gioiTinh" <%= gioiTinh == 1 ?"checked":"" %> value="1">Nam
	            </label>
	            <label class="form-check form-check-inline">
	            	<input class="form-check-input" type="radio" name="gioiTinh" <%= gioiTinh == 2 ?"checked":"" %> value="2">Khác
	            </label>
			</div>
		</div>
		
		<div class="form-group">
			<label for="url" class="col-form-label">Nhóm người dùng <strong>*</strong></label>
			<select class="form-control" id="nhomId" name="nhomId" onchange="loadChiTiet()">
				 <c:set var="nhomId"  value="<%= nhomId %>"></c:set>
                 <c:forEach var="item" items="${lsNhom}">
                     <option value="${item.id}" ${item.id == nhomId ? "selected":"" } >${item.ten}</option>
                 </c:forEach>
             </select>
		</div>
		
		<div id="formData"></div>
		
		<% if(id <= 0){ %>
			<div class="form-group">
				<label for="name" class="col-form-label">Tên đăng nhập<strong>*</strong></label> 
				<input type="text" class="form-control" id="username" name="username" value="" />
			</div>
			
			<div class="form-group">
				<label for="name" class="col-form-label">Mật khẩu<strong>*</strong></label> 
				<input type="password" class="form-control" id="password" name="password" value="" />
			</div>
			
			<div class="form-group">
				<label for="name" class="col-form-label">Xác nhận mật khẩu<strong>*</strong></label> 
				<input type="password" class="form-control" id="rePass" name="rePass" value="" />
			</div>			
		<% }else{ %>
			<div class="form-group">
				<label for="name" class="col-form-label">Mật khẩu</label> 
				<input type="password" class="form-control" id="passwordNew" name="passwordNew" value="" />
			</div>
			
			<div class="form-group">
				<label for="name" class="col-form-label">Xác nhận mật khẩu</label> 
				<input type="password" class="form-control" id="rePassNew" name="rePassNew" value="" />
			</div>
		<% } %>
				
		<div style="text-align: right;margin-top: 10px;">
			<a class="btn btn-secondary" href="/phongdaotao/user/list">Quay lại</a>
			<button type="submit" class="btn btn-primary">Lưu</button>
		</div>
	</div>
</form>
<script>
$(document).ready(function() {
	loadChiTiet();
});
function loadChiTiet(){
	var nhomId = $("#nhomId").val();
	var userId = $("#idAdd").val();
	if(nhomId != ''){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/phongdaotao/user/getChiTiet",
			data : {
				'nhomId' : nhomId,
				'userId' : userId
			},
			dataType : "html",
			success : function(data) {
				$("#formData").html(data);
			},
		});
	}
}
jQuery.validator.addMethod("checkTrungMa", function(value, element) {
	var id = $("#idAdd").val() == "" ? 0 : $("#idAdd").val();
	var check = false;
	$.ajax({
		url : '/phongdaotao/user/checkma',
		type: "POST",
		async: false,
		dataType : "json",
		data : {
			'userId' : id,
			'khoa' : value ,
			'loai' : '1'
		},
		success : function(data) {
			check = parseInt(data) > 0 ? false : true;
		}
	});
	return check;
	
}, "");

jQuery.validator.addMethod("checkTrungMaSV", function(value, element) {
	var id = $("#idAdd").val() == "" ? 0 : $("#idAdd").val();
	var check = false;
	$.ajax({
		url : '/phongdaotao/user/checkma',
		type: "POST",
		async: false,
		dataType : "json",
		data : {
			'userId' : id,
			'khoa' : value,
			'loai' : '2'
		},
		success : function(data) {
			check = parseInt(data) > 0 ? false : true;
		}
	});
	return check;
	
}, "");

jQuery("#formAdd").validate({
	rules: {
		maSV : {
			required: true,
			checkTrungMaSV: true
		},
		hoTen:{
			required: true
		},
		nhomId:{
			required: true,
		},
		lopId :{
			required: true,
		},
		username :{
			required: true,
			checkTrungMa : true
		},
		password :{
			required: true,
		},
		rePass :{
			equalTo: "#password",
			required: true,
		},
		rePassNew:{
			equalTo: "#passwordNew",
		}
		
	},
	messages: {
		maSV: {
			required : "Trường bắt buộc không được để trống",
			checkTrungMaSV : "Mã sinh viên đã tồn tại"
		},
		hoTen: 'Trường bắt buộc không được để trống',
		nhomId : 'Trường bắt buộc không được để trống',
		lopId : 'Trường bắt buộc không được để trống',
		password : 'Trường bắt buộc không được để trống',
		username : {
			required : "Trường bắt buộc không được để trống",
			checkTrungMa : "Tên đăng nhập đã tồn tại"
		},
		rePass : {
			required: "Trường bắt buộc không được để trống",
			equalTo: "Xác nhận mật khẩu không chính xác"
		},
		rePassNew : {
			equalTo: "Xác nhận mật khẩu không chính xác"
		}
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/phongdaotao/user/save',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});

function genUserName(){
	var name = $("#hoTen").val().trim();
	name = removeVietnameseTones(name);
	var arrName = name.trim().toLowerCase().split(" ");
	var username = arrName[arrName.length-1];
	for(var i = 0; i< arrName.length-1;i++){
		username += arrName[i].substring(0, 1);
	}

	$("#username").val(username);
	$("#password").val("12345678");
	$("#rePass").val("12345678");
}

function removeVietnameseTones(str) {
    str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g,"a"); 
    str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g,"e"); 
    str = str.replace(/ì|í|ị|ỉ|ĩ/g,"i"); 
    str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g,"o"); 
    str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g,"u"); 
    str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g,"y"); 
    str = str.replace(/đ/g,"d");
    str = str.replace(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g, "A");
    str = str.replace(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g, "E");
    str = str.replace(/Ì|Í|Ị|Ỉ|Ĩ/g, "I");
    str = str.replace(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O");
    str = str.replace(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U");
    str = str.replace(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y");
    str = str.replace(/Đ/g, "D");
    // Some system encode vietnamese combining accent as individual utf-8 characters
    // Một vài bộ encode coi các dấu mũ, dấu chữ như một kí tự riêng biệt nên thêm hai dòng này
    str = str.replace(/\u0300|\u0301|\u0303|\u0309|\u0323/g, ""); // ̀ ́ ̃ ̉ ̣  huyền, sắc, ngã, hỏi, nặng
    str = str.replace(/\u02C6|\u0306|\u031B/g, ""); // ˆ ̆ ̛  Â, Ê, Ă, Ơ, Ư
    // Remove extra spaces
    // Bỏ các khoảng trắng liền nhau
    str = str.replace(/ + /g," ");
    str = str.trim();
    // Remove punctuations
    // Bỏ dấu câu, kí tự đặc biệt
    str = str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'|\"|\&|\#|\[|\]|~|\$|_|`|-|{|}|\||\\/g," ");
    return str;
}
</script>