// ExtendThreadExample.java
class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Hello from MyThread: " + i);
            try {
                Thread.sleep(500); // يوقف نص ثانية علشان تباني التوازي
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ExtendThreadExample {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        t1.start(); // يبدأ الـ Thread

        // كود المين شغال في نفس الوقت
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