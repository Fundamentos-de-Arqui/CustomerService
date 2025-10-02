package com.soulware.platform.customerservice.shared.infrastructure.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;

@Configuration
@EnableWs
public class WebServiceConfig {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext ctx) {
        var servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(ctx);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean
    public XsdSchema patientSchema() {
        return new SimpleXsdSchema(new ClassPathResource("wsdl/patient.xsd"));
    }

    // WSDL available at: http://localhost:8080/ws/patient.wsdl
    @Bean(name = "patient")
    public DefaultWsdl11Definition patientWsdl(XsdSchema patientSchema) {
        var wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("PatientPort");
        wsdl.setLocationUri("/ws");
        wsdl.setTargetNamespace("http://soulware.com/customerservice/patient");
        wsdl.setSchema(patientSchema);
        return wsdl;
    }
}
