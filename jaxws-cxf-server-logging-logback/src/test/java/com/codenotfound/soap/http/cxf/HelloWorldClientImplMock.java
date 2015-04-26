package com.codenotfound.soap.http.cxf;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.codenotfound.services.helloworld.Greeting;
import com.codenotfound.services.helloworld.HelloWorldPortType;
import com.codenotfound.services.helloworld.Person;

// the mock implementation of the Hello World client
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
}
