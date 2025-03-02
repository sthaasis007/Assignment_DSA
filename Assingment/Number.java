package Assingment;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// NumberPrinter class that prints numbers
//this class has three methods to print:

//printZero() → Prints 0
//printEven(int num) → Prints even numbers
//printOdd(int num) → Prints odd numbers
class NumberPrinter {
    public void printZero() {
        System.out.print(0);
    }

    public void printEven(int num) {
        System.out.print(num);
    }

    public void printOdd(int num) {
        System.out.print(num);
    }
}

// ThreadController to manage synchronization
class ThreadController {
    private int n;//The number limit
    private int count = 1;//keep track of number printed
    private final Lock lock = new ReentrantLock();//Ensure only one thread printa at atime
    //Control execution flow
    private final Condition zeroTurn = lock.newCondition();
    private final Condition oddTurn = lock.newCondition();
    private final Condition evenTurn = lock.newCondition();
    private boolean isZeroTurn = true;

    public ThreadController(int n) {// Tracks if it's zero’s turn to print.
        this.n = n;
    }
   // Locks the thread (lock.lock()).
//Waits for its turn (zeroTurn.await()).
//Prints 0 using printer.printZero().
//Decides which thread to wake up:
//If count is odd → oddThread runs.
//If count is even → evenThread runs.
//Unlocks the thread (lock.unlock()) so the next thread can run.


    public void printZero(NumberPrinter printer) {
        for (int i = 1; i <= n; i++) {
            lock.lock();
            try {
                while (!isZeroTurn) {
                    zeroTurn.await();
                }
                printer.printZero();
                isZeroTurn = false;
                if (count % 2 == 1) {
                    oddTurn.signal();
                } else {
                    evenTurn.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
    //Locks the thread (lock.lock()).
//Waits for its turn (oddTurn.await()).
//Prints the odd number (1, 3, 5, ...).
//Increments count and notifies ZeroThread (zeroTurn.signal()).
//Unlocks the thread (lock.unlock()).


    public void printEven(NumberPrinter printer) {
        for (int i = 2; i <= n; i += 2) {
            lock.lock();
            try {
                while (isZeroTurn || count % 2 == 1) {
                    evenTurn.await();
                }
                printer.printEven(count);
                count++;
                isZeroTurn = true;
                zeroTurn.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void printOdd(NumberPrinter printer) {
        for (int i = 1; i <= n; i += 2) {
            lock.lock();
            try {
                while (isZeroTurn || count % 2 == 0) {
                    oddTurn.await();
                }
                printer.printOdd(count);
                count++;
                isZeroTurn = true;
                zeroTurn.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}

// Main class to run the program
public class Number {
    public static void main(String[] args) {
        int n = 5; // You can change this value
        NumberPrinter printer = new NumberPrinter();
        ThreadController controller = new ThreadController(n);

        Thread zeroThread = new Thread(() -> controller.printZero(printer));
        Thread evenThread = new Thread(() -> controller.printEven(printer));
        Thread oddThread = new Thread(() -> controller.printOdd(printer));

        zeroThread.start();
        evenThread.start();
        oddThread.start();

        try {
            zeroThread.join();
            evenThread.join();
            oddThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
// Execution Flow (Step-by-Step)
//For n = 5:

//Step	Thread	Action
//1	ZeroThread	Prints 0
//2	OddThread	Prints 1
//3	ZeroThread	Prints 0
//4	EvenThread	Prints 2
//5	ZeroThread	Prints 0
//6	OddThread	Prints 3
//7	ZeroThread	Prints 0
//8	EvenThread	Prints 4
//9	ZeroThread	Prints 0
//10	OddThread	Prints 5