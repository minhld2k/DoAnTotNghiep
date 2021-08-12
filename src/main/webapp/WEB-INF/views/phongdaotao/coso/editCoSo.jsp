<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form action="/phongdaotao/coso/save" method="post" id="formAdd">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle"><%= session.getAttribute("title") %></h1>
	</div>
	<div class="modal-body">

		<div class="form-group">
			<input type="hidden" class="form-control" id="idAdd" name="id" value="${coSo.id}">
		</div>
	
		<div class="form-group">
			<label for="name" class="col-form-label">Tên cơ sở<strong>*</strong> </label> 
			<input type="text" class="form-control" id="tenCoSo" name="tenCoSo" value="${coSo.ten}">
		</div>
		
		<div class="form-group">
			<label for="url" class="col-form-label">Địa chỉ <strong>*</strong></label>
			<input type="text" class="form-control" id="diaChi" name="diaChi" value="${coSo.diaChi}">
		</div>
				
		<div style="text-align: right;margin-top: 10px;">
			<a class="btn btn-secondary" href="/phongdaotao/coso/list">Quay lại</a>
			<button type="submit" class="btn btn-primary">Lưu</button>
		</div>
	</div>
</form>
<script>
jQuery("#formAdd").validate({
	rules: {
		tenCoSo:{
			required: true,
		},
		diaChi:{
			required: true,
		}
	},
	messages: {
		tenCoSo: 'Trường bắt buộc không được để trống',
		diaChi : 'Trường bắt buộc không được để trống'
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/phongdaotao/coso/save',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});
</script>