package com.javelwilson.nyammingsdb.shared;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.javelwilson.nyammingsdb.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AmazonSES {
    @Value("${application.email}")
    private String FROM = "javelawilson@gmail.com";

    @Value("${application.email.verification.subject}")
    private String EMAIL_VERIFICATION_SUBJECT;

    @Value("${application.email.verification.html}")
    private String EMAIL_VERIFICATION_HTML;

    @Value("${application.email.verification.text}")
    private String EMAIL_VERIFICATION_TEXT;

    @Value("${application.email.password.subject}")
    private String PASSWORD_RESET_SUBJECT;

    @Value("${application.email.password.html}")
    private String PASSWORD_RESET_HTML;

    @Value("${application.email.password.text}")
    private String PASSWORD_RESET_TEXT;


    public void verifyEmail(UserDto userDto) {

//        System.setProperty("aws.accessKeyId", "");
//        System.setProperty("aws.secretKey", "");

        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_EAST_1)
                .build();

        String htmlBodyWithToken = EMAIL_VERIFICATION_HTML.replace("$tokenValue", userDto.getEmailVerificationToken());
        String textBodyWithToken = EMAIL_VERIFICATION_TEXT.replace("$tokenValue", userDto.getEmailVerificationToken());

        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(userDto.getEmail()))
                .withMessage(new Message()
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBodyWithToken))
                                .withText(new Content().withCharset("UTF-8").withData(textBodyWithToken)))
                        .withSubject(new Content().withCharset("UTF-8").withData(EMAIL_VERIFICATION_SUBJECT)))
                .withSource(FROM);

        client.sendEmail(request);

        System.out.println("Email sent!");

    }

    public boolean sendPasswordResetRequest(String firstName, String email, String token)
    {
        boolean returnValue = false;

        AmazonSimpleEmailService client =
                AmazonSimpleEmailServiceClientBuilder.standard()
                        .withRegion(Regions.US_EAST_1).build();

        String htmlBodyWithToken = PASSWORD_RESET_HTML.replace("$tokenValue", token);
        htmlBodyWithToken = htmlBodyWithToken.replace("$firstName", firstName);

        String textBodyWithToken = PASSWORD_RESET_TEXT.replace("$tokenValue", token);
        textBodyWithToken = textBodyWithToken.replace("$firstName", firstName);


        SendEmailRequest request = new SendEmailRequest()
                .withDestination(
                        new Destination().withToAddresses( email ) )
                .withMessage(new Message()
                        .withBody(new Body()
                                .withHtml(new Content()
                                        .withCharset("UTF-8").withData(htmlBodyWithToken))
                                .withText(new Content()
                                        .withCharset("UTF-8").withData(textBodyWithToken)))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData(PASSWORD_RESET_SUBJECT)))
                .withSource(FROM);

        SendEmailResult result = client.sendEmail(request);
        if(result != null && (result.getMessageId()!=null && !result.getMessageId().isEmpty()))
        {
            returnValue = true;
        }

        return returnValue;
    }

}
