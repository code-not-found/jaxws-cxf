package com.codenotfound.soap.http.cxf.context;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.codenotfound.services.helloworld.HelloWorldPortType;
import com.codenotfound.soap.http.cxf.HelloWorldClientImpl;

@Configuration
// load the CXF properties file that contains the endpoint address
@PropertySource(value = "classpath:cxf.properties")
public class CxfClient {

    // autowire the Spring environment
    @Autowired
    private Environment environment;

    // client implementation that uses the helloWorldJaxWsProxyBean to call the
    // Hello World service
    @Bean(name = "helloWorldClientImplBean")
    public HelloWorldClientImpl helloWorldClientImpl() {
        return new HelloWorldClientImpl();
    }

    // create a Hello World JAX-WS proxy
    @Bean(name = "helloWorldJaxWsProxyBean")
    public HelloWorldPortType helloWorldjaxWsProxyFactoryBean() {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(HelloWorldPortType.class);
        jaxWsProxyFactoryBean.setAddress(environment
                .getProperty("helloworld.address"));

        return (HelloWorldPortType) jaxWsProxyFactoryBean.create();
    }
}
