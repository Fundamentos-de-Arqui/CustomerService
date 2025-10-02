package com.soulware.platform.customerservice.ping;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.time.OffsetDateTime;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@Endpoint
public class PingEndpoint {
    private static final String NS = "http://soulware.com/customerservice/ws/ping";

    @PayloadRoot(namespace = NS, localPart = "pingRequest")
    @ResponsePayload
    public Source ping(@RequestPayload Element request) {
        // request es el elemento <pingRequest> como DOM
        NodeList echoNodes = request.getElementsByTagNameNS(NS, "echo");
        String echo = (echoNodes.getLength() > 0) ? echoNodes.item(0).getTextContent() : "";

        String responseXml = """
      <ns2:pingResponse xmlns:ns2="%s">
        <echo>%s</echo>
        <serverTime>%s</serverTime>
      </ns2:pingResponse>
    """.formatted(NS, echo, java.time.OffsetDateTime.now().toString());

        return new org.springframework.xml.transform.StringSource(responseXml);
    }
}
