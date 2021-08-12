<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form action="/phongdaotao/lophoc/save" method="post" id="formAdd">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle"><%= session.getAttribute("title") %></h1>
	</div>
	<div class="modal-body">

		<div class="form-group">
			<input type="hidden" class="form-control" id="idAdd" name="id" value="${lopHoc.id}">
		</div>
	
		<div class="form-group">
			<label for="name" class="col-form-label">Tên lớp<strong>*</strong>
			</label> <input type="text" class="form-control" id="tenLop" name="tenLop" value="${lopHoc.ten}">
		</div>
		
		<div class="form-group">
			<label for="url" class="col-form-label">Khóa <strong>*</strong></label>
			<select class="form-control" id="khoaHocId" name="khoaHocId">
                 <c:forEach var="item" items="${listKhoaHoc}">
                     <option value="${item.id}" ${item.id == lopHoc.khoaHoc.id ? "selected":"" } >${item.ten}</option>
                 </c:forEach>
             </select>
		</div>
				
		<div style="text-align: right;margin-top: 10px;">
			<a class="btn btn-secondary" href="/phongdaotao/lophoc/list">Quay lại</a>
			<button type="submit" class="btn btn-primary">Lưu</button>
		</div>
	</div>
</form>
<script>
jQuery("#formAdd").validate({
	rules: {
		tenLop:{
			required: true
		},
		khoaHocId:{
			required: true,
		}
	},
	messages: {
		tenLop: 'Trường bắt buộc không được để trống',
		khoaHocId : 'Trường bắt buộc không được để trống'
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/phongdaotao/lophoc/save',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});
</script>