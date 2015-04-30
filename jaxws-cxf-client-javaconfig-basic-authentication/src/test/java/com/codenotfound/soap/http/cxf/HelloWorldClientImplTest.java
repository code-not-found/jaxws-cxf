package com.codenotfound.soap.http.cxf;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.apache.cxf.interceptor.security.JAASLoginInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.junit.AfterClass;
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
        Properties properties = System.getProperties();
        // set the location of the 'java.security.auth.login.config'
        properties.setProperty("java.security.auth.login.config",
                "./src/test/resources/jaas-login.conf");

        JaxWsServerFactoryBean jaxWsServerFactoryBean = new JaxWsServerFactoryBean();
        jaxWsServerFactoryBean.setServiceBean(new HelloWorldServerImplMock());
        jaxWsServerFactoryBean.setAddress(ENDPOINT_ADDRESS);

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
        System.clearProperty("java.security.auth.login.config");
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
