<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width" />
<title>购物车</title>
<style>
.item {
	height: 205px;
}

.item_img {
	float: left;
	height: 100px;
	width: 50%;
	overflow: hidden;
	background-repeat: no-repeat;
	background-size: cover;
}

.item_img img {
	width: 100%;
}

.item_img a img {
	height: 100px;
	border: 0px;
	width: 100%;
}

.item_detail {
	width: 50%;
	height: 100px;
	float: left;
	position: relative;
}

.item_detail a {
	height: 100px;
	text-align: center;
	vertical-align: middle;
}

.item_detail a img {
	vertical-align: middle;
}

i {
	display: inline-block;
	height: 100%;
	vertical-align: middle;
}

.item_title {
	font-weight: bold;
	font-size: 15px;
	height: 18px;
	overflow: hidden;
	background-color: grey;
}

.item_description {
	font-size: 11px;
	overflow: hidden;
	height: 24px;
	padding: 10px;
}

.item_shop {
	position: relative;
	height: 38px;
}

.item_number {
	position: absolute;
	top: 0px;
	font-weight: bold;
	font-size: 13px;
	background-color: grey;
	display: inline-block;
}

.item_price {
	position: absolute;
	top: 0px;
	right: 0px;
	font-weight: bold;
	font-size: 22px;
	background-color: grey;
	display: inline-block;
}

.item_delete {
	position: absolute;
	top: 0px;
	right: 0px;
	width: 20px;
	height: 20px;
}

.item_seperate {
	height: 10px;
}

.order {
	height: 100px;
	position: relative;
	font-weight: bold;
	font-size: 24px;
}

.order_freight {
	position: absolute;
	top: 40px;
	left: 80px;
	color: white;
	font-size: 15px;
}

.order_price {
	position: absolute;
	top: 10px;
	left: 20px;
	color: white;
}

.submit_order {
	position: absolute;
	bottom: 10px;
	right: 10px;
	color: white;
}

.content {
	color: white;
	z-index: 1;
	top: 0px;
	bottom: 0px;
	left: 0;
	width: 100%;
	padding: 0px;
}

a {
	display: block;
	color: white;
}

p {
	margin: 20px 10px 15px 0px;
	font-weight: bold;
	font-size: 1.3em;
}

p#orderNum {
	font-size: 8em;
	margin: 20px 30px 15px 100px;
}

p span {
	font-size: 1.4em;
	margin: 0px 0px 0px 0px;
}

#orderLoading {
	text-align: center;
	vertical-align: middle;
	/* 	height: 300px; */
}

.hide {
	display: none;
}

.item_num {
	position: absolute;
	top: 30%;
	left: 46%;
	width: 60px;
	font-size: 1.3em;
	color: black;
	background-color: white;
	text-align: center;
}

span.requiredField {
	padding-right: 3px;
	color: #ff6d6d;
	font-size: 10px;
}

div.address {
	font-size: 0.8em;
}

div .address li.selected {
	border: solid 1px #fee580;
	background:
		url(${pageContext.request.contextPath}/resources/image/cart.gif) right
		bottom no-repeat red;
}
</style>
</head>
<body class="page">
	<jsp:include page="header.jsp" />
	<div class="content">
		<div>
			<c:forEach items="${shoppingcart}" var="item">
				<div class="item" id="item_${item.id}">
					<div class="order">
						<div class="item_img"
							style="background-image: url(${item.itemImg });">
							<div class="item_shop">
								<span class="item_price"> ￥<strong
									id="item_price_${item.itemId}">${item.itemPrice }</strong>
								</span>
							</div>
							<div class="item_description">${item.itemDescription }</div>
							<div class="item_title">${item.itemName }</div>
						</div>
						<div class="item_detail">
							<a href="javascript:void(0);"><i></i> <img
								src="resources/image/shoppingcart/删除图标.png"
								onclick="deleteShopping(${item.id},'${openId}', true)"></img> </a>
						</div>
					</div>
					<div class="order">
						<div class="item_img">
							<a href="javascript:void(0);"> <img
								src="resources/image/shoppingcart/减少按键.png"
								onclick="decreaseShopping(${item.itemId},${item.id},'${openId}')"></img>
							</a>
						</div>
						<div class="item_img">
							<a href="javascript:void(0);"> <img
								src="resources/image/shoppingcart/增加按键.png"
								onclick="increaseShopping(${item.itemId},'${openId}')"></img>
							</a>
						</div>
						<div class="item_num">
							<strong id="item_num_${item.itemId}">${item.itemNumber }</strong>
						</div>
					</div>
				</div>
			</c:forEach>
			<div class="order">
				<a
					href="${pageContext.request.contextPath}/shoppingCart/address?openId=${openId}"
					onclick="javascript:return checkTotal();"><img width="100%"
					height="100"
					src="${pageContext.request.contextPath}/resources/image/shoppingcart/底层下单按键.png" />
					<div class="order_price">
						合计 ￥<span id="total_price">${totalPrice }</span>
					</div>
					<div class="order_freight hide">（含配送费 1.00 元）</div>
					<div class="submit_order">请确认您的商品清单 下一步</div></a>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
	<script>
	function checkTotal() {
		if ($("#total_price").text() < 20) {
			alert("您的购物车还未满20元，再挑几样吧！");
			return false;
		}
	}
	
    if($("#total_price").text() > 0) {
    	$(".order_freight").removeClass("hide");
    }
	</script>
</body>
</html>
