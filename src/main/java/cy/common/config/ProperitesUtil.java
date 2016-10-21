
package cy.common.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * load properties tools.
 *
 * @since 2016-09-22
 * @author chenyin
 */
public class ProperitesUtil {

    private static final Logger logger = LoggerFactory.getLogger(ProperitesUtil.class);
    private static final boolean debug = Boolean.getBoolean("debug"); // default

    /**
     * load properties. read from system properties firstly, if not exists, try
     * in file system and classpath in package then.
     *
     * @param path
     *        can be file system Path, jvm arguments, path in package.
     * @return Properties, return empty (not null) if not found.
     */
    public static Properties getProperties(String path) {
        Properties prop = null;
        if (Strings.isNullOrEmpty(System.getProperty(path))) {
            prop = ProperitesUtil.loadFromFile(System.getProperty(path));
        }
        if (prop == null) {
            prop = ProperitesUtil.loadFromFile(path);
        }
        if (prop == null) {
            prop = ProperitesUtil.loadFromStream(path);
        }
        return prop == null ? new Properties() : prop;
    }

    private static Properties loadFromFile(String path) {
        if (new File(path).exists()) {
            Properties prop = new Properties();
            FileInputStream input = null;
            try {
                input = new FileInputStream(path);
                prop.load(input);
            } catch (IOException ex) {
                logger.error("fail to load properties from file:" + path, ex);
                throw new IllegalArgumentException("fail to load properites from file" + path);
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        // eat it
                    }
                }
            }
            return prop;
        }
        return null;
    }

    private static Properties loadFromStream(String path) {
        InputStream stream = null;
        try {
            // load a properties resource from package
            stream = ProperitesUtil.class.getClassLoader().getResourceAsStream(path);
            if (stream != null) {
                Properties prop = new Properties();
                prop.load(stream);
                return prop;
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("fail to load properites from resourceStream " + path);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // eat it
                }
            }
        }

        return null;
    }

}
