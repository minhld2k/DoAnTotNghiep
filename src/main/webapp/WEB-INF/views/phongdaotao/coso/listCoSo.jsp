<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.entities.CoSo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	User _user = (User) session.getAttribute("USERLOGIN");
	String addOrEditUrl = "/phongdaotao/coso/addOrEdit";
	List<CoSo> lsData = (List<CoSo>) request.getAttribute("listCoSo");
%>
<div class="container-fluid p-0">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle">Danh sách cơ sở</h1>
		<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "ADD_COSO")){ %>
		<a class="btn btn-primary" href="<%=addOrEditUrl%>"
			style="float: right;"> <i class="fas fa-plus"></i><span class="an-themmoi">Thêm mới</span>
		</a>
		<% } %>
	</div>
	
	<div class="card">
		<div class="card-body">
			<div class="buoc-3">
				<span id="countCN" style="display: none;"></span>
				<ul id="ulCN" class="ul-cn">
					<% if (lsData != null && lsData.size() > 0) { 
					for (int i = 0;i<lsData.size();i++) { 
						CoSo coSo = lsData.get(i);
					%>
					<li>
						<div class="number-stt">
							<span class="stt"><%=1 + i %></span>
						</div>
						<div class="nd-dvc" style="min-height: 25px;">
							<div>
								<p><b><%=coSo.getTen()%></b></p>
							</div>
							<div>
								<p>Địa chỉ:  <%=coSo.getDiaChi() %></p>
							</div>
						</div>
						<div>
						<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "UPDATE_COSO")){ %>
							<a href='<%=addOrEditUrl + "?coSoId=" + coSo.getId()%>' title="Sửa">
								<i class='far fa-edit'></i>
							</a>
						<% } %>
						<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "DELETE_COSO")){ %>
							<a onclick="xoaCoSo('<%=coSo.getId() %>');" title= "Xóa">
								<i class='far fa-trash-alt'></i>
							</a>
						<% } %>
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
</div>
<div class="uni-loading-top" style="display: none"></div>

<script type="text/javascript"> 
function xoaCoSo(id) {
    var checked = false;
    $.confirm({
        'title': "",
        'message': "Bạn có muốn xóa cơ sở này không",
        'buttons': {
            'Có': {
                'class': 'blue',
                'action': function() {
                    $.ajax({
                        url: "/phongdaotao/coso/delete",
                        type: "POST",
                        dataType: "html",
                        data: {
                            "coSoId": id,
                        },
                        beforeSend: function() {
           				 	$(".uni-loading-top").show();
	           			},
	           			complete: function () {
	           				$(".uni-loading-top").hide();
	           			},
                        success: function(data) {
                        	location.reload();
                        }
                    });
                },
            },
            'Không': {
                'class': 'blue',
                'action': function() {
                }
            }
        }
    });
}
</script>
