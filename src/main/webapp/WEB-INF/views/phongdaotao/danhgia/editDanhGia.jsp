<%@page import="com.doan.totnghiep.entities.DanhGia"%>
<%@page import="com.doan.totnghiep.entities.WorkTaskDetail"%>
<%@page import="com.doan.totnghiep.entities.WorkTask"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="java.util.Date"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.LopHoc"%>
<%@page import="com.doan.totnghiep.util.Constant"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	DanhGia danhGia = (DanhGia) request.getAttribute("danhGia");
	long monId = (long) request.getAttribute("monId");
	
	long id = 0;
	String tieuDe = "";
	String noiDung = "";
	int thuTu = CommonUtil.getMaxThuTuOfDanhGia(monId);
	if(danhGia != null && danhGia.getId() > 0){
		id = danhGia.getId();
		tieuDe = danhGia.getTieuDe();
		noiDung = danhGia.getCauHoi();
		thuTu = danhGia.getThuTu();
	}
	
%>
<form action="/danhgia/save" method="post" id="formAdd">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle"><%= session.getAttribute("title") %></h1>
	</div>
	<div class="modal-body">
		<div class="form-group">
			<input type="hidden" name="id" value="<%= id%>"/>
			<input type="hidden" name="monId" value="<%= monId%>"/>
			<input type="hidden" name="thuTu" value="<%= thuTu %>"/>
		</div>
		<div class="form-group">
			<label for="name" class="col-form-label">Tiêu đề <strong>*</strong></label> 
			<textarea rows="2" name="tieuDe" class="form-control"><%=tieuDe %></textarea>
		</div>
		<div class="form-group" style="margin-bottom: 10px;">
			<label for="name" class="col-form-label">Câu hỏi <strong>*</strong></label> 
			<textarea rows="2" name="cauHoi" class="form-control"><%=noiDung %></textarea>
		</div>
		<div class="form-group" style="margin-bottom: 10px;display: flex;align-items: center;">
			<label for="name" class="col-3">Thứ tự </label> 
			<div class="col-9">
				<input type="text" class="form-control" value="<%= thuTu %>" disabled="disabled"/>
			</div>
		</div>
		
		<div style="text-align: right;margin-top: 10px;" id="btnSave">
			<a class="btn btn-secondary" href="/danhgia/list?monId=<%=monId%>">Quay lại</a>
			<button type="submit" class="btn btn-primary">Lưu</button>
		</div>
	</div>
</form>
<script type="text/javascript">
jQuery("#formAdd").validate({
	rules: {
		tieuDe:{
			required: true
		},
		cauHoi:{
			required: true
		}
	},
	messages: {
		tieuDe: 'Trường bắt buộc không được để trống',
		cauHoi: 'Trường bắt buộc không được để trống'
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/danhgia/save',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});
</script>
