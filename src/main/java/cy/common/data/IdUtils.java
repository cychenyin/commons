

package cy.common.data;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import cy.common.config.ProperitesUtil;

/**
 * global id generator.
 * Envelop of snowflake.
 * Need server.id be configured. configuration be read from system properties firstly, if not exists, try
 * in file system and classpath in package then.
 *
 * @see ProperitesUtil
 * @author cheny-ab
 *
 */
public class IdUtils {
    private static final Logger logger = LoggerFactory.getLogger(IdUtils.class);

    public static final String SERVER_ID = "server.id";
    public static final String CONFIG_PROPERTIES = "config.properties";
    private static int serverId;

    private IdUtils() {
        String configId = System.getProperty(SERVER_ID);
        if (configId == null) {
            Properties properties = ProperitesUtil.getProperties(CONFIG_PROPERTIES);
            if (properties.containsKey(SERVER_ID)) {
                configId = properties.getProperty(SERVER_ID);
            }
        }

        if (Strings.isNullOrEmpty(configId)) {
            serverId = Integer.parseInt(configId);
            if (serverId < 0 || serverId > Snowflake.MAX_NODE) {
                throw new RuntimeException("fail to init IdUtils, server id must is between 0 and 127,"
                                + " check config or jvm arguments please.");
            }
            worker = new Snowflake(serverId);
        } else {
            throw new RuntimeException("fail to init IdUtils, check config or jvm arguments please.");
        }
    }

    private Snowflake worker;

    private static IdUtils instance;

    private static IdUtils getInstance() {
        if (instance == null) {
            synchronized (IdUtils.class) {
                if (instance == null) {
                    instance = new IdUtils();
                }
            }
        }
        return instance;
    }

    public static long next() {
        return IdUtils.getInstance().worker.next();
    }

    public static int getServerId() {
        return serverId;
    }
}
