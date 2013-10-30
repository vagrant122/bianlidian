<xml>
	<ToUserName><![CDATA[${toUser}]]></ToUserName>
	<FromUserName><![CDATA[${fromUser}]]></FromUserName>
	<CreateTime>${createTime}</CreateTime>
	<MsgType><![CDATA[text]]></MsgType>
	<Content><![CDATA[
<#if textType=="orderNumber">
	您选购的商品清单：	
	<#list order.items as item>
	${item.itemName} X${item.itemNumber}
	</#list>
	<#if order.totalPrice>0>
	配送费 1.00 元
	</#if>
	总价 ${order.totalPrice}元
	~您的订单我们已经收到，请输入您的地址和联系方式 我们开始为您配送~
<#else>
	${content}?openId=${toUser}	
</#if>
	]]></Content>
</xml>