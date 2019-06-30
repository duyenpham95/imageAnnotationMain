package com.duyen.inheritancemapping;

import com.duyen.inheritancemapping.entities.joined.Check;
import com.duyen.inheritancemapping.entities.joined.CreditCard;
import com.duyen.inheritancemapping.repos.PaymentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JoinedTest {

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    public void testCreateCheckPayment() {
        Check check = new Check();
        check.setValue(10000d);
        check.setCheckNumber("Check-number-1");

        paymentRepository.save(check);
    }

    @Test
    public void testCreateCreditCardPayment() {
        CreditCard creditCard = new CreditCard();
        creditCard.setValue(5000d);
        creditCard.setCardNumber("Credit-number-1");

        paymentRepository.save(creditCard);
    }
}
