package com.codenotfound.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codenotfound.services.helloworld.HelloWorldPortType;
import com.codenotfound.types.helloworld.Greeting;
import com.codenotfound.types.helloworld.ObjectFactory;
import com.codenotfound.types.helloworld.Person;

public class HelloWorldImpl implements HelloWorldPortType {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(HelloWorldImpl.class);

    @Override
    public Greeting sayHello(Person request) {
        LOGGER.info(
                "Endpoint received person=[firstName:{},lastName:{}]",
                request.getFirstName(), request.getLastName());

        String greeting = "Hello " + request.getFirstName() + " "
                + request.getLastName() + "!";

        ObjectFactory factory = new ObjectFactory();
        Greeting response = factory.createGreeting();
        response.setGreeting(greeting);

        LOGGER.info("Endpoint sending greeting=[{}]",
                response.getGreeting());
        return response;
    }
}
