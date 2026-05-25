package luis.josh.catan.host;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostLogger{

    public static final Logger logger = LoggerFactory.getLogger(HostLogger.class.getName());

    private HostLogger() {}

    public static Logger getLogger(Class<?> cls) {
        return LoggerFactory.getLogger(cls.getName());
    }

}