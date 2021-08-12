<%@page import="com.doan.totnghiep.dto.ThoiKhoaBieuDTO"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="java.util.Date"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.LopHoc"%>
<%@page import="com.doan.totnghiep.util.Constant"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	List<ThoiKhoaBieuDTO> lsTKB = (List<ThoiKhoaBieuDTO>) request.getAttribute("lsTKB");
	LopHoc lop = (LopHoc) request.getAttribute("Lop");
	User _user = (User) session.getAttribute("USERLOGIN");
	boolean check = CommonUtil.checkQuyenByKhoa(_user.getId(), "SAVE_NHATKY");
%>
<style>
.table > thead > tr > th{
	padding: 5px;
}
.table > tbody > tr > td{
	padding: 5px;
}
</style>
<div class="trang-chitiet">
	<h1 class="tieude-chuyenmuc"><%= lop.getTen() %></h1>
    <form action="/giangvien/lop/saveNhatKy" method="post" id="formAdd">
		<div class="modal-body">
			<div class="viewForm box">
				<div class="row">
					<div class="col-lg-3" >
						<label for="name" class="mr-sm-2">Ngày học <strong>*</strong></label>
						<select class="form-control" id="tkbId" name="tkbId" onchange="loadData();">
						<% 
							if(lsTKB != null && lsTKB.size() > 0){ 
								for(int i = 0 ; i < lsTKB.size(); i++){
									ThoiKhoaBieuDTO tkb = lsTKB.get(i);
						%>
			                 <option data-val="<%= CommonUtil.toString(tkb.getNgay(), "yyyy-MM-dd")%>" value="<%= tkb.getId() %>" >
			                 	<%= CommonUtil.toString(tkb.getNgay(), "dd/MM/yyyy")  + " - " + Constant.getCaHoc(tkb.getCahoc()) +" - " + tkb.getTenmon()%>
			                 </option>
			                 <% } %>
			            <% }else{ %>
			            	<option value="">--Chọn--</option>
			            <% } %>
			            </select>
					</div>
					<div class="col-lg-6" >
						<div style="display: flex;">
							<div class="col-d-6">
								<label for="name" class="mr-sm-2">Buổi thứ <strong>*</strong></label>
								<input type="number" name="buoiThu" id="buoiThu" class="form-control" value=""/>
							</div>
							<div class="col-c-2"></div>
							<div class="col-d-6">
								<label for="name" class="mr-sm-2">Sổ work/task <strong>*</strong></label>
								<select class="form-control" id="wtId" name="wtId">
					            	<option value="">--Chọn--</option>
					            </select>
							</div>
						</div>
					</div>
					<div class="col-lg-3" >
						<label for="name" class="mr-sm-2">Lời nhắn P.Đào tạo (<span style="font-weight: normal;">GV ghi lại TÊN xe thực hành-NẾU CÓ</span>)</label>
						<textarea rows="1" class="form-control" name="loiNhanPDT" id="loiNhanPDT" ></textarea>
					</div>
					<hr>
					<div class="col-lg-12" >
						<div style="display: flex;">
							<div class="col-d-6">
								<label for="name" class="mr-sm-2">Địa điểm học <strong>*</strong></label>
								<select class="form-control" id="coSoId" name="coSoId" onchange="changPhong();">
					            	<option value="">--Chọn--</option>
					            	<c:forEach var="item" items="${lsCoSo}">
		                                <option value="${item.id}" >${item.ten}</option>
		                            </c:forEach>
					            </select>
							</div>
							<div class="col-c-2"></div>
							<div class="col-d-6">
								<label for="name" class="mr-sm-2">Phòng học <strong>*</strong></label>
								<select class="form-control" id="phongHocId" name="phongHocId">
					            	<option value="">--Chọn--</option>
					            </select>
							</div>
						</div>
					</div>
					<hr>
					<div class="col-lg-6" >
						<div style="display: flex;">
							<div class="col-d-6">
								<label for="name" class="mr-sm-2">Giờ vào <strong>*</strong></label>
								<input type="time" name="gioVao" id="gioVao" class="form-control" value=""/>
							</div>
							<div class="col-c-2"></div>
							<div class="col-d-6">
								<label for="name" class="mr-sm-2">Giờ bắt đầu <strong>*</strong></label>
								<input type="time" name="gioBatDau" id="gioBatDau" class="form-control" value=""/>
							</div>
						</div>
					</div>
					<div class="col-lg-6" >
						<div style="display: flex;">
							<div class="col-d-6">
								<label for="name" class="mr-sm-2">Đánh giá <strong>*</strong></label>
								<select class="form-control" id="danhGiaVao" name="danhGiaVao">
					            	<option value="">--Chọn--</option>
					            	<option value="1">Đúng</option>
					            	<option value="2">Trể</option>
					            </select>
							</div>
							<div class="col-c-2"></div>
							<div class="col-d-6">
								<label for="name" class="mr-sm-2">Lý do</label>
								<select class="form-control" id="lyDoVao" name="lyDoVao">
					            	<option value="">--Chọn--</option>
					            	<option value="1">SV đến lớp trễ</option>
					            	<option value="2">GV dến lớp trễ</option>
					            	<option value="3">Chuẩn bị trễ</option>
					            </select>
							</div>
						</div>
					</div>
					<hr>
					<div class="col-lg-4" >
						<label for="name" class="mr-sm-2">Giờ ra <strong>*</strong></label>
						<input type="time" name="gioRa" id="gioRa" class="form-control" value=""/>
					</div>
					<div class="col-lg-8" >
						<div style="display: flex;">
							<div class="col-d-6">
								<label for="name" class="mr-sm-2">Đánh giá <strong>*</strong></label>
								<select class="form-control" id="danhGiaRa" name="danhGiaRa">
					            	<option value="">--Chọn--</option>
					            	<option value="1">Sớm</option>
					            	<option value="2">Đúng</option>
					            	<option value="3">Trể</option>
					            </select>
							</div>
							<div class="col-c-2"></div>
							<div class="col-d-6">
								<label for="name" class="mr-sm-2">Lý do</label>
								<select class="form-control" id="lyDoRa" name="lyDoRa">
					            	<option value="">--Chọn--</option>
					            	<option value="1">Xong bài</option>
					            	<option value="2">Lớp đề nghị</option>
					            	<option value="3">Khác</option>
					            </select>
							</div>
						</div>
					</div>
					<hr>
					<div class="col-lg-4" >
						<label for="name" class="mr-sm-2">Sĩ số <strong>*</strong></label>
						<input type="number" name="siSo" id="siSo" class="form-control" value=""/>
					</div>
					<div class="col-lg-4" >
						<label for="name" class="mr-sm-2">Thực hiện tốt hoặc hiểu bài <strong>*</strong></label>
						<input type="number" name="hieuBai" id="hieuBai" class="form-control" value="" onchange="changKoHieu();"/>
					</div>
					<div class="col-lg-4" >
						<label for="name" class="mr-sm-2">Không làm được/không hiểu <strong>*</strong></label>
						<input type="number" name="koHieu" id="koHieu" class="form-control" value="" onchange="changHieuBai();"/>
					</div>
					<hr>
					<div class="col-lg-4" >
						<label for="name" class="mr-sm-2">Đánh giá của GV</label>
						<textarea rows="2" class="form-control" name="danhGiaGV" id="danhGiaGV" ></textarea>
					</div>
					<div class="col-lg-4" >
						<label for="name" class="mr-sm-2">Lời nhắn của GV</label>
						<textarea rows="2" class="form-control" name="loiNhanGV" id="loiNhanGV" ></textarea>
					</div>
					<div class="col-lg-4" >
						<label for="name" class="mr-sm-2">Ghi chú</label>
						<textarea rows="2" class="form-control" name="ghiChu" id="ghiChu" ></textarea>
					</div>
				</div>
			<% if(check){ %>
				<div style="display: flow-root;" id = "btnSave">
					<button type="submit" class="btn btn-primary" style="margin-top: 10px; margin-bottom: 10px; float: right;">Lưu</button>
				</div>
			<% } %>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
