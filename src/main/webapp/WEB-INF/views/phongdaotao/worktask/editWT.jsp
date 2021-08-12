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
	WorkTask wt = (WorkTask) request.getAttribute("workTask");
	long monId = (long) request.getAttribute("monId");
	
	long id = 0;
	String tenWT = "";
	int thuTu = CommonUtil.getMaxThuTuOfWT(monId);
	if(wt != null && wt.getId() > 0){
		id = wt.getId();
		tenWT = wt.getTen();
		thuTu = wt.getThuTu();
	}
	
%>
<style>
.table > thead > tr > th{
	padding: 5px;
}
.table > tbody > tr > td{
	padding: 5px;
}
</style>
<form action="/worktask/save" method="post" id="formAdd">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle"><%= session.getAttribute("title") %></h1>
	</div>
	<div class="modal-body">
		<div class="form-group" style="margin-bottom: 10px;display: flex;align-items: center;">
			<label for="name" class="col-3">Tên work/task <strong>*</strong></label> 
			<div class="col-9">
				<input type="hidden" name="id" value="<%=id%>"/>
				<input type="hidden" name="monId" value="<%=monId%>"/>
				<input type="text" class="form-control" id="tenWorkTask" name="tenWorkTask" value="<%=tenWT%>"/>
			</div>
		</div>
		<div class="form-group" style="margin-bottom: 10px;display: flex;align-items: center;">
			<label for="name" class="col-3">Thứ tự </label> 
			<div class="col-9">
				<input type="text" class="form-control" value="<%= thuTu %>" disabled="disabled"/>
				<input type="hidden" id="thuTu" name="thuTu" value="<%= thuTu %>"/>
			</div>
		</div>
		<div class="form-group">
			<table class="table table-bordered table-hover table-striped">
				<thead>
					<tr style="color:#d59100">
						<th>STT </th>
						<th>Tên công việc </th>
						<th style="width: 30px;">Chức năng</th>
					</tr>
				</thead>
				<tbody>
				<% 
				if(id > 0){
					List<WorkTaskDetail> lsDetails = CommonUtil.getWTDetailByWTid(id);
				if(lsDetails != null && lsDetails.size() > 0){
					for(int i = 0; i< lsDetails.size(); i++){%>
					<tr class="new">
						<td>
							<%= i + 1 %>
						</td>
						<td>
							<input class="form-control" type="text" name="tenWTDetail[]" value="<%=lsDetails.get(i).getTen()%>"/>
						</td>
						<td class="remCF">
							<a style="text-align: center;" title="Xóa" class="btn btn-xemdv" href="javascript:void(0)"> <i class="fas fa-window-close"></i></a>
						</td>
					</tr>
					<% } %>
				<% } %>
				<% } %>
				<tr class="new">
					<td>
						*
					</td>
					<td>
						<input type="text" class="form-control" name="faketenWTDetail[]" value=""/>
					</td>
					<td class="addCF">
						<a style="text-align: center;" title="Thêm" class="btn btn-xemdv" href="javascript:void(0)"> <i class="fa fa-plus"></i></a>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		<div style="text-align: right;margin-top: 10px;" id="btnSave">
			<a class="btn btn-secondary" href="/worktask/list?monId=<%=monId%>">Quay lại</a>
			<button type="submit" class="btn btn-primary">Lưu</button>
		</div>
	</div>
</form>
<script type="text/javascript">
$(function(){
	$('.table').on('click','.remCF',function(){
        $(this).parent().remove();
        var stt=1;
	    $('.table > tbody  > tr').each(function() {
	    	$(this).find('td').first().html(stt);
	    	stt++;
	    });
	    $(".table tr.new:last").find('td').first().html("*");
    });
	
	$('.table').on('click','.addCF',function(){
		var x = $('.table tr.new:last').clone(true);
	    var stt=1;
	    $('.table > tbody  > tr').each(function() {
	    	$(this).find('td').first().html(stt);
	    	$(this).find('td').last().html("<a class='btn btn-xemdv' href='javascript:void(0)'> <i class='fas fa-window-close'></i></a>");
	    	$(this).find('td').last().removeClass("addCF").addClass("remCF");
	    	$(this).find(':input').each(function(){
	    		$(this).attr('name',$(this).attr('name').replace("fake", "" ));
	    	});
	    	stt++;
	    });
	    stt-=1;
	    x.insertAfter('.table tr.new:last');
	    $('.table tr.new:last').find('td').first().html("*");
	    $('.table tr.new:last').find('td').last().html("<a class='btn btn-xemdv' href='javascript:void(0)'> <i class='fa fa-plus'></i></a>");
	    $('.table tr.new:last').find(':input').each(function(){
	    	$(this).val('');
	    });
    });
});

jQuery("#formAdd").validate({
	rules: {
		tenWorkTask:{
			required: true
		}
	},
	messages: {
		tenWorkTask: 'Trường bắt buộc không được để trống',
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/worktask/save',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});
</script>
