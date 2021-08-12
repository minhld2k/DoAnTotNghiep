<%@page import="java.util.Map"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.doan.totnghiep.entities.ThongBao"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.doan.totnghiep.dto.ThongBaoDTO"%>
<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	User _u = (User) session.getAttribute("USERLOGIN");
	Map<String, Object> map = CommonUtil.getThongBaoHome(_u.getId());
	List<ThongBaoDTO> lsData = (List<ThongBaoDTO>) map.get("lsData");
	int totalCount = (int) map.get("totalCount");
%>

<div class="dropdown-menu-header"><%= totalCount %> thông báo mới</div>
<div class="list-group">
<% if (lsData != null && lsData.size() > 0) { 
	for(int i = 0; i < lsData.size(); i++){ 
		ThongBao tb = CommonUtil.getThongBaoById(lsData.get(i).getId());
		%>
		<a onclick="loadChiTietThongBao(2,'<%=tb.getId() %>',1);" class="list-group-item" style="font-weight: bold;">
			<div class="row g-0 align-items-center">
				<div class="col-1">
					<i class="far fa-bell"></i>
				</div>
				<div class="col-11">
					<div class="text-dark"><%= tb.getTieuDe()%></div>
					<div class="text-muted small mt-1"><%= tb.getNoiDung() %></div>
					<div class="text-muted small mt-1"><%= CommonUtil.getTime(tb.getNgaySua()) %></div>
				</div>
			</div>
		</a>
	<% } %>
<% } %>
</div>
<div class="dropdown-menu-footer">
	<a href="/thongbao/listNhan" class="text-muted">Xem tất cả thông báo</a>
</div>
<script> 
    $(document).ready(function(){
    	$('#countNoti').text('<%=totalCount%>');
    });
</script>