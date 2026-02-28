import java.util.Arrays;

public class RingBuffer<T> {

    private final int capacity;
    private final Object[] data;

    // last written sequence number 
    private int lastSeq = -1;

    public RingBuffer(int capacity) {
        this.capacity = capacity;
        this.data = new Object[capacity];
    }

    public int capacity() {
        return capacity;
    }

    // package-private: used by reader/writer
    int lastSeq() {
        return lastSeq;
    }

    // package-private: used by writer
    void writeInternal(T item) {
        lastSeq++;
        int index = indexOf(lastSeq);
        data[index] = item;
    }

    // package-private: used by reader
    @SuppressWarnings("unchecked")
    T readInternal(int index) {
        return (T) data[index];
    }

    int indexOf(int seq) {
        return seq % capacity;
    }

    public Writer<T> writer() {
        return new Writer<>(this);
    }

    public Reader<T> createReader() {
        // reader starts from "next item after current lastSeq"
        return new Reader<>(this, lastSeq + 1);
    }

    public void printBuffer() {
        System.out.println(Arrays.toString(data));
    }
}