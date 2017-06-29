package pl.sages.kodolamacz;

import java.util.Optional;
import java.util.function.Function;

public class Lambda {

    public static void main(String[] args) {
        Optional<Value> value = Option.getValue();

        value.map(v -> v.getNameUpperCase());
        Function<Value, String> function1 = new Function<Value, String>() {
            @Override
            public String apply(Value v) {
                return v.getName();
            }
        };

        Function<Value, String> function2 = v -> v.getName();

        Function<Value, String> function = valueInner -> valueInner.id * 2 + "abc";
        // jesli wartosc siedzi to ją przekształć, jeśli nie to dalej będzie Optional.empty()
        Optional<String> s = value.map(function);


        Optional<String> mapValue = Optional.empty();
        if(value.isPresent()){
            mapValue = Optional.of(function.apply(value.get()));
        }

    }

}
