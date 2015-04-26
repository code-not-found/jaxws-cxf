package com.codenotfound.soap.http.cxf;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.codenotfound.services.helloworld.Person;

public class HelloWorldEndpointImplIT {

    private static String ENDPOINT_ADDRESS = "http://localhost:9090/cnf/services/helloworld";

    private static HelloWorldClientImplMock helloWorldClientImplMock;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        helloWorldClientImplMock = new HelloWorldClientImplMock(
                ENDPOINT_ADDRESS);
    }

    @Test
    public void testSayHello() {
        Person person = new Person();
        person.setFirstName("Jim");
        person.setLastName("Moriarty");

        assertEquals("Hello Jim Moriarty!",
                helloWorldClientImplMock.sayHello(person));
    }
}
