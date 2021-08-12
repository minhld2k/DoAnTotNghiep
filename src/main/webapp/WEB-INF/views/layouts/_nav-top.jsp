<%@page import="com.doan.totnghiep.entities.ThongBao"%>
<%@page import="java.util.Date"%>
<%@page import="com.doan.totnghiep.dto.ThongBaoDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<a class="sidebar-toggle js-sidebar-toggle"> <i
	class="hamburger align-self-center"></i>
</a>
<%
	User _u = (User) session.getAttribute("USERLOGIN");
%>
<div class="navbar-collapse collapse">
	<ul class="navbar-nav navbar-align">
		<li class="nav-item dropdown">
		<a
			class="nav-icon dropdown-toggle" onclick="loadThongBaoHome()" id="alertsDropdown"
			data-bs-toggle="dropdown">
				<div class="position-relative">
					<i class="align-middle" data-feather="bell"></i> <span
						class="indicator" id="countNoti"></span>
				</div>
		</a>
			<div class="dropdown-menu dropdown-menu-lg dropdown-menu-end py-0"
				aria-labelledby="alertsDropdown" id="mainThongBao">
			</div>
		</li>
	
		<li class="nav-item dropdown">
			<a class="nav-icon dropdown-toggle d-inline-block d-sm-none" href="#" data-bs-toggle="dropdown">
				<i class="align-middle" data-feather="settings"></i>
			</a> 
			<a class="nav-link dropdown-toggle d-none d-sm-inline-block" href="#" data-bs-toggle="dropdown">
				<span class="text-dark"><%= CommonUtil.getTenNguoiDungByUserId(_u.getId(), _u.getNhomid()) %></span>
			</a>
			<div class="dropdown-menu dropdown-menu-end">
				<a class="dropdown-item" href="/viewProfile">
					<i class="align-middle me-1" data-feather="user"></i> Trang cá nhân
				</a> 
				<a class="dropdown-item" href="/logout"><i class="align-middle me-2" data-feather="log-out"></i>Đăng xuất</a>				
			</div>
		</li>
	</ul>
</div>
<script>
$(document).ready(function(){
	loadThongBaoHome();
});
function loadThongBaoHome(){
	var url = "/thongbao/loadHome";
	$.ajax({
		async: true,
		url: url,
		type: "GET",
		dataType: "html",
		data: {},
		success: function(results) {
			test = results;
			if (results) {
				$("#mainThongBao").html(results);
			}
		},
		error: function () {
			console.log("Error");
		}
	});	
}
function loadChiTietThongBao(loaiDS,thongBaoId,isHome) {
	var url = "/thongbao/xemChiTiet";
	var name = 'Chi tiết thông báo';
	
	var _uniwindow = $.uniwindow({
		'title': name,
		'content': '<div class="uni-loading-mini"></div>'
	});
	$.ajax({
		async: true,
		url: url,
		type: "GET",
		dataType: "html",
		data: {
			'loaiDS' : loaiDS,
			'thongBaoId' : thongBaoId
		},
		success: function(results) {
			test = results;
			if (results) {
				if(loaiDS == 2){
					if(isHome == 0){
						var element = document.getElementById('sent'+thongBaoId);
						element.style.removeProperty("font-weight");
					}
					$("#countNoti").html($("#countNoti").html() - 1);
				}
				$(_uniwindow).html(results);
			}
		},
		error: function () {
			console.log("Error");
		}
	});	
}
</script>