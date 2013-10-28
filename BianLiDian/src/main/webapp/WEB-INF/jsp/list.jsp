<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width" />
<title>商品列表页</title>
<style>
.item img {
	border-style: solid;
	border-width: 1px;
	border-color: black;
}

.price {
	position: absolute;
	right: 5px;
	bottom: 5px;
	width: 50px;
	height: 15px;
	font-size: 0.8em;
	color: white;
	background-color: black;
	font-weight: bold;
}

#container {
	width: 100%;
}

.over {
	display: block;
}

.over img {
	display: block;
	position: absolute;
}

.under,.under img {
	display: none;
}

.content {
	color: white;
	z-index: -1;
	top: 0px;
	bottom: 0px;
	left: 0;
	width: 100%;
	padding: 0px;
	position: absolute;
}

.demo {
	position: fixed;
	bottom: 10px;
	right: 10px;
}

.demo span {
	position: absolute;
	top: 1px;
	left: 25px;
	font-size: 1.5em;
	color: red;
}

body {
	display: block;
	height: 100%;
}
</style>
</head>
<body class="page">
	<div id="scroll" class="demo">
		<a
			href="${pageContext.request.contextPath}/shoppingCart?openId=${openId}">
			<span id="in_cart_num">${cartNum}</span> <img width=60 height=60
			src="${pageContext.request.contextPath}/resources/image/购物车图标1.png"></img>
		</a>
	</div>
	<jsp:include page="header.jsp" />
	<div class="content" id="wrapper">
		<div id="scroller">
			<div id="container">
				<c:forEach items="${items}" var="item">
					<div class="item">
						<div class="${item.isCarted?'over':'under' }"
							id="cover_img_${item.id}"
							onclick="deleteShopping(${item.id},'${openId}');">
							<img
								src="${pageContext.request.contextPath}/resources/image/werrwer.png"
								height="${item.imgHeight }" width="${item.imgWidth }"></img><img
								style="width: 107px; height: 93px; top: 20px; left: 30px; border-width: 0px;"
								src="${pageContext.request.contextPath}/resources/image/放入购物车图标.png"></img>
						</div>
						<img
							src="${pageContext.request.contextPath}/resources/image/loading.gif"
							data-original="${item.imgUrl }" height="${item.imgHeight }"
							width="${item.imgWidth }" class="lazy"
							onclick="increaseShopping(${item.id},'${openId}')"></img>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
	<script
		src="${pageContext.request.contextPath}/resources/js/masonry.pkgd.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.lazyload.min.js"></script>
	<script type="text/javascript">
	var myScroll;
	function loaded() {
		myScroll = new iScroll('wrapper',{onScrollEnd:function(){  
			$("#wrapper").trigger('scroll');
        }});
	}
	document.addEventListener('DOMContentLoaded', loaded, false);
	
	var container = document.querySelector('#container');
	var msnry = new Masonry( container, {
	  // options...
	  itemSelector: '.item',
	  columnWidth: 160
	});
	
	var timestamp=new Date().getTime();
	var timer;
	function checkResume()
	{
	    var current=new Date().getTime();
	    if(current-timestamp>2000)
	    {
	    	$.getJSON(context + '/list/check/?openId=${openId}', function(data) {
				$("#in_cart_num").text(function(index, value) {
					return data.length;
				});
				
				$(".over").each(function(){ 
					$(this).removeClass();
					$(this).addClass("under");
				});
				
				$(data).each(function(){ 
					$("#cover_img_" + this.itemId).removeClass();
					$("#cover_img_" + this.itemId).addClass("over");
				});
			});
			timestamp=new Date().getTime();
			window.clearInterval(timer);
			timer=window.setInterval(checkResume,100);
	    }
	    timestamp=current;
	}
	timer=window.setInterval(checkResume,100);
	
	$("img.lazy").lazyload({ threshold : 100 });
</script>
</body>
</html>