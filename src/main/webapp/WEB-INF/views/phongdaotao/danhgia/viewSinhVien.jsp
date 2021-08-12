<%@page import="com.doan.totnghiep.entities.DanhGia"%>
<%@page import="com.doan.totnghiep.entities.WorkTaskDetail"%>
<%@page import="com.doan.totnghiep.entities.WorkTask"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="java.util.Date"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.LopHoc"%>
<%@page import="com.doan.totnghiep.util.Constant"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	List<WorkTask> lsData = (List<WorkTask>) request.getAttribute("lsWTSV");
	List<DanhGia> lsDanhGia = (List<DanhGia>) request.getAttribute("lsDGSV");
	long monId = (long) request.getAttribute("monId");
%>
<style>
.table > thead > tr > th{
	padding: 5px;
}
.table > tbody > tr > td{
	padding: 5px;
}
</style>
<script src="<c:url value='/static/js/form.js' />"></script>
<div class="trang-chitiet">
	<h1 class="tieude-chuyenmuc">Tự đánh giá</h1>
	
    <form method="POST" id="frmUpLoad" name="frmUpLoad" action="javascript:void(0)" onSubmit="saveDanhSach($(this))">
		<div class="modal-body">
			<div class="form-group">
				<label for="name" class="col-form-label">Phần 1: Đánh giá tổng quát</label> 
				<table class="table table-bordered table-hover table-striped">
					<thead>
						<tr style="color:#d59100">
							<th>STT</th>
							<th>Work/Task</th>
							<th>Kết quả</th>
						</tr>
					</thead>
					<tbody>
					<% if(lsData != null && lsData.size() > 0){
						for(int i = 0; i< lsData.size(); i++){%>
						<tr>
							<td><%=i+1 %></td>
							<td>
								<%= lsData.get(i).getTen() %>
							</td>
							<td>
								<select class="form-control select" id="ketQua<%=lsData.get(i).getId() %>" name="ketQua[]">
									<option value = "0">Chọn</option>
					                <option value="1" >Làm tốt</option>
					                <option value="2" >Làm được</option>
					                <option value="3" >Chưa được</option>
					            </select>
					            <input type="hidden" name="wtId[]" value="<%=lsData.get(i).getId()%>"/>
							</td>
						</tr>
						<% } %>
					<% } %>
					</tbody>
				</table>
			</div>
			<div class="form-group" style="margin-bottom: 10px;">
				<label for="name" class="col-form-label">Phần 2: Tự đánh giá</label>
			</div>
			<% 
				if(lsDanhGia != null && lsDanhGia.size() > 0){ 
				for(int i = 0; i<lsDanhGia.size();i++){
			%>
			<div class="form-group" style="margin-bottom: 10px;">
				<label for="name" class="col-form-label"><%= "2."+(i+1)+" " + lsDanhGia.get(i).getTieuDe() %></label> 
				<p><b>Câu hỏi: </b><%=lsDanhGia.get(i).getCauHoi() %></p>
				<textarea rows="2" id="traLoi<%=lsDanhGia.get(i).getId() %>" name="traLoi[]" class="form-control" placeholder="nhập câu trả lời"></textarea>
				<input type="hidden" name="danhGiaId[]" value="<%=lsDanhGia.get(i).getId()%>"/>
			</div>
				<% } %>
			<% } %>
			
			<div style="text-align: right;margin-top: 10px;" id="btnSave">
				<button type="submit" class="btn btn-primary">Lưu</button>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
$(document).ready(function(){
	loadData();
});

function loadData(){
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/danhgia/loadDataWTSV",
		data : {
			'monId' : '<%=monId%>'
		},
		dataType : "json",
		success : function(data) {
			if(data.lsData1.length > 0 || data.lsData2.length > 0){
				$("#btnSave").html('');
			}
			if(data.lsData1.length > 0){
				var lsData1 = data.lsData1;
				for(var i = 0 ;i<lsData1.length; i++){
					$("#ketQua"+lsData1[i].workTaskId).val(lsData1[i].ketQua);
				}
			}
			
			if(data.lsData2.length > 0){
				var lsData2 = data.lsData2;
				for(var i = 0 ;i<lsData2.length; i++){
					$("#traLoi"+lsData2[i].danhGiaId).val(lsData2[i].traLoi);
				}
			}
		},
		error : function(){
			console.log("error");
		}
	});
}
function saveDanhSach(e){
	var check = true;
	$("select[name='ketQua[]']").map(function(){
		$(this).removeClass('selected');
		$(this).parent().find(".help-inline").remove();
		var data = $(this).val();
		if(data == 0){
			check = false;
			$(this).addClass("selected");
			$(this).parent().append("<div class='form-validator-stack help-inline' style='color:red;'>Thông tin bắt buộc không được để trống</div>");
			$(this).focus();
			return;
		}
	});
	
	$("textarea[name='traLoi[]']").map(function(){
		$(this).removeClass('selected');
		$(this).parent().find(".help-inline").remove();
		var data = $(this).val();
		if(data == ''){
			check = false;
			$(this).addClass("selected");
			$(this).parent().append("<div class='form-validator-stack help-inline' style='color:red;'>Thông tin bắt buộc không được để trống</div>");
			$(this).focus();
			return;
		}
	});
	if(check){
		$('#frmUpLoad').ajaxSubmit({
	        url: '/danhgia/saveWTSV',
	        type : "POST",
	        dataType : "html",
	        cache : false,
	        data : $("#frmUpLoad").serialize(),
	        beforeSend: function() {
	        	$('.uni-loading-top').show();
	        },
			complete: function () {
				$(".uni-loading-top").hide();
			},
	        success : function(data) {
	        	if(data == "1"){
	        		$.confirm({
	        	        'title': "",
	        	        'message': "Lưu thành công",
	        	        'buttons': {
	        	            'Tiếp tục': {
	        	                'class': 'blue',
	        	                'action': function() {
	        	                	$(e).parents('.window-nav').find('a.back-menu').click();
	        	                },
	        	            }
	        	        }
	        	    });
				}else{
					$.confirm({
	        	        'title': "",
	        	        'message': "Lưu không thành công",
	        	        'buttons': {
	        	            'Tiếp tục': {
	        	                'class': 'blue',
	        	                'action': function() {
	        	                },
	        	            }
	        	        }
	        	    });
				}
	        }
	    });
	}
}
</script>
