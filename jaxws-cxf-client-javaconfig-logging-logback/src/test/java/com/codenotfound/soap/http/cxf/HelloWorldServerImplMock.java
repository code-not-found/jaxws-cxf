package com.codenotfound.soap.http.cxf;

import com.codenotfound.services.helloworld.Greeting;
import com.codenotfound.services.helloworld.HelloWorldPortType;
import com.codenotfound.services.helloworld.ObjectFactory;
import com.codenotfound.services.helloworld.Person;

//mock implementation of the Hello World service
public class HelloWorldServerImplMock implements HelloWorldPortType {

    @Override
    public Greeting sayHello(Person person) {
        ObjectFactory factory = new ObjectFactory();
        Greeting result = factory.createGreeting();
        result.setText("Hello " + person.getFirstName() + " "
                + person.getLastName() + "!");

        return result;
    }
}
