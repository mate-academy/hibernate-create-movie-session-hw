package mate.academy.model;

import java.util.Arrays;

public class PigLatin {
    public static String pigIt(String str) {
        String[] x = str.split("");
        return Arrays.stream(x)
                .map(y -> y.replace(y.charAt(0), y.charAt(1)))
                .map(y -> y.replace(y.charAt(y.length() - 1), y.charAt(0)))
                .map(b -> b + "ay")
                .reduce((v, f) -> v + f).orElseThrow();

    }
}