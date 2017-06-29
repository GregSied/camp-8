package pl.sages.kodolamacz;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiFunction;

public class Calc {


    public static void main(String[] args) {

        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        BiFunction<Integer, Integer, Integer> sub = (a, b) -> a - b;
        BiFunction<Integer, Integer, Integer> mult = (a, b) -> a * b;
        BiFunction<Integer, Integer, Integer> div = (a, b) -> a / b;

        Map<String, BiFunction<Integer, Integer, Integer>> operationMap = new HashMap<>();
        operationMap.put("+", add);
        operationMap.put("-", sub);
        operationMap.put("*", mult);
        operationMap.put("/", div);


        Scanner scanner = new Scanner(System.in);
        String next = "t";
        while("t".equals(next)){
            System.out.println("Podaj dwie wartości");
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            System.out.println("Podaj operację (+ - / *)");
            String o = scanner.next();
            BiFunction<Integer, Integer, Integer> function = operationMap.get(o);
            System.out.println(function.apply(a, b));
            System.out.println("czy dalej? t/n");
            next = scanner.next();
        }
    }


}


