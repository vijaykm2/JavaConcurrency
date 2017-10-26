import problem1.Problem1;
import problem3.Problem3;
import problem5.Problem5;
import problem6.Problem6;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Problem1 problem1 = new Problem1("/Users/vkrish10/Documents/.spring-tutorials/JavaConcurrency/src/problem1");
        problem1.execute();
        Problem3 problem3 = new Problem3();
        problem3.execute();
        Problem5 problem5 = new Problem5("src/problem1");
        problem5.execute();
        System.out.println();
        System.out.println("executing problem 6");
        Problem6 problem6 = new Problem6("src/problem1");
        problem6.execute();

    }
}
