<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.footer {
	background-image:
		url(${pageContext.request.contextPath}/resources/image/di.png);
	height: 44px;
	bottom: 0px;
	position: absolute;
	z-index: 2;
}

.footer button {
	float: left;
	width: 106px;
	height: 44px;
}

.footer a {
	display: block;
	float: left;
	width: 106px;
	height: 44px;
}

.shoppingCartNum {
	position: absolute;
	right: 110px;
	top: -5px;
	font-size: 0.6em;
	color: red;
	font-weight: bold;
	background-color: black;
}

.stay {
	height: 44px;
	width: 320px;
	background-color: black;
}
</style>
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/global_v009.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/iscroll.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.scrollfollow.js"></script>
