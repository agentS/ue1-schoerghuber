package sve2.jee.fhbay.util.loggerimplementation;

import org.slf4j.LoggerFactory;
import sve2.jee.fhbay.util.Logger;
import sve2.jee.fhbay.util.LoggerQualifier;
import sve2.jee.fhbay.util.LoggerType;

import javax.enterprise.context.Dependent;

@Dependent
@LoggerQualifier(type = LoggerType.ADVANCED)
public class Slf4jLogger implements Logger {
    private static final org.slf4j.Logger logger =
            LoggerFactory.getLogger(Slf4jLogger.class);

    @Override
    public void info(String message) {
        logger.info(message);
    }
}
