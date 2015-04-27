package com.codenotfound.soap.http.cxf;

import java.io.IOException;
import java.util.Properties;

import org.apache.cxf.Bus;
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

    public HelloWorldClientImpl(Bus bus) throws IOException {
        Properties cxfProperties = loadProperties("/cxf.properties");

        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(HelloWorldPortType.class);
        jaxWsProxyFactoryBean.setAddress(cxfProperties
                .getProperty("helloworld.address"));

        // set the CXF bus which has the loggingFeature added
        jaxWsProxyFactoryBean.setBus(bus);
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
