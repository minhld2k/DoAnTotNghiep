<%@page import="com.doan.totnghiep.entities.ChucNang"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.doan.totnghiep.dto.UserDetailDTO"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	JSONObject user = (JSONObject) request.getAttribute("Detail");
	long nhomId = user.getLong("nhomId");
	long userId = user.getLong("id");
%>
	
	<%	if(nhomId == 1){ 
			long lopId = user.getLong("lopId");
	%>	
		<div class="form-group">
			<label for="name" class="col-form-label">Ngày sinh</label> 
			<input type="date" class="form-control" id="ngaySinh" name="ngaySinh" value="<%= user.getString("ngaySinh") %>" />
		</div>
		
		<div class="form-group">
			<label for="url" class="col-form-label">Lớp <strong>*</strong></label>
			<select class="form-control" id="lopId" name="lopId">
				 <c:set var="lopId"  value="<%= lopId %>"></c:set>
				 <option value="">--Chọn lớp--</option>
                 <c:forEach var="item" items="${lsLop}">
                     <option value="${item.id}" ${item.id == lopId ? "selected":"" } >${item.ten}</option>
                 </c:forEach>
             </select>
		</div>
		
		<div class="form-group">
			<label for="name" class="col-form-label">Số điện thoại gia đình</label> 
			<input type="text" class="form-control" id="phoneFamily" name="phoneFamily" value="<%=user.getString("phoneFamily")%>" />
		</div>
		
		<div class="form-group">
			<label for="name" class="col-form-label">Giới thiệu</label> 
			<textarea rows="2" class="form-control" id="moTa" name="moTa"><%=user.getString("moTa")%></textarea>
		</div>
	<% }else if(nhomId == 2){ %>
		<div class="form-group">
			<label for="name" class="col-form-label">Giới thiệu</label> 
			<textarea rows="2" class="form-control" id="moTa" name="moTa"><%=user.getString("moTa")%></textarea>
		</div>
	<% }else if(nhomId == 3){ %>
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
	
	<script type="text/javascript">
	$(document).ready(function() {
		checkChucNang();
	});
	function checkChucNang(){
		var nhomId = '<%=nhomId%>';
		var userId = '<%=userId%>';
		console.log(userId);
		if(nhomId != ''){
			$.ajax({
				type : "GET",
				contentType : "application/json",
				url : "/quantri/nhom/getChucNang",
				data : {
					'nhomId' : nhomId,
					'userId' : userId
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
<% } %>
		
