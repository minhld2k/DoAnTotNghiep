<%@page import="com.doan.totnghiep.entities.QuanTri"%>
<%@page import="com.doan.totnghiep.entities.GiangVien"%>
<%@page import="java.util.Date"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.SinhVien"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	User u = (User) session.getAttribute("USERLOGIN");
	String email = u.getEmail();
	String hoTen = "";
	String soDienThoai = "";
	int gioiTinh = 0;
	String diaChi = "";
	Date ngaySinh = null;
	long lopId = 0;
	String soDienThoaiGiaDinh = "";
	String moTa = "";
	String tenLop = "";
	if(u.getNhomid() == 1){
		SinhVien sv = CommonUtil.getSinhVienByUserId(u.getId());
		hoTen = sv.getHoTen();
		soDienThoai = sv.getSoDienThoaiCaNhan();
		gioiTinh = sv.getGioiTinh();
		diaChi = sv.getDiaChi();
		ngaySinh = sv.getNgaySinh();
		lopId = sv.getLop().getId();
		tenLop = sv.getLop().getTen();
		soDienThoaiGiaDinh = sv.getSoDienThoaiGiaDinh();
		moTa = sv.getMoTa();
	}else if(u.getNhomid() == 2){
		GiangVien gv = CommonUtil.getGiangVienByUserId(u.getId());
		hoTen = gv.getHoTen();
		soDienThoai = gv.getSoDienThoai();
		gioiTinh = gv.getGioiTinh();
		diaChi = gv.getDiaChi();
		moTa = gv.getMoTa();
	}else if(u.getNhomid() == 3){
		QuanTri qt = CommonUtil.getPDTByUserId(u.getId());
		hoTen = qt.getHoTen();
		soDienThoai = qt.getSoDienThoai();
		gioiTinh = qt.getGioiTinh();
		diaChi = qt.getDiaChi();
	}
	String birthDay = "";
	if(ngaySinh != null){
		birthDay = CommonUtil.toString(ngaySinh, "yyyy-MM-dd");
	}
%>
<div class="card">
	<div class="card-body">
		<div class="m-sm-4">
			<form action="/saveUserProfile" method="post" id="formAdd">
				<div class="modal-body">
					<div class="form-group">
						<label for="name" class="col-form-label">Họ tên <strong>*</strong> </label> 
						<input type="text" class="form-control" id="hoTen" name="hoTen" value="<%= hoTen %>" />
					</div>
					
					<div class="form-group">
						<label for="name" class="col-form-label">Email <strong>*</strong></label> 
						<input type="text" class="form-control" id="email" name="email" value="<%= email %>" />
					</div>
					
					<div class="form-group">
						<label for="name" class="col-form-label">Số điện thoại <strong>*</strong></label> 
						<input type="text" class="form-control" id="phone" name="phone" value="<%= soDienThoai %>" />
					</div>
					
					<div class="form-group">
						<label for="name" class="col-form-label">Địa chỉ <strong>*</strong></label> 
						<textarea rows="2" class="form-control" id="diaChi" name="diaChi"><%=diaChi %></textarea>
					</div>
					
					<div class="form-group">
						<label for="name" class="col-form-label">Giới tính <strong>*</strong></label> 
						<div>
							<label class="form-check form-check-inline">
			            		<input class="form-check-input" type="radio" name="gioiTinh" <%= gioiTinh == 0 ?"checked":"" %> value="0">Nữ
			          		</label>
							<label class="form-check form-check-inline">
				            	<input class="form-check-input" type="radio" name="gioiTinh" <%= gioiTinh == 1 ?"checked":"" %> value="1">Nam
				            </label>
				            <label class="form-check form-check-inline">
				            	<input class="form-check-input" type="radio" name="gioiTinh" <%= gioiTinh == 2 ?"checked":"" %> value="2">Khác
				            </label>
						</div>
					</div>
					
					<% if(u.getNhomid() == 1){%>
						<div class="form-group">
							<label for="name" class="col-form-label">Ngày sinh <strong>*</strong></label> 
							<input type="date" class="form-control" id="ngaySinh" name="ngaySinh" value="<%= birthDay %>" />
						</div>
						
						<div class="form-group">
							<label for="url" class="col-form-label">Lớp </label>
							<select class="form-control" id="lopId" name="lopId" disabled="disabled"> 
								 <option value="<%=lopId%>"><%= tenLop %></option>
				             </select>
						</div>
						
						<div class="form-group">
							<label for="name" class="col-form-label">Số điện thoại gia đình <strong>*</strong></label> 
							<input type="text" class="form-control" id="phoneFamily" name="phoneFamily" value="<%=soDienThoaiGiaDinh%>" />
						</div>
						
						<div class="form-group">
							<label for="name" class="col-form-label">Giới thiệu</label> 
							<textarea rows="2" class="form-control" id="moTa" name="moTa"><%= moTa %></textarea>
						</div>
					<% }else if(u.getNhomid() == 2){ %>
						<div class="form-group">
							<label for="name" class="col-form-label">Giới thiệu</label> 
							<textarea rows="2" class="form-control" id="moTa" name="moTa"><%= moTa %></textarea>
						</div>
					<% } %>
					
					<div class="form-group">
						<label for="name" class="col-form-label">Mật khẩu cũ</label> 
						<input type="password" class="form-control" id="passwordOld" name="passwordOld" value="" />
					</div>
					
					<div class="form-group">
						<label for="name" class="col-form-label">Mật khẩu mới</label> 
						<input type="password" class="form-control" id="passwordNew" name="passwordNew" value="" />
					</div>
					
					<div class="form-group">
						<label for="name" class="col-form-label">Xác nhận mật khẩu</label> 
						<input type="password" class="form-control" id="rePass" name="rePass" value="" />
					</div>
					
					<div style="text-align: right;margin-top: 10px;">
						<button type="submit" class="btn btn-primary">Lưu</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<script>
