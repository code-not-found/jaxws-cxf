package com.codenotfound.soap.http.cxf.context;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.Bus;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.codenotfound.soap.http.cxf.HelloWorldEndpointImpl;

@Configuration
// load the CXF bean definitions (which contain a CXF Bus) in the Spring context
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
public class CxfServlet {

    // auto-wire the CXF Bus created by the 'cxf.cml' import
    @Autowired
    Bus cxf;

    // create an Endpoint for the CXF Hello World service
    @Bean(name = "helloWorldProviderBean")
    public EndpointImpl helloWorldEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(new HelloWorldEndpointImpl());
        endpoint.setAddress("/helloworld");
        // set the CXF bus which has the loggingFeature added
        endpoint.setBus(bus());
        endpoint.publish();

        return endpoint;
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
        List<Feature> features = new ArrayList<Feature>();
        features.add(loggingFeature());
        cxf.setFeatures(features);

        return cxf;
    }
}
