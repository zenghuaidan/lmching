package com.lmching.mall.service;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.util.Pair;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lmching.mall.property.MallProperties;

 
@Service
public class EmailServiceImpl implements EmailService {
 
	@Autowired
	MallProperties mallProperties;
	
	@Autowired
	private JavaMailSender mailSender;
	
//	@Autowired
//	private VelocityEngine velocityEngine;
	
	public void sendSimpleMail(String sendTo, String subject, String content) {				
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		 
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(mallProperties.getFromMail());
			helper.setTo(sendTo);
			helper.setSubject(subject);
			helper.setText(content, true);			
		} catch (Exception e) {
		}
		
		mailSender.send(mimeMessage);
	}
 
	public void sendAttachmentsMail(String sendTo, String subject, String content, List<Pair<String, File>> attachments) {
 
		MimeMessage mimeMessage = mailSender.createMimeMessage();
 
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(mallProperties.getFromMail());
			helper.setTo(sendTo);
			helper.setSubject(subject);
			helper.setText(content);
 
			for (Pair<String, File> pair : attachments) {
				helper.addAttachment(pair.getFirst(), new FileSystemResource(pair.getSecond()));
			}
		} catch (Exception e) {
		}
 
		mailSender.send(mimeMessage);
	}
 
	public void sendInlineMail() {
 
		MimeMessage mimeMessage = mailSender.createMimeMessage();
 
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(mallProperties.getFromMail());
			helper.setTo("286352250@163.com");
			helper.setSubject("主题：嵌入静态资源");
			helper.setText("<html><body><img src=\"cid:weixin\" ></body></html>", true);
 
			FileSystemResource file = new FileSystemResource(new File("weixin.jpg"));
			helper.addInline("weixin", file);
		} catch (Exception e) {
		}
 
		mailSender.send(mimeMessage);
	}
 
	public void sendTemplateMail(String sendTo, String subject, Map<String, Object> content, List<Pair<String, File>> attachments) {
 
//		MimeMessage mimeMessage = mailSender.createMimeMessage();
// 
//		try {
//			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//			helper.setFrom(emailConfig.getEmailFrom());
//			helper.setTo(sendTo);
//			helper.setSubject(subject);
// 
//			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "template.vm", "UTF-8", content);
//			helper.setText(text, true);
//			
//			for (Pair<String, File> pair : attachments) {
//				helper.addAttachment(pair.getFirst(), new FileSystemResource(pair.getSecond()));
//			}
//		} catch (Exception e) {
//		}
// 
//		mailSender.send(mimeMessage);
	}
}
