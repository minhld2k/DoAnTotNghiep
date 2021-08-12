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
	<h1 class="tieude-chuyenmuc">Đánh giá work/task</h1>
	
    <form method="POST" id="frmUpLoad" name="frmUpLoad" action="javascript:void(0)" onSubmit="saveDanhSach($(this))">
		<div class="modal-body">
			<div class="form-group">
				<table class="table table-bordered table-hover table-striped">
					<thead>
						<tr style="color:#d59100">
							<th>Tên công việc</th>
							<th>Kết quả</th>
							<th>Ý kiến</th>
						</tr>
					</thead>
					<tbody>
					<% if(lsData != null && lsData.size() > 0){
						for(int i = 0; i< lsData.size(); i++){%>
						<tr>
							<td colspan="3">
								<b><%= "Task " + lsData.get(i).getThuTu() + ". " + lsData.get(i).getTen() %></b>
							</td>
						</tr>
						<% 
							List<WorkTaskDetail> lsDetail = CommonUtil.getWTDetailByWTid(lsData.get(i).getId()) ;
							if(lsDetail != null && lsDetail.size() > 0){
								for(int j = 0 ;j<lsDetail.size();j++){ %>
								<tr>
									<td>
										<%=lsDetail.get(j).getThuTu() + ". " + lsDetail.get(j).getTen()%>
										<input type="hidden" name="wtId[]" value="<%=lsDetail.get(j).getId()%>"/>
									</td>
									<td>
										<select class="form-control select" id="ketQua<%=lsDetail.get(j).getId() %>" name="ketQua[]">
											<option value = "0">Chọn</option>
							                <option value="1" >Làm tốt</option>
							                <option value="2" >Làm được</option>
							                <option value="3" >Chưa được</option>
							            </select>
									</td>
									<td>
										<input type="text"  class="form-control" value="" id="yKienKhac<%=lsDetail.get(j).getId() %>" name="yKienKhac[]"/>
									</td>
								</tr>	
						<% 		}
							}
						%>
						<% } %>
					<% } %>
					</tbody>
				</table>
			</div>
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
		url : "/worktask/loadDataWTSV",
		data : {
			'monId' : '<%=monId%>'
		},
		dataType : "json",
		success : function(data) {
			if(data.length > 0){
				for(var i = 0 ;i< data.length; i++){
					$("#ketQua"+data[i].workTaskDetailId).val(data[i].ketQua);
					$("#yKienKhac"+data[i].workTaskDetailId).val(data[i].ykien);
				}
			}
		},
		error : function(){
			console.log("error");
		}
	});
}
function saveDanhSach(e){
	$('#frmUpLoad').ajaxSubmit({
        url: '/worktask/saveWTSV',
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
</script>
