<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.entities.PhongHoc"%>
<%@page import="com.doan.totnghiep.entities.LopHoc"%>
<%@page import="com.doan.totnghiep.entities.KhoaHoc"%>
<%@page import="com.doan.totnghiep.entities.NhomNguoiDung"%>
<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
List<PhongHoc> lsData = (List<PhongHoc>) request.getAttribute("listPhongHoc");
String addOrEditUrl = "/phongdaotao/phonghoc/addOrEdit";
int totalCount = (int) request.getAttribute("totalCount");
int count = (int) request.getAttribute("count");
User _user = (User) session.getAttribute("USERLOGIN");
%>

<% if (lsData != null && lsData.size() > 0) { 
	for (int i = 0;i<lsData.size();i++) { 
		PhongHoc phongHoc = lsData.get(i);
	%>
	<li>
		<div class="number-stt">
			<span class="stt"><%=1 + i + count%></span>
		</div>
		<div class="nd-dvc" style="min-height: 25px;">
			<div>
				<p><b><%=phongHoc.getTen()%></b></p>
			</div>
			<div>
				<p>Địa điểm:  <%= phongHoc.getCoSo().getTen() %></p>
			</div>
		</div>
		<div>
		<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "UPDATE_PHONG")){ %>
			<a href='<%=addOrEditUrl + "?phongId=" + phongHoc.getId()%>' title="Sửa">
				<i class='far fa-edit'></i>
			</a>
		<% } %>
		<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "DELETE_PHONG")){ %>
			<a onclick="xoaPhongHoc('<%=phongHoc.getId() %>');" title= "Xóa">
				<i class='far fa-trash-alt'></i>
			</a>
		<% } %>
		</div>
	</li>
	<% } %>
<% } else if(count == 0) { %>
	<div class="no-data">Không tìm thấy dữ liệu</div>
<% } %>
<script> 
    $(document).ready(function(){
    	$('#countCN').text('<%=totalCount%>');
    });
</script>