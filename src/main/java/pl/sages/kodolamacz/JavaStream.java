package pl.sages.kodolamacz;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JavaStream {

    public static void main(String[] args) {
        List<String> stringList = Arrays.asList("Ala","ma","kota","i","psa","Franek","ma","mysz");
        List<Integer> stringLengths = new ArrayList<>(stringList.size());
        for (String s : stringList) {
            int length = s.length();
            stringLengths.add(length);
        }

        Stream<String> stream = stringList.stream();
        Stream<String> lowerStream = stream.map(s -> s.toLowerCase());
        Stream<Integer> integerStream = lowerStream.map(s -> s.length());
        Stream<Integer> integerStream1 = integerStream.map(k -> k*k);

        Stream<Integer> integerStream2 = stringList.stream().map(s -> s.toLowerCase())
                .map(s -> s.length()).map(i -> i * i);

        stringList.stream().map(s -> s.toLowerCase().length() * s.toLowerCase().length());

        // wypisanie kolejnych elementów bez zbierania strumienia
        integerStream1.forEach(i -> System.out.println(i));

        // tutaj zbieramy je do listy
        List<Integer> collect = integerStream2.collect(Collectors.toList());

        for (Integer integer : collect) {
            System.out.println(integer);
        }


        IntStream intStream = stringList.stream().mapToInt(s -> s.length());
        OptionalInt min = intStream.min();
        // nie można drugi raz przekształcać strumienia
        // więc to rzuca błąd
        //intStream.filter(i -> i > 0).forEach(i -> System.out.println(i));
        // ale można (i należy) wielokrotnie otwierać strumień z kolekcji
        stringList.stream().mapToInt(s -> s.length()).forEach(i -> System.out.println(i));

        List<String> result = new ArrayList<>();
        for (String s : stringList) {
            // chcemy sprawdzić czy pierwsza litera napisu jest "a"
            if(s.toLowerCase().charAt(0) == 'a'){
                result.add(s);
            }
        }

        // w filter musi byc funkcja, która zwraca boolean == Predicate
        stringList.stream().filter(s -> s.toLowerCase().charAt(0) == 'a')
                .forEach(s -> System.out.println(s));

        long streamLength = stringList.stream().count();
//        stringList.stream().

        System.out.println("Bez skończenia strumienia");
        stringList.stream().map(s -> mapToIntAndPrint(s));
        System.out.println("forEach");
        stringList.stream().map(s -> mapToIntAndPrint(s)).forEach(i -> System.out.println(i));
        System.out.println("Z skończeniem strumienia");
        List<Integer> collect1 = stringList.stream().map(s -> mapToIntAndPrint(s)).collect(Collectors.toList());
        List<Integer> collectRef = stringList.stream()
                .map(JavaStream::mapToIntAndPrint)
                .collect(Collectors.toList());

        List<List<Character>> listOfLists = stringList.stream().map(s -> splitIntoChars(s)).collect(Collectors.toList());
        List<Character> lists = stringList.stream().flatMap(s -> splitIntoChars(s).stream()).collect(Collectors.toList());


        System.out.println(lists);
        System.out.println(listOfLists);

        List<Integer> values = Arrays.asList(1,2,3,4,5);

        int silnia = values.stream().reduce((a, b) -> a * b).orElse(1);
        System.out.println("silnia="+silnia);

        Double aDouble = values.stream().map(i -> (double) i).reduce((a, b) -> (a + b) * 0.5).orElse(0.0);
        System.out.println("średnia="+aDouble);

        MeanAccumulator reduce = values.stream().reduce(new MeanAccumulator(),
                (ma, i) -> ma.add(i),
                (u, u2) -> u.add(u2));
        System.out.println("średnia="+reduce.getMean());


        System.out.println(stringList.stream().reduce((a, b) -> a + " " + b).orElse(""));

        List<Integer> ints = Arrays.asList(1,2,3,3,3,4,5,17,18,22);
        // na strumieniach sporo rzeczy można zrobić krócej
        // zamiast tworzyć nową klasę anonimową z wyrażenia lambda, która
        // jest bardzo prosta bo używa tylko jednej metody
        ints.stream().distinct().forEach(i -> System.out.println(i));
        // to możemy powiedzieć wprost - użyj tej metody
        ints.stream().distinct().forEach(System.out::println);

        Set<Integer> uniqueValues = new HashSet<>();
        for (Integer integer : ints) {
            uniqueValues.add(integer);
        }

        for (Integer uniqueValue : uniqueValues) {
            System.out.println(uniqueValue);
        }

        List<Integer> mess = Arrays.asList(34, 1, -3, 9, 17);
        System.out.println(mess.stream().sorted().collect(Collectors.toList()));

        List<String> stringMess = Arrays.asList("kot", "bieszczady", "toruń", "warszawa","kiosk");
        System.out.println(stringMess.stream().sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList()));

        // pomija n elementów - w tym wypadku 3 (kot, bieszczady i torun)
        stringMess.stream().skip(3).forEach(System.out::println);

        // ogranicza strumien do 2 elementow (czyli kot i bieszczady)
        stringMess.stream().limit(2).forEach(System.out::println);

        stringMess.stream()
                .skip(2)
                .limit(2)
                .map(s -> "Miasto " + s.toUpperCase())
                .forEach(System.out::println);

//        IntStream infiniteStream = IntStream.generate(() -> new Random().nextInt());
//        infiniteStream.forEach(i -> {
//            try {
//                Thread.sleep(100);
//                System.out.println("Losowa wartość="+i);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

        Random rand = new Random();
        IntStream tempStream = IntStream.generate(() -> rand.nextInt(50)-10);
        tempStream.forEach(i -> {
            try {
                Thread.sleep(1000);
                System.out.print("Temperatura wynosi "+i + " stopni");
                if(i > 30){
                    System.out.print(" strasznie gorąco!");
                }else if(i < 0){
                    System.out.print(" ale zimno!");
                }
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }


    static class MeanAccumulator{
        int sum = 0;
        int count = 0;

        MeanAccumulator add(int i){
            sum += i;
            count++;
            return this;
        }

        MeanAccumulator add(MeanAccumulator other){
            this.count += other.count;
            this.sum += other.sum;
            return this;
        }

        double getMean(){
            return sum * 1.0 / count;
        }
    }

    private static List<Character> splitIntoChars(String s){
        List<Character> characters =  new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            characters.add(c);
        }
        return characters;
    }

    private static int mapToIntAndPrint(String s){
        // wypisuje, aby śledzić czy metoda została zawołana
        System.out.println("Wołam metodę z parametrem = "+s);
        return s.length();
    }

    private void method(String a1, String a2){}
    private void method(int value1, int k){}
}
