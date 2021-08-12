<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description"
	content="Responsive Admin &amp; Dashboard Template based on Bootstrap 5">
<meta name="author" content="AdminKit">
<meta name="keywords"
	content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">

<link rel="preconnect" href="https://fonts.gstatic.com">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" />

<title><tiles:getAsString name="title" /></title>

<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"> 
<link href="<c:url value='/static/css/main.css' />" rel="stylesheet"> 
<link href="<c:url value='/static/css/mainMobile.css' />" rel="stylesheet"> 
<link href="<c:url value='/static/css/jquery.confirm.css' />" rel="stylesheet"> 
<link href="<c:url value='/static/css/select.css' />" rel="stylesheet">

<script src="<c:url value='/static/js/app.js' />"></script>
<script src="<c:url value='/static/js/jquery-1.12.4.min.js' />"></script>
<script src="<c:url value='/static/js/main.js' />"></script>
<script src="<c:url value='/static/js/jquery.confirm.js' />"></script>
<script src="<c:url value='/static/js/jquery.validate.js' />"></script>
<script src="<c:url value='/static/js/selectize.min.js' />" ></script>
</head>

<body>
	<div class="wrapper">
		<nav id="sidebar" class="sidebar js-sidebar">
			<tiles:insertAttribute name="nav-left" />
		</nav>
		<div class="main">
			<nav class="navbar navbar-expand navbar-light navbar-bg">
				<tiles:insertAttribute name="nav-top" />
			</nav>
			<main class="content-body">
				<div id="content" class="content">
					<tiles:insertAttribute name="body" />
				</div>
			</main>
			<footer class="footer">
				<tiles:insertAttribute name="footer" />
			</footer>
		</div>
	</div>

</body>
</html>