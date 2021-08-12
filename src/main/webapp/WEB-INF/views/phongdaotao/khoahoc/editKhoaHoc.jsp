<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form action="/phongdaotao/khoahoc/save" method="post" id="formAdd">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle"><%= session.getAttribute("title") %></h1>
	</div>
	<div class="modal-body">

		<div class="form-group">
			<input type="hidden" class="form-control" id="idAdd" name="id" value="${khoaHoc.id}">
		</div>
	
		<div class="form-group">
			<label for="name" class="col-form-label">Tên khóa<strong>*</strong>
			</label> <input type="text" class="form-control" id="tenKhoa" name="tenKhoa" value="${khoaHoc.ten}">
		</div>
		
		<div class="form-group">
			<label for="url" class="col-form-label">Tháng / Năm bắt đầu <strong>*</strong></label>
			<div style="display: flex;">
				<div class="col-d-4">
					<input type="number" name="thangStart" id="thangStart" class="form-control" value="${khoaHoc.thangBatDau}"/>
				</div>
				<div class="col-d-2"> / </div>
				<div class="col-d-4">
					<input type="number" name="namStart" id="namStart" class="form-control" value="${khoaHoc.namBatDau}"/>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="url" class="col-form-label">Tháng / Năm kết thúc</label>
			<div style="display: flex;">
				<div class="col-d-4">
					<input type="number" name="thangEnd" id="thangEnd" class="form-control" value="${khoaHoc.thangKetThuc}"/>
				</div>
				<div class="col-d-2"> / </div>
				<div class="col-d-4">
					<input type="number" name="namEnd" id="namEnd" class="form-control" value="${khoaHoc.namKetThuc}"/>
				</div>
			</div>
		</div>
		
		<div style="text-align: right;margin-top: 10px;">
			<a class="btn btn-secondary" href="/phongdaotao/khoahoc/list">Quay lại</a>
			<button type="submit" class="btn btn-primary">Lưu</button>
		</div>
	</div>
</form>
<script>
jQuery("#formAdd").validate({
	groups: {
		nameGroup: "thangStart namStart"
	},
	rules: {
		tenKhoa:{
			required: true
		},
		thangStart:{
			required: true,
			max : 12,
			min : 1
		},
		namStart:{
			required: true
		}
	},
	messages: {
		tenKhoa: 'Trường bắt buộc không được để trống',
		thangStart:{
			required: 'Trường bắt buộc không được để trống',
			max: 'Tháng nhỏ hơn hoặc bằng 12',
			min: 'Tháng lớn hơn hoặc bằng 1'
		},
		namStart : 'Trường bắt buộc không được để trống'
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/phongdaotao/khoahoc/save',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});
</script>