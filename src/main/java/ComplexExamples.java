import com.google.common.collect.Ordering;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.*;


public class ComplexExamples {

    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }

        @Override
        public String toString() {
            return name + " (" + id + ") ";
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };

    //Task1 --------------------------------------------------------------------------------
    /*На входе массив Person[] - на выходе хеш-таблица без дубликатов и null-объектов Person,
    сгруппированная по имени */
    public static Map<String,List<Person>> taskOne (Person[] persons) {
        return Arrays.stream(persons)
                .distinct()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Person::getName).reversed())
                .collect(Collectors.groupingBy(Person::getName));
    }

    //Task2 --------------------------------------------------------------------------------

    /* решение без использования Stream API.
    Вначале входной массив сортируется в порядке возрастания.
    Используются два индекса (low и high), изначально указывающие на две конечные точки массива.
    Затем область поиска уменьшается на каждой итерации цикла.*/
    public static String taskTwoV1(int[] array, int sum) {
        String result = null;
        Arrays.sort(array);
        int first = 0;
        int last = array.length - 1;
        while (first < last) {
            int s = array[first] + array[last];
            if (s == sum) {
                result = "[" + array[first] + "," + array[last] + "]";
                break;
            } else {
                if (s < sum) first++;
                else last--;
            }
        }
        return result;
    }


    /* решение с использованием Stream API.
    Используется хеш-таблица для решения этой задачи за линейное время.
    Каждый элемент массива array[i] добавляется в карту (.peek(i->map.put(array[i],i ))).
    Проверяется, есть ли разница значений уже в карте или нет(map.containsKey(sum-array[i])).
    Если разница есть, пара найдена.*/
    public static List<Integer> taskTwo(int[] array, int sum) {
        Map<Integer,Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        IntStream.range(0, array.length)
                .peek(i->map.put(array[i],i ))
                .filter(i-> map.containsKey(sum-array[i]))
                .peek(i->list.add(array[map.get(sum-array[i])]))
                .peek(i->list.add(array[i]))
                .limit(1)
                .count();
        return list;
    }

    //Task3 --------------------------------------------------------------------------------
    public static boolean fuzzySearch(String key, String str) {
        StringBuilder sb = new StringBuilder(str);
        List<Integer> list = new ArrayList<>();
        // разбить ключ поиска на массив символов
        String[] chars = key.split("");
        // проверка на то, что все символы ключа содержатся в слове
        boolean r1 = Arrays.stream(chars)
                .map(ch->{
                    boolean flag = Pattern.compile(".*" + ch+ ".*").matcher(sb.toString()).matches();
                    if (flag) {
                        int index = sb.indexOf(ch);
                        sb.deleteCharAt(index);
                        list.add(index);
                    }
                    return flag;
                })
                .allMatch( f->f);
        // проверка правильности последовательности символов
        boolean r2 = Ordering. natural().isOrdered(list);

        return r1&r2;
    }


    public static void main(String[] args) {
        System.out.println("Raw data (taskOne):");

        for (Person person : RAW_DATA) {
            System.out.println(person.id + " - " + person.name);
        }

        /*Task1 - убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени,
        в каждой группе вывести ключ (имя, по которому производилась группировка) и
        всех людей из этой группы с порядковым номером и id*/
        System.out.println();
        System.out.println("************************************************************");
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id (taskOne):");
        taskOne(RAW_DATA).entrySet().stream()
                .map(entry -> {
                    AtomicInteger index = new AtomicInteger();
                    return new StringBuilder(entry.getKey() + ":" + "\n")
                            .append(entry.getValue().stream()
                                    .map(person -> (index.getAndIncrement() + 1) + " - " + person.toString())
                                    .collect(Collectors.joining("\n")))
                            .toString();
                })
                .forEach(System.out::println);
        System.out.println();

        /* Task1 - убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени,
        найти количество человек в каждой группе*/
        System.out.println("Duplicate filtered, grouped by name, sorted by name " + "\n" +
                "and find quantity of persons with same name (taskOne):");
        taskOne(RAW_DATA).entrySet().stream()
                .map(entry -> new StringBuilder("Key: " + entry.getKey() + "\n")
                        .append("Value: " + entry.getValue().size()).toString())
                .forEach(System.out::println);
        System.out.println();

        int[] array = {3, 4, 2, 7};
        System.out.println("************************************************************");
        System.out.println();
        System.out.println("Input array for taskTwo: " + Arrays.toString(array));
        System.out.println("Sum of elements equals 10: " + taskTwo(array,10).toString());
        System.out.println();
        System.out.println("Input array for taskTwoV1: " + Arrays.toString(array));
        System.out.println("Sum of elements equals 10: " + taskTwoV1(array,10));
        System.out.println();
        System.out.println("************************************************************");
        System.out.println();

        System.out.println("Function fuzzySearch results:");
        System.out.println("Result fuzzySearch(\"car\", \"ca6$$#_rtwheel\"): " + fuzzySearch("car", "ca6$$#_rtwheel")); // true
        System.out.println("Result fuzzySearch(\"cwhl\", \"cartwheel\"): " + fuzzySearch("cwhl", "cartwheel")); // true
        System.out.println("Result fuzzySearch(\"cwhee\", \"cartwheel\"): " + fuzzySearch("cwhee", "cartwheel")); // true
        System.out.println("Result fuzzySearch(\"cartwheel\", \"cartwheel\"): " + fuzzySearch("cartwheel", "cartwheel")); // true
        System.out.println("Result fuzzySearch(\"cwheeel\", \"cartwheel\"): " + fuzzySearch("cwheeel", "cartwheel")); // false
        System.out.println("Result fuzzySearch(\"lw\", \"cartwheel\"): " + fuzzySearch("lw", "cartwheel")); // false

    }
}
