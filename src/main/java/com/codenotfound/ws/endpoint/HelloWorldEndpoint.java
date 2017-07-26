package com.codenotfound.ws.endpoint;

import java.math.BigInteger;

import javax.xml.bind.JAXBElement;

import org.example.ticketagent.TFlightsResponse;
import org.example.ticketagent.TListFlights;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.codenotfound.types.helloworld.Greeting;
import com.codenotfound.types.helloworld.ObjectFactory;
import com.codenotfound.types.helloworld.Person;


@Endpoint
public class HelloWorldEndpoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldEndpoint.class);

  private static final String NAMESPACE_URI = "http://codenotfound.com/types/helloworld";

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "person")
  @ResponsePayload
  public Greeting sayHello(@RequestPayload Person request) {
    LOGGER.info("Endpoint received person[firstName={},lastName={}]", request.getFirstName(),
        request.getLastName());

    String greeting = "Hello " + request.getFirstName() + " " + request.getLastName() + "!";

    ObjectFactory factory = new ObjectFactory();
    Greeting response = factory.createGreeting();
    response.setGreeting(greeting);

    LOGGER.info("Endpoint sending greeting='{}'", response.getGreeting());
    return response;
  }
  
  @PayloadRoot(namespace = "http://example.org/TicketAgent.xsd", localPart = "listFlightsRequest")
  @ResponsePayload
  public JAXBElement<TFlightsResponse> listFlights(
      @RequestPayload JAXBElement<TListFlights> request) {

    ObjectFactory factory = new ObjectFactory();
    TFlightsResponse tFlightsResponse = factory.createTFlightsResponse();
    tFlightsResponse.getFlightNumber().add(BigInteger.valueOf(101));

    return factory.createListFlightsResponse(tFlightsResponse);
  }
  
}
