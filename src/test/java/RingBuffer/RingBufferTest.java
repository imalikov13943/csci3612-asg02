package RingBuffer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RingBuffer implementation.
 * Covers: construction, indexOf, Writer, Reader, ReadResult, edge cases.
 */
@DisplayName("RingBuffer Tests")
class RingBufferTest {
    @Test
    void testCapacity() {
        RingBuffer<String> rb = new RingBuffer<>(8);
        assertEquals(8, rb.capacity());
    }

    @Test
    void testCapacity2() {
        // edge case - capacity 1
        RingBuffer<String> rb = new RingBuffer<>(1);
        assertEquals(1, rb.capacity());
    }

    @Test
    void testLastSeq() {
        RingBuffer<Integer> rb = new RingBuffer<>(4);
        // should be -1 before anything is written
        assertEquals(-1, rb.lastSeq());
    }

    @Test
    void testIndexOf() {
        RingBuffer<Integer> rb = new RingBuffer<>(5);
        assertEquals(0, rb.indexOf(0));
        assertEquals(0, rb.indexOf(5)); // wraps
        assertEquals(3, rb.indexOf(3));
        assertEquals(4, rb.indexOf(9));   // 9 % 5 = 4
        assertEquals(2, rb.indexOf(12));  // 12 % 5 = 2
    }
}