# Решение домашнего задания №2.

# Задача 1. 
Входные данные - массив людей RAW_DATA:

                0 - Harry
                0 - Harry
                1 - Harry
                2 - Harry
                3 - Emily
                4 - Jack
                4 - Jack
                5 - Amelia
                5 - Amelia
                6 - Amelia
                7 - Amelia
                8 - Amelia

Рассмотрено две версии задачи:

- Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени, 
найти количество человек в каждой группе (метод taskOneV1(Person[] persons) ). 
Вывод:

                Key: Amelia
                Value:4
                Key: Emily
                Value:1
                Key: Harry
                Value:3
                Key: Jack
                Value:1

- Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени, 
в каждой группе вывести ключ (имя, по которому производилась группировка) и 
всех людей из этой группы с порядковым номером и id (метод taskOne(Person[] persons)). 
Вывод:

                Amelia:
                1 - Amelia (5)
                2 - Amelia (6)
                3 - Amelia (7)
                4 - Amelia (8)                
                Emily:
                1 - Emily (3)                
                Harry:
                1 - Harry (0)
                2 - Harry (1)
                3 - Harry (2)                
                Jack:
                1 - Jack (4)


# Задача 2.
Входные данные: 
- массив чисел: [3, 4, 2, 7], 
- входное число 10

Необходимо найти два элемента массива, которые в сумме дают входное число.

10 -> [3, 7] - вывести пару именно в скобках, числа в сумме равны 10

Здесь также рассмотрено две версии задачи:

- метод taskTwo(int[] array, int sum) - решение с использованием Stream API. 
Используется хеш-таблица для решения этой задачи за линейное время. 
Каждый элемент массива array[i] добавляется в карту (.peek(i->map.put(array[i],i ))). 
Проверяется, есть ли разница уже в карте или нет(map.containsKey(sum-array[i])). 
Если разница есть, пара найдена.

- метод taskTwoV1(int[] array, int sum) - решение без использования Stream API.
 Вначале входной массив сортируется в порядке возрастания.
 Используются два индекса (low и high), изначально указывающие на две конечные точки массива. 
 Затем область поиска уменьшается на каждой итерации цикла.


# Задача 3.
Реализовать функцию нечеткого поиска (метод fuzzySearch(String key, String str))
            
                fuzzySearch("car", "ca6$$#_rtwheel"); // true
                fuzzySearch("cwhl", "cartwheel"); // true
                fuzzySearch("cwhee", "cartwheel"); // true
                fuzzySearch("cartwheel", "cartwheel"); // true
                fuzzySearch("cwheeel", "cartwheel"); // false
                fuzzySearch("lw", "cartwheel"); // false