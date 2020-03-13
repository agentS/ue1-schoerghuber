package sve2.jee.fhbay.rest;

import sve2.jee.fhbay.util.Logger;
import sve2.jee.fhbay.util.LoggerQualifier;
import sve2.jee.fhbay.util.LoggerType;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@RequestScoped
public class HelloResource {
    @Inject
    @LoggerQualifier(type = LoggerType.ADVANCED)
    private Logger logger;

    @Inject
    private org.slf4j.Logger slf4jLogger;

    @PostConstruct
    public void onBeanConstructed() { // Since we are using @RequestScoped a new bean is created for every request. Therefore, this method is called for every request to the REST API.
        this.slf4jLogger.info("HelloResource bean has been bean created");
    }

    @PreDestroy
    public void onDestroyBean() { // Since we are using @RequestScoped the bean only exists for the duration of the request and is destroyed after processing the request. Therefore, this method is called after every request to the REST API has been processed.
        this.slf4jLogger.info("HelloResource bean is about to be destroyed");
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String getHelloMessage() {
        this.logger.info("getHelloMessage called");
        this.slf4jLogger.info("getHelloMessage called");

        return "Hello from JAX-RS!";
    }
}
