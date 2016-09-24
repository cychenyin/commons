
package cy.common.config;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cy.common.config.ProperitesUtil;

public class ProperitesUtilTest {

    @Before
    public void setUp() throws Exception {}

    @Test
    public void testGetPropertiesFromFile() {
        String path = "src/test/resources/config.properties";
        File f = new File(path);
        if (f.exists()) {
            Properties prop = ProperitesUtil.getProperties(path);
            Assert.assertTrue(prop.size() > 0);
        }
    }

    @Test
    public void testGetPropertiesFromJvmArgs() {
        String path = "src/test/resources/config.properties";
        System.setProperty("config.properties", path);
        File f = new File(path);
        if (f.exists()) {
            Properties prop = ProperitesUtil.getProperties("config.properties");
            Assert.assertTrue(prop.size() > 0);
        }
    }

    @Test
    public void testGetPropertiesFromPackage() {
        Properties prop = ProperitesUtil.getProperties("property/init.properties");
        Assert.assertTrue(prop.size() > 0);
    }
}
