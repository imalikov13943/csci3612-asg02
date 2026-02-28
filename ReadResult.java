import java.util.Optional;

public final class ReadResult<T> {

    public enum Status { OK, EMPTY, MISSED }

    private final Status status;
    private final T value;
    private final int missed;

    private ReadResult(Status status, T value, int missed) {
        this.status = status;
        this.value = value;
        this.missed = missed;
    }

    public static <T> ReadResult<T> ok(T value) {
        return new ReadResult<>(Status.OK, value, 0);
    }

    public static <T> ReadResult<T> empty() {
        return new ReadResult<>(Status.EMPTY, null, 0);
    }

    public static <T> ReadResult<T> missed(int missed) {
        return new ReadResult<>(Status.MISSED, null, missed);
    }

    public Status status() { return status; }
    public Optional<T> value() { return Optional.ofNullable(value); }
    public int missedCount() { return missed; }

    @Override
    public String toString() {
        return switch (status) {
            case OK -> "OK(" + value + ")";
            case EMPTY -> "EMPTY";
            case MISSED -> "MISSED(" + missed + ")";
        };
    }
}