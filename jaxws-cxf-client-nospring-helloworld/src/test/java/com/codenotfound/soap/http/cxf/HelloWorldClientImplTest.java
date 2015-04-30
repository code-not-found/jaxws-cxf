package com.codenotfound.soap.http.cxf;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.junit.BeforeClass;
import org.junit.Test;

import com.codenotfound.services.helloworld.Person;

public class HelloWorldClientImplTest {

    private static String ENDPOINT_ADDRESS = "http://localhost:9090/cnf/services/helloworld";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        /* create a server endpoint for the Hello World service */
        // create a JaxWsServerFactoryBean
        JaxWsServerFactoryBean jaxWsServerFactoryBean = new JaxWsServerFactoryBean();
        // set the mock implementation of the Hello World service
        jaxWsServerFactoryBean.setServiceBean(new HelloWorldServerImplMock());
        // set the address at which the Hello World endpoint will be exposed
        jaxWsServerFactoryBean.setAddress(ENDPOINT_ADDRESS);
        // create the server endpoint
        jaxWsServerFactoryBean.create();
    }

    @Test
    public void testSayHello() throws IOException {
        Person person = new Person();
        person.setFirstName("Sherlock");
        person.setLastName("Holmes");

        assertEquals("Hello Sherlock Holmes!",
                new HelloWorldClientImpl().sayHello(person));
    }
}
