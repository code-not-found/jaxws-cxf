package com.codenotfound.soap.http.cxf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Properties;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.interceptor.security.JAASLoginInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.codenotfound.services.helloworld.Person;

public class HelloWorldEndpointImplTest {

    private static String ENDPOINT_ADDRESS = "http://localhost:9090/cnf/services/helloworld";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Properties properties = System.getProperties();
        // set the 'java.security.auth.login.config' property with the location
        // of the JAAS login configuration
        properties.setProperty("java.security.auth.login.config",
                "./src/test/resources/jaas-login.conf");

        JaxWsServerFactoryBean jaxWsServerFactoryBean = new JaxWsServerFactoryBean();
        jaxWsServerFactoryBean.setServiceBean(new HelloWorldEndpointImpl());
        jaxWsServerFactoryBean.setAddress(ENDPOINT_ADDRESS);

        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        jaxWsServerFactoryBean.getFeatures().add(loggingFeature);

        // create a JAASLoginInterceptor to enable authentication
        JAASLoginInterceptor jaasLoginInterceptor = new JAASLoginInterceptor();
        // set the context to lookup from 'java.security.auth.login.config'
        jaasLoginInterceptor.setContextName("jaasContext");
        // add the JAASLoginInterceptor to the server
        jaxWsServerFactoryBean.getInInterceptors().add(jaasLoginInterceptor);

        jaxWsServerFactoryBean.create();
    }

    @AfterClass
    public static void setUpAfterClass() throws Exception {
        // clear the 'java.security.auth.login.config' property
        System.clearProperty("java.security.auth.login.config");
    }

    @Test
    public void testSayHelloProxy() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Watson");

        assertEquals("Hello John Watson!", new HelloWorldClientImplMock(
                ENDPOINT_ADDRESS).sayHello(person, "john.watson", "w4750n"));
    }

    @Test
    public void testSayHelloNoCredentials() {
        Person person = new Person();
        person.setFirstName("Marie");
        person.setLastName("Hudson");

        try {
            new HelloWorldClientImplMock(ENDPOINT_ADDRESS).sayHello(person);
            fail("no credentials should fail");

        } catch (SOAPFaultException soapFaultException) {
            assertEquals(
                    "Authentication required but no user or password was supplied",
                    soapFaultException.getFault().getFaultString());
        }
    }

    @Test
    public void testSayHelloUnknownUser() {
        Person person = new Person();
        person.setFirstName("Mycroft");
        person.setLastName("Holmes");

        try {
            new HelloWorldClientImplMock(ENDPOINT_ADDRESS).sayHello(person,
                    "mycroft.holmes", "h0lm35");
            fail("unknown user should fail");

        } catch (SOAPFaultException soapFaultException) {
            assertEquals(
                    "Authentication failed (details can be found in server log)",
                    soapFaultException.getFault().getFaultString());
        }
    }

    @Test
    public void testSayHelloIncorrectPassword() {
        Person person = new Person();
        person.setFirstName("Jim");
        person.setLastName("Moriarty");

        try {
            new HelloWorldClientImplMock(ENDPOINT_ADDRESS).sayHello(person,
                    "jim.moriarty", "moriarty");
            fail("incorrect password should fail");

        } catch (SOAPFaultException soapFaultException) {
            assertEquals(
                    "Authentication failed (details can be found in server log)",
                    soapFaultException.getFault().getFaultString());
        }
    }
}
