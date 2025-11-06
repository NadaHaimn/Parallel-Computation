// RunnableExample.java
class MyTask implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Hello from MyTask: " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class RunnableExample {
    public static void main(String[] args) {
        Runnable task = new MyTask();
        Thread t = new Thread(task);
        t.start(); // تشغيل الـ Thread

        for (int i = 1; i <= 5; i++) {
            System.out.println("Main thread: " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}