package com.test.spring.jms.listeners;


import com.test.spring.jms.model.Email;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailListener {

    @JmsListener(containerFactory = "jmsListenerContainerFactory",destination = "mailbox")
    public void getMessage(Email email, MessageHeaders messageHeaders){
        System.out.println("============================");
        System.out.println(email);
        System.out.println("============================");
        for(Map.Entry<String,Object> entry:messageHeaders.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
        System.out.println("============================");
    }
}
