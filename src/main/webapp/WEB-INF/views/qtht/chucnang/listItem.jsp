<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
List<ChucNang> lsChucNang = (List<ChucNang>) request.getAttribute("listChucNang");
String tenChucNang = (String) request.getAttribute("tenChucNang");
String khoa = (String) request.getAttribute("khoa");
String addOrEditUrl = "/quantri/chucnang/addOrEdit";
int count = (int) request.getAttribute("count");
int totalCount = (int) request.getAttribute("totalCount");
User _user = (User) session.getAttribute("USERLOGIN");
%>

<% if (lsChucNang != null && lsChucNang.size() > 0) { 
	for (int i = 0;i<lsChucNang.size();i++) { 
		ChucNang cn = lsChucNang.get(i);
	%>
	<li>
		<div class="number-stt">
			<span class="stt"><%=1 + i + count%></span>
		</div>
		<div class="nd-dvc">
			<div>
				<p><b><%=cn.getName()%></b></p>
			</div>
			<div>
				<p>Khóa: <%=cn.getKey() %></p>
			</div>
		</div>
		<div>
		<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "UPDATE_CHUCNANG")){ %>
			<a href='<%=addOrEditUrl + "?chucNangId=" + cn.getId()%>' title="Sửa">
				<i class='far fa-edit'></i>
			</a>
		<% } %>
		<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "DELETE_CHUCNANG")){ %>
			<a onclick="xoaChucNang('<%=cn.getId() %>');" title= "Xóa">
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