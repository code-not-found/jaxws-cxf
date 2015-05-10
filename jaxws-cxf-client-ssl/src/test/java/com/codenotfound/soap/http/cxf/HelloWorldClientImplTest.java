package com.codenotfound.soap.http.cxf;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;

import org.apache.cxf.configuration.jsse.TLSServerParameters;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngineFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codenotfound.services.helloworld.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/cxf-client.xml" })
public class HelloWorldClientImplTest {

    private static String KEY_STORE_PATH_NAME = "./src/test/resources/jks/keystore-server.jks";
    private static String KEY_STORE_PASSWORD = "keystore-server-password";
    private static String PRIVATE_KEY_PASSWORD = "server-privatekey-password";

    private static String ENDPOINT_ADDRESS = "https://localhost:9443/cnf/services/helloworld";

    @Autowired
    private HelloWorldClientImpl helloWorldClientImplBean;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        /*
         * create a JettyHTTPServerEngineFactory to handle the configuration of
         * network port numbers for use with "HTTPS"
         */
        JettyHTTPServerEngineFactory jettyHTTPServerEngineFactory = new JettyHTTPServerEngineFactory();

        // load the key store containing the server certificate
        File keyStoreFile = new File(KEY_STORE_PATH_NAME);
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(keyStoreFile),
                KEY_STORE_PASSWORD.toCharArray());

        // create a key manager to handle the server private/public key pair
        KeyManagerFactory keyManagerFactory = KeyManagerFactory
                .getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, PRIVATE_KEY_PASSWORD.toCharArray());
        KeyManager[] keyManager = keyManagerFactory.getKeyManagers();

        // set the TLSServerParameters on theJettyHTTPServerEngineFactory
        TLSServerParameters tLSServerParameters = new TLSServerParameters();
        tLSServerParameters.setKeyManagers(keyManager);
        jettyHTTPServerEngineFactory.setTLSServerParametersForPort(9443,
                tLSServerParameters);

        JaxWsServerFactoryBean jaxWsServerFactoryBean = new JaxWsServerFactoryBean();
        jaxWsServerFactoryBean.setServiceBean(new HelloWorldServerImplMock());
        jaxWsServerFactoryBean.setAddress(ENDPOINT_ADDRESS);
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
