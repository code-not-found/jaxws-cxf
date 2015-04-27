package com.codenotfound.soap.http.cxf.context;

import java.util.ArrayList;

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
// load the CXF bean definitions (which contain a CXF Bus) in the Spring context
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
@PropertySource(value = "classpath:cxf.properties")
public class CxfClient {

    @Autowired
    private Environment environment;

    // auto-wire the CXF Bus created by the 'cxf.cml' import
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
        // set the CXF bus which has the loggingFeature added
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

    // add the loggingFeature to the auto-wired CXF bus
    @Bean(name = "bus")
    Bus bus() {
        ArrayList<Feature> features = new ArrayList<Feature>();
        features.add(loggingFeature());
        cxf.setFeatures(features);

        return cxf;
    }
}
