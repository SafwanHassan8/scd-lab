package scdlab12;

class SafeBox<T extends Number> {
    private T value;
    public synchronized void put(T value) {
        this.value = value;
        notifyAll(); // wake up waiting threads
    }
    public synchronized T get() throws InterruptedException {
        while (value == null) {
            wait(); 
        }
        T temp = value;
        value = null;
        return temp;
    }
}

public class Lab12 {
    public static void main(String[] args) {
        SafeBox<Integer> box = new SafeBox<>();
        Thread producer = new Thread(() -> {
            box.put(42);
            System.out.println("Produced 42");
        });
        Thread consumer = new Thread(() -> {
            try {
                System.out.println("Consumed " + box.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        consumer.start();
        producer.start();
    }
}
