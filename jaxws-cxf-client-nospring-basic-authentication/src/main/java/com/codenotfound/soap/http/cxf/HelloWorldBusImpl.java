package com.codenotfound.soap.http.cxf;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.feature.LoggingFeature;

public class HelloWorldBusImpl {

    private Bus bus;

    public HelloWorldBusImpl() {
        bus = BusFactory.newInstance().createBus();

        addFeatures();
    }

    public Bus getBus() {
        return bus;
    }

    private void addFeatures() {
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);

        List<Feature> features = new ArrayList<Feature>();
        features.add(loggingFeature);
        
        bus.setFeatures(features);
    }
}
