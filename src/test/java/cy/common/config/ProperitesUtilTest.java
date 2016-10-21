
package cy.common.config;

import java.io.File;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProperitesUtilTest {

    @Before
    public void setUp() throws Exception {}

    @Test
    public void testGetPropertiesFromFile() {
        String path = "src/test/resources/config.properties";
        File file = new File(path);
        if (file.exists()) {
            Properties prop = ProperitesUtil.getProperties(path);
            Assert.assertTrue(prop.size() > 0);
        }
    }

    @Test
    public void testGetPropertiesFromJvmArgs() {
        String path = "src/test/resources/config.properties";
        System.setProperty("config.properties", path);
        File file = new File(path);
        if (file.exists()) {
            Properties prop = ProperitesUtil.getProperties("config.properties");
            Assert.assertTrue(prop.size() > 0);
        }
    }

    @Test
    public void testGetPropertiesFromPackage() {
        Properties prop = ProperitesUtil.getProperties("config.properties");
        Assert.assertTrue(prop.size() > 0);
    }
}
