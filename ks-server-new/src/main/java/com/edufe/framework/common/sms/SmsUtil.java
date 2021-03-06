package com.edufe.framework.common.sms;

import java.net.URLEncoder;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * 短信服务类
 * @author yangchao
 *
 */
@Configuration
@EnableCaching
public class SmsUtil{

	private static Logger log = (Logger) Logger.getInstance(SmsUtil.class);	
	// 用户名
	@Value("${spring.sms.accesskey}")
	private String accesskey;
	// 密码
	@Value("${spring.sms.accesssecret}")
	private String accessSecret;
	// 签名
//	@Value("${spring.sms.sign}")
//	private String sign;
	
	//找回密码模板
	public static final String TEMPLATE_PWD = "2007";
	
	//普通短信
    public boolean sendsms(String telephone, String text, String templateId,String sign) throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://api.1cloudsp.com/api/v2/send");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());

        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", sign),
                new NameValuePair("templateId", templateId),
                new NameValuePair("mobile", telephone),
                new NameValuePair("content", URLEncoder.encode(text, "utf-8"))//（示例模板：{1}您好，您的订单于{2}已通过{3}发货，运单号{4}）
        };
//        NameValuePair[] data = {
//        		new NameValuePair("accesskey", accesskey),
//        		new NameValuePair("secret", accessSecret),
//        		new NameValuePair("sign", "123"),
//        		new NameValuePair("templateId", "100"),
//        		new NameValuePair("mobile", "13900000001,13900000002"),
//        		new NameValuePair("content", URLEncoder.encode("先生##9:40##快递公司##1234567", "utf-8"))//（示例模板：{1}您好，您的订单于{2}已通过{3}发货，运单号{4}）
//        };
        postMethod.setRequestBody(data);

        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println("statusCode: " + statusCode + ", body: "
                    + postMethod.getResponseBodyAsString());
        return statusCode == 200;
    }
}
