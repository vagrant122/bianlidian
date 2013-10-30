function increaseShopping(itemId, openId) {
	$.getJSON(context + '/shoppingCart/increase/' + itemId + '?openId='
			+ openId, function(data) {
		$("#item_num_" + itemId).text(function(index, value) {
			return data.itemNumber;
		});
		$("#item_price_" + itemId).text(function(index, value) {
			return data.itemPrice;
		});
		$("#total_price").text(function(index, value) {
			if (data.totalPrice > 0) {
				$(".order_freight").removeClass("hide");
			}
			return data.totalPrice;
		});
		if (Number(data.itemNumber) == 1) {
			$("#in_cart_num").text(function(index, value) {
				return Number(value) + 1;
			});
		}
		$("#cover_img_" + itemId).removeClass();
		$("#cover_img_" + itemId).addClass("over");
	});
}

function decreaseShopping(itemId, id, openId) {
	$.getJSON(context + '/shoppingCart/decrease/' + itemId + '?openId='
			+ openId, function(data) {
		if (!data || Number(data.itemNumber) <= 0) {
			$("#item_" + id).remove();
			$("#in_cart_num").text(function(index, value) {
				return Number(value) - 1;
			});
		} else {
			$("#item_num_" + itemId).text(function(index, value) {
				return data.itemNumber;
			});
			$("#item_price_" + itemId).text(function(index, value) {
				return data.itemPrice;
			});
		}
		$("#total_price").text(function(index, value) {
			if (data.totalPrice == 0) {
				$(".order_freight").addClass("hide");
			}
			return data.totalPrice;
		});
	});
}

function deleteShopping(id, openId, isAlert) {
	var r = true;
	if (isAlert) {
		r = confirm("您真的确定要删除吗？\n\n请确认！");
	}
	if (r == true) {
		$.getJSON(context + '/shoppingCart/delete/' + id + '?openId=' + openId,
				function(data) {
					$("#in_cart_num").text(function(index, value) {
						return Number(value) - 1;
					});
					$("#total_price").text(function(index, value) {
						if (data == 0) {
							$(".order_freight").addClass("hide");
						}
						return data;
					});
					$("#item_" + id).remove();
					$("#cover_img_" + id).removeClass();
					$("#cover_img_" + id).addClass("under");
				});
	}
}

function submitOrder(openId) {
	if (checkRequired()) {
		var address = {
			"id" : $receiverId
		};
		if ($("#newReceiver").css("display") != "none") {
			address = {
				"receiver" : $("#receiver").val(),
				"address" : $("#address").val(),
				"telephone" : $("#telephone").val(),
			};
		}
		$.post(context + '/shoppingCart/submit/?openId=' + openId, address,
				function(data) {
					// var orderNum = Number(data);
					$(".item").remove();
					$("#in_cart_num").text(function(index, value) {
						return 0;
					});
					$("#total_price").text(function(index, value) {
						if (data == 0) {
							$(".order_freight").addClass("hide");
						}
						return 0.0;
					});
					// $("#orderNum").text(orderNum);

					// setTimeout(function() {
					// $("#orderLoading").addClass("hide");
					//
					// $("#orderHint").removeClass();
					$("#dialogOverlay").hide();
					$("#newReceiver").hide();
					$(".address").hide();
					alert("您的订单已提交成功，欢迎下次再来！");
					// }, 2000);
				}, "json");
	}
}

function checkRequired() {
	if ($("#newReceiver").css("display") != "none") {
		if (!$("#receiver").val() || !$("#address").val()
				|| !$("#telephone").val()) {
			alert("必填项不能为空！");
			return false;
		}
	} else if (!$receiverId) {
		alert("地址不能为空！");
		return false;
	}
	return true;
}

function loading(canvas, options) {
	this.canvas = canvas;
	if (options) {
		this.radius = options.radius || 12;
		this.circleLineWidth = options.circleLineWidth || 4;
		this.circleColor = options.circleColor || 'lightgray';
		this.moveArcColor = options.moveArcColor || 'gray';
	} else {
		this.radius = 12;
		this.circelLineWidth = 4;
		this.circleColor = 'lightgray';
		this.moveArcColor = 'gray';
	}
}
loading.prototype = {
	show : function() {
		var canvas = this.canvas;
		if (!canvas.getContext)
			return;
		if (canvas.__loading)
			return;
		canvas.__loading = this;
		var ctx = canvas.getContext('2d');
		var radius = this.radius;
		var me = this;
		var rotatorAngle = Math.PI * 1.5;
		var step = Math.PI / 6;
		canvas.loadingInterval = setInterval(function() {
			ctx.clearRect(0, 0, canvas.width, canvas.height);
			var lineWidth = me.circleLineWidth;
			var center = {
				x : canvas.width / 2 - radius,
				y : canvas.height / 2 - radius
			};
			ctx.beginPath();
			ctx.lineWidth = lineWidth;
			ctx.strokeStyle = me.circleColor;
			ctx.arc(center.x, center.y, radius, 0, Math.PI * 2);
			ctx.closePath();
			ctx.stroke();
			// 在圆圈上面画小圆
			ctx.beginPath();
			ctx.strokeStyle = me.moveArcColor;
			ctx.arc(center.x, center.y, radius, rotatorAngle, rotatorAngle
					+ Math.PI * .45);
			ctx.stroke();
			rotatorAngle += step;

		}, 50);
	},
	hide : function() {
		var canvas = this.canvas;
		canvas.__loading = false;
		if (canvas.loadingInterval) {
			window.clearInterval(canvas.loadingInterval);
		}
		var ctx = canvas.getContext('2d');
		if (ctx)
			ctx.clearRect(0, 0, canvas.width, canvas.height);
	}
};