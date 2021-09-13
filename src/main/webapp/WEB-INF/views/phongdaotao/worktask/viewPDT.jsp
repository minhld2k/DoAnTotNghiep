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
	List<Object[]> lsSinhVien = (List<Object[]>) request.getAttribute("lsSinhVien");
	List<WorkTask> lsData = (List<WorkTask>) request.getAttribute("lsWTSV");
	long monId = (long) request.getAttribute("monId");
	long lopId = (long) request.getAttribute("lopId");
%>
<style>
.table > thead > tr > th{
	padding: 5px;
}
.table > tbody > tr > td{
	padding: 5px;
}
</style>
<div class="trang-chitiet">
	<div class="modal-body">
		<div class="form-group" style="margin-bottom: 10px;display: flex;align-items: center;">
			<label for="name" class="col-3">Sinh viên </label> 
			<div class="col-9">
				<select class="form-control" id="sinhVienId" onchange="loadData();">
					<option value="">--Chọn--</option>
				<% 
					if(lsSinhVien != null && lsSinhVien.size() > 0){ 
						for(int i = 0 ; i < lsSinhVien.size(); i++){
				%>
	                 <option value="<%= lsSinhVien.get(i)[0] %>" ><%= lsSinhVien.get(i)[1].toString() %></option>
	                 <% } %>
	            <% } %>
	            </select>
			</div>
		</div>
		<div class="form-group" id="dataSV" style="display: none;">
			<table class="table table-bordered table-hover table-striped">
				<thead>
					<tr style="color:#d59100">
						<th>Tên công việc</th>
						<th>Kết quả</th>
						<th>Ý kiến</th>
					</tr>
				</thead>
				<tbody>
				<% if(lsData != null && lsData.size() > 0){
					for(int i = 0; i< lsData.size(); i++){%>
					<tr>
						<td colspan="3">
							<b><%= "Task " + lsData.get(i).getThuTu() + ". " + lsData.get(i).getTen() %></b>
						</td>
					</tr>
					<% 
						List<WorkTaskDetail> lsDetail = CommonUtil.getWTDetailByWTid(lsData.get(i).getId()) ;
						if(lsDetail != null && lsDetail.size() > 0){
							for(int j = 0 ;j<lsDetail.size();j++){ %>
							<tr>
								<td>
									<%=lsDetail.get(j).getThuTu() + ". " + lsDetail.get(j).getTen()%>
								</td>
								<td>
									<select class="form-control select" id="ketQua<%=lsDetail.get(j).getId() %>" name="ketQua[]">
										<option value = "0">Chọn</option>
						                <option value="1" >Làm tốt</option>
						                <option value="2" >Làm được</option>
						                <option value="3" >Chưa được</option>
						            </select>
								</td>
								<td>
									<input type="text"  class="form-control" value="" id="yKienKhac<%=lsDetail.get(j).getId() %>" name="yKienKhac[]" />
								</td>
							</tr>	
					<% 		}
						}
					%>
					<% } %>
				<% } %>
				</tbody>
			</table>
		</div>
		<div class="form-group" id="dataLop" style="display: none;">
			<table class="table table-bordered table-hover table-striped">
				<thead>
					<tr style="color:#d59100">
						<th>Sinh viên</th>
						<th>Làm tốt</th>
						<th>Làm được</th>
						<th>Chưa được</th>
					</tr>
				</thead>
				<tbody>
				<% 
					if(lsSinhVien != null && lsSinhVien.size() > 0){ 
						for(int i = 0 ; i < lsSinhVien.size(); i++){
				%>
					<tr>
						<td>
							<b><%= lsSinhVien.get(i)[1].toString() %></b>
						</td>
						<td id="lamTot<%= lsSinhVien.get(i)[0] %>"></td>
						<td id="lamDuoc<%= lsSinhVien.get(i)[0] %>"></td>
						<td id="chuaDuoc<%= lsSinhVien.get(i)[0] %>"></td>
					</tr>
	                 <% } %>
	            <% } %>
				</tbody>
			</table>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	loadDataLop();
	
	$("#sinhVienId").select2({
		tags: false,
		language: {
	        noResults: function(term) {
	          	return "Không tìm thấy kết quả";
	      	}
	    }
	});
});
function loadDataLop(){
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/worktask/loadDataLop",
		data : {
			'monId' : '<%=monId%>',
			'lopId' : '<%=lopId%>'
		},
		dataType : "json",
		success : function(data) {
			$("#dataSV").hide();
			$("#dataLop").show();
			if(data.length > 0){
				for(var i = 0 ;i< data.length; i++){
					$("#lamTot"+data[i][0]).html(data[i][1]);
					$("#lamDuoc"+data[i][0]).html(data[i][2]);
					$("#chuaDuoc"+data[i][0]).html(data[i][3]);
				}
			}
		},
		error : function(){
			console.log("error");
		}
	});
}
function loadData(){
	var sinhVienId = $("#sinhVienId").val();
	if(sinhVienId != '' && sinhVienId != 0){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/worktask/loadDataWTSV",
			data : {
				'monId' : '<%=monId%>',
				'sinhVienId' : sinhVienId,
				'loai' : '1'
			},
			dataType : "json",
			success : function(data) {
				$("#dataSV").show();
				$("#dataLop").hide();
				if(data.length > 0){
					for(var i = 0 ;i< data.length; i++){
						$("#ketQua"+data[i].workTaskDetailId).val(data[i].ketQua);
						$("#yKienKhac"+data[i].workTaskDetailId).val(data[i].ykien);
					}
				}else{
					$(".select").val('0');
				}
			},
			error : function(){
				console.log("error");
			}
		});
	}else{
		loadDataLop();
	}
}
</script>
