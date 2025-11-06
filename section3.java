// ParallelComputationDemo.java
public class section3 {

    // --- 1. Thread Naming ---
    static class NamedThread extends Thread {
        public NamedThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println("Running thread: " + getName());
        }
    }

    // --- 2. Thread Naming using setName() ---
    static class SetNameThread extends Thread {
        @Override
        public void run() {
            System.out.println("Thread before naming: " + getName());
            setName("RenamedThread");
            System.out.println("Thread after renaming: " + getName());
        }
    }

    // --- 3. Runnable with Thread Naming ---
    static class NamedRunnable implements Runnable {
        private final String name;
        public NamedRunnable(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            System.out.println("Running runnable thread: " + name);
        }
    }

    // --- 4. Thread Priority Demo ---
    static class PriorityThread extends Thread {
        public PriorityThread(String name, int priority) {
            super(name);
            setPriority(priority);
        }

        @Override
        public void run() {
            System.out.println(getName() + " started with priority " + getPriority());
        }
    }

    // --- 5. Thread Group Demo ---
    static class GroupedThread extends Thread {
        public GroupedThread(ThreadGroup group, String name) {
            super(group, name);
        }
        @Override
        public void run() {
            System.out.println("Thread in group: " + getThreadGroup().getName() + " -> " + getName());
        }
    }

    // --- 6. Daemon vs User Threads ---
    static class DaemonExample extends Thread {
        public DaemonExample(String name, boolean isDaemon) {
            super(name);
            setDaemon(isDaemon);
        }
        @Override
        public void run() {
            System.out.println(getName() + " (isDaemon=" + isDaemon() + ") is running");
            try { Thread.sleep(1000); } catch (InterruptedException e) { }
            System.out.println(getName() + " finished");
        }
    }

    // --- 7. LAB EXERCISE: MultiExecutor ---
    import java.util.List;
    static class MultiExecutor {
        private final List<Runnable> tasks;
        public MultiExecutor(List<Runnable> tasks) {
            this.tasks = tasks;
        }
        public void executeAll() {
            for (Runnable task : tasks) {
                Thread thread = new Thread(task);
                thread.start();
            }
        }
    }

    // --- Main method to run all demos ---
    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Thread Naming ===");
        new NamedThread("Thread-1").start();
        Thread.sleep(200);

        System.out.println("\n=== setName() Example ===");
        new SetNameThread().start();
        Thread.sleep(200);

        System.out.println("\n=== Runnable with Name ===");
        new Thread(new NamedRunnable("RunnableThread")).start();
        Thread.sleep(200);

        System.out.println("\n=== Thread Priority ===");
        new PriorityThread("LowPriority", Thread.MIN_PRIORITY).start();
        new PriorityThread("HighPriority", Thread.MAX_PRIORITY).start();
        Thread.sleep(200);

        System.out.println("\n=== Thread Group ===");
        ThreadGroup group = new ThreadGroup("MyGroup");
        new GroupedThread(group, "GroupThread-1").start();
        new GroupedThread(group, "GroupThread-2").start();
        Thread.sleep(200);

        System.out.println("\n=== Daemon vs User Thread ===");
        DaemonExample user = new DaemonExample("UserThread", false);
        DaemonExample daemon = new DaemonExample("DaemonThread", true);
        daemon.start();
        user.start();
        user.join(); // JVM waits only for user threads
        System.out.println("\n=== MultiExecutor ===");
        MultiExecutor executor = new MultiExecutor(
            java.util.List.of(
                () -> System.out.println("Task 1 running..."),
                () -> System.out.println("Task 2 running..."),
                () -> System.out.println("Task 3 running...")
            )
        );
        executor.executeAll();
    }
}