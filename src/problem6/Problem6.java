package problem6;

import problem6.StringToFileMapper2;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.BiFunction;

public class Problem6 {
    public static final ConcurrentHashMap<String, Set<File>> stringToFileMap = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, Integer> stringToLengthMap = new ConcurrentHashMap<>();
    private File[] files;
    private ExecutorService executorService;
    public Problem6(String dirName){
        executorService = Executors.newFixedThreadPool(4);
        File dir =  new File(dirName);
        files = dir.listFiles((dir1, name) -> name.endsWith(".txt"));
    }

    public void execute() throws InterruptedException, ExecutionException {
        List<Future<Long>> futureList;
        Collection<StringToFileMapper2> callables = new ArrayList<>();
        for(File file: files){
            callables.add(new StringToFileMapper2(file));
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

        System.out.println("\n\n\n*****Computing longest string *****\n");
        BiFunction<Map.Entry<String, Integer>, Map.Entry<String, Integer>, Map.Entry<String, Integer>> reducer = (entry1, entry2) -> {
            if(entry1.getValue().compareTo(entry2.getValue()) == -1){
                return entry2;
            } else {
                return entry1;
            }
        };
        Map.Entry<String, Integer> longestEntry = stringToLengthMap.reduceEntries(100l,reducer);
        System.out.println("Longest string is: "+ longestEntry.getKey()+ " and length is : "+longestEntry.getValue());
        executorService.shutdown();
        stringToFileMap.clear();
    }
}
