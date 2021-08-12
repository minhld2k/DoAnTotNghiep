<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.dto.MonHocDTO"%>
<%@page import="com.doan.totnghiep.entities.KhoaHoc"%>
<%@page import="com.doan.totnghiep.entities.NhomNguoiDung"%>
<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
List<MonHocDTO> lsMonHoc = (List<MonHocDTO>) request.getAttribute("listMonHoc");
String addOrEditUrl = "/phongdaotao/monhoc/addOrEdit";
int totalCount = (int) request.getAttribute("totalCount");
int count = (int) request.getAttribute("count");
User _user = (User) session.getAttribute("USERLOGIN");
%>

<% if (lsMonHoc != null && lsMonHoc.size() > 0) { 
	for (int i = 0;i<lsMonHoc.size();i++) { 
		MonHocDTO item = lsMonHoc.get(i);
	%>
	<li>
		<div class="number-stt">
			<span class="stt"><%=1 + i + count%></span>
		</div>
		<div class="nd-dvc" style="min-height: 25px;">
			<div>
				<p><b><%=item.getTen()%></b></p>
			</div>
			<div>
				<p>Giảng viên: <%=item.getTengiangvien() %></p>
			</div>
			<div>
				<p>Số tiết: <%=item.getSotiet() %></p>
			</div>
			<div class="btn-dvc">
			 	<div>
			 	<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "LIST_WORKTASK")){ %>
					<a class="btn btn-xemdv" style="margin: 0 3px 3px 0px;" href='<%="/worktask/list?monId="+item.getId()%>'>
						Work/task
					</a>
				<% } %>
				<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "LIST_DANHGIA")){ %>
					<a class="btn btn-xemdv" style="margin: 0 3px 3px 0px;" href='<%="/danhgia/list?monId="+item.getId()%>'>
						Đánh giá
					</a>
				<% } %>
			 	</div>
			 </div>
		</div>
		<div>
		<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "UPDATE_MON")){ %>
			<a href='<%=addOrEditUrl + "?monId=" + item.getId()%>' title="Sửa">
				<i class='far fa-edit'></i>
			</a>
		<% } %>
		<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "DELETE_MON")){ %>
			<a onclick="xoaMonHoc('<%=item.getId() %>');" title= "Xóa">
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