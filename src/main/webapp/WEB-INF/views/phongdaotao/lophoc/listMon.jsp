<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.util.Constant"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	List<Object[]> listMonHoc = (List<Object[]>) request.getAttribute("listMonHoc");
	long lopId = (Long) request.getAttribute("lopId");
	User _user = (User) session.getAttribute("USERLOGIN");
%>
<div class="trang-chitiet">
	<h1 class="tieude-chuyenmuc">Danh sách môn học
	<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "ADD_MONHOC_LOPHOC")){ %>
		 <button type="button" class="btn btn-primary" style="float: right;" onclick="chonMon();"><i class="fas fa-plus"></i><span class="an-themmoi">Thêm mới</span></button>
	<% } %>
	 </h1>
    <%
		if(listMonHoc != null && listMonHoc.size() > 0){
	%>
		 <ul>
		 	<li>
			<%
				for(int itemYCBS = 0; itemYCBS < listMonHoc.size(); itemYCBS++){
			%>
			
	            <div class="nd-tt">
	            	<div class="d-td">
						<p class="id">Tên môn học: </p>
						<p><%=listMonHoc.get(itemYCBS)[1].toString() %></p>
						<p class="id">Trạng thái</p>
						<p><%= Constant.getTrangThaiMonHoc(Integer.parseInt(listMonHoc.get(itemYCBS)[2].toString())) %></p>
						<p>
							<div class="btn-dvc">
							 	<div>
							 	<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "DELETE_MONHOC_LOPHOC")){ %>
									<button class="btn-xemdv" style="margin: 0 3px 3px 0px;" onclick="deleteMon('<%=lopId%>','<%=listMonHoc.get(itemYCBS)[0].toString()%>')">
										Xóa
									</button>
								<% } %>
									<button id="btChonFi" type="button" onclick="viewUni(1,'<%=listMonHoc.get(itemYCBS)[0] %>')"
										class="btn-xemdv chonTaiLieuCaNhan" style="display: inline-block;margin: 0 3px 3px 0px;" title="" >
										Điểm danh
									</button>
									
									<button id="btChonFi" type="button" onclick="viewUni(2,'<%=listMonHoc.get(itemYCBS)[0] %>')"
										class="btn-xemdv chonTaiLieuCaNhan" style="display: inline-block;margin: 0 3px 3px 0px;" title="" >
										Điểm thi
									</button>
								<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "VIEW_MONHOC_WORKTASK")){ %>
									<% if(CommonUtil.select("isworktask", lopId, (long) listMonHoc.get(itemYCBS)[0]) == 0){ %>
									<button class="btn-xemdv" style="margin: 0 3px 3px 0px;" onclick="updateWorkTask('<%=lopId%>','<%=listMonHoc.get(itemYCBS)[0].toString()%>',1)">
										Bật work/task
									</button>
									<% }else{ %>
									<button class="btn-xemdv" style="margin: 0 3px 3px 0px;" onclick="viewUni(3,'<%=listMonHoc.get(itemYCBS)[0] %>')">
										Work/Task
									</button>
									<% } %>
								<% } %>
								<% if(CommonUtil.checkQuyenByKhoa(_user.getId(), "VIEW_MONHOC_DANHGIA")){ %>
									<% if(CommonUtil.select("isdanhgia", lopId, (long) listMonHoc.get(itemYCBS)[0]) == 0){ %>
									<button class="btn-xemdv" style="margin: 0 3px 3px 0px;" onclick="updateWorkTask('<%=lopId%>','<%=listMonHoc.get(itemYCBS)[0].toString()%>',2)">
										Bật dánh giá
									</button>
									<% }else{ %>
									<button class="btn-xemdv" style="margin: 0 3px 3px 0px;" onclick="viewUni(4,'<%=listMonHoc.get(itemYCBS)[0] %>')">
										Đánh giá
									</button>
									<% } %>
								<% } %>
							 	</div>
							 </div>
						</p>
					</div>
	            </div>
	        	<%	
					} %>
			</li>
		</ul>
		<% }else{ %>
			<div class="no-data">Không tìm thấy môn học</div>
		<% } %>
		<!-- Modal -->
		 <div class="div-modal">
		     <div class="background-modal"></div>
			 <div class="content-modal">
			      <a class="close-md" onclick="closeModal();"><i class="far fa-times-circle"></i></a>
				  <h1 class="tile-h1">Chọn môn học</h1>
				  <div class="row-dm">
				     <div class="label-nd">Tên môn:</div>
	                     <div class="nd-tt">
							<input class="form-ip" type="text" id="tenMonSearch" name="tenMonSearch" />
							<input type="hidden" id="lopId" name="lopId" value="<%=lopId%>">
						 </div>	
				  </div>
				  <div class="row-dm flex-right">
				     <button class="btn-xemdv" type="button" onclick="searchMon();" name="xem" id="btnLuu"><i class="fas fa-search"></i>Tìm kiếm</button>
				  </div>
				  <div class="danh-sach-tep">
				     <h2>Danh sách môn</h2>
				     <div id="divSearchMon"></div>
				  </div>
			 </div>
		 </div>
