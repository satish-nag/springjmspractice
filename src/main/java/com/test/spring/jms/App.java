package com.test.spring.jms;

import com.test.spring.jms.model.Email;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

import javax.jms.DeliveryMode;
import javax.jms.Session;
import java.util.concurrent.TimeUnit;

/**
 * author: Satish Nagamalla
 *
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(App.class,args);
        JmsTemplate jmsTemplate = configurableApplicationContext.getBean("jmsTemplate", JmsTemplate.class);
        System.out.println("Sending an email message.");
        jmsTemplate.setExplicitQosEnabled(true);
        jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        jmsTemplate.setTimeToLive(10000);
        jmsTemplate.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        System.out.println("++++++++++++++++++++++++++++"+jmsTemplate.isExplicitQosEnabled());
        jmsTemplate.convertAndSend("mailbox",new Email("satish","Hi satish, How are you"));
        JmsListenerEndpointRegistry jmsListenerEndpointRegistry=configurableApplicationContext.getBean( JmsListenerEndpointRegistry.class);
        for(MessageListenerContainer messageListenerContainer:jmsListenerEndpointRegistry.getListenerContainers()){
            DefaultMessageListenerContainer defaultMessageListenerContainer =
                    (DefaultMessageListenerContainer)messageListenerContainer;
            defaultMessageListenerContainer.shutdown();
        }
        System.out.println("exiting main......................");
    }
}
