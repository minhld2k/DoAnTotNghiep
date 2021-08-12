<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.doan.totnghiep.dto.UserDetailDTO"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	JSONObject user = (JSONObject) request.getAttribute("UserDetail");
	long nhomId = user.getLong("nhomId");
%>
<div class="trang-chitiet">
	<ul>
	<%	if(nhomId == 1){ %>	
		<li>
			<div class="label-nd">Nhóm</div>
			<div class="nd-tt">
	     		<p><%=user.getString("tenNhom")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Họ và tên</div>
			<div class="nd-tt">
	     		<p><%=user.getString("hoTen")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Giới tính</div>
			<div class="nd-tt">
	     		<p><%=user.getString("gioiTinh")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Ngày sinh</div>
			<div class="nd-tt">
	     		<p><%=user.getString("ngaySinh")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Địa chỉ</div>
			<div class="nd-tt">
	     		<p><%=user.getString("diaChi")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Lớp</div>
			<div class="nd-tt">
	     		<p><%=user.getString("lop")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Số điện thoại</div>
			<div class="nd-tt">
	     		<p><%=user.getString("phone")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Số điện thoại gia đình</div>
			<div class="nd-tt">
	     		<p><%=user.getString("phoneFamily")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Giới thiệu</div>
			<div class="nd-tt">
	     		<p><%=user.getString("moTa")%></p>
			</div>					 
		</li>
	<% }else if(nhomId == 2){ %>
		<li>
			<div class="label-nd">Nhóm</div>
			<div class="nd-tt">
	     		<p><%=user.getString("tenNhom")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Họ và tên</div>
			<div class="nd-tt">
	     		<p><%=user.getString("hoTen")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Giới tính</div>
			<div class="nd-tt">
	     		<p><%=user.getString("gioiTinh")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Địa chỉ</div>
			<div class="nd-tt">
	     		<p><%=user.getString("diaChi")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Số điện thoại</div>
			<div class="nd-tt">
	     		<p><%=user.getString("phone")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Giới thiệu</div>
			<div class="nd-tt">
	     		<p><%=user.getString("moTa")%></p>
			</div>					 
		</li>
	<% }else if(nhomId == 3){ %>
		<li>
			<div class="label-nd">Nhóm</div>
			<div class="nd-tt">
	     		<p><%=user.getString("tenNhom")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Họ và tên</div>
			<div class="nd-tt">
	     		<p><%=user.getString("hoTen")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Giới tính</div>
			<div class="nd-tt">
	     		<p><%=user.getString("gioiTinh")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Số điện thoại</div>
			<div class="nd-tt">
	     		<p><%=user.getString("phone")%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Địa chỉ</div>
			<div class="nd-tt">
	     		<p><%=user.getString("diaChi")%></p>
			</div>					 
		</li>
	<% } %>   
	</ul>
</div>