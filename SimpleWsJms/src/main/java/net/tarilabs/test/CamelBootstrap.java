package net.tarilabs.test;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

@Singleton
@Startup
public class CamelBootstrap {
	private CamelContext camelContext;
	private ProducerTemplate producerTemplate;

	public CamelContext getCamelContext() {
		return camelContext;
	}

	public ProducerTemplate getProducerTemplate() {
		return producerTemplate;
	}

	@PostConstruct
	protected void init() throws Exception {
		camelContext = new DefaultCamelContext();
		camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				// in the JMS connection we can use # for the ConnectionFactory because by not using Spring the default for Camel is the JNDIRegistry
				// just remind by default it's implied it's a Queue as per Camel JMS doc
				from("direct:spoolOnJms")
				.log("spoolOnJms: ${body}")
				.to("jms:sample?connectionFactory=#ConnectionFactory");
			}
		});
		camelContext.start();
		producerTemplate = camelContext.createProducerTemplate();
	}
}
