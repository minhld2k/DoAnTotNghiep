$(document).ready(function() {
	$(".viewForm").hide();
	$('.viewFind').on('click', function() {	
		if ($(".viewForm").is(":hidden")) {
			$(".viewForm").show();
		}else{
			$(".viewForm").hide();
		}
	});
});

function getData(data){
	if (data == "[]") {
		return null;
	}else{
		var temp = data.substring(1,data.length-1);
		return temp.split(", ");
	}
}

function validateDate(date2) {
	var datenow = $.datepicker.formatDate('yymmdd', new Date());
	var date = date2.split("/");
	var d = parseInt(date[0], 10),
		m = parseInt(date[1], 10),
		y = parseInt(date[2], 10);
	var bd = new Date(y, m - 1, d);
	var date1 = $.datepicker.formatDate('yymmdd', bd);
	return (date1 <= datenow );
}

function validateEmail(email) {
	  var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	  return re.test(email);
}
function validatePhone(sdt) {
	  var re = /^\+?[0-9. ()-]{10,25}$/ ;
	  return re.test(sdt);
}
function validateSo(num) {
	  var re = /^[0-9]+$/ ;
	  return re.test(num);
}
function validateDateDKDVC(val) {
	var datenow = $.datepicker.formatDate('yymmdd', new Date());
 	var rex = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/;
	var kq = false;
	if(rex.test(val)){
		var date = val.split("/");
		var d = parseInt(date[0], 10),
			m = parseInt(date[1], 10),
			y = parseInt(date[2], 10);
		var bd = new Date(y, m - 1, d);
		var date1 = $.datepicker.formatDate('yymmdd', bd);
		kq = (date1 <= datenow);
	}
	return kq;
}
function CompareDates(str1,str2) {
    if(str1 == "")
    return false;
    var dt1   = parseInt(str1.split("/")[0],10); 
    var mon1  = parseInt(str1.split("/")[1],10); 
    var yr1   = parseInt(str1.split("/")[2],10);  
    var dt2   = parseInt(str2.split("/")[0],10);  
    var mon2  = parseInt(str2.split("/")[1],10);  
    var yr2   = parseInt(str2.split("/")[2],10); 
    mon1 = mon1 -1 ;
    mon2 = mon2 -1 ;
    var date1 = new Date(yr1, mon1, dt1); 
    var date2 = new Date(yr2, mon2, dt2); 
    if(date2 >= date1)
    {
        return false; 
    } 
    else 
    { 
        return true;
    } 
}
function validateDateBieuDoNgayThang(val) {
	var datenow = $.datepicker.formatDate('yymmdd', new Date());
 	var rex = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/;
	return rex.test(val);
}

function checkLoadMore() {
	return $('.content-body').scrollTop() >= $('#content').height() - $('.content-body').height() + 10;
}
function redirectURL(_url) {
	window.location.href = _url;
}
$.fn.datepicker = function(options){};
(function($){
	$.uniwindow = function(params){
		var _idNav = "nav-content-" + new Date().getTime();
		var markup = [
			'<nav class="window-nav">',
				'<div class="window-nav-header">',
					'<a class="back-menu"><i class="fas fa-chevron-left"></i></a>',
					'<h1 class="tieude-chuyenmuc">', params.title, '</h1>',
				'</div>',
				'<div id="' + _idNav + '" class="window-nav-content">',
					params.content,
				'</div>',
			'</nav>'
		].join('');
		$(markup).appendTo('body').fadeIn(10).css('left', 0).find('a.back-menu').click(function() {
			$(this).parents('.window-nav').css('left', '').delay(1000).queue(function() {
				$(".uni-loading-top").hide();
				$(this).remove();
			});
		});
		return "#" + _idNav;
	}
})(jQuery);