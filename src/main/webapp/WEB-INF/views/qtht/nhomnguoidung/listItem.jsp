<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.NhomNguoiDung"%>
<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
List<NhomNguoiDung> lsNhom = (List<NhomNguoiDung>) request.getAttribute("lstNhomNguoiDung");
String tenNhom = (String) request.getAttribute("tenNhom");
String addOrEditUrl = "/quantri/nhom/addOrEdit";
int totalCount = (int) request.getAttribute("totalCount");
int count = 0;
User _user = (User) session.getAttribute("USERLOGIN");
%>

<% if (lsNhom != null && lsNhom.size() > 0) { 
	for (int i = 0;i<lsNhom.size();i++) { 
		NhomNguoiDung nhom = lsNhom.get(i);
	%>
	<li>
		<div class="number-stt">
			<span class="stt"><%=1 + i + count%></span>
		</div>
		<div class="nd-dvc" style="min-height: 25px;">
			<div>
				<p><b><%=nhom.getTen()%></b></p>
			</div>
		</div>
		<div>
		<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "UPDATE_NHOMNGUOIDUNG")){ %>
			<a href='<%=addOrEditUrl + "?nhomId=" + nhom.getId()%>' title="Sửa">
				<i class='far fa-edit'></i>
			</a>
		<% } %>
		<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "DELETE_NHOMNGUOIDUNG")){ %>
			<a onclick="xoaNhom('<%=nhom.getId() %>');" title= "Xóa">
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