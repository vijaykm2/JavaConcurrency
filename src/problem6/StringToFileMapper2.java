package problem6;

import problem5.Problem5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class StringToFileMapper2 implements Callable<Long>{
    private final File file;
    public StringToFileMapper2(File file){
        this.file = file;
    }

    @Override
    public Long call() throws Exception {
        Long count = this.file.length();
        try(BufferedReader reader = new BufferedReader(new FileReader(this.file))){
            Stream<String> lineStream = reader.lines();
            lineStream.forEach(line -> {
                String[] words = line.split("[^\\w']+");
                for(String word: words){
                    Function<String, Set<File>> mappingFunction = x -> ConcurrentHashMap.newKeySet();
                    Function<String, Integer> stringToLengthFn = x -> x.length();
                    Problem6.stringToFileMap.computeIfAbsent(word, mappingFunction).add(this.file);
                    Problem6.stringToLengthMap.computeIfAbsent(word, stringToLengthFn);

                }

            });
        }
        return count;
    }
}
