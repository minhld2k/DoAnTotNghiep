<%@page import="com.doan.totnghiep.entities.QuanTri"%>
<%@page import="com.doan.totnghiep.entities.GiangVien"%>
<%@page import="java.util.Date"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.SinhVien"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="Responsive Admin &amp; Dashboard Template based on Bootstrap 5">
	<meta name="author" content="AdminKit">
	<meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">

	<link rel="preconnect" href="https://fonts.gstatic.com">

	<title>Ispace Đà Nẵng</title>

	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"> 
	<link href="<c:url value='/static/css/main.css' />" rel="stylesheet"> 
	
	<script src="<c:url value='/static/js/app.js' />"></script>
	<script src="<c:url value='/static/js/jquery-1.12.4.min.js' />"></script>
	<script src="<c:url value='/static/js/jquery.validate.js' />"></script>
	<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
</head>
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
	String maSV = "";
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
		maSV = sv.getMa();
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
<style>
strong {
	color: red;
}
</style>
<body>
	<main class="d-flex w-100">
		<div class="container d-flex flex-column">
			<div class="row vh-100">
				<div class="col-sm-10 col-md-8 col-lg-6 mx-auto d-table h-100">
					<div class="d-table-cell align-middle">

						<div class="text-center mt-4">
							<p class="lead">
								Vui lòng cập nhật thông tin cá nhân và mật khẩu của bạn để tiếp tục
							</p>
						</div>

						<div class="card">
							<div class="card-body">
								<div class="m-sm-4">
									<form action="/updateUserProfile" method="post" id="formAdd">
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
													<label for="name" class="col-form-label">Mã sinh viên</label> 
													<input type="text" class="form-control" readonly="readonly" id="maSV" name="maSV" value="<%=maSV%>" />
												</div>
												
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
												<label for="name" class="col-form-label">Mật khẩu <strong>*</strong></label> 
												<input type="password" class="form-control" id="password" name="password" value="" />
											</div>
											
											<div class="form-group">
												<label for="name" class="col-form-label">Xác nhận mật khẩu <strong>*</strong></label> 
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

					</div>
				</div>
			</div>
		</div>
	</main>
</body>
<script>
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
		password :{
			required: true,
		},
		rePass :{
			equalTo: "#password",
			required: true,
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
		password : 'Trường bắt buộc không được để trống',
		rePass : {
			required: "Trường bắt buộc không được để trống",
			equalTo: "Xác nhận mật khẩu không chính xác"
		}
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/updateUserProfile',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});
</script>