package problem3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class Problem3 {
    public static final LongAdder longAdder = new LongAdder();
    public static final AtomicLong atomicLong = new AtomicLong(0l);
    private ExecutorService executorService = Executors.newFixedThreadPool(1000);
    public void execute() throws InterruptedException{
        Collection<Incrementer> callablesLongAdder = new ArrayList<>(1000);
        for(int i = 0; i<1000; i++ ){
            callablesLongAdder.add(new Incrementer(true));
        }
        long startTime = System.currentTimeMillis();
        executorService.invokeAll(callablesLongAdder);
        long endTime = System.currentTimeMillis();
        System.out.println("Long adder took "+ (startTime - endTime) +"milli seconds");
        System.out.println("Long adder result: "+ longAdder.sum());
        Collection<Incrementer> callablesAtomicLong = new ArrayList<>(1000);
        for(int i = 0; i<1000; i++ ){
            callablesAtomicLong.add(new Incrementer(false));
        }
        startTime = System.currentTimeMillis();
        executorService.invokeAll(callablesAtomicLong);
        endTime = System.currentTimeMillis();
        System.out.println("AtomicLong took "+ (startTime - endTime) +" milli seconds");
        System.out.println("Atomic long result" + atomicLong.get());
        executorService.shutdown();
    }
}
