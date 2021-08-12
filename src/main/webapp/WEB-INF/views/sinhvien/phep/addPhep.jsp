<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form action="/sinhvien/phep/save" method="post" id="formAdd">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle">Thêm mới phép</h1>
	</div>
	<div class="modal-body">		
		<div class="form-group">
			<label for="name" class="mr-sm-2">Thời gian <strong>*</strong>:</label>
			<div class="div-date">
                <div class="col-d-4">
                   <input type="date" name="tuNgay" id="tuNgay" class="form-ip" value="" onchange="getSoNgayNghi();" placeholder="Từ ngày">
                </div>
                <div class="col-d-2">
                   <i class="fas fa-arrow-right"></i>
                </div>
                <div class="col-d-4">
                   <input type="date" name="denNgay" id="denNgay" class="form-ip" value="" onchange="getSoNgayNghi()" placeholder="Đến ngày">
                </div>
           	</div>
		</div>
		
		<div class="form-group">
			<label for="name" class="col-form-label">Số ngày nghỉ <strong>*</strong></label> 
			<input type="number" name="soNgayNghi" id="soNgayNghi" class="form-ip" value="">
		</div>

		<div class="form-group">
			<label for="name" class="col-form-label">Lý do <strong>*</strong> </label> 
			<textarea rows="2" class="form-control" id="lyDo" name="lyDo"></textarea>
		</div>
		
		<div style="text-align: right;margin-top: 10px;">
			<a class="btn btn-secondary" href="/sinhvien/phep">Quay lại</a>
			<button type="submit" class="btn btn-primary">Lưu</button>
		</div>
	</div>
</form>
<script>
$(document).ready(function(){
	getCurentDay();
});
function getCurentDay(){
	var date = new Date();
	var d = date.getDate().toString();
    var m = (date.getMonth() + 1 ).toString();
    var tuNgay = date.getFullYear()+ '-' +(m[1]?m:"0"+m[0]) + '-' + (d[1]?d:"0"+d[0]) ;
	$("#tuNgay").val(tuNgay);
	$("#denNgay").val(tuNgay);
	getSoNgayNghi();
}
function getSoNgayNghi(){
	var tuNgay = new Date($("#tuNgay").val());
	var denNgay = new Date($("#denNgay").val());
	var millisBetween = denNgay.getTime() - tuNgay.getTime();
	var soNgay = millisBetween / (1000 * 3600 * 24);
	
	if (soNgay < 1){
		soNgay = 1;
	}else{
		soNgay = Math.round(Math.abs(soNgay)) + 1 ;
	}
	$("#soNgayNghi").val(soNgay);
}
jQuery("#formAdd").validate({
	rules: {
		lyDo:{
			required: true
		},
		soNgayNghi:{
			required: true
		},
		tuNgay:{
			required: true
		},
		denNgay:{
			required: true
		}
	},
	messages: {
		lyDo: 'Trường bắt buộc không được để trống',
		soNgayNghi: 'Trường bắt buộc không được để trống',
		tuNgay : 'Trường bắt buộc không được để trống',
		denNgay : 'Trường bắt buộc không được để trống'
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/sinhvien/phep/save',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});
</script>