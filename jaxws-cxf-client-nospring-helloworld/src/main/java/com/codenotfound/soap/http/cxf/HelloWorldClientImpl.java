package com.codenotfound.soap.http.cxf;

import java.io.IOException;
import java.util.Properties;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codenotfound.services.helloworld.Greeting;
import com.codenotfound.services.helloworld.HelloWorldPortType;
import com.codenotfound.services.helloworld.Person;

public class HelloWorldClientImpl {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(HelloWorldClientImpl.class);

    private HelloWorldPortType helloWorldJaxWsProxy;

    public HelloWorldClientImpl() throws IOException {
        // load the CXF properties file that contains the endpoint address
        Properties cxfProperties = loadProperties("/cxf.properties");

        /* create a Java proxy to call the Hello World service */
        // create a JaxWsProxyFactoryBean
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        // set the Hello World service class
        jaxWsProxyFactoryBean.setServiceClass(HelloWorldPortType.class);
        // set the endpoint address
        jaxWsProxyFactoryBean.setAddress(cxfProperties
                .getProperty("helloworld.address"));
        // create the Java proxy
        this.helloWorldJaxWsProxy = (HelloWorldPortType) jaxWsProxyFactoryBean
                .create();
    }

    public String sayHello(Person person) {
        Greeting greeting = helloWorldJaxWsProxy.sayHello(person);

        String result = greeting.getText();
        LOGGER.info("result={}", result);
        return result;
    }

    private Properties loadProperties(String file) throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream(file));

        return properties;
    }
}
