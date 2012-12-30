package net.tarilabs.test;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService()
public class SpoolOnQueue {
	
	@EJB
	CamelBootstrap cb;

	@WebMethod()
	public String sayHello(String name) {
	    cb.getProducerTemplate().sendBody("direct:spoolOnJms", name);
	    return "Your message has been spooled on JMS";
	}
}
