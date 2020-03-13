package sve2.jee.fhbay.util.loggerimplementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class LoggerProducer {
    @Produces
    public org.slf4j.Logger produceLogger(InjectionPoint injectionPoint) {
        // factory method for creating a CDI logger bean
        // a factory method allows specifying parameters used for constructing the beans
        return LoggerFactory.getLogger(
                injectionPoint.getMember().getDeclaringClass() // get the class reflection information of the class which has the logger bean injected
        ); // create a new logger and use the name of the class which has the logger injected as identifier for the log messages
    }

    //public void dispose(@Disposes Logger logger) {
    //    System.out.println("disposing the logger");
    //}
}
