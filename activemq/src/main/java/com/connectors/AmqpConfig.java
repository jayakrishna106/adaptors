package com.connectors;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AmqpConfig {

    @Value("${AMQP_HOST}")
    private String amqpHost;
    @Value("${AMQP_SERVICE_PORT}")
    private String amqpPort;
    @Value("${AMQP_SERVICE_USERNAME}")
    private String userName;
    @Value("${AMQP_SERVICE_PASSWORD}")
    private String pass;
    @Value("${AMQP_REMOTE_URI}")
    private String remoteUri;

    @Bean
    public org.apache.qpid.jms.JmsConnectionFactory amqpConnectionFactory() {
        org.apache.qpid.jms.JmsConnectionFactory jmsConnectionFactory = new org.apache.qpid.jms.JmsConnectionFactory();
        jmsConnectionFactory.setRemoteURI(remoteUri);
        jmsConnectionFactory.setUsername(userName);
        jmsConnectionFactory.setPassword(pass);
        return jmsConnectionFactory;
    }

}
