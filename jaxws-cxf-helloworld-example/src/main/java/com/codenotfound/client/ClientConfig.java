package com.codenotfound.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codenotfound.services.helloworld.HelloWorldPortType;

@Configuration
public class ClientConfig {

    @Value("${helloworld.service.address}")
    private String helloworldServiceAddress;

    @Bean(name = "helloWorldClientProxyBean")
    public HelloWorldPortType opportunityPortType() {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(HelloWorldPortType.class);
        jaxWsProxyFactoryBean.setAddress(helloworldServiceAddress);

        return (HelloWorldPortType) jaxWsProxyFactoryBean.create();
    }

}
