public final class Writer<T> {

    private final RingBuffer<T> buffer;

    public Writer(RingBuffer<T> buffer) {
        this.buffer = buffer;
    }

    // Only one writer thread should call this method, so no synchronization is needed.
    public void write(T item) {
        buffer.writeInternal(item);
    }
}