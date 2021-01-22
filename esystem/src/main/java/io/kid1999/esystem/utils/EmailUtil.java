package io.kid1999.esystem.utils;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author kid1999
 * @date 2020/9/30 20:36
 * @title 邮件工具类
 */
@Log
@Service
public class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;

    // 注入常量
    @Value("${mail.fromMail.addr}")
    private String from;

    /**
     * 发送文本邮件
     * @param toAddr
     * @param title
     * @param content
     */

    @Async("taskExecutor")
    public void sendTextMail(String toAddr, String title, String content) {
        // 纯文本邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(toAddr);
        message.setSubject(title);
        message.setText(content);

        try {
            mailSender.send(message);
            log.info("Text邮件已经发送。");
        } catch (Exception e) {
            log.warning("发送Text邮件时发生异常！");
        }

    }

    /**
     * 发送html邮件
     * @param toAddr
     * @param title
     * @param content
     */
    @Async("taskExecutor")
    public void sendHtmlMail(String toAddr, String title, String content) {
        // html 邮件对象
        MimeMessage message = mailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toAddr);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);
            log.info("html邮件发送成功");
        } catch (MessagingException e) {
            log.warning("发送html邮件时发生异常！");
        }
    }


    /**
     * 发送带附件的邮件
     * @param toAddr
     * @param title
     * @param content
     * @param filePath
     */
    @Async("taskExecutor")
    public void sendAttachmentsMail(String toAddr, String title, String content, String filePath){
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toAddr);
            helper.setSubject(title);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            //helper.addAttachment("test"+fileName, file);

            mailSender.send(message);
            log.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            log.warning("发送带附件的邮件时发生异常！");
        }
    }


    /**
     * 发送正文中有静态资源（图片）的邮件
     * @param toAddr
     * @param title
     * @param content
     * @param rscPath
     * @param rscId
     */
    @Async("taskExecutor")
    public void sendInlineResourceMail(String toAddr, String title, String content, String rscPath, String rscId){
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toAddr);
            helper.setSubject(title);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            mailSender.send(message);
            log.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            log.warning("发送嵌入静态资源的邮件时发生异常！");
        }
    }


    /**
     * 发送邮箱验证码
     */

    @Async("taskExecutor")
    public void sendMailCode(String toAddr, String title, String mailCode) {
        log.info("发送邮箱验证码");
        // html 邮件对象
        MimeMessage message = mailSender.createMimeMessage();

        String content="<html>\n" +
                "<body>\n" +
                "    <h3>您的验证码是："+ mailCode +"</h3>\n" +
                "</body>\n" +
                "</html>";

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toAddr);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);
            log.info("html邮件发送成功");
        } catch (MessagingException e) {
            log.warning("发送html邮件时发生异常！");
        }
    }

    @Async("taskExecutor")
    public void sendMailByUserToUser(String srcname, String toname, String email, String reply) {
        log.info("回复邮件信息");
        // html 邮件对象
        MimeMessage message = mailSender.createMimeMessage();

        String content="<html>\n" +
                "<body>\n" +
                "    <h2>来自："+ srcname +"的回复</h2>\n" +
                "<p>" + toname +"</p>" +
                "<p>" + reply +"</p>" +
                "</body>\n" +
                "</html>";

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("来自：" + srcname +"的回复");
            helper.setText(content, true);
            mailSender.send(message);
            log.info("html邮件发送成功");
        } catch (MessagingException e) {
            log.warning("发送html邮件时发生异常！");
        }
    }
}