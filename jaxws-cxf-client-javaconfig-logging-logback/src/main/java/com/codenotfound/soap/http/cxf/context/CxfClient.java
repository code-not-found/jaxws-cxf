package com.codenotfound.soap.http.cxf.context;

import java.util.ArrayList;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.codenotfound.services.helloworld.HelloWorldPortType;
import com.codenotfound.soap.http.cxf.HelloWorldClientImpl;

@Configuration
@PropertySource(value = "classpath:cxf.properties")
public class CxfClient {

    @Autowired
    private Environment environment;

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
        // set the CXF bus on the jaxWsProxyFactoryBean
        jaxWsProxyFactoryBean.setBus(bus());

        return (HelloWorldPortType) jaxWsProxyFactoryBean.create();
    }

    // create a loggingFeature that will log all received/sent messages
    @Bean(name = "loggingFeature")
    LoggingFeature loggingFeature() {
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);

        return loggingFeature;
    }

    // create a CXF bus and add the loggingFeature to the CXF bus
    @Bean(name = "bus")
    Bus bus() {
        SpringBusFactory springBusFactory = new SpringBusFactory();
        Bus bus = springBusFactory.createBus();

        ArrayList<Feature> features = new ArrayList<Feature>();
        features.add(loggingFeature());
        bus.setFeatures(features);

        return bus;
    }
}
