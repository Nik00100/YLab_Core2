import org.junit.jupiter.api.Test;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ComplexExamplesTest {

    @Test
    void taskOne() {
        ComplexExamples.Person[] persons = new ComplexExamples.Person[]{
                new ComplexExamples.Person(0, "Егор"),
                new ComplexExamples.Person(0, "Настя"), // дубликат
                new ComplexExamples.Person(1, "Виктор"), // тёзка
                new ComplexExamples.Person(2, "Егор"),
                new ComplexExamples.Person(3, "Настя"),
        };

        Map<String, List<ComplexExamples.Person>> map = new HashMap<>();

        List<ComplexExamples.Person> victors = new ArrayList<>();
        victors.add(new ComplexExamples.Person(1, "Виктор"));

        List<ComplexExamples.Person> egors = new ArrayList<>();
        egors.add(new ComplexExamples.Person(0, "Егор"));
        egors.add(new ComplexExamples.Person(2, "Егор"));

        List<ComplexExamples.Person> nastyas = new ArrayList<>();
        nastyas.add(new ComplexExamples.Person(0, "Настя"));
        nastyas.add(new ComplexExamples.Person(3, "Настя"));

        map.put("Виктор", victors);
        map.put("Егор", egors);
        map.put("Настя", nastyas);

        assertEquals(map.keySet().size(),
                ComplexExamples.taskOne(persons).keySet().size());
    }

    @Test
    void taskTwo() {
        int[] inputArray = {1, 3, 4, 2, 8, 12, 7, 4};
        int sum = 11;
        List<Integer> result = new ArrayList<>();
        result.add(3);
        result.add(8);

        assertEquals(result.toString(),
                ComplexExamples.taskTwo(inputArray,sum).toString());
    }

    @Test
    void fuzzySearch() {
        assertEquals(true,
                ComplexExamples.fuzzySearch("car", "carshering"));

        assertEquals(false,
                ComplexExamples.fuzzySearch("foot", "forthrightness"));

    }
}