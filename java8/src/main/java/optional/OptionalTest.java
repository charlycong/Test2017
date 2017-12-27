package optional;

import java.util.Optional;

/**
 * @author etix-2017-3
 * @version 1.0
 * @Date 2017-12-22 09:26
 */
public class OptionalTest {

    int a1;

    int a2;

    public static void main(String[] arg) {

        Optional<Optional<Optional<Long>>> optional = Optional.of(Optional.of(Optional.of(Long.parseLong("10"))));
        System.out.println(optional.flatMap(Optional::get));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(optional.flatMap(Optional::get));
        System.out.println(optional.flatMap(Optional::get));
        System.out.println(optional.flatMap(Optional::get));
        System.out.println(optional.flatMap(Optional::get));
        System.out.println(optional.flatMap(Optional::get));
    }
}
