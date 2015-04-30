package com.codenotfound.soap.http.cxf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.codenotfound.services.helloworld.Greeting;
import com.codenotfound.services.helloworld.HelloWorldPortType;
import com.codenotfound.services.helloworld.Person;

public class HelloWorldClientImpl {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(HelloWorldClientImpl.class);

    @Autowired
    private HelloWorldPortType helloWorldJaxWsProxyBean;

    public String sayHello(Person person) {
        Greeting greeting = helloWorldJaxWsProxyBean.sayHello(person);

        String result = greeting.getText();
        LOGGER.info("result={}", result);
        return result;
    }
}
