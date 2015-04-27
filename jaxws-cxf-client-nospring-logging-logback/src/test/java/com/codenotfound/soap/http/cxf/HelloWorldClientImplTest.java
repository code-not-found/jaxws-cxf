package com.codenotfound.soap.http.cxf;

import static org.junit.Assert.assertEquals;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
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
        jaxWsServerFactoryBean.create();

        // create a CXF bus that has a loggingFeature
        HelloWorldBusImpl helloWorldBusImpl = new HelloWorldBusImpl();
        helloWorldClientImpl = new HelloWorldClientImpl(
                helloWorldBusImpl.getBus());
    }

    @Test
    public void testSayHello() {
        Person person = new Person();
        person.setFirstName("Sherlock");
        person.setLastName("Holmes");

        assertEquals("Hello Sherlock Holmes!",
                helloWorldClientImpl.sayHello(person));
    }
}
