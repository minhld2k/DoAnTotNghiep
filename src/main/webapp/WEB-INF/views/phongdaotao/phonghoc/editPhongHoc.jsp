<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form action="/phongdaotao/phonghoc/save" method="post" id="formAdd">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle"><%= session.getAttribute("title") %></h1>
	</div>
	<div class="modal-body">

		<div class="form-group">
			<input type="hidden" class="form-control" id="idAdd" name="id" value="${phongHoc.id}">
		</div>
	
		<div class="form-group">
			<label for="name" class="col-form-label">Tên phòng<strong>*</strong>
			</label> <input type="text" class="form-control" id="ten" name="ten" value="${phongHoc.ten}">
		</div>
		
		<div class="form-group">
			<label for="url" class="col-form-label">Địa điểm <strong>*</strong></label>
			<select class="form-control" id="coSoId" name="coSoId">
                 <c:forEach var="item" items="${listCoSo}">
                     <option value="${item.id}" ${item.id == phongHoc.coSo.id ? "selected":"" } >${item.ten}</option>
                 </c:forEach>
             </select>
		</div>
				
		<div style="text-align: right;margin-top: 10px;">
			<a class="btn btn-secondary" href="/phongdaotao/phonghoc/list">Quay lại</a>
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
		coSoId:{
			required: true,
		}
	},
	messages: {
		ten: 'Trường bắt buộc không được để trống',
		coSoId : 'Trường bắt buộc không được để trống'
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/phongdaotao/phonghoc/save',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});
</script>