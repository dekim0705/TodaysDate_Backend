package com.kh.backend_finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public void sendNotification(String to, String subject, String text) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("devpawcommunity@naver.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // 회원가입 이메일 인증
    private String ePw = createKey();

    // 이메일 작성
    private MimeMessage createEmailMessage(String to) throws Exception {

        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to); // 보내는 대상
        message.setSubject("[오늘의 데이트] 이메일 인증 코드"); // 제목

        String msg="";
        msg += "<p>안녕하세요!</p>";
        msg += "<p>오늘의 데이트 회원가입 완료를 위해</p>";
        msg += "<p>아래 코드를 입력해 주세요.</p>";
        msg += "<h3>이메일 인증 코드: " + ePw + "</h3>";
        msg += "<p>감사합니다.</p>";

        message.setText(msg, "utf-8", "html"); // 내용
        message.setFrom(new InternetAddress("devpawcommunity@naver.com", "오늘의데이트")); // 보내는 사람

        return message;
    }

    // 인증키 생성
    public String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3);
            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }

    // 이메일 보내기
    public String sendSimpleMessage(String to) throws Exception {

        MimeMessage message = createEmailMessage(to); // 메일 발송
        try {// 예외처리
            javaMailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw; // 메일로 보냈던 인증 코드를 서버로 반환
    }

    // 비밀번호 재설정 이메일 작성
    public MimeMessage createPasswordResetMessage(String to) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("[오늘의 데이트] 비밀번호 재설정 링크");

        String msg="";
        msg += "<p>안녕하세요!</p>";
        msg += "<p>오늘의 데이트 재설정된 비밀번호 입니다.</p>";
        msg += "<p>로그인 시 아래 코드를 입력해 주세요.</p>";
        msg += "<h3>임시 비밀번호 : " + ePw + "</h3>";
        msg += "<p>감사합니다.</p>";

        message.setText(msg, "utf-8", "html"); // 내용
        message.setFrom(new InternetAddress("devpawcommunity@naver.com", "오늘의데이트")); // 보내는 사람

        return message;
    }

    // 이메일 보내기
    public String sendPasswordAuthKey(String to) throws Exception {

        MimeMessage message = createPasswordResetMessage(to); // 메일 발송
        try {// 예외처리
            javaMailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw; // 메일로 보냈던 인증 코드를 서버로 반환
    }

    // 인증 링크 보내기
    public void sendEmailWithLink(String to, String subject, String content) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("오늘의데이트 <devpawcommunity@naver.com>");
            helper.setTo(to);
            helper.setSubject(subject);

//            String content = "안녕하세요! 오늘의 데이트 회원가입을 완료하기 위해 아래 링크를 클릭해 주세요 : <br>";
//            content += "<a href=\"" + link + "\">인증하기</a>";
            helper.setText(content, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
