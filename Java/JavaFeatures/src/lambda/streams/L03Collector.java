package lambda.streams;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import util.Person;

/**
 * See The java.util.stream.Collector<T,A,R>
 * - The overall purpose of colletor is to convert T (input) to R (result) using A (accumulator)
 * The interface provides the following 4 important abstract methods
 * - Supplier<A> supplier() : A supplier that accepts void and returns a collection to store A. (return Collection<A>)
 * - BiConsumer<A,T> accumulator() : A bi-consumer that accepts (A, T) and returns void. Add T to A (A = A + T)
 * - BinaryOperator<A> combiner() : A bi-function that accepts (A1, A2) and returns A. Merge A1 and M2 into A. (A = A1 + A2)
 * - Function<A,R> finisher() : A function that accepts (A) and returns R. (convert A to R)
 */

public class L03Collector
{
    public static void main(String arg[])
    {
        // Note: A ==> HashSet<Person> T ==> Person R ==> List<String>
        // ###############################################################################

        /**
         * ###############################################################################
         * Version 1
         * ###############################################################################
         */

        // A supplier that returns a collection -- HashSet of Persons
        Supplier<HashSet<Person>> supplier1 = () -> new HashSet<Person>();

        // Add new person element 't' to collection 'a'
        BiConsumer<HashSet<Person>, Person> accumilator1 = (a, t) -> a.add(t);

        // Merge collections and return merged collection
        BinaryOperator<HashSet<Person>> combiner1 = (a1, a2) -> {
            a1.addAll(a2);
            return a1;
        };

        Function<HashSet<Person>, List<String>> finisher1 = (a) -> a.stream()
            .map(Person::getName)
            .toList();

        Collector<Person, HashSet<Person>, List<String>> c1 = Collector.of(supplier1, accumilator1, combiner1, finisher1);

        List<String> listName = Stream.of(Person.getPersons())
            .collect(c1);

        System.out.println(listName);

        /**
         * ###############################################################################
         * Version 2
         * ###############################################################################
         */

        Supplier<HashSet<Person>> supplier2 = HashSet<Person>::new;
        BiConsumer<HashSet<Person>, Person> accumilator2 = HashSet<Person>::add;
        BinaryOperator<HashSet<Person>> combiner2 = (a1, a2) -> {
            a1.addAll(a2);
            return a1;
        };
        Function<HashSet<Person>, List<String>> finisher2 = (a) -> a.stream()
            .map(Person::getName)
            .toList();

        Collector<Person, HashSet<Person>, List<String>> c2 = Collector.of(supplier2, accumilator2, combiner2, finisher2);
        System.out.println(Stream.of(Person.getPersons()).collect(c2));

        /**
         * ###############################################################################
         * Version 3
         * ###############################################################################
         */

        Collector<Person, HashSet<Person>, List<String>> c3 = Collector.of(HashSet<Person>::new, HashSet<Person>::add, (a1, a2) -> {
            a1.addAll(a2);
            return a1;
        }, (a) -> a.stream()
            .map(Person::getName)
            .toList());

    }
}
