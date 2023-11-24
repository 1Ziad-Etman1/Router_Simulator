public class Semaphore {
    public int permits;

    public Semaphore(int initialPermits) {
        permits = initialPermits;
    }

    public synchronized void acquire() throws InterruptedException {
        while (permits == 0) {
            wait(); // Wait until someone releases a permit
        }
        permits--; // Decrement the number of permits
    }

    public synchronized void release() {
        permits++; // Increment the number of permits
        notify(); // Notify waiting threads that a permit is available
    }
}

