package luis.josh.catan.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientLogger{

    public static final Logger logger = LoggerFactory.getLogger(ClientLogger.class.getName());

    private ClientLogger() {}

    public static Logger getLogger(Class<?> cls) {
        return LoggerFactory.getLogger(cls.getName());
    }

}