package zadanie1;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class People {
    public static void main(String[] args) {
        InputStream is = People.class.getResourceAsStream("people.txt");

        Scanner scanner = new Scanner(is);

        Collection<Person> people = new LinkedList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            String[] data = line.split(";");

            String name = data[0];
            String surname = data[1];
            float height = Float.parseFloat(data[2]);
            float weight = Float.parseFloat(data[3]);

            Person person = new Person(name, surname, height, weight);

            people.add(person);
            System.out.println(person.toString());
        }

        System.out.println("Max height: " + getCompared(people, Comparator.naturalOrder(), (Person person) -> {
            return person.getHeight();
        }));
        System.out.println("Min height: " + getCompared(people, Comparator.reverseOrder(), Person::getHeight));

        System.out.println("Max weight: " + getCompared(people, Comparator.naturalOrder(), Person::getWeight));
        System.out.println("Min weight: " + getCompared(people, Comparator.reverseOrder(), Person::getWeight));

        System.out.println("Avg height: " + getAvg(people, Person::getHeight));
        System.out.println("Avg weight: " + getAvg(people, Person::getWeight));
    }

    private static <Type, FieldType> Collection<Type> getCompared(Collection<Type> collection,
                                                   Comparator<FieldType> comparator,
                                                   Function<Type, FieldType> getField) {
        if(collection.isEmpty()) {
            return Collections.emptyList();
        }

        Collection<Type> result = new LinkedList<>();
        result.add(collection.iterator().next());

        for(Type object : collection) {
            FieldType objectField = getField.apply(object);
            FieldType resultField = getField.apply(result.iterator().next());

            if(comparator.compare(objectField, resultField) > 0) {
                result.clear();
                result.add(object);
            } else if(objectField.equals(resultField)) {
                result.add(object);
            }
        }

        return result;
    }

    private static <Type, FieldType extends Number> double getAvg(Collection<Type> collection,
                                                                  Function<Type, FieldType> getField) {
        if(collection.isEmpty()) {
            return 0;
        }

        double sum = 0;

        for(Type object : collection) {
            sum += getField.apply(object).doubleValue();
        }

        return sum / collection.size();
    }
}
