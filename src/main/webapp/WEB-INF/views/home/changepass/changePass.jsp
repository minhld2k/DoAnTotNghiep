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
	<script src="<c:url value='/static/js/app.js' />"></script>
	<script src="<c:url value='/static/js/jquery-1.12.4.min.js' />"></script>
	<script src="<c:url value='/static/js/jquery.validate.js' />"></script>
	<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
</head>
<%
	String code = (String) session.getAttribute("Code");
	String email = (String) session.getAttribute("EMAIL");
	email = email.substring(0, 3) + "*****" + email.substring(email.length() - 11);
%>
<body>
	<main class="d-flex w-100">
		<div class="container d-flex flex-column">
			<div class="row vh-100">
				<div class="col-sm-10 col-md-8 col-lg-6 mx-auto d-table h-100">
					<div class="d-table-cell align-middle">

						<div class="text-center mt-4">
							<p class="lead">
								Cập nhật mật khẩu
							</p>
						</div>

						<div class="card">
							<div class="card-body">
								<div class="m-sm-4">
									<form action="/changePass" method="post" id="formAdd"> 
										<div class="mb-3">
											<label class="form-label">Mật khẩu <strong style="color: red;">*</strong></label> 
											<input type="password" class="form-control form-control-lg" id="passwordNew" name="passwordNew" value="" />
										</div>
										
										<div class="mb-3">
											<label class="form-label">Xác nhận mật khẩu <strong style="color: red;">*</strong></label> 
											<input type="password" class="form-control form-control-lg" id="rePassNew" name="rePassNew" value="" />
										</div>
										<div class="text-center mt-3">
										 	<button type="submit" class="btn btn-lg btn-primary">Lưu</button>
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
		passwordNew :{
			required: true,
		},
		rePassNew:{
			equalTo: "#passwordNew",
			required: true
		}
		
	},
	messages: {
		passwordNew : 'Trường bắt buộc không được để trống',
		rePassNew : {
			required: "Trường bắt buộc không được để trống",
			equalTo: "Xác nhận mật khẩu không chính xác"
		}
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/changePass',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});
</script>