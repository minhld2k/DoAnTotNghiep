<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="java.util.Date"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.LopHoc"%>
<%@page import="com.doan.totnghiep.util.Constant"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	List<Object[]> lsSinhVien = (List<Object[]>) request.getAttribute("lsSinhVien");
	List<Object[]> lsTKB = (List<Object[]>) request.getAttribute("lsTKB");
	LopHoc lop = (LopHoc) request.getAttribute("Lop");
	User _user = (User) session.getAttribute("USERLOGIN");
	boolean check = CommonUtil.checkQuyenByKhoa(_user.getId(), "SAVE_DIEMDANH");
%>
<style>
.table > thead > tr > th{
	padding: 5px;
}
.table > tbody > tr > td{
	padding: 5px;
}
</style>
<div class="trang-chitiet">
	<h1 class="tieude-chuyenmuc"><%= lop.getTen() %></h1>
    <form action="/giangvien/lop/saveDiemDanh" method="post" id="formAdd">
		<div class="modal-body">
			<div class="form-group" style="margin-bottom: 10px;display: flex;align-items: center;">
				<label for="name" class="col-2">Ngày </label> 
				<div class="col-10">
					<select class="form-control" id="tkbId" name="tkbId" onchange="loadData();">
					<% 
						if(lsTKB != null && lsTKB.size() > 0){ 
							for(int i = 0 ; i < lsTKB.size(); i++){
					%>
		                 <option data-val="<%= CommonUtil.toString((Date) lsTKB.get(i)[2], "yyyy-MM-dd")%>" value="<%= lsTKB.get(i)[0] %>" ><%= CommonUtil.toString((Date) lsTKB.get(i)[2], "dd/MM/yyyy")  + " - " + Constant.getCaHoc( (int) lsTKB.get(i)[1]) %></option>
		                 <% } %>
		            <% }else{ %>
		            	<option value="">--Chọn--</option>
		            <% } %>
		            </select>
				</div>
			</div>
			<div class="form-group">
				<table class="table table-bordered table-hover table-striped">
					<thead>
						<tr style="color:#d59100">
							<th>STT </th>
							<th>Tên sinh viên </th>
							<th>ĐD</th>
							<th>KT</th>
							<th>TH</th>
						</tr>
					</thead>
					<tbody>
					<% if(lsSinhVien != null && lsSinhVien.size() > 0){
						for(int i = 0; i< lsSinhVien.size(); i++){%>
						<tr>
							<td>
								<input type="hidden" name="sinhVienId[]" value="<%=lsSinhVien.get(i)[0]%>"/>
								<%= i + 1 %>
							</td>
							<td>
								<%=lsSinhVien.get(i)[1].toString()%>
							</td>
							<td>
								<select class="form-control select" id="diemDanh<%=lsSinhVien.get(i)[0]%>" name="diemDanh[]">
					                 <option value="1" >C</option>
					                 <option value="2" >P</option>
					                 <option value="3" >K</option>
					            </select>
							</td>
							<td>
								<input type="checkbox" class="checkbox" value="0" id="kienThuc<%=lsSinhVien.get(i)[0]%>" name="<%=lsSinhVien.get(i)[0]%>_kienThuc"/>
							</td>
							<td>
								<input type="checkbox" class="checkbox" value="0" id="thucHanh<%=lsSinhVien.get(i)[0]%>" name="<%=lsSinhVien.get(i)[0]%>_thucHanh"/>
							</td>
						</tr>
						<% } %>
					<% } %>
					</tbody>
				</table>
			</div>
			<% if(check){ %>
				<div style="text-align: right;margin-top: 10px;" id="btnSave">
					<button type="submit" class="btn btn-primary">Lưu</button>
				</div>
			<% } %>
		</div>
	</form>
</div>
<script type="text/javascript">
$(document).ready(function(){
	loadData();
});
$(".checkbox").on('change', function() {
	if ($(this).is(':checked')) {
	  $(this).attr('value', 1);
	} else {
	  $(this).attr('value', 0);
	}
});
jQuery("#formAdd").validate({
	rules: {
		tkbId:{
			required: true
		}
	},
	messages: {
		tkbId: 'Trường bắt buộc không được để trống',
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/giangvien/lop/saveDiemDanh',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});

function loadData(){
	<% if(check && _user.getNhomid() == 2){%>
		checkShowButton();
	<% } %>
	var tkbId = $("#tkbId").val();
	if(tkbId != '' && tkbId != 0){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/giangvien/lop/loadDataSV",
			data : {
				'tkbId' : tkbId
			},
			dataType : "json",
			success : function(data) {
				if(data.length > 0){
					<% if(check && _user.getNhomid() == 2){%>
						$("#btnSave").html('');
					<% } %>
					for(var i = 0 ;i< data.length; i++){
			        	if(data[i].kienThuc == 1){
			        		$("#kienThuc"+data[i].sinhVienId).prop("checked",true);
			        	}else{
			        		$("#kienThuc"+data[i].sinhVienId).prop("checked",false);
			        	}
						if(data[i].thucHanh == 1){
							$("#thucHanh"+data[i].sinhVienId).prop("checked",true);        		
						}else{
							$("#thucHanh"+data[i].sinhVienId).prop("checked",false);
						}
						$("#diemDanh"+data[i].sinhVienId).val(data[i].denLop);
					}
				}else{
					$(".checkbox").prop("checked",false);
					$(".select").val(1);
				}
			},
			error : function(){
				console.log("error");
			}
		});
	}
}

function checkShowButton(){
	var date1 = new Date($("#tkbId").find(':selected').attr('data-val'));
	var date2 = new Date();
	if(date2.getDate() == date1.getDate() && date2.getMonth() == date1.getMonth() && date2.getFullYear() == date1.getFullYear()){
		$("#btnSave").html('<button type="submit" class="btn btn-primary">Lưu</button>');
	}else{
		$("#btnSave").html('');
	}
}
</script>
