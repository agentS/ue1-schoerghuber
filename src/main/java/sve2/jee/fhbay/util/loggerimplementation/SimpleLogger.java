package sve2.jee.fhbay.util.loggerimplementation;

import sve2.jee.fhbay.util.Logger;
import sve2.jee.fhbay.util.LoggerQualifier;
import sve2.jee.fhbay.util.LoggerType;

import javax.enterprise.context.Dependent;

@Dependent // this bean has the same lifecycle as its parent bean
@LoggerQualifier(type = LoggerType.SIMPLE)
public class SimpleLogger implements Logger {
    @Override
    public void info(String message) {
        System.out.println(message);
    }
}
