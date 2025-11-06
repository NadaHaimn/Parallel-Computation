// ParallelComputationSection4.java
public class ParallelComputationSection4 {

    // 1. Example of Checked & Unchecked Exceptions
    static void checkedExceptionExample() throws java.io.IOException {
        throw new java.io.IOException("Checked Exception Example");
    }

    static void uncheckedExceptionExample() {
        throw new RuntimeException("Unchecked Exception Example");
    }

    // 2. Thread with try-catch handling
    static class SafeThread extends Thread {
        public SafeThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                System.out.println(getName() + " is running safely...");
                if (getName().equals("ErrorThread")) {
                    throw new RuntimeException("Something went wrong in " + getName());
                }
            } catch (Exception e) {
                System.out.println("Exception caught in " + getName() + ": " + e.getMessage());
            }
        }
    }

    // 3. Thread with custom UncaughtExceptionHandler
    static class CustomHandlerThread extends Thread {
        public CustomHandlerThread(String name) {
            super(name);
            setUncaughtExceptionHandler((t, e) -> {
                System.out.println("Custom handler caught exception in " + t.getName() + ": " + e.getMessage());
            });
        }

        @Override
        public void run() {
            System.out.println(getName() + " is running...");
            throw new RuntimeException("Uncaught exception in " + getName());
        }
    }

    // 4. Example of DefaultUncaughtExceptionHandler
    static class GlobalHandlerThread extends Thread {
        public GlobalHandlerThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(getName() + " is running...");
            throw new RuntimeException("Global handler caught this!");
        }
    }

    // 5. Race Condition Example
    static class Counter {
        int count = 0;

        // Uncomment this synchronized keyword to fix the race condition
        // synchronized void increment() {
        void increment() {
            count++;
        }
    }

    static class RaceThread extends Thread {
        private final Counter counter;

        public RaceThread(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("=== Checked & Unchecked Exceptions ===");
        try {
            checkedExceptionExample();
        } catch (Exception e) {
            System.out.println("Handled: " + e.getMessage());
        }

        try {
            uncheckedExceptionExample();
        } catch (Exception e) {
            System.out.println("Handled: " + e.getMessage());
        }

        System.out.println("\n=== Exception Handling in Threads (try-catch) ===");
        new SafeThread("SafeThread").start();
        new SafeThread("ErrorThread").start();

        // Wait a bit
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        System.out.println("\n=== UncaughtExceptionHandler Example ===");
        new CustomHandlerThread("CustomThread").start();

        // Wait a bit
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        System.out.println("\n=== DefaultUncaughtExceptionHandler Example ===");
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println("Global handler: Exception in " + t.getName() + " -> " + e.getMessage());
        });
        new GlobalHandlerThread("GlobalThread-1").start();
        new GlobalHandlerThread("GlobalThread-2").start();

        // Wait a bit
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        System.out.println("\n=== Race Condition Example ===");
        Counter counter = new Counter();
        Thread t1 = new RaceThread(counter);
        Thread t2 = new RaceThread(counter);
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {}

        System.out.println("Final count (expected 2000, may differ due to race condition): " + counter.count);
    }
}