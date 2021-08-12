<%@page import="com.doan.totnghiep.entities.SinhVien"%>
<%@page import="com.doan.totnghiep.entities.User"%>
<%@page import="com.doan.totnghiep.util.CommonUtil"%>
<%@page import="com.doan.totnghiep.util.Constant"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	List<Object[]> listMonHoc = (List<Object[]>) request.getAttribute("listMonHoc");
	User _user = (User) session.getAttribute("USERLOGIN");
	SinhVien sv = CommonUtil.getSinhVienByUserId(_user.getId());
%>
<div class="trang-chitiet">
	 <h1 class="tieude-chuyenmuc">Danh sách môn học</h1>
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
									<button id="btChonFi" type="button" onclick="viewUni(1,'<%=listMonHoc.get(itemYCBS)[0] %>')"
										class="btn-xemdv" style="display: inline-block;margin: 0 3px 3px 0px;" title="" >
										Điểm danh
									</button>
									<button id="btChonFi" type="button" onclick="viewUni(2,'<%=listMonHoc.get(itemYCBS)[0] %>')"
										class="btn-xemdv" style="display: inline-block;margin: 0 3px 3px 0px;" title="" >
										Điểm thi
									</button>
									<% if(CommonUtil.select("isworktask", sv.getLop().getId(), (long) listMonHoc.get(itemYCBS)[0]) == 1){ %>
									<button class="btn-xemdv" style="margin: 0 3px 3px 0px;" onclick="viewUni(3,'<%=listMonHoc.get(itemYCBS)[0] %>')">
										Work/Task
									</button>
									<% } %>
									<% if(CommonUtil.select("isdanhgia", sv.getLop().getId(), (long) listMonHoc.get(itemYCBS)[0]) == 1){ %>
									<button class="btn-xemdv" style="margin: 0 3px 3px 0px;" onclick="viewUni(4,'<%=listMonHoc.get(itemYCBS)[0] %>')">
										Tự đánh giá
									</button>
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
</div>
<div class="uni-loading" style="display: none;"></div>
<div class="uni-loading-top" style="display: none"></div>
<a href="javascript:void(0)" id="loadMore"  ></a>
<script type="text/javascript">
function viewUni(truongHop,monId){
	var name = "Điểm danh";
	var _url = "/sinhvien/lop/viewDiemDanh";
	
	if(truongHop == 2){
		name = "Điểm thi";
		_url = "/sinhvien/lop/viewDiemThi";
	}
	
	if(truongHop == 3){
		name = "Đánh giá work/task";
		_url = "/worktask/viewSinhVien";
	}
	
	if(truongHop == 4){
		name = "Tự đánh giá";
		_url = "/danhgia/viewSinhVien";
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
			'monId' : monId
		},
		success: function(results) {
			$(_uniwindow).html(results);
		},
		error: function (err) {
		}
	});
}
</script>
