<%@page import="com.doan.totnghiep.entities.LopHoc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	LopHoc lop = (LopHoc) request.getAttribute("LopSV");
%>
<div class="container-fluid p-0">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle">Danh sách lớp học</h1>
	</div>
	
	<div class="card">
		<div class="card-body">
			<div class="buoc-3">
				<span id="countCN" style="display: none;"></span>
				<ul id="ulCN" class="ul-cn">
					<% if (lop != null) {%>
					<li>
						<div class="number-stt">
							<span class="stt">1</span>
						</div>
						<div class="nd-dvc" style="min-height: 25px;">
							<div>
								<p><b><%=lop.getTen()%></b></p>
							</div>
							<div>
								<p>Khóa:  <%=lop.getKhoaHoc().getTen() %></p>
							</div>
							<div class="btn-dvc">
							 	<div>
									<button class="btn btn-xemdv" style="margin: 0 3px 3px 0px;" onclick="xemMonHoc('<%= lop.getId()%>')">
										Môn học
									</button>
							 	</div>
							 </div>
						</div>
					</li>
				<% } else { %>
					<div class="no-data">Không tìm thấy dữ liệu</div>
				<% } %>
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="uni-loading" style="display: none;"></div>
<div class="uni-loading-top" style="display: none"></div>
<script type="text/javascript">
var tenLop = '';
var khoaHocId = 0;
var ajaxRuning = 0;

function xemMonHoc(lopId) {
	var url = "/sinhvien/lop/xemMonHoc";
	var name = 'Danh sách môn học';
	
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
			'lopId' : lopId
		},
		success: function(results) {
			test = results;
			if (results) {
				$(_uniwindow).html(results);
			}
		},
		error: function () {
			console.log("Error");
		}
	});	
}
</script>
