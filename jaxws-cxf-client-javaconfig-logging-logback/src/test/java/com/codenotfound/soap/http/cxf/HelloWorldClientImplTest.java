package com.codenotfound.soap.http.cxf;

import static org.junit.Assert.assertEquals;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codenotfound.services.helloworld.Person;
import com.codenotfound.soap.http.cxf.context.CxfClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CxfClient.class)
public class HelloWorldClientImplTest {

    private static String ENDPOINT_ADDRESS = "http://localhost:9090/cnf/services/helloworld";

    @Autowired
    private HelloWorldClientImpl helloWorldClientImplBean;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        /* create a server endpoint for the Hello World service */
        // create a JaxWsServerFactoryBean
        JaxWsServerFactoryBean jaxWsServerFactoryBean = new JaxWsServerFactoryBean();
        // set the mock implementation of the Hello World service
        jaxWsServerFactoryBean.setServiceBean(new HelloWorldServerImplMock());
        // set the endpoint address
        jaxWsServerFactoryBean.setAddress(ENDPOINT_ADDRESS);
        // create the server endpoint
        jaxWsServerFactoryBean.create();
    }

    @Test
    public void testSayHello() {
        Person person = new Person();
        person.setFirstName("Sherlock");
        person.setLastName("Holmes");

        assertEquals("Hello Sherlock Holmes!",
                helloWorldClientImplBean.sayHello(person));
    }
}
