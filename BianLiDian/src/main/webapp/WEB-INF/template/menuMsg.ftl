<xml>
	<ToUserName><![CDATA[${toUser}]]></ToUserName>
	<FromUserName><![CDATA[${fromUser}]]></FromUserName>
	<CreateTime>${createTime}</CreateTime>
<#if eventKey=="V1000_BIANLIDIAN_GOODS">
	<MsgType><![CDATA[news]]></MsgType>
<ArticleCount>7</ArticleCount>
<Articles>
 <item>
 <Title><![CDATA[超级便利店【使用指南】]]></Title> 
 <Description><![CDATA[超级便利店【使用指南】]]></Description>
 <PicUrl><![CDATA[http://mmsns.qpic.cn/mmsns/IKPicpUNEibPVWA4WzXAGqy5iaCnxammgaPU9AhGcicdc2U0WHvCJ6poBQ/0]]></PicUrl>
 <Url><![CDATA[]]></Url>
 </item>
 <item>
 <Title><![CDATA[【零食】嘴巴不能停                                  ╭(╯^╰)╮]]></Title>
 <Description><![CDATA[【零食】嘴巴不能停                                  ╭(╯^╰)╮]]></Description>
 <PicUrl><![CDATA[http://mmsns.qpic.cn/mmsns/IKPicpUNEibPVWA4WzXAGqy5iaCnxammgaPAFCeicF4RYAYiaecOricmH2mw/0]]></PicUrl>
 <Url><![CDATA[http://bianlidian.guangshenbian.com/BianLiDian/list/1?openId=${toUser}]]></Url>
 </item>
 <item>
 <Title><![CDATA[【熟食】辣得停不下来                                  ~(￣3￣)~]]></Title>
 <Description><![CDATA[【熟食】辣得停不下来                                  ~(￣3￣)~]]></Description>
 <PicUrl><![CDATA[http://mmsns.qpic.cn/mmsns/IKPicpUNEibPVWA4WzXAGqy5iaCnxammgaPEVHLzQHLn2pd3M8TtZcwKA/0]]></PicUrl>
 <Url><![CDATA[http://bianlidian.guangshenbian.com/BianLiDian/list/2?openId=${toUser}]]></Url>
 </item>
 <item>
 <Title><![CDATA[【泡面】赶紧把肚子填满                             ~(￣▽￣)~]]></Title>
 <Description><![CDATA[【泡面】赶紧把肚子填满                             ~(￣▽￣)~]]></Description>
 <PicUrl><![CDATA[http://mmsns.qpic.cn/mmsns/IKPicpUNEibPVWA4WzXAGqy5iaCnxammgaPvWFhV7ic42OV7KLwrCZo57A/0]]></PicUrl>
 <Url><![CDATA[http://bianlidian.guangshenbian.com/BianLiDian/list/4?openId=${toUser}]]></Url>
 </item>
 <item>
 <Title><![CDATA[【饮料】水是生命之源                                  O(∩_∩)O]]></Title>
 <Description><![CDATA[【饮料】水是生命之源                                  O(∩_∩)O]]></Description>
 <PicUrl><![CDATA[http://mmsns.qpic.cn/mmsns/IKPicpUNEibPVWA4WzXAGqy5iaCnxammgaPFQeuvZfUr0SDq4bf8yWz0A/0]]></PicUrl>
 <Url><![CDATA[http://bianlidian.guangshenbian.com/BianLiDian/list/3?openId=${toUser}]]></Url>
 </item>
 <item>
 <Title><![CDATA[【水果】这里全都是维C                              ╮(￣▽￣)╭]]></Title>
 <Description><![CDATA[【水果】这里全都是维C                              ╮(￣▽￣)╭]]></Description>
 <PicUrl><![CDATA[http://mmsns.qpic.cn/mmsns/IKPicpUNEibPVWA4WzXAGqy5iaCnxammgaPS1SW63D1tjjurU3dLIgHOg/0]]></PicUrl>
 <Url><![CDATA[http://bianlidian.guangshenbian.com/BianLiDian/list/4?openId=${toUser}]]></Url>
 </item>
  <item>
 <Title><![CDATA[【“日”用品】你懂的                                   (#￣▽￣#)]]></Title>
 <Description><![CDATA[【“日”用品】你懂的                                   (#￣▽￣#)]]></Description>
 <PicUrl><![CDATA[http://mmsns.qpic.cn/mmsns/IKPicpUNEibPVWA4WzXAGqy5iaCnxammgaPEYd7MVAqxb8EX9Ya75Rb9w/0]]></PicUrl>
 <Url><![CDATA[http://bianlidian.guangshenbian.com/BianLiDian/list/4?openId=${toUser}]]></Url>
 </item>
</Articles>
<#elseif eventKey=="V2000_BIANLIDIAN_CART">
	<MsgType><![CDATA[news]]></MsgType>
<ArticleCount>1</ArticleCount>
<Articles>
 <item>
 <Title><![CDATA[猛戳这里进入【购物车】！猛戳！！]]></Title>
 <Description><![CDATA[【您选的商品都在这里】]]></Description>
 <PicUrl><![CDATA[http://mmsns.qpic.cn/mmsns/IKPicpUNEibPWu0zklicdMuN9LZepA2eUpHpxcBuSwzGn9ibxNgNMweFeg/0]]></PicUrl>
 <Url><![CDATA[http://bianlidian.guangshenbian.com/BianLiDian/shoppingCart?openId=${toUser}]]></Url>
 </item>
</Articles>
</#if>
</xml>