</div>
<div class="uni-loading" style="display: none;"></div>
<div class="uni-loading-top" style="display: none"></div>
<a href="javascript:void(0)" id="loadMore"  ></a>
<script type="text/javascript">
$(document).ready(function(){
});
var lopId = '<%=lopId%>';
function viewUni(truongHop,monId){
	var name = "Điểm danh";
	var _url = "/phongdaotao/lop/viewDiemDanh";
	
	if(truongHop == 2){
		name = "Điểm thi";
		_url = "/phongdaotao/lop/viewDiemThi";
	}
	if(truongHop == 3){
		name = "Đánh giá work/task";
		_url = "/worktask/viewPDT";
	}
	if(truongHop == 4){
		name = "Tự đánh giá";
		_url = "/danhgia/viewPDT";
	}
	
	var _uniwindow = $.uniwindow({
		'title': name,
		'content': '<div class="uni-loading-mini"></div>'
	});
	$.ajax({
		async: true,
		url: _url,
		type: "POST",
		dataType: "html",
		data: {
			'monId' : monId,
			'lopId' : lopId
		},
		success: function(results) {
			$(_uniwindow).html(results);
		},
		error: function (err) {
		}
	});
}
function closeModal(){
   $('body').removeClass('open-modal');
}

function chonMon(){
	searchMon();
	$('body').addClass('open-modal');
}

function searchMon(){
	var key = $("#tenMonSearch").val();
	$.ajax({
        url: '/phongdaotao/lophoc/searchMon',
        type : "POST",
        dataType : "html",
        cache: false,
        data :{
        	'key' : key,
        },
        beforeSend : function(){
			$("#divSearchMon").html("<div class='uni-loading'></div>");
		},
        success : function(result) {
        	$("#divSearchMon").html("");
        	$("#divSearchMon").html(result);
        }
	});
}

function addMon(monId){
	var lopId = $("#lopId").val();
	var check = false;
	$.ajax({
        url: '/phongdaotao/lophoc/addMon',
        type : "POST",
        dataType : "json",
        cache: false,
        data :{
        	'monId' : monId,
        	'lopId' : lopId
        },
        success : function(result) {
        	if(result.kq == 1){
	        	closeModal();
	        	loadContent(lopId);
        	}else{
        		$.confirm({
        	        'title': "",
        	        'message': "Môn học hiện đang học",
        	        'buttons': {
        	            'Tiếp tục': {
        	                'class': 'blue',
        	                'action': function() {
        	                }
        	            }
        	        }
        	    });
        	}
        }
	});
}

function loadContent(lopId){
	var url = "/phongdaotao/lophoc/xemMonHoc";
	$.ajax({
		async: true,
		url: url,
		type: "GET",
		dataType: "html",
		data: {
			'lopId' : lopId
		},
		success: function(results) {
			test = results;
			if (results) {
				$(".window-nav-content").html(results);
			}
		},
		error: function () {
			console.log("Error");
		}
	});	
}

function deleteMon(lopId,monId){
	$.confirm({
        'title': "",
        'message': "Bạn có muốn xóa môn học này không",
        'buttons': {
            'Có': {
                'class': 'blue',
                'action': function() {
                    $.ajax({
                        url: "/phongdaotao/lophoc/deleteMon",
                        type: "POST",
                        dataType: "html",
                        data: {
                            "lopId": lopId,
                            "monId" : monId
                        },
                        beforeSend: function() {
           				 	$(".uni-loading-top").show();
	           			},
	           			complete: function () {
	           				$(".uni-loading-top").hide();
	           			},
                        success: function(data) {
                        	loadContent(lopId);
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

function updateWorkTask(lopId,monId,loai){
	$.confirm({
        'title': "",
        'message': "Bạn có muốn bật chức năng này không",
        'buttons': {
            'Có': {
                'class': 'blue',
                'action': function() {
                    $.ajax({
                        url: "/phongdaotao/lophoc/updateWT",
                        type: "POST",
                        dataType: "html",
                        data: {
                            "lopId": lopId,
                            "monId" : monId,
                            "loai" : loai
                        },
                        beforeSend: function() {
           				 	$(".uni-loading-top").show();
	           			},
	           			complete: function () {
	           				$(".uni-loading-top").hide();
	           			},
                        success: function(data) {
                        	loadContent(lopId);
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
