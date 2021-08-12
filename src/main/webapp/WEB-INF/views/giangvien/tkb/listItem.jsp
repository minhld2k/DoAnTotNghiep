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
	List<ThoiKhoaBieuDTO> lsData = (List<ThoiKhoaBieuDTO>) request.getAttribute("TKB");
%>

<% if (lsData != null && lsData.size() > 0) { 
	for (int i = 0;i<lsData.size();i++) { 
		ThoiKhoaBieuDTO item = lsData.get(i);
	%>
	<li>
		<div class="number-stt">
			<span class="stt"><%=1 + i%></span>
		</div>
		<div class="nd-dvc" style="min-height: 25px;">
			<div>
				<p><b><%= Constant.getCaHoc(item.getCahoc())%> - <%=Constant.getDayStringOld(item.getNgay()) %> - ngày: <%=CommonUtil.toString(item.getNgay(), "dd/MM/yyyy")%></b></p>
			</div>
			<div>
				<p><%= item.getTenlop()%> - môn: <%=item.getTenmon() %></p>
			</div>
			<div>
				<p>Địa điểm: <%=item.getTenphong() %></p>
			</div>
			<% if(!item.getMota().isEmpty()){ %>
				<div>
					<p>Lời nhắn phòng đào tạo: <%=item.getMota() %></p>
				</div>
			<% } %>
		</div>
	</li>
	<% } %>
<% } else { %>
	<div class="no-data">Không tìm thấy dữ liệu</div>
<% } %>