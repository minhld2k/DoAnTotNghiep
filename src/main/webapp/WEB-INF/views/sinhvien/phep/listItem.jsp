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
	List<PhepDTO> lsData = (List<PhepDTO>) request.getAttribute("lsPhep");
%>

<% if (lsData != null && lsData.size() > 0) { 
	for (int i = 0;i<lsData.size();i++) { 
		PhepDTO item = lsData.get(i);
	%>
	<li>
		<div class="number-stt">
			<span class="stt"><%=1 + i%></span>
		</div>
		<div class="nd-dvc" style="min-height: 25px;">
			<div>
				<p>Lý do: <%= item.getLyDo()%></p>
			</div>
			<div>
				<p>Số ngày nghĩ: <%= item.getSoNgayNghi()%></p>
			</div>
			<div>
				<p>Thời gian : từ <%=CommonUtil.toString(item.getTuNgay(), "dd/MM/yyyy")%> đến <%=CommonUtil.toString(item.getDenNgay(), "dd/MM/yyyy")%></p>
			</div>
			<div>
				<% 
					String trangThai = "<span style='color: blue;'>chấp nhận</span>";
					if(item.getTrangThai() == 1){
						trangThai = "<span style='color: red;'>đã hủy</span>";
					}
				%>
				<p>Trạng thái: <%=trangThai %></p>
			</div>
		</div>
		<% 
		if(item.getTrangThai() == 0 && item.getTuNgay().after(new Date()) ){ %>
			<div>
				<a onclick="huyPhep('<%=item.getId() %>');">
					Hủy
				</a>
			</div>
		<% } %>
	</li>
	<% } %>
<% } else { %>
	<div class="no-data">Không tìm thấy dữ liệu</div>
<% } %>