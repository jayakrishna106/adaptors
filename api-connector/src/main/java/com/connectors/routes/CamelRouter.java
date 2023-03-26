package com.connectors.routes;

import com.connectors.model.APIFields;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.Files.*;


@Component
public class CamelRouter extends RouteBuilder {
    
    @Autowired
    private Environment env;
    
    @Value("${camel.servlet.mapping.context-path}")
    private String contextPath;



    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .enableCORS(true)
                .port(env.getProperty("server.port", "8080"))
                .contextPath(contextPath.substring(0, contextPath.length() - 2))
                // turn on openapi api-doc
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "User API")
                .apiProperty("api.version", "1.0.0");

        /**
         * method
         * description
         * consumes * media type
         * produces * media type
         * response message
         * response code
         *
         * exception
         *  * response code
         *  * response message
         *
         * to queue
         *
         */
        Arrays.stream(getApis()).toList().stream().forEach(x -> {

                    rest().produces(x.getProduces())
                            .consumes(x.getConsumes())
                            .get(x.getPath())
                            .responseMessage(x.getResponseCode(),x.getResponseMessage())
                            .to(x.getTo());

                    from(x.getTo())
                            .routeId("test").log("got the message")
                            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204))
                            .setBody(constant("Abcslsdjsdfs"));

                }
        );



    }

    APIFields[] getApis() throws IOException {
        JsonParser objectMapper = new ObjectMapper().createParser(ResourceUtils.getFile("classpath:sample.json"));
        return objectMapper.readValueAs(APIFields[].class);
    }

}
