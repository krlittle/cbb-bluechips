package Borman.cbbbluechips.email;

import Borman.cbbbluechips.models.paypal.PaypalDonationRequest;
import Borman.cbbbluechips.zdata.PasswordRecoveryData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    private Logger logger = LoggerFactory.getLogger(EmailService.class);

    public boolean sendPasswordRecoveryEmail(String email, String password) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(email);
            helper.setSubject("Password Recovery | CBB Bluechips");
            helper.setText(buildPasswordRecoveryEmailBody(password), true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        logger.info("Sending Password Recovery Email to " + email);

        try {
            javaMailSender.send(message);
            logger.info("Mail sent");
            return true;
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }

    }

    private String buildPasswordRecoveryEmailBody(String password) {
        return PasswordRecoveryData.PASSWORD_RECOVERY_BODY.replace("${password}", password);
    }


    public void sendUpdateEmail(PaypalDonationRequest paypalDonationRequest) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            //TODO remove hardcode to me test
            helper.setTo("bradenborman00@gmail.com");
            helper.setSubject("CBB Bluechips | Donation Confirmed");
            helper.setText(buildDonationConfirmedEmailBody(paypalDonationRequest), true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        logger.info("Sending Confirmed Payment Email to " + paypalDonationRequest.getBuyerEmail());

        try {
            javaMailSender.send(message);
            logger.info("Mail sent");
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    private String buildDonationConfirmedEmailBody(PaypalDonationRequest request) {
        return PasswordRecoveryData.DONATION_CONFIRMED_BODY
                .replace("${order_id}", request.getOrderID())
                .replace("${description}", request.getPurchaseUnits().get(0).getDescription())
                .replace("${amount}", request.getPurchaseUnits().get(0).getAmount().getValue());
    }

}
