class PrintPlusThread extends Thread {
    private int n;

    public PrintPlusThread(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            System.out.println("+");
        }
    }
}

class PrintMinusThread extends Thread {
    private int n;

    public PrintMinusThread(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            System.out.println("-");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Create thread instances
        Thread thread1 = new PrintPlusThread(10);  // Prints "+" 10 times
        Thread thread2 = new PrintMinusThread(10); // Prints "-" 10 times

        // Start threads
        thread1.start();
        thread2.start();
    }
}
