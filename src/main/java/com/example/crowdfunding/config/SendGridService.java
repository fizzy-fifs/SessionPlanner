package com.example.crowdfunding.config;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;
import java.text.MessageFormat;

public class SendGridService {
    public static void sendRewardsEmail(String to, String projectTitle, String rewardName, String uuid) throws IOException {
        Email sender = new Email("BusinessMagic2@gmail.com");
        Email receiver = new Email(to);

        String subject = "Your FundedLocal Donation";
        String message = MessageFormat.format("Thank you for donating to {0}, you have earned {1}. Your unique identification number is {2}", projectTitle, rewardName, uuid);
        Content content = new Content("text/plain",message);

        Mail mail = new Mail(sender, subject, receiver, content);

        SendGrid sg = new SendGrid(System.getenv("sendgrid_api_key"));
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (IOException ex){
            throw ex;
        }
    }
}
