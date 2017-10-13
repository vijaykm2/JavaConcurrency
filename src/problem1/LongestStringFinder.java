package problem1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public final class LongestStringFinder implements Callable<String> {
    private File file;
    @Override
    public String call() {

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            Stream<String> linesStream =reader.lines();
            linesStream.forEach(line -> {
                String[] words = line.split(" ");
                String longest = "";
                for(String word: words){
                    if(word.length() >=longest.length()){
                        longest = word;;
                    }
                }
                Comparator<String> lengthComparator = (x, y) -> {
                   Integer xl= x != null ? x.length() : 0;
                   Integer yl= y != null ? y.length() : 0;
                   return xl.compareTo(yl);

                };
                Problem1.longestString.accumulateAndGet(longest, BinaryOperator.maxBy(lengthComparator));
            });
        }catch (Exception e){
            e.printStackTrace();

        }
        return "Longest string observed by "+Thread.currentThread().getId() + " is " +Problem1.longestString.get();
    }
    public LongestStringFinder(File file){
        this.file = file;
    }
}
