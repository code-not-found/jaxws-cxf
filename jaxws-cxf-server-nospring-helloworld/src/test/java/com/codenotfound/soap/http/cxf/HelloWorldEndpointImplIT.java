package com.codenotfound.soap.http.cxf;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.codenotfound.services.helloworld.Person;

public class HelloWorldEndpointImplIT {

    private static String ENDPOINT_ADDRESS = "http://localhost:9090/cnf/services/helloworld";

    @Test
    public void testSayHello() {
        Person person = new Person();
        person.setFirstName("Jim");
        person.setLastName("Moriarty");

        assertEquals("Hello Jim Moriarty!", new HelloWorldClientImplMock(
                ENDPOINT_ADDRESS).sayHello(person));
    }
}
