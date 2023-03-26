package com.connectors;

import org.apache.camel.builder.RouteBuilder;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SampleAutowiredAmqpRoute extends RouteBuilder {

    @Autowired JmsConnectionFactory amqpConnectionFactory;
    @Bean
    public org.apache.camel.component.amqp.AMQPComponent amqpConnection() {
        org.apache.camel.component.amqp.AMQPComponent amqp = new org.apache.camel.component.amqp.AMQPComponent();
        amqp.setConnectionFactory(amqpConnectionFactory);
        return amqp;
    }

    @Override
    public void configure() throws Exception {
        from("file:src/main/data?noop=true")
            .to("amqp:queue:SCIENCEQUEUE");

        /*from("timer:bar")
            .setBody(constant("Hello from Camel"))
            .to("amqp:queue:SCIENCEQUEUE");*/
    }

}
