package problem3;

import java.util.concurrent.Callable;

public class Incrementer implements Callable<Long> {
    private final boolean useLongAdder;
    public Incrementer(boolean useLongAdder){
        this.useLongAdder = useLongAdder;
    }
    @Override
    public Long call() throws Exception {
        Long result = 0l;
        if(useLongAdder){
            for(int i = 0; i<100000; i++){
               Problem3.longAdder.increment();
            }
            result = Problem3.longAdder.sum();
        }else {
            for(int i = 0; i<100000; i++){
                Problem3.atomicLong.incrementAndGet();
            }
            result =  Problem3.atomicLong.get();
        }
        return result;
    }
}
