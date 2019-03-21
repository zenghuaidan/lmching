package com.lmching.mall.service;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.data.util.Pair;
 
public interface EmailService {
 
	/**
	 * 发送简单邮件
	 * @param sendTo 收件人地址
	 * @param titel  邮件标题
	 * @param content 邮件内容
	 */
	public void sendSimpleMail(String sendTo, String subject, String content);
	
	/**
	 * 发送简单邮件
	 * @param sendTo 收件人地址
	 * @param titel  邮件标题
	 * @param content 邮件内容
	 * @param attachments<文件名，附件> 附件列表
	 */
	public void sendAttachmentsMail(String sendTo, String subject, String content, List<Pair<String, File>> attachments);
	
	/**
	 * 发送模板邮件
	 * @param sendTo 收件人地址
	 * @param titel  邮件标题
	 * @param content<key, 内容> 邮件内容
	 * @param attachments<文件名，附件> 附件列表
	 */
	public void sendTemplateMail(String sendTo, String subject, Map<String, Object> content, List<Pair<String, File>> attachments);
	
}
