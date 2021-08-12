<%@page import="com.doan.totnghiep.dto.PhepDTO"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.doan.totnghiep.util.Constant"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.dto.ThoiKhoaBieuDTO"%>
<%@page import="com.doan.totnghiep.dto.MonHocDTO"%>
<%@page import="com.doan.totnghiep.entities.KhoaHoc"%>
<%@page import="com.doan.totnghiep.entities.NhomNguoiDung"%>
<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	List<Object[]> lsData = (List<Object[]>) request.getAttribute("lsPhep");
%>

<% if (lsData != null && lsData.size() > 0) { 
	for (int i = 0;i<lsData.size();i++) { 
	%>
	<li>
		<div class="number-stt">
			<span class="stt"><%=1 + i%></span>
		</div>
		<div class="nd-dvc" style="min-height: 25px;">
			<div>
				<p><%= lsData.get(i)[6].toString() +" - " + lsData.get(i)[7].toString()%></p>
			</div>
			<div>
				<p>Lý do: <%= lsData.get(i)[4].toString()%></p>
			</div>
			<div>
				<p>Số ngày nghĩ: <%= lsData.get(i)[3].toString()%></p>
			</div>
			<div>
				<p>Thời gian : từ <%=CommonUtil.toString((Date) lsData.get(i)[1], "dd/MM/yyyy")%> đến <%=CommonUtil.toString((Date) lsData.get(i)[2], "dd/MM/yyyy")%></p>
			</div>
			<div>
				<% 
					String trangThai = "<span style='color: blue;'>chấp nhận</span>";
					if((int)lsData.get(i)[5] == 1){
						trangThai = "<span style='color: red;'>đã hủy</span>";
					}
				%>
				<p>Trạng thái: <%=trangThai %></p>
			</div>
		</div>
	</li>
	<% } %>
<% } else { %>
	<div class="no-data">Không tìm thấy dữ liệu</div>
<% } %>