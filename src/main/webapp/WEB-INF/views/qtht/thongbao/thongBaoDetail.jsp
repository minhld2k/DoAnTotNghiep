<%@page import="com.doan.totnghiep.entities.UniFileUpLoads"%>
<%@page import="org.json.JSONArray"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.doan.totnghiep.entities.ThongBao"%>
<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.doan.totnghiep.dto.UserDetailDTO"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	ThongBao tb = (ThongBao) request.getAttribute("ThongBaoDetail");
	int loaiDS = (int) request.getAttribute("loaiDS");
%>
<div class="trang-chitiet">
	<ul>
	<%	if(loaiDS == 2){ %>	
		<li>
			<div class="label-nd">Người gửi</div>
			<div class="nd-tt">
	     		<p><%= CommonUtil.getTenNguoiDungByUserId(tb.getUser().getId(), tb.getUser().getNhomid())%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Ngày gửi</div>
			<div class="nd-tt">
	     		<p><%=new SimpleDateFormat("dd/MM/yyyy HH:mm").format(tb.getNgaySua())%></p>
			</div>					 
		</li>
	<% }%>
		<li>
			<div class="label-nd">Tiêu đề</div>
			<div class="nd-tt">
	     		<p><%=tb.getTieuDe()%></p>
			</div>					 
		</li>
		<li>
			<div class="label-nd">Nội dung</div>
			<div class="nd-tt">
	     		<p><%=tb.getNoiDung()%></p>
			</div>					 
		</li>
	<% 
		List<UniFileUpLoads> lsFile = CommonUtil.getUniFileUpLoads(tb.getId(), 0);
		if(lsFile != null && lsFile.size() > 0){
			for(int i = 0; i<lsFile.size();i++){
				UniFileUpLoads file = lsFile.get(i); %>
		<li>
			<div class="label-nd">Tệp đính kèm</div>
			<div class="nd-tt">
	     		<p><a href="<%=file.getLinkFile()%>" download><i class="fa fa-download"></i><%=file.getTenFile() %></a></p>
			</div>					 
		</li>
	<% 		}
		}
	%>
	<%	if(loaiDS == 1){ %>	
		<li>
			<div class="label-nd">Người nhận</div>
			<div class="nd-tt">
	     		<select class="form-control" id="userId" multiple disabled="disabled" >
					<%
						JSONArray arr = CommonUtil.getNguoiDungByThongBaoId(tb.getId());
						for(int i = 0; i < arr.length(); i++){
							JSONObject json = arr.getJSONObject(i);
					%>
	                     <option value="<%= json.getLong("id")%>" selected="selected"><%=json.getString("ten") %></option>
	                <% } %>
				</select>
			</div>					 
		</li>
	<% } %>   
	</ul>
</div>
<% if(loaiDS == 1){%>
<script>
$(document).ready(function(){
	$("#userId").select2({
		tags: false,
		width: '100%'
	});
});
</script>
<% }%>