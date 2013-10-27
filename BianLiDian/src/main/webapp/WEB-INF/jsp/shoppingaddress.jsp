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
<body class="page" onunload="">
	<jsp:include page="header.jsp" />
	<div class="content">
		<div>
			<div class="address">
				<div id="receiverList">
					<p>使用历史地址</p>
					<ul>
						<c:forEach var="address" items="${addressList }"
							varStatus="status">
							<li <c:if test="${status.count}==0">class="selected" </c:if>
								id="${address.id}">
								<div>
									<strong>${address.receiver}</strong> <span>${address.address}</span>
									${address.telephone}
								</div>
							</li>
						</c:forEach>
					</ul>
					<p id="newReceiverButton" class="button">输入一个新地址</p>
				</div>
				<table id="newReceiver" class="hide">
					<tr>
						<th width="100"><span class="requiredField">*</span>收货人:</th>
						<td><input type="text" id="receiver" name="receiver"
							class="text" maxlength="200" /></td>
					</tr>
					<tr>
						<th><span class="requiredField">*</span>地址:</th>
						<td><input type="text" id="address" name="address"
							class="text" maxlength="200" /></td>
					</tr>
					<tr>
						<th><span class="requiredField">*</span>电话:</th>
						<td><input type="text" id="telephone" name="telephone"
							class="text" maxlength="200" /></td>
					</tr>
				</table>
			</div>
			<div class="order">
				<a href="javascript:submitOrder('${openId}');"><img width="100%"
					height="100"
					src="${pageContext.request.contextPath}/resources/image/shoppingcart/底层下单按键.png" />
					<div class="order_price">
						请确认您的地址
					</div>
					<div class="submit_order">确认下单</div></a>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
	<script>
	var $receiverId;
    
	// 收货地址
	$("#receiverList li").on("click", function() {
		var $this = $(this);
		$receiverId=$this.attr("id");
		$("#receiverList li").removeClass("selected");
		$this.addClass("selected");
	});
    
	// 新收货地址
	$("#newReceiverButton").click(function() {
		if ($("#newReceiver").css("display") == "none") {
			$("#dialogOverlay").show();
			$("#newReceiver").show();			
		} else {
			$("#dialogOverlay").hide();
			$("#newReceiver").hide();
		}
	});	
    </script>
</body>
</html>
