<%@page import="com.doan.totnghiep.entities.DanhGia"%>
<%@page import="com.doan.totnghiep.entities.WorkTaskDetail"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.entities.LopHoc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	User _user = (User) session.getAttribute("USERLOGIN");
	long monId = (long) request.getAttribute("monId");
	List<DanhGia> lsData = (List<DanhGia>) request.getAttribute("lsDanhGia");
	String addOrEditUrl = "/danhgia/addOrEdit?monId=" + monId;
%>
<div class="container-fluid p-0">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle">Danh sách đánh giá</h1>
		<a class="btn btn-primary" href="/phongdaotao/monhoc/list"
			style="float: right; margin-left: 5px;"> <i class="fas fa-share-square"></i><span class="an-themmoi">Quay lại</span>
		</a>
		<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "ADD_DANHGIA")){ %>
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
				<% if (lsData != null && lsData.size() > 0) {%>
					<% 
						for(int i = 0; i < lsData.size();i++){ 
							DanhGia item = lsData.get(i);
					%>
					<li>
						<div class="number-stt">
							<span class="stt"><%= i + 1 %></span>
						</div>
						<div class="nd-dvc" style="min-height: 25px;">
							<div>
								<p><b><%=item.getTieuDe()%></b></p>
							</div>
							<div>
								<p><i><%= "- " + item.getCauHoi() %></i></p>
							</div>
						</div>
						<div>
						<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "UPDATE_DANHGIA")){ %>
							<a href='<%=addOrEditUrl + "&danhGiaId=" + item.getId()%>' title="Sửa">
								<i class='far fa-edit'></i>
							</a>
						<% } %>
						<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "DELETE_DANHGIA")){ %>
							<a onclick="xoaDanhGia('<%=item.getId() %>');" title= "Xóa">
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
<div class="uni-loading" style="display: none;"></div>
<div class="uni-loading-top" style="display: none"></div>
<script type="text/javascript">
function xoaDanhGia(danhGiaId) {
    var checked = false;
    $.confirm({
        'title': "",
        'message': "Bạn có muốn xóa đánh giá này không",
        'buttons': {
            'Có': {
                'class': 'blue',
                'action': function() {
                    $.ajax({
                        url: "/danhgia/delete",
                        type: "POST",
                        dataType: "html",
                        data: {
                            "danhGiaId": danhGiaId
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
