package com.codenotfound.soap.http.cxf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.xml.ws.WebServiceException;

import org.junit.Test;

import com.codenotfound.services.helloworld.Person;

public class HelloWorldEndpointImplIT {

    private static String ENDPOINT_ADDRESS = "http://localhost:9090/cnf/services/secured/helloworld";

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

        } catch (WebServiceException webServiceException) {
            assertEquals(
                    "HTTP response '401: Unauthorized' when communicating with http://localhost:9090/cnf/services/secured/helloworld",
                    webServiceException.getCause().getLocalizedMessage());
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

        } catch (WebServiceException webServiceException) {
            assertEquals(
                    "HTTP response '401: Unauthorized' when communicating with http://localhost:9090/cnf/services/secured/helloworld",
                    webServiceException.getCause().getLocalizedMessage());
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

        } catch (WebServiceException webServiceException) {
            assertEquals(
                    "HTTP response '401: Unauthorized' when communicating with http://localhost:9090/cnf/services/secured/helloworld",
                    webServiceException.getCause().getLocalizedMessage());
        }
    }
}
