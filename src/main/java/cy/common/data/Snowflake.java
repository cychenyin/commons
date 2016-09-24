
package cy.common.data;

/**
 * A snowflake is a source of k-ordered unique 64-bit integers.
 * base @link https://github.com/relops/snowflake
 * modified by chenyin 2016-09-21
 * 1. change bits of node & sequence
 * 2. support await when id exhausted
 * 3. definition of MAX_NODE & MAX_SEQUENCE
 */
public class Snowflake {

    public static final int NODE_SHIFT = 7; // 10;
    public static final int SEQ_SHIFT = 15; // 12;

    public static final int MAX_NODE = (1 << NODE_SHIFT) - 1; // 1023;
    public static final int MAX_SEQUENCE = (1 << SEQ_SHIFT) - 1; // 4095;

    private int sequence;
    private long referenceTime;

    private int node;
    protected long exhaustedCounter = 0;

    /**
     * A snowflake is designed to operate as a singleton instance within the context of a node.
     * If you deploy different nodes, supplying a unique node id will guarantee the uniqueness
     * of ids generated concurrently on different nodes.
     *
     * @param node This is an id you use to differentiate different nodes.
     */
    public Snowflake(int node) {
        if (node < 0 || node > MAX_NODE) {
            throw new IllegalArgumentException(String.format("node must be between %s and %s", 0, MAX_NODE));
        }
        this.node = node;
    }

    /**
     * Generates a k-ordered unique 64-bit integer. Subsequent invocations of this method will produce
     * increasing integer values.
     *
     * @return The next 64-bit integer.
     */
    public long next() {

        long currentTime = System.currentTimeMillis(); // System.currentTimeMillis();
        long counter;

        synchronized (this) {

            if (currentTime < referenceTime) {
                throw new RuntimeException(String.format("Last referenceTime %s is after reference time %s",
                                referenceTime, currentTime));
            } else if (currentTime > referenceTime) {
                this.sequence = 0;
            } else {
                this.sequence++;
                if (this.sequence > Snowflake.MAX_SEQUENCE) {
                    // throw new RuntimeException("Sequence exhausted at " + this.sequence);
                    this.sequence = 0;

                    currentTime = tilNextMillis(currentTime);
                    exhaustedCounter++;
                }
            }
            counter = this.sequence;
            referenceTime = currentTime;
        }
        return currentTime << NODE_SHIFT << SEQ_SHIFT | node << SEQ_SHIFT | counter;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
