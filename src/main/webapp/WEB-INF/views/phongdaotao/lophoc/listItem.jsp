<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.entities.LopHoc"%>
<%@page import="com.doan.totnghiep.entities.KhoaHoc"%>
<%@page import="com.doan.totnghiep.entities.NhomNguoiDung"%>
<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
List<LopHoc> lsData = (List<LopHoc>) request.getAttribute("listLopHoc");
String addOrEditUrl = "/phongdaotao/lophoc/addOrEdit";
int totalCount = (int) request.getAttribute("totalCount");
int count = (int) request.getAttribute("count");
User _user = (User) session.getAttribute("USERLOGIN");
%>

<% if (lsData != null && lsData.size() > 0) { 
	for (int i = 0;i<lsData.size();i++) { 
		LopHoc lopHoc = lsData.get(i);
	%>
	<li>
		<div class="number-stt">
			<span class="stt"><%=1 + i + count%></span>
		</div>
		<div class="nd-dvc" style="min-height: 25px;">
			<div>
				<p><b><%=lopHoc.getTen()%></b></p>
			</div>
			<div>
				<p>Khóa:  <%=lopHoc.getKhoaHoc().getTen() %></p>
			</div>
			<div class="btn-dvc">
			 	<div>
			 	<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "MONHOC_LOPHOC")){ %>
					<button class="btn btn-xemdv" style="margin: 0 3px 3px 0px;" onclick="xemMonHoc('<%= lopHoc.getId()%>')">
						Môn học
					</button>
				<% } %>
				<button id="btChonFi" type="button" onclick="viewNhatKy('<%=lopHoc.getId() %>')"
					class="btn-xemdv" style="display: inline-block;margin: 0 3px 3px 0px;" title="" >
					Nhật ký lên lớp
				</button>
				<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "LIST_TKB")){ %>
					<a id="btChonFi" class="btn btn-xemdv chonTaiLieuCaNhan" style="display: inline-block" title="" href="/phongdaotao/tkb/list?lopId=<%=lopHoc.getId()%>">
						TKB
					</a>
				<% } %>
			 	</div>
			 </div>
		</div>
		<div>
		<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "UPDATE_LOP")){ %>
			<a href='<%=addOrEditUrl + "?lopId=" + lopHoc.getId()%>' title="Sửa">
				<i class='far fa-edit'></i>
			</a>
		<% } %>
		<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "DELETE_LOP")){ %>
			<a onclick="xoaLopHoc('<%=lopHoc.getId() %>');" title= "Xóa">
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