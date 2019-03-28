package com.lmching.mall.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.lmching.mall.model.User;
import com.lmching.mall.property.MallProperties;
import com.lmching.mall.repository.UserRepository;
import com.lmching.mall.service.EmailService;
import com.lmching.mall.service.UserService;

@Controller
public class MallController {
	
	@Autowired
	SessionLocaleResolver localeResolver;
	
	@Autowired
	ResourceBundleMessageSource messageSource;
	
	@Autowired
	MallProperties hkdaProperties;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailService emailService;
	
	@ResponseBody
	@PostMapping("/resetPassword")
	public boolean resetPassword(@RequestParam String resetPasswordCode, @RequestParam String newPassword) {		
		return userService.updatePasswordByResetPasswordCode(newPassword, resetPasswordCode);
	}
	
	@ResponseBody
	@PostMapping("/register")
	public List<String> register(@Valid User newUser, BindingResult result, HttpServletRequest request) {
		List<String> errorFields = new ArrayList<>();
		if (result.hasErrors()) {
			for(FieldError field : result.getFieldErrors()) {
				errorFields.add(field.getField());
			}
		}
		
		if(!User.isValidPassowd(newUser.getPassword())) {
			errorFields.add("password");
		}
		if(!User.isValidPassowd(newUser.getVerifyPassword())) {
			errorFields.add("verifyPassword");
		}
		
		if (!errorFields.contains("password") && !errorFields.contains("verifyPassword")) {
			if(!newUser.getPassword().equals(newUser.getVerifyPassword())) errorFields.add("differentPassword");
		}
		
		User existingUser = userService.findByEmail(newUser.getEmail());
		if(existingUser != null) {
			errorFields.add("userExist");
		}
		
		if(errorFields.size() == 0) {
			userService.addNewUser(newUser, request);
		}
		
		return errorFields;
	}
	
	@GetMapping("/active/{activeCode}")
	public String activeUser(HttpServletRequest request, HttpServletResponse response, @PathVariable String activeCode) {
		if(!StringUtils.isBlank(activeCode)) {
			userService.activeUser(activeCode);
		}		
		return "redirect:/login";
	}
	
	@ResponseBody
	@PostMapping("/doforgetpassword")
	@Transactional
	public boolean forgetPassword(@RequestParam(value="email") String email, HttpServletRequest request) {
		return userService.forgetPassword(email, request);
	}
	
	private TrustManager[] getTrustManager() {
		return new TrustManager[] { new X509TrustManager() {
			public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
			}

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

		} };
	}
	
	private CloseableHttpClient getHttpClient() {
		
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, getTrustManager(), null);
			
			LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(ctx);
			
			CookieStore cookieStore = new BasicCookieStore();
			
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslSocketFactory)
					.setDefaultCookieStore(cookieStore).build();
			return httpclient;			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/ipn", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void ipn(HttpServletRequest request, HttpServletResponse response) {	
		System.out.println("Receive ipn at " + new Date());
		CloseableHttpClient httpClient = getHttpClient();
		HttpPost post = new HttpPost(hkdaProperties.getPaypalIpnUrl());
		post.addHeader("ContentType", "application/x-www-form-urlencoded");
		Enumeration<String> parameterNames = request.getParameterNames();				
		List<NameValuePair> params = new ArrayList<>();
		while(parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			params.add(new BasicNameValuePair(paramName, request.getParameter(paramName)));
			System.out.println(paramName + "->" + request.getParameter(paramName));
		}
		params.add(new BasicNameValuePair("cmd", "_notify-validate"));
		post.setEntity(new UrlEncodedFormEntity(params,Consts.UTF_8));
		try {
			CloseableHttpResponse httpResponse = httpClient.execute(post);			
			HttpEntity httpEntity = httpResponse.getEntity();
			if(httpEntity != null){
				InputStream is = httpEntity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(is,Consts.UTF_8));
				try {
					String line = null;
					while((line=br.readLine())!=null){
						System.out.println(line);
						if("VERIFIED".equals(line)) {
							// Update order status
						}
					}					
				} finally {
					is.close();					
				}
			} else {
				System.out.println("httpEntity is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
