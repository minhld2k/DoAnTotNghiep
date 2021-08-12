<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="java.util.List"%>
<%@page import="com.doan.totnghiep.entities.LopHoc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	List<Object[]> lsData = (List<Object[]>) request.getAttribute("LopGV");
	User _user = (User) session.getAttribute("USERLOGIN");
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
				<% if (lsData != null & lsData.size() > 0) {
					for(int i = 0; i< lsData.size(); i++){
				%>
					<li>
						<div class="number-stt">
							<span class="stt"><%= i + 1 %></span>
						</div>
						<div class="nd-dvc" style="min-height: 25px;">
							<div>
								<p><b><%=lsData.get(i)[1].toString()%></b></p>
							</div>
							<div class="btn-dvc">
							 	<div>
						 		<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "DIEMDANH")){ %>
									<button class="btn btn-xemdv" style="margin: 0 3px 3px 0px;" onclick="chonTruongHop('<%=lsData.get(i)[0].toString()%>','1');">
										Điểm danh
									</button>
								<% } %>
								<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "NHATKY")){ %>
									<button class="btn btn-xemdv" style="margin: 0 3px 3px 0px;" onclick="$('#lopId').val('<%=lsData.get(i)[0].toString()%>');viewUni('3');">
										Nhật ký
									</button>
								<% } %>
								<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "DIEMTHI")){ %>
									<button class="btn btn-xemdv" style="margin: 0 3px 3px 0px;" onclick="chonTruongHop('<%=lsData.get(i)[0].toString()%>','2');">
										Điểm thi
									</button>
								<% } %>
							 	</div>
							 </div>
						</div>
					</li>
					<% } %>
				<% } else { %>
					<div class="no-data">Không tìm thấy dữ liệu</div>
				<% } %>
				</ul>
			</div>
		</div>
	</div>
	
	<!-- Modal -->
	 <div class="div-modal">
	     <div class="background-modal"></div>
		 <div class="content-modal">
		      <a class="close-md" onclick="closeModal();"><i class="far fa-times-circle"></i></a>
			  <h1 class="tile-h1">Chọn môn</h1>
			  <input type="hidden" id="lopId" >
			  <div class="row-dm">
			     <select class="form-ip" id="monId" name="monId">
				</select>
			  </div>
			  <div class="row-dm" style="text-align: center;">
			     <button class="btn-xemdv" style="float: inherit;" type="button" name="xem" id="btnLuu">Chọn</button>
			  </div>
		 </div>
	 </div>
</div>
<div class="uni-loading" style="display: none;"></div>
<div class="uni-loading-top" style="display: none"></div>
<script type="text/javascript">
var ajaxRuning = 0;
function closeModal(){
   $('body').removeClass('open-modal');
   $("#monId").html("");
   $('#btnLuu').attr("onclick","");
}
function chonTruongHop(lopId,truongHop){
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/giangvien/lop/getMonHoc",
		data : {
			'lopId' : lopId
		},
		dataType : "json",
		success : function(data) {
			var length = 0;
			var html = "";
        	$.each(data, function(key, value) {
        		if(key == 'size'){
        			length = value;
        		}else{
					 html += "<option value=" + key + ">" + value + "</option>";
        		}
			});
        	$("#monId").html(html);
			$("#lopId").val(lopId);
			if (length == 1){
				viewUni(truongHop);
			}else{
				$('body').addClass('open-modal');
		        $('#btnLuu').attr("onclick","viewUni("+truongHop+")");
			}
		},
	});
}
function viewUni(truongHop){
	var monId = $("#monId").val();
	var lopMonId = $("#lopId").val();
	closeModal();
	var name = "Điểm danh";
	var _url = "/giangvien/lop/viewDiemDanh";
	
	if(truongHop == 2){
		name = "Điểm thi";
		_url = "/giangvien/lop/viewDiemThi";
	}
	
	if(truongHop == 3){
		name = "Nhật ký lên lớp";
		_url = "/giangvien/lop/viewNhatKy";
	}
	var _uniwindow = $.uniwindow({
		'title': name,
		'content': '<div class="uni-loading-mini"></div>'
	});
	$.ajax({
		async: true,
		url: _url,
		type: "POST",
		dataType: "html",
		data: {
			'lopId' : lopMonId,
			'monId' : monId
		},
		success: function(results) {
			$(_uniwindow).html(results);
		},
		error: function (err) {
		}
	});
}
</script>
