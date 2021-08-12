<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	long lopId = (Long) request.getAttribute("lopId");
	List<Object[]> lsMon = (List<Object[]>) request.getAttribute("lsMon");
%>
<form action="/phongdaotao/tkb/update" method="post" id="formAdd">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle">Cập nhật lịch học</h1>
	</div>
	<div class="modal-body">

		<div class="form-group">
			<input type="hidden" class="form-control" id="idAdd" name="id" value="${thoiKhoaBieu.id}">
			<input type="hidden" class="form-control" id="lopId" name="lopId" value="<%=lopId%>">
		</div>
	
		<div class="form-group">
			<label for="name" class="col-form-label">Ca học<strong>*</strong> </label> 
			<select class="form-control" id="cahoc" name="cahoc">
                 <option value="1" ${1 == thoiKhoaBieu.cahoc ? "selected":"" } >Sáng </option>
                 <option value="2" ${2 == thoiKhoaBieu.cahoc ? "selected":"" } >Chiều</option>
                 <option value="3" ${3 == thoiKhoaBieu.cahoc ? "selected":"" } >Tối</option>
            </select>
		</div>
		
		<div class="form-group">
			<label for="url" class="col-form-label">Ngày <strong>*</strong></label>
			<input type="date" class="form-control" id="ngay" name="ngay" value="<fmt:formatDate value="${thoiKhoaBieu.ngay}" pattern="yyyy-MM-dd"></fmt:formatDate>">
		</div>
		
		<div class="form-group">
			<label for="url" class="col-form-label">Môn học <strong>*</strong></label>
			<select class="form-control" id="monId" name="monId">
				<option value="0">--Chọn--</option>
                <% for(Object[] mon : lsMon){ %>
                	<c:set var="monId" value="<%=mon[0].toString() %>"></c:set>
                    <option value="<%=mon[0].toString() %>" ${monId == thoiKhoaBieu.mon.id ? "selected":"" } ><%= mon[1].toString() %></option>
                <% } %>
             </select>
		</div>
		
		<div class="form-group">
			<label for="url" class="col-form-label">Phòng học</label>
			<select class="form-control" id="phongId" name="phongId">
				<option value="0">--Chọn--</option>
                 <c:forEach var="item" items="${lsPhong}">
                     <option value="${item.id}" ${item.id == thoiKhoaBieu.phong.id ? "selected":"" } >${item.ten}</option>
                 </c:forEach>
             </select>
		</div>
		
		<div class="form-group">
			<label for="name" class="col-form-label">Lời nhắn PDT</label> 
			<textarea rows="2" class="form-control" id="moTa" name="moTa">${thoiKhoaBieu.mota}</textarea>
		</div>
		
		<div style="text-align: right;margin-top: 10px;">
			<a class="btn btn-secondary" href="/phongdaotao/tkb/list?lopId=<%=lopId%>">Quay lại</a>
			<button type="submit" class="btn btn-primary">Lưu</button>
		</div>
	</div>
</form>
<script>
jQuery("#formAdd").validate({
	rules: {
		cahoc:{
			required: true
		},
		ngay:{
			required: true
		},
		monId:{
			required: true
		}
	},
	messages: {
		cahoc: 'Trường bắt buộc không được để trống',
		ngay: 'Trường bắt buộc không được để trống',
		monId : 'Trường bắt buộc không được để trống'
	},
	errorClass		: "field-validation-error error",
	errorElement	: "div",
	submitHandler	: function(form) {
		$('#formAdd input[type="submit"]').prop('disabled', true);
		$(form).ajaxSubmit({
			url		: '/phongdaotao/tkb/update',
            type	: "post",
			dataType:	"html",
			success: function(data) {
			}
		});
	return false; 
	}
});
</script>