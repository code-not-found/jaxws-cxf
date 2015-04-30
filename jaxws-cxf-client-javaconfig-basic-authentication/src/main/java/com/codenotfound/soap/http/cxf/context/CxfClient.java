package com.codenotfound.soap.http.cxf.context;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.Bus;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.codenotfound.services.helloworld.HelloWorldPortType;
import com.codenotfound.soap.http.cxf.HelloWorldClientImpl;

@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
@PropertySource(value = "classpath:cxf.properties")
public class CxfClient {

    @Autowired
    private Environment environment;

    @Autowired
    Bus cxf;

    @Bean(name = "helloWorldClientImplBean")
    public HelloWorldClientImpl helloWorldClientImpl() {
        return new HelloWorldClientImpl();
    }

    @Bean(name = "helloWorldJaxWsProxyBean")
    public HelloWorldPortType helloWorldjaxWsProxyFactoryBean() {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(HelloWorldPortType.class);
        jaxWsProxyFactoryBean.setAddress(environment
                .getProperty("helloworld.address"));
        jaxWsProxyFactoryBean.setBus(bus());

        // set the user name for basic authentication
        jaxWsProxyFactoryBean.setUsername(environment
                .getProperty("client.username"));
        // set the password for basic authentication
        jaxWsProxyFactoryBean.setPassword(environment
                .getProperty("client.password"));

        return (HelloWorldPortType) jaxWsProxyFactoryBean.create();
    }

    @Bean(name = "loggingFeature")
    LoggingFeature loggingFeature() {
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);

        return loggingFeature;
    }

    @Bean(name = "bus")
    Bus bus() {
        List<Feature> features = new ArrayList<Feature>();
        features.add(loggingFeature());
        cxf.setFeatures(features);

        return cxf;
    }
}
