package cp.Week8;

// Without any printf statements, then in most hardware these threads are 
// running in parallel. However, the output of information to the terminal 
// happens concurrently, and thus the entire example is concurrent. 

class Task1 extends Thread {
    public void run() {
        for (int i = 1; i <= 7; i++) {
            System.out.printf("↓ Task1 - %d%n", i); 
           try { Thread.sleep((long)(Math.random() * 500)); } catch (InterruptedException ignored) {} // Simulate random delay
        }
    }
}

class Task2 extends Thread {
    public void run() {
        for (int i = 1; i <= 7; i++) {
            System.out.printf("%30s↓ Task2 - %c%n", "", i+96);
            try { Thread.sleep((long)(Math.random() * 500)); } catch (InterruptedException ignored) {} // Simulate random delay
        }
    }
}

public class ConcurrencyExample {
    public static void main(String[] args) {
        Task1 t1 = new Task1();
        Task2 t2 = new Task2();
        t1.start();
        t2.start();
    }
}
