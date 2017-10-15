package problem5;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class Problem5 {
    public static final ConcurrentHashMap<String, Set<File>> stringToFileMap = new ConcurrentHashMap<>();
    private File[] files;
    private ExecutorService  executorService;
    public Problem5(String dirName){
        executorService = Executors.newFixedThreadPool(4);
        File dir =  new File(dirName);
        files = dir.listFiles((dir1, name) -> name.endsWith(".txt"));
    }

    public void execute() throws InterruptedException, ExecutionException {
        List<Future<Long>> futureList;
        Collection<StringToFileMapper> callables = new ArrayList<>();
        for(File file: files){
            callables.add(new StringToFileMapper(file));
        }
        futureList = executorService.invokeAll(callables);
        stringToFileMap.forEach((String key, Set<File> files) -> {
            StringBuilder fileNames = new StringBuilder();
            for(File file: files){
                fileNames.append(file.getName());
                fileNames.append(", ");

            }
            System.out.println(key+" is present in files "+fileNames.toString());
        });
        System.out.println("Hashmap size = "+ stringToFileMap.size());
        executorService.shutdown();
        stringToFileMap.clear();
    }
}
