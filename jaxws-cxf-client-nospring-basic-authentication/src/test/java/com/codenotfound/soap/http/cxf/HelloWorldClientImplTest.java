package com.codenotfound.soap.http.cxf;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.apache.cxf.interceptor.security.JAASLoginInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.codenotfound.services.helloworld.Person;

public class HelloWorldClientImplTest {

    private static String ENDPOINT_ADDRESS = "http://localhost:9090/cnf/services/helloworld";

    private static HelloWorldClientImpl helloWorldClientImpl;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        JaxWsServerFactoryBean jaxWsServerFactoryBean = new JaxWsServerFactoryBean();
        jaxWsServerFactoryBean.setServiceBean(new HelloWorldServerImplMock());
        jaxWsServerFactoryBean.setAddress(ENDPOINT_ADDRESS);

        Properties properties = System.getProperties();
        // set the 'java.security.auth.login.config' property with the location
        // of the JAAS login configuration
        properties.setProperty("java.security.auth.login.config",
                "./src/test/resources/jaas-login.conf");
        // create a JAASLoginInterceptor to enable authentication
        JAASLoginInterceptor jaasLoginInterceptor = new JAASLoginInterceptor();
        // set the context to lookup from 'java.security.auth.login.config'
        jaasLoginInterceptor.setContextName("jaasContext");
        // add the JAASLoginInterceptor to the server
        jaxWsServerFactoryBean.getInInterceptors().add(jaasLoginInterceptor);

        jaxWsServerFactoryBean.create();

        helloWorldClientImpl = new HelloWorldClientImpl(
                new HelloWorldBusImpl().getBus());
    }

    @AfterClass
    public static void setUpAfterClass() throws Exception {
        // clear the 'java.security.auth.login.config' property
        System.clearProperty("java.security.auth.login.config");
    }

    @Test
    public void testSayHello() {
        Person person = new Person();
        person.setFirstName("Sherlock");
        person.setLastName("Holmes");

        assertEquals("Hello Sherlock Holmes!", helloWorldClientImpl.sayHello(
                person, "sherlock.holmes", "h0lm35"));
    }
}
