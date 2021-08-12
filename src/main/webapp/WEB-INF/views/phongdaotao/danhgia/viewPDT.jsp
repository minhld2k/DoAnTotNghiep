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
	List<Object[]> lsSinhVien = (List<Object[]>) request.getAttribute("lsSinhVien");
	List<WorkTask> lsData = (List<WorkTask>) request.getAttribute("lsWTSV");
	List<DanhGia> lsDanhGia = (List<DanhGia>) request.getAttribute("lsDGSV");
	long monId = (long) request.getAttribute("monId");
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
		<div class="form-group">
			<label for="name" class="col-form-label">Phần 1: Đánh giá tổng quát</label> 
			<table class="table table-bordered table-hover table-striped">
				<thead>
					<tr style="color:#d59100">
						<th>STT</th>
						<th>Work/Task</th>
						<th>Kết quả</th>
					</tr>
				</thead>
				<tbody>
				<% if(lsData != null && lsData.size() > 0){
					for(int i = 0; i< lsData.size(); i++){%>
					<tr>
						<td><%=i+1 %></td>
						<td>
							<%= lsData.get(i).getTen() %>
						</td>
						<td>
							<select class="form-control select" id="ketQua<%=lsData.get(i).getId() %>" name="ketQua[]">
								<option value = "0">Chọn</option>
				                <option value="1" >Làm tốt</option>
				                <option value="2" >Làm được</option>
				                <option value="3" >Chưa được</option>
				            </select>
						</td>
					</tr>
					<% } %>
				<% } %>
				</tbody>
			</table>
		</div>
		<div class="form-group" style="margin-bottom: 10px;">
			<label for="name" class="col-form-label">Phần 2: Tự đánh giá</label>
		</div>
		<% 
			if(lsDanhGia != null && lsDanhGia.size() > 0){ 
			for(int i = 0; i<lsDanhGia.size();i++){
		%>
		<div class="form-group" style="margin-bottom: 10px;">
			<label for="name" class="col-form-label"><%= "2."+(i+1)+" " + lsDanhGia.get(i).getTieuDe() %></label> 
			<p><b>Câu hỏi: </b><%=lsDanhGia.get(i).getCauHoi() %></p>
			<textarea rows="2" id="traLoi<%=lsDanhGia.get(i).getId() %>" name="traLoi[]" class="form-control textarea"></textarea>
		</div>
			<% } %>
		<% } %>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	loadData();
});
function loadData(){
	var sinhVienId = $("#sinhVienId").val();
	if(sinhVienId != '' && sinhVienId != 0){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/danhgia/loadDataWTSV",
			data : {
				'monId' : '<%=monId%>',
				'sinhVienId' : sinhVienId,
				'loai' : '1'
			},
			dataType : "json",
			success : function(data) {
				if(data.lsData1.length > 0){
					var lsData1 = data.lsData1;
					for(var i = 0 ;i<lsData1.length; i++){
						$("#ketQua"+lsData1[i].workTaskId).val(lsData1[i].ketQua);
					}
				}else{
					$(".select").val('0');
				}
				
				if(data.lsData2.length > 0){
					var lsData2 = data.lsData2;
					for(var i = 0 ;i<lsData2.length; i++){
						$("#traLoi"+lsData2[i].danhGiaId).val(lsData2[i].traLoi);
					}
				}else{
					$(".textarea").val('');
				}
			},
			error : function(){
				console.log("error");
			}
		});
	}
}
</script>
