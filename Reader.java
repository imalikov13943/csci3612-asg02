public final class Reader<T> {

    private final RingBuffer<T> buffer;

    // Next sequence number this reader wants to read
    private int nextSeq;

    public Reader(RingBuffer<T> buffer, int startSeq) {
        this.buffer = buffer;
        this.nextSeq = startSeq;
    }

    public ReadResult<T> read() {
        int lastWritten = buffer.lastSeq();

        // Buffer empty or no new items for this reader
        if (lastWritten < 0 || nextSeq > lastWritten) {
            return ReadResult.empty();
        }

        // Oldest sequence still available
        int oldestAvailable = Math.max(0, lastWritten - buffer.capacity() + 1);

        // Slow reader
        if (nextSeq < oldestAvailable) {
            int missed = oldestAvailable - nextSeq;
            nextSeq = oldestAvailable;
            return ReadResult.missed(missed);
        }

        int index = buffer.indexOf(nextSeq);
        T value = buffer.readInternal(index);
        nextSeq++;
        return ReadResult.ok(value);
    }
}