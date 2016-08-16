package com.codenotfound.soap.http.cxf;

import static org.junit.Assert.assertEquals;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codenotfound.services.helloworld.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/META-INF/spring/context-requester.xml" })
public class HelloWorldClientTest {

    private static String ENDPOINT_ADDRESS = "http://localhost:9090/codenotfound/services/helloworld";

    @Autowired
    private HelloWorldClient helloWorldClientBean;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        createServerEndpoint();
    }

    @Test
    public void testSayHello() {
        Person person = new Person();
        person.setFirstName("Jane");
        person.setLastName("Doe");

        assertEquals("Hello Jane Doe!", helloWorldClientBean.sayHello(person));
    }

    private static void createServerEndpoint() {
        JaxWsServerFactoryBean jaxWsServerFactoryBean = new JaxWsServerFactoryBean();

        // create the loggingFeature
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);

        // adding loggingFeature to print the received/sent messages
        jaxWsServerFactoryBean.getFeatures().add(loggingFeature);

        HelloWorldImpl implementor = new HelloWorldImpl();
        jaxWsServerFactoryBean.setServiceBean(implementor);
        jaxWsServerFactoryBean.setAddress(ENDPOINT_ADDRESS);

        jaxWsServerFactoryBean.create();
    }
}
