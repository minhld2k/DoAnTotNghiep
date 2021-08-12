<%@page import="com.doan.totnghiep.dto.MonHocDTO"%>
<%@page import="com.doan.totnghiep.util.Constant"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	List<MonHocDTO> listMonHoc = (List<MonHocDTO>) request.getAttribute("listMonHocDTO");
%>
<ul class="taiLieuCN">
	<%
	if (!listMonHoc.isEmpty()) {
		for (int i = 0; i < listMonHoc.size(); i++) {
				MonHocDTO tep = listMonHoc.get(i);
	%>
	<li>
		<div class="number-stt"><span class="stt"><%=i + 1%></span></div>
		<div class="nd-dvc" style="line-height: 100%;">
			<div class="ten-dvc">
				<span class="name-flie"><a><%=tep.getTen()%></a></span>
				<a class="add" onclick="addMon('<%=tep.getId()%>')"><i class="fas fa-plus-square"></i></a>
			</div>
		</div>
	</li>
	<%
		}
	} else {
	%>
	<li>
		<div class="nd-dvc">
			<div class="ten-dvc">
				<span class="name-flie"><a>Không tìm thấy dữ liệu</a></span>
			</div>
		</div>
	</li>
	<%
		}
	%>
</ul>
