
public class Shop {

    static final int PAGES = 8;      
    static final int THREADS = 4;    

    public static void main(String[] args) throws InterruptedException {

        System.out.println("ядер у машині: "
                + Runtime.getRuntime().availableProcessors());

        
        System.out.println("\nпослідовно:");

        long t0 = System.nanoTime();

        for (int page = 1; page <= PAGES; page++) {
            loadPage(page);
        }

        seconds("час", t0);


        System.out.println("\nу 4 потоки:");

        t0 = System.nanoTime();

        Thread[] threads = new Thread[THREADS];

       
        for (int t = 0; t < THREADS; t++) {

            final int me = t;

            threads[t] = new Thread(() -> {

                
                for (int page = me + 1; page <= PAGES; page += THREADS) {
                    loadPage(page);
                }

            }, "каталог-" + (t + 1));
        }

        
        for (Thread thread : threads) {
            thread.start();
        }

        
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("усі " + PAGES + " сторінок готові");

        seconds("час", t0);
    }

    
    static void loadPage(int page) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Переривання потоку
        }

        System.out.println("  [" + Thread.currentThread().getName()
                + "] сторінка " + page + " готова");
    }

    
    static void seconds(String label, long t0) {
        System.out.println(label + ": "
                + String.format("%.2f",
                (System.nanoTime() - t0) / 1_000_000_000.0)
                + " с");
    }
}

