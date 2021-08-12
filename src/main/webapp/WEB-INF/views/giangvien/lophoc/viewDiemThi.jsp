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
	long monId = (long) request.getAttribute("monId");
	LopHoc lop = (LopHoc) request.getAttribute("Lop");
	User _user = (User) session.getAttribute("USERLOGIN");
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
	<h1 class="tieude-chuyenmuc"><%= lop.getTen() %></h1>
    <form action="/giangvien/lop/saveDiemThi" method="post" id="formAdd">
		<div class="modal-body">
			<div class="form-group" style="margin-bottom: 10px;display: flex;align-items: center;">
				<input type="hidden" name="monId" value="<%= monId%>"/>
				<input type="hidden" name="lopId" value="<%= lop.getId()%>"/>
			</div>
			<div class="form-group">
				<table class="table table-bordered table-hover table-striped">
					<thead>
						<tr style="color:#d59100">
							<th>STT </th>
							<th>Tên sinh viên </th>
							<th>LT</th>
							<th>TH</th>
							<th>Ghi chú</th>
						</tr>
					</thead>
					<tbody>
					<% if(lsSinhVien != null && lsSinhVien.size() > 0){
						for(int i = 0; i< lsSinhVien.size(); i++){%>
						<tr>
							<td>
								<input type="hidden" name="sinhVienId[]" value="<%=lsSinhVien.get(i)[0]%>"/>
								<%= i + 1 %>
							</td>
							<td>
								<%=lsSinhVien.get(i)[1].toString()%>
							</td>
							<td style="max-width: 50px;min-width: 50px">
								<input style="padding: inherit;" type="text" name="lyThuyet[]" class="form-control" id="lyThuyet<%=lsSinhVien.get(i)[0]%>"/>
							</td>
							<td  style="max-width: 50px;min-width: 50px">
								<input style="padding: inherit;" type="text" name="thucHanh[]" class="form-control" id="thucHanh<%=lsSinhVien.get(i)[0]%>"/>
							</td>
							<td  style="max-width: 150px;">
								<input style="padding: inherit;" type="text" name="ghiChu[]" class="form-control" id="ghiChu<%=lsSinhVien.get(i)[0]%>"/>
							</td>
						</tr>
						<% } %>
					<% } %>
					</tbody>
				</table>
			</div>
			<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "SAVE_DIEMTHI")){ %>
				<div style="text-align: right;margin-top: 10px;" id="btnSave">
					<button type="submit" class="btn btn-primary">Lưu</button>
				</div>
			<% } %>
		</div>
	</form>
</div>
<script type="text/javascript">
$(document).ready(function(){
	loadData();
});
function loadData(){
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/giangvien/lop/loadDiemThiSV",
		data : {
			'lopId' : '<%=lop.getId()%>',
			'monId' : '<%=monId%>'
		},
		dataType : "json",
		success : function(data) {
			if(data.length > 0){
				$("#btnSave").html('');
				for(var i = 0 ;i< data.length; i++){
					$("#lyThuyet"+data[i].sinhVienId).val(data[i].lyThuyet);
					$("#thucHanh"+data[i].sinhVienId).val(data[i].thucHanh);
					$("#ghiChu"+data[i].sinhVienId).val(data[i].ghiChu);
				}
			}
		},
		error : function(){
			console.log("error");
		}
	});
}
</script>
