package com.codenotfound.soap.http.cxf.context;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.codenotfound.soap.http.cxf.HelloWorldEndpointImpl;

@Configuration
// load the CXF bean definitions in the Spring context
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
public class CxfServlet {

    // autowire the CXF bus environment
    @Autowired
    Bus bus;

    // create an Endpoint for the CXF Hello World service
    @Bean(name = "helloWorldProviderBean")
    public EndpointImpl helloWorldEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(new HelloWorldEndpointImpl());
        endpoint.setAddress("/helloworld");
        endpoint.publish();

        return endpoint;
    }
}
