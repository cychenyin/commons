
package cy.common.data;

import org.junit.Assert;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

public class IdUtilsTest {

    /**
     * config.properties in root of project
     */
    @Ignore
    @Test
    public void testInitFromSystemProperies() {
        File file = new File(IdUtils.CONFIG_PROPERTIES);
        if (file.exists()) {
            IdUtils.next();
            Assert.assertTrue(IdUtils.getServerId() == 12);
        }
    }

    @Ignore
    @Test
    public void testInitFromJvmArguments() {
        System.setProperty(IdUtils.SERVER_ID, "22");
        IdUtils.next();
        Assert.assertTrue(IdUtils.getServerId() == 22);
    }

    /**
     * config.properties path declared by jvm arguments
     * make sure file not exists in root of project.
     */
    @Test
    public void testInitFromPackage() {
        String path = "src/test/resources/config.properties";
        File file = new File(path);
        if (file.exists()) {
            System.setProperty(IdUtils.CONFIG_PROPERTIES, path);
            IdUtils.next();
            Assert.assertEquals(13, IdUtils.getServerId());
        }
    }
}
