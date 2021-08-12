<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.dto.Menu"%>
<%@page import="java.util.List"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="sidebar-content js-simplebar">
	<a class="sidebar-brand" href="/" style="padding-bottom: 0px">
		<img src="/static/images/aspace-logo.png" style="width: 120px">
	</a>

	<ul class="sidebar-nav">
	<%
		User _u = (User) session.getAttribute("USERLOGIN");
		List<Menu> listMenuHeThong = CommonUtil.getMenuByUserId(_u.getId(), 1);
		List<Menu> listMenuQuanLy = CommonUtil.getMenuByUserId(_u.getId(), 2);
		if(listMenuHeThong != null && listMenuHeThong.size() > 0){
	%>
		<li class="sidebar-header">Quản trị hệ thống</li>
		<% for(Menu menu : listMenuHeThong) { %>
			<li class="sidebar-item">
				<a class="sidebar-link" href="<%=menu.getUrl()%>"> 
					<%= menu.getIcon().isEmpty() ? "<i class='fas fa-angle-right'></i>" : menu.getIcon() %><span class="align-middle"><%=menu.getTen() %></span>
				</a>
			</li>
		<% } %>
	<%} %>
		
	<% if(listMenuQuanLy != null & listMenuQuanLy.size() > 0){ %>
		<li class="sidebar-header">Quản lý</li>
		<% for(Menu menu : listMenuQuanLy) { %>
		<li class="sidebar-item">
			<a class="sidebar-link" href="<%=menu.getUrl()%>"> 
				<%= menu.getIcon().isEmpty() ? "<i class='fas fa-angle-right'></i>" : menu.getIcon() %><span class="align-middle"><%=menu.getTen() %></span>
			</a>
		</li>
		<% } %>
	<% } %>
	</ul>

</div>
