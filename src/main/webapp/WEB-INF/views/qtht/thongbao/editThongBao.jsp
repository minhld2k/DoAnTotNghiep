<%@page import="org.apache.taglibs.standard.extra.spath.ASCII_UCodeESC_CharStream"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.entities.LopHoc"%>
<%@page import="com.doan.totnghiep.entities.KhoaHoc"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.dto.UserDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	int truongHopId = (int) request.getAttribute("truongHopId");
	User _user = (User) session.getAttribute("USERLOGIN");
%>
<form action="/thongbao/save" method="post" id="formAdd" enctype="multipart/form-data">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle"><%= session.getAttribute("title") %></h1>
	</div>
	<div class="modal-body">

		<div class="form-group">
			<input type="hidden" class="form-control" id="idAdd" name="id" value="${thongBao.id}">
		</div>

		<div class="form-group">
			<label for="name" class="col-form-label">Tiêu đề <strong>*</strong></label> 
			<input type="text" class="form-control" id="tieuDe" name="tieuDe" value="${thongBao.tieuDe}">
		</div>

		<div class="form-group">
			<label for="url" class="col-form-label">Nội dung <strong>*</strong></label> 
			<textarea rows="2" class="form-control" id="noiDung" name="noiDung"><c:out value="${thongBao.noiDung}"></c:out></textarea>
		</div>
		
		<div class="form-group">
			<label for="url" class="col-form-label">Tệp đính kèm</label> 
			<input type="file" name="tepDinhKem" class="form-control" id="isFile" onchange="ajaxGetTenFile();"/>
		</div>
		
		<div class="form-group" style="margin-bottom: 10px;">
			<label for="url" class="col-form-label">Người nhận thông báo </label>
			<%
				if(truongHopId == 1 || truongHopId == 2){
					List<UserDTO> lsData = CommonUtil.getAllUser(truongHopId);
			%>
				<select class="form-control" id="userId" name="userId[]" multiple >
					<c:set var="lsUser" value="<%=lsData %>" />
					<c:forEach var="item" items="${lsUser}">
	                     <option value="${item.id}">${item.ten}</option>
	                </c:forEach>
				</select>
			<% 
				}else if(truongHopId == 3){
					List<KhoaHoc> lsData = CommonUtil.getAllKhoaHoc();
			%>
				<select class="form-control" id="khoaId" name="khoaId[]" multiple >
					<c:set var="lsKhoa" value="<%=lsData %>" />
					<c:forEach var="item" items="${lsKhoa}">
	                     <option value="${item.id}">${item.ten}</option>
	                </c:forEach>
				</select>
			<% 
				}else if(truongHopId == 4){ 
					List<LopHoc> lsData = CommonUtil.getAllLopHoc(-1,_user);
			%>
				<select class="form-control" id="lopId" name="lopId[]" multiple >
					<c:set var="lsLop" value="<%=lsData %>" />
					<c:forEach var="item" items="${lsLop}">
	                     <option value="${item.id}">${item.ten}</option>
	                </c:forEach>
				</select>
			<% } %>
		</div>
		
		<div style="text-align: right;">
			<a class="btn btn-secondary" href="/thongbao/listgui">Quay lại</a>
			<button type="submit" class="btn btn-primary">Lưu</button>
		</div>
	</div>
</form>
<script>
$(document).ready(function(){
	<% if(truongHopId == 1 || truongHopId == 2){ %>
	$("#userId").select2({
		tags: false,
		width: '100%'
	});
	<%  }else if(truongHopId == 3){ %>
	$("#khoaId").select2({
		tags: false,
		width: '100%'
	});
	<%  }else if(truongHopId == 4){  %>
	$("#lopId").select2({
		tags: false,
		width: '100%'
	});
	<% } %>
});
$.validator.addMethod("checkTotalSizeFile", function (value, element) {
    return checkTotalSizeFile();
}, "Dung lượng tối đa 10 MB.");
jQuery("#formAdd").validate({
	rules: {
		tieuDe:{
			required: true
		},
		noiDung:{
			required: true
		},
		tepDinhKem:{
			checkTotalSizeFile : true
		}
	},
	messages: {
		tieuDe: 'Trường bắt buộc không được để trống',
		noiDung: 'Trường bắt buộc không được để trống'
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/thongbao/save',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});

function ajaxGetTenFile(){
	$("#isFile").removeClass('selected');
	$("#isFile").parent().find(".help-inline").remove();
	var _c = $('#isFile').val();
	if(_c != 0){
		var _file = $('#isFile')[0].files, _fileN = '';
		for (var i = 0; i < _file.length; i++) {
			_c = _file[i].name.split('\\');
			_c = _c[_c.length-1];
			if (_c.length>23){
				_c = _c.substr(0, 10) + "..." + _c.substr(_c.length -10, 10);
			}
			if(i < _file.length - 1) {
				_c = _c.concat("<br>");
			}
			_fileN = _fileN + (_file.length > 1 ? "- " : "") + _c;
		}
		$('#tenFile').val(_fileN);
	}else{
		$('#tenFile').val("");
	}
}

function checkTotalSizeFile(){
	var maxSize = 10;
	var flagg = true;
	$('#formAdd input[type = file]').each(function(){
		var c = $(this).val();
		if(c != 0){
			fileSize = this.files[0].size;
			if(fileSize > maxSize*1024*1024){
				flagg = false;
			}
		}
 	});
	return flagg;
}
</script>