package com.codenotfound.soap.http.cxf;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.codenotfound.services.helloworld.Greeting;
import com.codenotfound.services.helloworld.HelloWorldPortType;
import com.codenotfound.services.helloworld.Person;

/**
 * mock implementation of the Hello World client
 */
public class HelloWorldClientImplMock {

    private HelloWorldPortType helloWorldClientProxy;

    public HelloWorldClientImplMock(String endpointAddress) {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(HelloWorldPortType.class);
        jaxWsProxyFactoryBean.setAddress(endpointAddress);

        helloWorldClientProxy = (HelloWorldPortType) jaxWsProxyFactoryBean
                .create();
    }

    public String sayHello(Person person) {
        Greeting greeting = helloWorldClientProxy.sayHello(person);
        String result = greeting.getText();

        return result;
    }

    public String sayHello(Person person, String userName, String password) {
        // set the user credentials
        setBasicAuthentication(userName, password);

        return sayHello(person);
    }

    private void setBasicAuthentication(String userName, String password) {
        // the BindingProvider provides access to the protocol binding and
        // associated context objects for request/response message processing
        BindingProvider bindingProvider = (BindingProvider) helloWorldClientProxy;
        // set the user name for basic authentication
        bindingProvider.getRequestContext().put(
                BindingProvider.USERNAME_PROPERTY, userName);
        // set the password for basic authentication
        bindingProvider.getRequestContext().put(
                BindingProvider.PASSWORD_PROPERTY, password);
    }
}
