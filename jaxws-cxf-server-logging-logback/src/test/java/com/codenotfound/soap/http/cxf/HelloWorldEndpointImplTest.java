package com.codenotfound.soap.http.cxf;

import static org.junit.Assert.assertEquals;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.junit.BeforeClass;
import org.junit.Test;

import com.codenotfound.services.helloworld.Person;

public class HelloWorldEndpointImplTest {

    private static String ENDPOINT_ADDRESS = "http://localhost:9090/cnf/services/helloworld";

    private static HelloWorldClientImplMock helloWorldClientImplMock;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        JaxWsServerFactoryBean jaxWsServerFactoryBean = new JaxWsServerFactoryBean();
        jaxWsServerFactoryBean.setServiceBean(new HelloWorldEndpointImpl());
        jaxWsServerFactoryBean.setAddress(ENDPOINT_ADDRESS);
        // create a loggingFeature that will log all received/sent messages
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        // add the loggingFeature to the jaxWsServerFactoryBean
        jaxWsServerFactoryBean.getFeatures().add(loggingFeature);
        jaxWsServerFactoryBean.create();

        helloWorldClientImplMock = new HelloWorldClientImplMock(
                ENDPOINT_ADDRESS);
    }

    @Test
    public void testSayHelloProxy() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Watson");

        assertEquals("Hello John Watson!",
                helloWorldClientImplMock.sayHello(person));
    }
}
