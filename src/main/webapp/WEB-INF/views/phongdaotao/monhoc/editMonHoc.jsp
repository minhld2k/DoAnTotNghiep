<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form action="/phongdaotao/monhoc/save" method="post" id="formAdd">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle"><%= session.getAttribute("title") %></h1>
	</div>
	<div class="modal-body">

		<div class="form-group">
			<input type="hidden" class="form-control" id="idAdd" name="id" value="${monHoc.id}">
		</div>
	
		<div class="form-group">
			<label for="name" class="col-form-label">Tên môn học<strong>*</strong>
			</label> <input type="text" class="form-control" id="ten" name="ten" value="${monHoc.ten}">
		</div>
		
		<div class="form-group">
			<label for="url" class="col-form-label">Số tiết <strong>*</strong></label>
			<input type="number" class="form-control" id="soTiet" name="soTiet" value="${monHoc.soTiet}">
		</div>
		
		<div class="form-group">
			<label for="url" class="col-form-label">Giảng viên <strong>*</strong></label>
			<select class="form-control" id="giangVienId" name="giangVienId">
                 <c:forEach var="item" items="${lsGiangVien}">
                     <option value="${item.id}" ${item.id == monHoc.giangVien.id ? "selected":"" } >${item.hoTen}</option>
                 </c:forEach>
             </select>
		</div>
		
		<div style="text-align: right;margin-top: 10px;">
			<a class="btn btn-secondary" href="/phongdaotao/monhoc/list">Quay lại</a>
			<button type="submit" class="btn btn-primary">Lưu</button>
		</div>
	</div>
</form>
<script>
jQuery("#formAdd").validate({
	rules: {
		ten:{
			required: true
		},
		giangVienId:{
			required: true
		},
		soTiet:{
			required: true
		}
	},
	messages: {
		ten: 'Trường bắt buộc không được để trống',
		soTiet: 'Trường bắt buộc không được để trống',
		giangVienId : 'Trường bắt buộc không được để trống'
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/phongdaotao/monhoc/save',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});
</script>