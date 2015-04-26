package com.codenotfound.soap.http.cxf;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.codenotfound.services.helloworld.Greeting;
import com.codenotfound.services.helloworld.HelloWorldPortType;
import com.codenotfound.services.helloworld.Person;

// the mock implementation of the Hello World client
public class HelloWorldClientImplMock {

    private HelloWorldPortType helloWorldClientProxy;

    public HelloWorldClientImplMock(String endpointAddress) {
        // create a CXF JaxWsProxyFactoryBean for creating JAX-WS proxies
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        // set the service class of the Hello World service
        jaxWsProxyFactoryBean.setServiceClass(HelloWorldPortType.class);
        // set the address at which the Hello World endpoint will be called
        jaxWsProxyFactoryBean.setAddress(endpointAddress);

        // create a JAX-WS proxy for the Hello World service
        helloWorldClientProxy = (HelloWorldPortType) jaxWsProxyFactoryBean
                .create();
    }

    public String sayHello(Person person) {
        Greeting greeting = helloWorldClientProxy.sayHello(person);
        String result = greeting.getText();

        return result;
    }
}