$.validator.addMethod("checkPass", function (value, element) {
	var check = false;
    var passOld = $("#passwordOld").val();
    if((passOld != '' && passOld != value) || (passOld == '')){
    	check = true;
    }
    return check;
}, "Mật khẩu mới phải khác mật khẩu cũ");
$.validator.addMethod("checkRequied", function (value, element) {
	var check = true;
    var passOld = $("#passwordOld").val();
    if((passOld != '' && '' == value)){
    	check = false;
    }
    return check;
}, "Nhập mật khẩu mới");
$.validator.addMethod("check", function (value, element) {
	var check = false;
	$.ajax({
		url : '/checkPass',
		type: "POST",
		async: false,
		dataType : "json",
		data : {
			'password' : value 
		},
		success : function(data) {
			check = parseInt(data) > 0 ? true : false;
		}
	});
	return check;	
}, "Mật khẩu không chính xác");
jQuery("#formAdd").validate({
	rules: {
		hoTen:{
			required: true
		},
		email:{
			required: true,
			email : true
		},
		phone :{
			required: true,
			number: true
		},
		diaChi :{
			required: true,
		},
		gioiTinh :{
			required: true,
		},
		ngaySinh :{
			required: true,
		},
		phoneFamily :{
			required: true,
			number: true
		},
		passwordOld :{
			check : true
		},
		passwordNew :{
			checkRequied : true,
			checkPass : true
		},
		rePass :{
			equalTo: "#passwordNew",
		},
		
	},
	messages: {
		hoTen: 'Trường bắt buộc không được để trống',
		email:{
			required: 'Trường bắt buộc không được để trống',
			email : 'Vui lòng nhập đúng định dạng email'
		},
		phone :{
			required: 'Trường bắt buộc không được để trống',
			number: 'Vui lòng nhập kiểu số'
		},
		diaChi : 'Trường bắt buộc không được để trống',
		gioiTinh : 'Trường bắt buộc không được để trống',
		ngaySinh : 'Trường bắt buộc không được để trống',
		phoneFamily :{
			required: 'Trường bắt buộc không được để trống',
			number: 'Vui lòng nhập kiểu số'
		},
		rePass : {
			equalTo: "Xác nhận mật khẩu không chính xác"
		}
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/saveUserProfile',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});
</script>