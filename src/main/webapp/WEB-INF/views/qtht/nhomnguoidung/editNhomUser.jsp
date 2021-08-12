<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form action="/quantri/nhom/save" method="post" id="formAdd">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle"><%= session.getAttribute("title") %></h1>
	</div>
	<div class="modal-body">

		<div class="form-group">
			<input type="hidden" class="form-control" id="idAdd" name="id" value="${nhomNguoiDung.id}">
		</div>
	
		<div class="form-group">
			<label for="name" class="col-form-label">Tên nhóm người dùng <strong>*</strong>
			</label> <input type="text" class="form-control" id="tenNhom" name="tenNhom" value="${nhomNguoiDung.ten}">
		</div>
		
		<div class="form-group">
			<label for="url" class="col-form-label">Chức năng</label>
			<% List<ChucNang> lsCn = (List<ChucNang>) request.getAttribute("LISTCHUCNANG"); %>
			<table>
				<tbody>
					<tr>
						<td>
							<% for(ChucNang cn : lsCn){ %>
								<label class="form-check"> 
									<input class="form-check-input" id="check" type="checkbox" value="<%=cn.getId() %>" name="chucnang[]"> 
									<span class="form-check-label"><%=cn.getName() %></span>
								</label>
							<% } %>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div style="text-align: right;">
			<a class="btn btn-secondary" href="/quantri/nhom/list">Quay lại</a>
			<button type="submit" class="btn btn-primary">Lưu</button>
		</div>
	</div>
</form>
<script>
$(document).ready(function() {
	checkChucNang();
});
jQuery("#formAdd").validate({
	rules: {
		tenNhom:{
			required: true
		}
	},
	messages: {
		tenNhom: 'Trường bắt buộc không được để trống'
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/quantri/nhom/save',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});

function checkChucNang(){
	var nhomId = $("#idAdd").val();
	if(nhomId != ''){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/quantri/nhom/getChucNang",
			data : {
				'nhomId' : nhomId
			},
			dataType : "json",
			success : function(data) {
				var chucnang = getData(data.chucnangs);
				if(chucnang != null){
					for (let i = 0; i < chucnang.length; i++) {
						$('input[type="checkbox"]').each(function() {
					      	if($(this).val() == chucnang[i]){
					      		$(this).prop("checked",true);
					      	}
					    });
					}
				}
			},
		});
	}
}
</script>