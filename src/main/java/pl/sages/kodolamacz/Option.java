package pl.sages.kodolamacz;

import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

public class Option {

    public static void main(String[] args) {

        // pudełko, które trzyma wartość lub jest puste
        Optional<Value> value = getValue();

        // zamiast wprost wykonywać przekształcenie na wartości
        // mówię w jaki sposób chcę to wykonać i jeśli wartość
        // jest to zostanie wykonane to przekształcenie
        // dalej nie wiem czy ta wartość istnieje
        Optional<String> s = value.map(new Function<Value, String>() {
            @Override
            public String apply(Value value) {
                return value.name;
            }
        });

        if(s.isPresent()){
            System.out.println(s.get());
        }else{
            System.out.println("brak wartości");
        }

        // albo bierzemy to co siedzi w środku, a jeśli nic nie ma to bierzemy pusty string ""
        String s1 = s.orElse("");
//
//        if(s == null){
//            s = "";
//        }

    }

    public static Optional<Value> getValue(){
        if(new Random().nextBoolean()){
            return Optional.of(new Value());
        }else{
            return Optional.empty();
        }
    }



}

class Value {
    int id = 0;
    String name = "abc";
    String mightBeNull = null;

    public String getName() {
        return name;
    }

    public Optional<String> getValue(){
        return Optional.ofNullable(mightBeNull);
    }

    public String getNameUpperCase(){
        return name.toUpperCase();
    }
}