$(document).ready(function(){
	loadData();
});
jQuery("#formAdd").validate({
	rules: {
		tkbId:{
			required: true
		},
		koHieu:{
			required: true
		},
		hieuBai:{
			required: true
		},
		siSo:{
			required: true
		},
		danhGiaRa:{
			required: true
		},
		buoiThu:{
			required: true
		},
		wtId:{
			required: true
		},
		coSoId:{
			required: true
		},
		phongHocId:{
			required: true
		},
		gioVao:{
			required: true
		},
		gioBatDau:{
			required: true
		},
		danhGiaVao:{
			required: true
		},
		gioRa:{
			required: true
		}
	},
	messages: {
		tkbId: 'Trường bắt buộc không được để trống',
		koHieu: 'Trường bắt buộc không được để trống',
		hieuBai: 'Trường bắt buộc không được để trống',
		siSo: 'Trường bắt buộc không được để trống',
		danhGiaRa: 'Trường bắt buộc không được để trống',
		buoiThu: 'Trường bắt buộc không được để trống',
		wtId: 'Trường bắt buộc không được để trống',
		coSoId: 'Trường bắt buộc không được để trống',
		phongHocId: 'Trường bắt buộc không được để trống',
		gioVao: 'Trường bắt buộc không được để trống',
		gioBatDau: 'Trường bắt buộc không được để trống',
		danhGiaVao: 'Trường bắt buộc không được để trống',
		gioRa: 'Trường bắt buộc không được để trống',
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/giangvien/lop/saveNhatKy',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});
function loadData(){
	<% if(check && _user.getNhomid() == 2){%>
		checkShowButton();
	<% } %>
	var tkbId = $("#tkbId").val();
	if(tkbId != '' && tkbId != 0){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/giangvien/lop/loadDataNhatKy",
			data : {
				'tkbId' : tkbId
			},
			dataType : "json",
			success : function(data) {
				<% if(check && _user.getNhomid() == 2){%>
					$("#btnSave").html('');
				<% } %>
				setDefault();
				if(data.lsWT.length > 0){
					var html = '<option value="">--Chọn--</option>';
					for(var i = 0 ;i< data.lsWT.length; i++){
			        	html += '<option value="'+data.lsWT[i].id+'">'+data.lsWT[i].ten+'</option>';
					}
					$("#wtId").html(html);
				}else{
					$("#wtId").html('<option value="">--Chọn--</option>');
				}
				if(data.Phong != null){
					$("#coSoId").val(data.Phong.cosoid);
					changPhong();
					setTimeout(function() {
						$("#phongHocId").val(data.Phong.id);
					}, 50);
				}else{
					$("#coSoId").val("");
					$("#phongHocId").val("");
				}
				$("#buoiThu").val(data.buoiThu);
				$("#gioVao").val(data.lsGio.gioVao);
				$("#gioRa").val(data.lsGio.gioRa);
				$("#gioBatDau").val(data.lsGio.gioBatDau);
				$("#siSo").val(data.siSo);
				if(data.nhatKy != null){
					var nk = data.nhatKy;
					$("#coSoId").val(data.coSoId);
					changPhong();
					$("#buoiThu").val(nk.buoiThu);
					$("#gioVao").val(nk.gioVao);
					$("#gioRa").val(nk.gioRa);
					$("#gioBatDau").val(nk.gioBatDau);
					$("#danhGiaRa").val(nk.danhGiaRa);
					$("#danhGiaVao").val(nk.danhGiaVao);
					$("#danhGiaGV").val(nk.danhGiaGv);
					$("#ghiChu").val(nk.ghiChu);
					$("#hieuBai").val(nk.hieuBai);
					$("#koHieu").val(nk.kohieu);
					$("#loiNhanGV").val(nk.loiNhanGv);
					$("#loiNhanPDT").val(nk.loiNhanPdt);
					$("#lyDoRa").val(nk.lyDoRa);
					$("#lyDoVao").val(nk.lyDoVao);
					$("#siSo").val(nk.siSo);
					setTimeout(function() {
						$("#phongHocId").val(nk.phongId);
						$("#wtId").val()
					}, 50);
				}
			},
			error : function(){
				console.log("error");
			}
		});
	}
}

function checkShowButton(){
	var date1 = new Date($("#tkbId").find(':selected').attr('data-val'));
	var date2 = new Date();
	if(date2.getDate() == date1.getDate() && date2.getMonth() == date1.getMonth() && date2.getFullYear() == date1.getFullYear()){
		$("#btnSave").html('<button type="submit" class="btn btn-primary" style="margin-top: 10px; margin-bottom: 10px; float: right;">Lưu</button>');
	}else{
		$("#btnSave").html('');
	}
}

function changPhong(){
	var coSoId = $("#coSoId").val();
	if(coSoId != '' && coSoId != 0){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/giangvien/getPhongHoc",
			data : {
				'coSoId' : coSoId
			},
			dataType : "json",
			success : function(data) {
				if(data.length > 0){
					var html = '<option value="">--Chọn--</option>';
					for(var i = 0 ;i< data.length; i++){
			        	html += '<option value="'+data[i].id+'">'+data[i].ten+'</option>';
					}
					$("#phongHocId").html(html);
				}else{
					$("#phongHocId").html('<option value="">--Chọn--</option>');
				}
			},
			error : function(){
				console.log("error");
			}
		});
	}else{
		$("#phongHocId").html('<option value="">--Chọn--</option>');
	}
}
function setDefault(){
	$("#buoiThu").val('');
	$("#gioVao").val('');
	$("#gioRa").val('');
	$("#gioBatDau").val('');
	$("#danhGiaRa").val('');
	$("#danhGiaVao").val('');
	$("#danhGiaGV").val('');
	$("#ghiChu").val('');
	$("#hieuBai").val('');
	$("#koHieu").val('');
	$("#loiNhanGV").val('');
	$("#loiNhanPDT").val('');
	$("#lyDoRa").val('');
	$("#lyDoVao").val('');
	$("#siSo").val('');
}

function changHieuBai(){
	$("#hieuBai").val($("#siSo").val() - $("#koHieu").val());
}

function changKoHieu(){
	$("#koHieu").val($("#siSo").val() - $("#hieuBai").val());
}
</script>
