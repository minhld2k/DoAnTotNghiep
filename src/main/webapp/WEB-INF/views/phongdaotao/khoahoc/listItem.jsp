<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.entities.KhoaHoc"%>
<%@page import="com.doan.totnghiep.entities.NhomNguoiDung"%>
<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
List<KhoaHoc> lsKhoaHoc = (List<KhoaHoc>) request.getAttribute("listKhoaHoc");
User _user = (User) session.getAttribute("USERLOGIN");
String addOrEditUrl = "/phongdaotao/khoahoc/addOrEdit";
int totalCount = (int) request.getAttribute("totalCount");
int count = (int) request.getAttribute("count");
%>

<% if (lsKhoaHoc != null && lsKhoaHoc.size() > 0) { 
	for (int i = 0;i<lsKhoaHoc.size();i++) { 
		KhoaHoc khoaHoc = lsKhoaHoc.get(i);
	%>
	<li>
		<div class="number-stt">
			<span class="stt"><%=1 + i + count%></span>
		</div>
		<div class="nd-dvc" style="min-height: 25px;">
			<div>
				<p><b><%=khoaHoc.getTen()%></b></p>
			</div>
			<div>
				<p>Thời gian bắt đầu: Tháng <%=khoaHoc.getThangBatDau() %> / <%=khoaHoc.getNamBatDau() %></p>
			</div>
		</div>
		<div>
			<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "UPDATE_KHOA")){ %>
				<a href='<%=addOrEditUrl + "?khoaId=" + khoaHoc.getId()%>' title="Sửa">
					<i class='far fa-edit'></i>
				</a>
			<% } %>
			<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "DELETE_KHOA")){ %>
				<a onclick="xoaKhoaHoc('<%=khoaHoc.getId() %>');" title= "Xóa">
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