package com.codenotfound.soap.http.cxf;

import static org.junit.Assert.assertEquals;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.junit.BeforeClass;
import org.junit.Test;

import com.codenotfound.services.helloworld.Person;
import com.codenotfound.soap.http.cxf.HelloWorldEndpointImpl;

public class HelloWorldEndpointImplTest {

    private static String ENDPOINT_ADDRESS = "http://localhost:9090/cnf/services/helloworld";

    private static HelloWorldClientImplMock helloWorldClientImplMock;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        /* create a server endpoint for the Hello World service */
        // create a JaxWsServerFactoryBean
        JaxWsServerFactoryBean jaxWsServerFactoryBean = new JaxWsServerFactoryBean();
        // set the implementation of the Hello World service
        jaxWsServerFactoryBean.setServiceBean(new HelloWorldEndpointImpl());
        // set the address at which the Hello World endpoint will be exposed
        jaxWsServerFactoryBean.setAddress(ENDPOINT_ADDRESS);
        // create the server
        jaxWsServerFactoryBean.create();

        // create a client mock to make the service call
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
