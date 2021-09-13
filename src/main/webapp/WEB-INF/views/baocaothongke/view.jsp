<%@page import="com.doan.totnghiep.entities.LopHoc"%>
<%@page import="java.util.List"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	List<LopHoc> lsLop = (List<LopHoc>) request.getAttribute("listLopHoc");
	User _user = (User) session.getAttribute("USERLOGIN");
%>
<div class="container-fluid p-0">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle">Báo cáo thống kê</h1>
	</div>
	
	<div class="card">
		<div class="card-body">
			<div class="box">
				<div class="row">
					<div class="col-lg-4" >
						<label for="name" class="mr-sm-2">Mã sinh viên</label> 
						<input type="text" name="maSV" id="maSV" class="form-control" value=""/>
					</div>
					
					<div class="col-lg-4" >
						<label for="name" class="mr-sm-2">Lớp</label> 
						<select name="lopId" id="lopId" class="form-control">
							<option value=""> -- Chọn -- </option>
							<% for(int i= 0 ; i < lsLop.size(); i ++){ %>
								<option value="<%=lsLop.get(i).getId()%>"><%=lsLop.get(i).getTen() %></option>
							<% } %>
						</select>
					</div>
					
					<div class="col-lg-4" >
						<label for="name" class="mr-sm-2">Học kỳ</label>
						<select name="hocKy" id="hocKy" class="form-control">
							<option value=""> -- Chọn -- </option>
							<% for(int i = 1 ; i <= 7; i ++){ %>
								<option value="<%=i%>">Học kỳ <%=i %> </option>
							<% } %>
							<option value="11">Cuối khóa</option>
						</select>
					</div>
				</div>
				<div style="display: flow-root;">
					<button type="button" onclick="btnsearch();" class="btn btn-primary"
						style="margin-top: 10px; margin-bottom: 10px; float: right;">
						<span class="glyphicon glyphicon-search"></span> Xem
					</button>
				</div>
			</div>
			
			<div class="buoc-3" id="ulCN">
			</div>
		</div>
	</div>
</div>
<div class="uni-loading" style="display: none;"></div>
<div class="uni-loading-top" style="display: none"></div>
<a href="javascript:void(0)" id="loadMore"  ></a>
<script type="text/javascript">
var lopId = 0;
var maSV = '';
var hocKy = 0;
var ajaxRuning = 0;

$(document).ready(function(){
	loadMore(lopId, maSV, hocKy);
});
     
function loadMore(lopId, maSV, hocKy) {
	ajaxRuning = 1;
	$.ajax({
		async: true,
		url: "/baocao/loadData",
		type: "GET",
		dataType: "html",
		data: {
			'lopId' : lopId,			
			'maSV' : maSV,
			'hocKy' : hocKy
		},
		beforeSend: function() {
			 $(".uni-loading").show();
		},
		complete: function () {
			$(".uni-loading").hide();
			ajaxRuning = 0;
		},
		success: function(results) {
			test = results;
			if (results) {
				$("#ulCN").append(test);
			}
		},
		error: function () {
			console.log("Error");
		}
	});	
}

function btnsearch(){
	if (ajaxRuning == 0) {
		lopId = $('#lopId').val();
		maSV =  $('#maSV').val();
		hocKy =  $('#hocKy').val();
		if(lopId == '' && maSV == ''){
			$.confirm({
		        'title': "",
		        'message': "Vui lòng nhập mã sinh viên hoặc chọn lớp để xem báo cáo",
		        'buttons': {
		            'Đóng': {
		                'class': 'blue',
		                'action': function() {
		                }
		            }
		        }
		    });
			return;
		}else if(hocKy == ''){
			$.confirm({
		        'title': "",
		        'message': "Vui lòng chọn học kỳ để xem báo cáo",
		        'buttons': {
		            'Đóng': {
		                'class': 'blue',
		                'action': function() {
		                }
		            }
		        }
		    });
			return;
		}else{
			$("#ulCN").html("");
			loadMore(lopId, maSV, hocKy);
		}
	}
}

</script>
