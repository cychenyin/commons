
package cy.common.data;

import org.junit.Assert;
import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

public class SnowflakeTest {

    int nodeSeqenceShift = Snowflake.NODE_SHIFT + Snowflake.SEQ_SHIFT;
    long nodeMask = ((1L << Snowflake.NODE_SHIFT) - 1L) << Snowflake.SEQ_SHIFT;
    long sequenceMask = (1L << Snowflake.SEQ_SHIFT) - 1L;
    private Snowflake sf;
    private int serverId = 1;

    @Before
    public void init() {
        sf = new Snowflake(this.serverId);
    }

    private long getTimestamp(long id) {
        return id >> (Snowflake.NODE_SHIFT + Snowflake.SEQ_SHIFT);
    }

    private Date getDate(long timestamp) {
        return new Date(timestamp);
    }

    private long getNode(long id) {
        return (id & nodeMask) >> Snowflake.SEQ_SHIFT;
    }

    private long getSeqence(long id) {
        return id & sequenceMask;
    }

    @Test
    public void testConstant() {
        Assert.assertTrue(Snowflake.MAX_NODE == (1 << Snowflake.NODE_SHIFT) - 1);
        Assert.assertTrue(Snowflake.MAX_SEQUENCE == (1 << Snowflake.SEQ_SHIFT) -1);
    }

    @Test
    public void testTimestamp() {
        long before = System.currentTimeMillis();
        long id = sf.next();
        long after = System.currentTimeMillis();
        long timestamp = this.getTimestamp(id);

        Assert.assertTrue(timestamp >= before);
        Assert.assertTrue(timestamp <= after);
    }

    @Test
    public void testNode() {
        long id = sf.next();
        long node = this.getNode(id);
        Assert.assertTrue(node == this.serverId);
    }

    @Test
    public void testNode2() {
        Snowflake sf2 = new Snowflake(2);
        long id = sf2.next();
        long node = this.getNode(id);
        Assert.assertTrue(node == 2);
    }

    @Test
    public void testNode3() {
        Snowflake sf2 = new Snowflake(127);
        long id = sf2.next();
        long node = this.getNode(id);
        Assert.assertTrue(node == 127);
    }

    @Test
    public void testSequence() {
        long s = this.getSeqence(sf.next());

    }

    @Test
    public void testNext() {
        long id = sf.next();

        long timestamp = this.getTimestamp(id);
        long node = this.getNode(id);
        long seq = this.getSeqence(id);

        Assert.assertTrue(timestamp > 0);
        Assert.assertTrue(node == this.serverId);
        Assert.assertTrue(seq >= 0);
        Assert.assertTrue(seq < Snowflake.MAX_SEQUENCE);
    }

    @Test
    public void testNext2() {

        long lid = sf.next();

        long lt = this.getTimestamp(lid);
        long ln = this.getNode(lid);
        long ls = this.getSeqence(lid);

        Assert.assertTrue(ln == this.serverId);
        Assert.assertTrue(ls >= 0);
        Assert.assertTrue(ls < Snowflake.MAX_SEQUENCE);

        long rid = sf.next();

        long rt = this.getTimestamp(rid);
        long rn = this.getNode(rid);
        long rs = this.getSeqence(rid);

        Assert.assertTrue(rn == this.serverId);
        Assert.assertTrue(rs >= 0);
        Assert.assertTrue(rs < Snowflake.MAX_SEQUENCE);

        Assert.assertTrue(lid < rid);
        Assert.assertTrue(lt <= rt);
        Assert.assertTrue(ln == rn);
        // System.out.println(Long.toBinaryString(lid));
        // System.out.println(Long.toBinaryString(rid));
        // System.out.println(Long.toBinaryString(ls));
        // System.out.println(Long.toBinaryString(rs));
        // System.out.println(Long.toBinaryString(sequenceMask));
        if (lt == rt) {
            Assert.assertTrue(ls + 1 == rs);
        } else {
            Assert.assertTrue(rs == 0);
        }
    }

    @Test
    public void testNextIncreased() {
        long last = sf.next();
//        System.out.println("1st last= " + Long.toBinaryString(last));
        last = sf.next();
//        System.out.println("2nd last= " + Long.toBinaryString(last));

        for (int i = 0; i < 1000000; i++) {
            long next = sf.next();
            if (next <= last) {
//                System.out.println("debug");
//                System.out.println("last= " + Long.toBinaryString(last));
//                System.out.println("next= " + Long.toBinaryString(next));
//                System.out.println("   i= " + i);
                if (next < 0) {
                    System.out.println("less then zero.");
                }
            }
            Assert.assertTrue(next > last);

            last = next;
        }
    }
}
