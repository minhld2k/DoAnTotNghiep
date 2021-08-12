<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.doan.totnghiep.entities.ThongBao"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.doan.totnghiep.dto.ThongBaoDTO"%>
<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
List<ThongBaoDTO> lsThongBao = (List<ThongBaoDTO>) request.getAttribute("listThongBao");
int loaiDS = (int) request.getAttribute("loaiDS");
String addOrEditUrl = "/thongbao/addOrEdit";
int count = (int) request.getAttribute("count");
int totalCount = (int) request.getAttribute("totalCount");
%>

<% if (lsThongBao != null && lsThongBao.size() > 0) { 
	for (int i = 0;i<lsThongBao.size();i++) { 
		ThongBao tb = CommonUtil.getThongBaoById(lsThongBao.get(i).getId());
	%>
	<li>
		<div class="number-stt">
			<span class="stt"><%=1 + i + count%></span>
		</div>
		<div class="nd-dvc">
			<% if(loaiDS == 1){ %>
				<div onclick="loadChiTietThongBao(1,'<%=tb.getId()%>',0)">
					<p><%=tb.getTieuDe()%></p>
				</div>
			<% }else if(loaiDS == 2){ %>
			<div id="sent<%=tb.getId() %>" onclick="loadChiTietThongBao(2,'<%=tb.getId()%>',0)" style='<%= lsThongBao.get(i).getTrangThai() == 0 ? "font-weight: bold;" : "" %>'>
				<div>
					<p><%= CommonUtil.getTenNguoiDungByUserId(tb.getUser().getId(), tb.getUser().getNhomid()) %><span style="float: right;"><%= new SimpleDateFormat("dd/MM/yyyy HH:mm").format(tb.getNgaySua()) %></span></p>
				</div>
				<div>
					<p><%= tb.getTieuDe()%></p>
				</div>
			</div>
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