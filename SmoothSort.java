import java.util.List;

public class SmoothSort {
    public static long iterations = 0; // Счётчик итераций для замера производительности

    /**
     * Вычисляет числа Леонардо (1, 1, 3, 5, 9, 15, 25, 41...)
     * Нужны для определения размеров куч
     */
    private static int leonardo(int k) {
        if (k < 2) return 1;
        return leonardo(k - 1) + leonardo(k - 2) + 1;
    }

    // ========== МЕТОДЫ ДЛЯ МАССИВА ==========

    /**
     * ГЛАВНЫЙ МЕТОД СОРТИРОВКИ МАССИВА
     */
    public static void sort(int[] arr) {
        iterations = 0;
        int n = arr.length;
        if (n < 2) return;

        int p = n - 1, q = p, r = 0;

        //Построение куч Леонардо
        while (p > 0) {
            iterations++;
            if ((r & 0x03) == 0) heapify(arr, r, q);
            if (leonardo(r) == p) r++;
            else {
                r--;
                q -= leonardo(r);
                heapify(arr, r, q);
                q = r - 1;
                r++;
            }
            // Перемещаем максимальный элемент в конец
            int temp = arr[0]; arr[0] = arr[p]; arr[p] = temp;
            p--;
        }

       //сортировка
        for (int i = 0; i < n - 1; i++) {
            int j = i + 1;
            while (j > 0 && arr[j] < arr[j - 1]) {
                iterations++;
                int temp = arr[j]; arr[j] = arr[j - 1]; arr[j - 1] = temp;
                j--;
            }
        }
    }

    /**
     * ВОССТАНОВЛЕНИЕ КУЧИ (просеивание)
     * Отвечает за поддержание свойства кучи: родитель > детей
     * Использует битовые операции для навигации по дереву Леонардо
     */
    private static void heapify(int[] arr, int start, int end) {
        int i = start, j = 0, k = 0;
        // Навигация по структуре дерева
        while (k < end - start + 1) {
            iterations++;
            if ((k & 0xAAAAAAAA) != 0) { j += i; i >>= 1; }
            else { i += j; j >>= 1; }
            k++;
        }
        // Просеивание элементов
        while (i > 0) {
            j >>= 1; k = i + j;
            while (k < end) {
                iterations++;
                if (arr[k] > arr[k - i]) break;
                int temp = arr[k]; arr[k] = arr[k - i]; arr[k - i] = temp;
                k += i;
            }
            i = j;
        }
    }

    // ========== МЕТОДЫ ДЛЯ КОЛЛЕКЦИЙ (ArrayList) ==========

    /**
     * ГЛАВНЫЙ МЕТОД СОРТИРОВКИ КОЛЛЕКЦИИ
     */
    public static void sort(List<Integer> list) {
        iterations = 0;
        int n = list.size();
        if (n < 2) return;

        int p = n - 1, q = p, r = 0;

        //Построение куч Леонардо
        while (p > 0) {
            iterations++;
            if ((r & 0x03) == 0) heapifyList(list, r, q);
            if (leonardo(r) == p) r++;
            else {
                r--;
                q -= leonardo(r);
                heapifyList(list, r, q);
                q = r - 1; r++;
            }
            // Обмен корня с последним элементом
            int temp = list.get(0);
            list.set(0, list.get(p));
            list.set(p, temp);
            p--;
        }

       //сортировка
        for (int i = 0; i < n - 1; i++) {
            int j = i + 1;
            while (j > 0 && list.get(j) < list.get(j - 1)) {
                iterations++;
                int temp = list.get(j);
                list.set(j, list.get(j - 1));
                list.set(j - 1, temp);
                j--;
            }
        }
    }

    /**
     * ВОССТАНОВЛЕНИЕ КУЧИ ДЛЯ КОЛЛЕКЦИИ
     */
    private static void heapifyList(List<Integer> list, int start, int end) {
        int i = start, j = 0, k = 0;
        while (k < end - start + 1) {
            iterations++;
            if ((k & 0xAAAAAAAA) != 0) { j += i; i >>= 1; }
            else { i += j; j >>= 1; }
            k++;
        }
        while (i > 0) {
            j >>= 1; k = i + j;
            while (k < end) {
                iterations++;
                if (list.get(k) > list.get(k - i)) break;
                int temp = list.get(k);
                list.set(k, list.get(k - i));
                list.set(k - i, temp);
                k += i;
            }
            i = j;
        }
    }
}