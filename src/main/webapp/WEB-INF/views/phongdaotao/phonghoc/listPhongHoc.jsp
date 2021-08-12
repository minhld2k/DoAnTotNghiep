<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String addOrEditUrl = "/phongdaotao/phonghoc/addOrEdit";
	User _user = (User) session.getAttribute("USERLOGIN");
%>
<div class="container-fluid p-0">
	<div class="mb-3">
		<h1 class="h3 d-inline align-middle">Danh sách phòng học</h1>
		<button class="btn btn-primary viewFind" style="float: right; margin-left: 5px;"> 
			<i class="fas fa-search"></i><span class="an-themmoi">Hiển thị tìm kiếm</span>
		</button>
		<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "ADD_PHONG")){ %>
			<a class="btn btn-primary" href="<%=addOrEditUrl%>"
				style="float: right;"> <i class="fas fa-plus"></i><span class="an-themmoi">Thêm mới</span>
			</a>
		<% } %>
	</div>
	
	<div class="card">
		<div class="card-body">
			<div class="viewForm box">
				<div class="row">
					<div class="col-lg-6" >
						<label for="name" class="mr-sm-2">Tên phòng:</label>
						<input type="text" name="ten" id="ten" class="form-control" value=""/>
					</div>
					<div class="col-lg-6" >
						<label for="name" class="mr-sm-2">Địa điểm:</label>
                        <select class="form-control" id="coSoId">
                        	<option value="" >--Chọn--</option>
                            <c:forEach var="item" items="${listCoSo}">
                                <option value="${item.id}" >${item.ten}</option>
                            </c:forEach>
                        </select>
					</div>
				</div>
				<div style="display: flow-root;">
					<button type="button" onclick="btnsearch();" class="btn btn-primary"
						style="margin-top: 10px; margin-bottom: 10px; float: right;">
						<span class="glyphicon glyphicon-search"></span> Tìm kiếm
					</button>
				</div>
			</div>
			
			<div class="buoc-3">
				<span id="countCN" style="display: none;"></span>
				<ul id="ulCN" class="ul-cn"></ul>
			</div>
		</div>
	</div>
</div>
<div class="uni-loading" style="display: none;"></div>
<div class="uni-loading-top" style="display: none"></div>
<a href="javascript:void(0)" id="loadMore"  ></a>
<script type="text/javascript">
var ten = '';
var coSoId = 0;
var ajaxRuning = 0;

$(document).ready(function(){
	loadMore(ten,coSoId);
	$('.content-body').scroll(function() {
		if(ajaxRuning == 0 && checkLoadMore()) {
			var count = $(".ul-cn li").length;
			var countDVC = parseInt($("#countCN").text()) || 0;
			if(countDVC > 0 && countDVC > count) {
				ajaxRuning = 1;
				setTimeout(function() {
					loadMore(ten,coSoId);
				}, 5);
			}
		}
	});
});
     
function loadMore(ten,coSoId) {
	ajaxRuning = 1;
	var count = $(".ul-cn li").length;
	$.ajax({
		async: true,
		url: "/phongdaotao/phonghoc/loadmore",
		type: "GET",
		dataType: "html",
		data: {
			'count' : count,			
			'ten' : ten,
			'coSoId' : coSoId
		},
		beforeSend: function() {
			 $(".uni-loading").show();
		},
		complete: function () {
			$(".uni-loading").hide();
			ajaxRuning = 0;
		},
		success: function(results) {
			test = results;
			if(results==""){
				$("#countDS").val("1");
			}
			if (results) {
				$("#ulCN").append(test);
			}
		},
		error: function () {
			console.log("Error");
		}
	});	
}

function btnsearch(){
	if (ajaxRuning == 0) {
		ten = $('#ten').val();
		coSoId = $('#coSoId').val();
		$("#ulCN").html("");
		loadMore(ten,coSoId);
	}
}

function xoaPhongHoc(phongId) {
    var checked = false;
    $.confirm({
        'title': "",
        'message': "Bạn có muốn xóa phòng học này không",
        'buttons': {
            'Có': {
                'class': 'blue',
                'action': function() {
                    $.ajax({
                        url: "/phongdaotao/phonghoc/delete",
                        type: "POST",
                        dataType: "html",
                        data: {
                            "phongId": phongId,
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
