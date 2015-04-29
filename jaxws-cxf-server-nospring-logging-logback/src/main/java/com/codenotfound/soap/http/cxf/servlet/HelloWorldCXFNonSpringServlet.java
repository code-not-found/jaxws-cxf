package com.codenotfound.soap.http.cxf.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.xml.ws.Endpoint;

import org.apache.cxf.feature.Feature;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import com.codenotfound.soap.http.cxf.HelloWorldEndpointImpl;

public class HelloWorldCXFNonSpringServlet extends CXFNonSpringServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void loadBus(ServletConfig servletConfig) {
        super.loadBus(servletConfig);

        // add the loggingFeature
        addFeatures();

        Endpoint.publish("/helloworld", new HelloWorldEndpointImpl());
    }

    private void addFeatures() {
        List<Feature> features = new ArrayList<Feature>();

        // create a loggingFeature that will log all received/sent messages
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        features.add(loggingFeature);

        // set the features on the CXF bus
        bus.setFeatures(features);
    }
}
