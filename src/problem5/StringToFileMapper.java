package problem5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public final class StringToFileMapper implements Callable<Long> {

    private final File file;
    public StringToFileMapper(File file){
        this.file = file;
    }


    @Override
    public Long call() throws Exception {
        Long count = 0l;
        try (BufferedReader reader = new BufferedReader(new FileReader(this.file))){
            Set<File> fileSet = ConcurrentHashMap.newKeySet();
            fileSet.add(this.file);
            Stream<String> lineStream = reader.lines();
            lineStream.forEach(line -> {
                String[] words = line.split(" ");
                for(String word: words){
                    //count = count.add(BigInteger.ONE);
                    BiFunction<Set<File>, Set<File>, Set<File>> reMappingFunction = (Set<File> oldSet, Set<File> newSet) -> {
                        oldSet.addAll(newSet);
                        return oldSet;
                    };
                    System.out.println("Word " +word+" is in "+ this.file.getName());
                    Set<File> currentSet = Problem5.stringToFileMap.get(word);

                    Problem5.stringToFileMap.merge(word, fileSet, reMappingFunction);
                }

            });
            //count +=lineStream.count();
        }
        return count;
    }
}
