<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="java.util.Date"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.LopHoc"%>
<%@page import="com.doan.totnghiep.util.Constant"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	int loai = (int) request.getAttribute("loai");
	List<Object[]> lsSinhVien = (List<Object[]>) request.getAttribute("listSinhVien");
	List<Object[]> lsMon = null;
%>
<style>
.table > thead > tr > th{
	padding: 5px;
}
.table > tbody > tr > td{
	padding: 5px;
}
</style>
<% if(lsSinhVien != null && lsSinhVien.size() > 0){ %>
	<div class="form-group" style="max-width: 100%;overflow-x: auto;">
		<table class="table table-bordered table-hover table-striped">
			<thead>
				<tr style="color:#d59100">
					<th>STT </th>
					<th>Tên sinh viên </th>
				<% if(loai == 1){ %>
				<% 
					lsMon = (List<Object[]>) request.getAttribute("lsMon");
					for(int i = 0; i < lsMon.size();i++){ 
				%>
					<th><%=lsMon.get(i)[1] %></th>
				<% 	} %>
					<th>ĐTB</th>
					<th>Xếp loại</th>
					<th>Số môn nợ</th>
				<% }else if(loai == 2){ %>
				<% for(int i = 1; i <= 7;i++){ %>
					<th>Học kỳ <%=i %></th>
				<% 	} %>
					<th>Điểm đồ án</th>
					<th>Điểm TNCT</th>
					<th>ĐTB</th>
					<th>Xếp loại</th>
					<th>Số môn nợ</th>
					<th>Tình trạng</th>
				<% } %>
				</tr>
			</thead>
			<tbody>
			<% for(int i = 0; i< lsSinhVien.size(); i++){%>
				<tr>
					<td>
						<%= i + 1 %>
					</td>
					<td>
						<%=lsSinhVien.get(i)[1].toString()%>
					</td>
				<% if(loai == 1){ %>
					<% for(int j = 0; j < lsMon.size();j++){ %>
						<td id='<%="diem" + lsSinhVien.get(i)[0] + lsMon.get(j)[0]%>'></td>
					<% } %>
					<td id="dtb<%=lsSinhVien.get(i)[0]%>"></td>
					<td id="xepLoai<%=lsSinhVien.get(i)[0]%>"></td>
					<td id="noMon<%=lsSinhVien.get(i)[0]%>"></td>
				<% }else if(loai == 2){ %>
					<% for(int hk = 1; hk <= 7;hk++){ %>
						<td id='<%="hk" +hk+ lsSinhVien.get(i)[0]%>'></td>
					<% 	} %>
					<td id="da<%=lsSinhVien.get(i)[0]%>"></td>
					<td id="tnct<%=lsSinhVien.get(i)[0]%>"></td>
					<td id="dtb<%=lsSinhVien.get(i)[0]%>"></td>
					<td id="xepLoai<%=lsSinhVien.get(i)[0]%>"></td>
					<td id="noMon<%=lsSinhVien.get(i)[0]%>"></td>
					<td id="tinhTrang<%=lsSinhVien.get(i)[0]%>"></td>
				<% } %>
				</tr>
			<% } %>
			</tbody>
		</table>
	</div>
<% }else{ %>
	<div class="no-data">Không tìm thấy dữ liệu</div>
<% } %>
<script type="text/javascript">
$(document).ready(function(){
	loadData();
});

function loadData(){
	lopId = $('#lopId').val();
	maSV =  $('#maSV').val();
	hocKy =  $('#hocKy').val();
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/baocao/loadDiem",
		data: {
			'lopId' : lopId,			
			'maSV' : maSV,
			'hocKy' : hocKy
		},
		dataType : "json",
		success : function(data) {
			console.log(data);
			if(data.length > 0){
				for(var i = 0 ;i< data.length; i++){
					var sinhVienId = data[i].sinhVienId;
					$("#dtb"+sinhVienId).html(data[i].dtb);
					$("#xepLoai"+sinhVienId).html(data[i].xepLoai);
					$("#noMon"+sinhVienId).html(data[i].soMonNo);
					if(data[i].diem.length > 0){
						for(var j = 0 ; j<data[i].diem.length; j++){
							if(hocKy != 11){
								var monId = data[i].diem[j].monId;
								$("#diem"+sinhVienId+monId).html(data[i].diem[j].diem);
							}else{
								var hk = data[i].diem[j].hocKy;
								$("#hk"+hk+sinhVienId).html(data[i].diem[j].dtb);
							}
						}
					}
					if(hocKy == 11){
						$("#tinhTrang"+sinhVienId).html(data[i].tinhTrang);
						$("#da"+sinhVienId).html(data[i].diemDoAn);
						$("#tnct"+sinhVienId).html(data[i].diemChinhTri);
					}
				}
			}
		},
		error : function(){
			console.log("error");
		}
	});
}
</script>
