package problem1;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

//import static java.util.concurrent.Executors;

public class Problem1 {
    private ExecutorService executorService;
    private File[] files;
    public static final AtomicReference<String> longestString = new AtomicReference<>();
    public Problem1(String dirName) {
        executorService = Executors.newFixedThreadPool(4);
        File dir =  new File(dirName);
        files = dir.listFiles((dir1, name) -> name.endsWith(".txt"));
    }

    public void execute() throws InterruptedException {
        List<Future<String>> futureList = new ArrayList<>();
        Collection<LongestStringFinder> callables = new ArrayList<>();
        for(File file: files){
            callables.add(new LongestStringFinder(file));
        }
        futureList = executorService.invokeAll(callables);
        for(Future future: futureList){
            while(!future.isDone()){
                Long i = 23l;
                System.out.println("i ="+i);
                Long j = i;
                System.out.println("j = "+j);
                i = null;
                System.out.println("j = "+j+" i ="+i);

            }
            try {
                System.out.println(future.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
        System.out.println("Problem 1 - longest string = "+longestString.get());
    }


}
