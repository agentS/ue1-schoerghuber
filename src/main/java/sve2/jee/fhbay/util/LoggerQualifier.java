package sve2.jee.fhbay.util;

import javax.inject.Qualifier;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface LoggerQualifier {
    LoggerType type() default LoggerType.SIMPLE;
}
