<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width" />
<title>首页</title>
<style>
.page img {
	width: 320px;
}

.category {
	height: 130px;
}

.category img {
	height: 130px;
}

.category_title {
	height: 40px;
	width: 320px;
	background-color: black;
	opacity: 0.5;
	position: absolute;
}

.category_name {
	margin-left: 15px;
	margin-top: 5px;
	color: white;
	font-weight: bold;
	font-size: 13px;
	float: left;
}

.category_arrow {
	margin-right: 5px;
	float: right;
}

.category_description {
	margin-top: 22px;
	margin-left: 40px;
	color: white;
	font-size: 11px;
}
</style>
</head>
<body class="page">
	<jsp:include page="header.jsp" />
	<div class="content" id="wrapper">
		<div id="scroller">
			<c:forEach items="${categories}" var="category">
				<div class="category">
					<a href="list/${category.id }"> <img src="${category.imgUrl }" />
					</a>
				</div>
			</c:forEach>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>
