package com.codenotfound.soap.http.cxf;

import static org.junit.Assert.assertEquals;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.junit.BeforeClass;
import org.junit.Test;

import com.codenotfound.services.helloworld.Greeting;
import com.codenotfound.services.helloworld.HelloWorldPortType;
import com.codenotfound.services.helloworld.Person;

public class HelloWorldImplTest {

    private static String ENDPOINT_ADDRESS = "http://localhost:9080/codenotfound/services/helloworld";

    private static HelloWorldPortType helloWorldRequesterProxy;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        createServerEndpoint();
        helloWorldRequesterProxy = createClientProxy();
    }

    @Test
    public void testSayHelloProxy() {
        Person person = new Person();
        person.setFirstName("Jane");
        person.setLastName("Doe");

        Greeting greeting = helloWorldRequesterProxy.sayHello(person);
        assertEquals("Hello Jane Doe!", greeting.getText());
    }

    private static void createServerEndpoint() {
        JaxWsServerFactoryBean jaxWsServerFactoryBean = new JaxWsServerFactoryBean();

        // adding loggingFeature to print the received/sent messages
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);

        jaxWsServerFactoryBean.getFeatures().add(loggingFeature);

        HelloWorldImpl implementor = new HelloWorldImpl();
        jaxWsServerFactoryBean.setServiceBean(implementor);
        jaxWsServerFactoryBean.setAddress(ENDPOINT_ADDRESS);
        jaxWsServerFactoryBean.create();
    }

    private static HelloWorldPortType createClientProxy() {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();

        // adding loggingFeature to print the received/sent messages
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);

        jaxWsProxyFactoryBean.getFeatures().add(loggingFeature);
        jaxWsProxyFactoryBean.setServiceClass(HelloWorldPortType.class);
        jaxWsProxyFactoryBean.setAddress(ENDPOINT_ADDRESS);

        return (HelloWorldPortType) jaxWsProxyFactoryBean.create();
    }
}
