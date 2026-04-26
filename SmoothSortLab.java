import java.io.*;
import java.util.*;

public class SmoothSortLab {
    // Счетчик итераций
    public static long iterations = 0;

    // Рекурсивная функция для получения чисел Леонардо (L_k = L_{k-1} + L_{k-2} + 1)
    static int leonardo(int k) {
        if (k < 2) return 1;
        return leonardo(k - 1) + leonardo(k - 2) + 1;
    }

    // Метод построения кучи (пирамиды) на основе чисел Леонардо
    static void heapify(int[] arr, int start, int end) {
        int i = start;
        int j = 0;
        int k = 0;
        // Определение границ текущего поддерева
        while (k < end - start + 1) {
            iterations++; // Учет итерации при расчете границ
            if ((k & 0xAAAAAAAA) != 0) {
                j = j + i; i = i >> 1;
            } else {
                i = i + j; j = j >> 1;
            }
            k = k + 1;
        }
        // Восстановление порядка внутри кучи (просеивание)
        while (i > 0) {
            j = j >> 1;
            k = i + j;
            while (k < end) {
                iterations++; // Учет итерации при сравнении/перестановке
                if (arr[k] > arr[k - i]) break;
                // Обмен элементов, если нарушен порядок кучи
                int temp = arr[k];
                arr[k] = arr[k - i];
                arr[k - i] = temp;
                k = k + i;
            }
            i = j;
        }
    }

    // Главный метод алгоритма Smoothsort
    static void smoothSort(int[] arr) {
        iterations = 0; // Сброс счетчика перед каждым вызовом (Пункт 3 ТЗ)
        int n = arr.length;
        if (n < 2) return; // Массив из 1 элемента уже отсортирован

        int p = n - 1;
        int q = p;
        int r = 0;

        // Первый этап: построение последовательности куч Леонардо
        while (p > 0) {
            iterations++;
            if ((r & 0x03) == 0) {
                heapify(arr, r, q);
            }
            if (leonardo(r) == p) {
                r = r + 1;
            } else {
                r = r - 1;
                q = q - leonardo(r);
                heapify(arr, r, q);
                q = r - 1;
                r = r + 1;
            }
            // Вывод корня кучи (наибольшего элемента) в конец
            int temp = arr[0];
            arr[0] = arr[p];
            arr[p] = temp;
            p = p - 1;
        }

        // Второй этап: финальное упорядочивание
        // Это обеспечивает адаптивность на почти отсортированных данных
        for (int i = 0; i < n - 1; i++) {
            int j = i + 1;
            while (j > 0 && arr[j] < arr[j - 1]) {
                iterations++;
                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j = j - 1;
            }
        }
    }

    // ГЕНЕРАТОР: создает 100 файлов с данными разного размера
    public static void generateDataFiles(int setsCount, int step) throws IOException {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdir();
        Random random = new Random();
        for (int i = 1; i <= setsCount; i++) {
            int size = i * step;
            PrintWriter pw = new PrintWriter(new FileWriter("data/set_" + size + ".txt"));
            for (int j = 0; j < size; j++) {
                pw.println(random.nextInt(1000000)); // Генерация случайных чисел
            }
            pw.close();
        }
    }

    // ЧТЕНИЕ: загрузка данных из файла перед сортировкой
    public static int[] readDataFromFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        List<Integer> list = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            list.add(Integer.parseInt(line.trim()));
        }
        br.close();
        // Преобразование коллекции ArrayList обратно в массив int[]
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) throws IOException {
        int count = 100; // Всего 100 наборов данных
        int step = 100;  // Шаг размера (от 100 до 10 000)

        // Подготовка данных заранее
        generateDataFiles(count, step);

        // Формат вывода для удобного копирования в Excel/CSV
        System.out.println("Size;Iterations;Time_ns");

        for (int i = 1; i <= count; i++) {
            int size = i * step;
            // Чтение данных

            int[] data = readDataFromFile("data/set_" + size + ".txt");

//            int[] data = new int[size];
//            for(int j=0; j<size; j++) data[j] = j;  - Полностью отсортированный массив

            // ИЗМЕРЕНИЕ: только работа алгоритма
            long startTime = System.nanoTime();
            smoothSort(data);
            long endTime = System.nanoTime();

            // Вывод результатов эксперимента
            System.out.println(size + ";" + iterations + ";" + (endTime - startTime));
        }
    }
}
