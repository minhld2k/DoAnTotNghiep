<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form action="/quantri/chucnang/save" method="post" id="formAdd">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle"><%= session.getAttribute("title") %></h1>
	</div>
	<div class="modal-body">

		<div class="form-group">
			<input type="hidden" class="form-control" id="idAdd" name="id" value="${chucNang.id}">
		</div>
		<div class="form-group">
			<label for="key" class="col-form-label">Khóa <strong>*</strong>
			</label> <input type="text" class="form-control" id="key" name="key" value="${chucNang.key}">
		</div>

		<div class="form-group">
			<label for="name" class="col-form-label">Tên chức năng <strong>*</strong>
			</label> <input type="text" class="form-control" id="name" name="name" value="${chucNang.name}">
		</div>

		<div class="form-group">
			<label for="url" class="col-form-label">URL</label> <input
				type="text" class="form-control" id="urlAdd" name="url" value="${chucNang.url}">
		</div>

		<div class="form-group">
			<label for="url" class="col-form-label">Icon</label> <input
				type="text" class="form-control" id="urlAdd" name="icon" value="${chucNang.icon}">
		</div>
		
		<div class="form-group">
			<label for="url" class="col-form-label">Tên module</label>
			<select class="form-control" name="module">
				<option value="1" ${chucNang.module == 1 ? "selected":"" }>Hệ thống</option>
				<option value="2" ${chucNang.module == 2 ? "selected":"" }>Quản lý</option>
			</select>
		</div>

		<div class="form-group" style="margin: 10px 0px;">
			<label class="form-check"> 
				<input class="form-check-input" type="checkbox" id="ismenu" value="false" name="ismenu" ${chucNang.ismenu == 1 ? "checked":"" }> 
				<span class="form-check-label"> Là menu </span>
			</label>
		</div>
		
		<div style="text-align: right;">
			<a class="btn btn-secondary" href="/quantri/chucnang/list">Quay lại</a>
			<button type="submit" class="btn btn-primary">Lưu</button>
		</div>
	</div>
</form>
<script>
$("#ismenu").on('change', function() {
	if ($(this).is(':checked')) {
	  $(this).attr('value', 'true');
	} else {
	  $(this).attr('value', 'false');
	}
});
jQuery.validator.addMethod("checkTrungMa", function(value, element) {
	var id = $("#idAdd").val() == "" ? 0 : $("#idAdd").val();
	var check = false;
	$.ajax({
		url : '/quantri/chucnang/checkma',
		type: "POST",
		async: false,
		dataType : "json",
		data : {
			'chucNangId' : id,
			'khoa' : value 
		},
		success : function(data) {
			check = parseInt(data) > 0 ? false : true;
		}
	});
	return check;
	
}, "");
jQuery("#formAdd").validate({
	rules: {
		key:{
			required: true,
			checkTrungMa: true
		},
		name:{
			required: true
		}
	},
	messages: {
		key: {
			required : 'Trường bắt buộc không được để trống',
			checkTrungMa: 'Mã chức năng đã tồn tại'
		},
		name: 'Trường bắt buộc không được để trống'
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/quantri/chucnang/save',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});
</script>