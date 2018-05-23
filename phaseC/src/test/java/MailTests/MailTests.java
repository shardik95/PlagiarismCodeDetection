package MailTests;

import edu.northeastern.cs5500.mail.MailSender;
import org.junit.Test;

public class MailTests {

    @Test(expected = NullPointerException.class)
    public void MailTest(){
        MailSender ms=new MailSender();
        ms.sendMail("Hello","ruchithams1@gmail.com");

    }
}
