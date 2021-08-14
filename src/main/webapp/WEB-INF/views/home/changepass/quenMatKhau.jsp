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
	<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
</head>
<body>
	<main class="d-flex w-100">
		<div class="container d-flex flex-column">
			<div class="row vh-100">
				<div class="col-sm-10 col-md-8 col-lg-6 mx-auto d-table h-100">
					<div class="d-table-cell align-middle">

						<div class="text-center mt-4">
							<p class="lead">
								Nhập tên đăng nhập để tiếp tục
							</p>
						</div>

						<div class="card">
							<div class="card-body">
								<div class="m-sm-4">
									<form action="/quenMatKhau" method="post"> 
										<div class="mb-3">
											<label class="form-label">Tên đăng nhập</label>
											<input class="form-control form-control-lg" type="text" name="username" />
										</div>
										
										<div>
											<label class="form-label" style="color: red;"><c:out value="${ERROR_USERNAME}" /></label>
										</div>
										<div class="text-center mt-3">
										 	<button type="submit" class="btn btn-lg btn-primary">Tiếp tục</button>
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