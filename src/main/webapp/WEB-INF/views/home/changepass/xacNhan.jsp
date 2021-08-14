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
								Mã xác nhận đã được gửi về mail <%=email %> bạn vui lòng kiểm tra mail để lấy mã xác nhận
							</p>
						</div>

						<div class="card">
							<div class="card-body">
								<div class="m-sm-4">
									<div class="mb-3">
										<label class="form-label">Mã xác nhận</label>
										<input class="form-control form-control-lg" type="text" id="maXacNhan" />
									</div>
									
									<div>
										<label class="form-label" style="color: red;" id="error"></label>
									</div>
									<div class="text-center mt-3">
									 	<button type="button" class="btn btn-lg btn-primary" onclick="checkMa();">Tiếp tục</button>
									</div>
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
function checkMa(){
	var code = '<%=code%>';
	var maXacNhan = $("#maXacNhan").val();
	if(maXacNhan == ''){
		$("#error").html("Mã xác nhận là bắt buộc");
		return;
	}else{
		if(maXacNhan != code){
			$("#error").html("Mã xác nhận không chính xác");
			return;
		}else{
			$("#error").html("");
			location.href = "/viewChangPass";
		}
	}
}
</script>