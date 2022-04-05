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
    public static void sendRewardsEmail(String to, String projectTitle, String rewardName, String rewardId) throws IOException {
        Email sender = new Email("BusinessMagic2@gmail.com");
        Email receiver = new Email(to);

        String subject = "Your FundedLocal Donation";
        String message = MessageFormat.format("Thank you for donating to {0}, you have earned {1}. Your unique identification number is {2}", projectTitle, rewardName, rewardId);
        Content content = new Content("text/plain",message);

        Mail mail = new Mail(sender, subject, receiver, content);

        SendGrid sg = new SendGrid(System.getenv("sendgrid_api_key"));
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex){
            throw ex;
        }
    }

    public static void sendDonorListEmail(String to, String googleSheetsId) throws IOException {
        Email sender = new Email("BusinessMagic2@gmail.com");
        Email receiver = new Email(to);

        String subject = "Congratulations on completing your raise";
        String message = MessageFormat.format("Congratulations on completing your raise. Here is a list of people who funded your project: https://docs.google.com/spreadsheets/d/{0}/edit", googleSheetsId);
        Content content = new Content("text/plain",message);

        Mail mail = new Mail(sender, subject, receiver, content);

        SendGrid sg = new SendGrid(System.getenv("sendgrid_api_key"));
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex){
            throw ex;
        }
    }
}
