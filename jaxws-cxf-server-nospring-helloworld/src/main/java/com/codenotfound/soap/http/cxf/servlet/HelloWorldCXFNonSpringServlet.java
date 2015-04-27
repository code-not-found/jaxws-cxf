package com.codenotfound.soap.http.cxf.servlet;

import javax.servlet.ServletConfig;
import javax.xml.ws.Endpoint;

import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import com.codenotfound.soap.http.cxf.HelloWorldEndpointImpl;

public class HelloWorldCXFNonSpringServlet extends CXFNonSpringServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void loadBus(ServletConfig servletConfig) {
        // create a bus
        super.loadBus(servletConfig);
        // CXF Endpoint for the Hello World service
        Endpoint.publish("/helloworld", new HelloWorldEndpointImpl());
    }
}
