package sve2.jee.fhbay.util.loggerimplementation;

import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class LoggerProducer {
    @Produces
    public org.slf4j.Logger produceLogger(InjectionPoint injectionPoint) { // factory method for creating a CDI logger bean
        return LoggerFactory.getLogger(
                injectionPoint.getMember().getDeclaringClass() // get the class reflection information of the class which has the logger bean injected
        ); // create a new logger and use the name of the class which has the logger injected as identifier for the log messages
    }
}
