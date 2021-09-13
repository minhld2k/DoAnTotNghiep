<%@page import="com.doan.totnghiep.entities.NgoaiHeThong"%>
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
    <form action="/phongdaotao/lop/saveDiemNgoaiHeThong" method="post" id="formAdd">
		<div class="modal-body">
			<div class="form-group" style="margin-bottom: 10px;display: flex;align-items: center;">
				<input type="hidden" name="lopId" value="<%= lop.getId()%>"/>
			</div>
			<div class="form-group" style="margin-bottom: 10px;display: flex;align-items: center;">
				<label for="name" class="col-2">Môn </label> 
				<div class="col-10">
					<select class="form-control" id="loai" name="loai" onchange="loadData();">
		                 <option value="1" >Đồ án tốt nghiệp</option>
		                 <option value="2" >Điểm tốt nghiệp chính trị</option>
		            </select>
				</div>
			</div>
			<div class="form-group" id="loai1">
				<table class="table table-bordered table-hover table-striped">
					<thead>
						<tr style="color:#d59100">
							<th>STT </th>
							<th>Tên sinh viên </th>
							<th>TH</th>
							<th>BC</th>
						</tr>
					</thead>
					<tbody>
					<% if(lsSinhVien != null && lsSinhVien.size() > 0){
						for(int i = 0; i< lsSinhVien.size(); i++){
							NgoaiHeThong nht = CommonUtil.getDiemBySinhVienId(Long.parseLong(lsSinhVien.get(i)[0].toString()));
					%>
						<tr>
							<td>
								<input type="hidden" name="sinhVienId[]" value="<%=lsSinhVien.get(i)[0]%>"/>
								<%= i + 1 %>
							</td>
							<td>
								<%=lsSinhVien.get(i)[1].toString()%>
							</td>
							<td style="max-width: 50px;min-width: 50px">
								<input style="padding: inherit;" type="text" name="thucHien[]" class="form-control" value='<%=nht.getThucHien() == null ? "" :nht.getThucHien()%>'/>
							</td>
							<td style="max-width: 50px;min-width: 50px">
								<input style="padding: inherit;" type="text" name="baoCao[]" class="form-control" value='<%=nht.getBaoCao() == null ? "" : nht.getBaoCao()%>' />
							</td>
						</tr>
						<% } %>
					<% } %>
					</tbody>
				</table>
			</div>
			
			<div class="form-group" id="loai2" style="display: none;">
				<table class="table table-bordered table-hover table-striped">
					<thead>
						<tr style="color:#d59100">
							<th>STT </th>
							<th>Tên sinh viên </th>
							<th>Điểm</th>
						</tr>
					</thead>
					<tbody>
					<% if(lsSinhVien != null && lsSinhVien.size() > 0){
						for(int i = 0; i< lsSinhVien.size(); i++){
							NgoaiHeThong nht = CommonUtil.getDiemBySinhVienId(Long.parseLong(lsSinhVien.get(i)[0].toString()));
					%>
						<tr>
							<td>
								<input type="hidden" name="sinhVienId[]" value="<%=lsSinhVien.get(i)[0]%>"/>
								<%= i + 1 %>
							</td>
							<td>
								<%=lsSinhVien.get(i)[1].toString()%>
							</td>
							<td style="max-width: 50px;min-width: 50px">
								<input style="padding: inherit;" type="text" name="chinhTri[]" class="form-control" value='<%=nht.getChinhTri() == null ? "" : nht.getChinhTri() %>'/>
							</td>
						</tr>
						<% } %>
					<% } %>
					</tbody>
				</table>
			</div>
			<div style="text-align: right;margin-top: 10px;" id="btnSave">
				<button type="submit" class="btn btn-primary">Lưu</button>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
$(document).ready(function(){
	loadData();
});
function loadData(){
	var loai = $("#loai").val();
	if(loai == 1){
		$("#loai1").show();
		$("#loai2").hide();
	}else{
		$("#loai2").show();
		$("#loai1").hide();
	}
}
</script>
