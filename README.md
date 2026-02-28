# Assignment 2 

## Project Overview
This project implements a fixed-capacity Ring Buffer (size **N**) that supports:
- **Single Writer** calling `write()`
- **Multiple Readers**, each with its own independent reading position
- Reading by one reader **does not remove** items for other readers
- When the buffer becomes full, the writer **overwrites the oldest** data
- Slow readers may **miss items** if data is overwritten before they read it

The design uses **sequence numbers** to identify each written item and allows each reader to track its own progress.

---

## Main Class Responsibilities

### `RingBuffer<T>`
**Responsibility:** Core data structure and storage.
- Owns the fixed-size array `data[]`
- Tracks the global `lastSeq` (last written sequence number)
- Computes slot index: `index = seq % capacity`
- Provides package-private methods used by Reader/Writer:
  - `writeInternal(item)`
  - `readInternal(index)`

### `Writer<T>`
**Responsibility:** Encapsulates writing (single writer rule).
- Has a reference to `RingBuffer`
- Exposes only `write(T item)` which delegates to `RingBuffer.writeInternal()`
- Only one writer thread should use this instance

### `Reader<T>`
**Responsibility:** Independent reader cursor.
- Stores its own `nextSeq` (the next sequence number to read)
- On `read()`:
  - Returns `EMPTY` if no new items exist for this reader
  - Detects overwrite: if `nextSeq < oldestAvailable`, reader **missed items**
  - Advances only its own cursor; does not affect other readers

### `ReadResult<T>`
**Responsibility:** Value object representing read outcome.
- `OK(value)` — item read successfully
- `EMPTY` — no new items available for this reader
- `MISSED(k)` — reader fell behind; `k` items were overwritten and missed

---

## UML Class Diagram

![UML Class Diagram](/img/UML%20Class%20Diagram.png)


## UML Sequence Diagram for write()

![!\[!\\[UML Class Diagram\\](/img/UML%20Class%20Diagram.png)\](<img/UML Sequence Diagram — write().png>)](<img/UML Sequence Diagram — write().png>)

## UML Sequence Diagram for read()

![!\[UML Class Diagram\](<img/UML Sequence Diagram — reader().png>)](<img/UML Sequence Diagram — reader().png>)

## How to Run
From project root:

Compile:
javac -d out RingBuffer/*.java

Run:
java -cp out RingBuffer.Main