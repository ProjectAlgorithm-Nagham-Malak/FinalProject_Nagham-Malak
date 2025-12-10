package sorting.algorithms.benchmark.dashboard;
public class SortingAlgorithms {

    public static void insertionSort(int[] arr) 
    {
        for (int i = 1 ; i < arr.length ; i++) 
        {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) 
            {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    
    public static void mergeSort(int[] arr) 
    {
        mergeSortRec(arr, 0, arr.length - 1);
    }

    private static void mergeSortRec(int[] arr, int lIndex, int rIndex) {
        if (lIndex >= rIndex) return;
        int mIndex = (lIndex + rIndex) / 2;
        mergeSortRec(arr, lIndex, mIndex);
        mergeSortRec(arr, mIndex + 1, rIndex);
        merge(arr, lIndex, mIndex, rIndex);
    }

    private static void merge(int[] arr, int lIndex, int mIndex, int rIndex) 
    {
        int lSize = mIndex - lIndex + 1;
        int rSize = rIndex - mIndex;

        int[] lArr = new int[lSize];
        int[] rArr = new int[rSize];

        for (int i = 0; i < lSize; i++) 
        {
            lArr[i] = arr[lIndex + i];
        }
        for (int i = 0; i < rSize; i++) 
        {
            rArr[i] = arr[mIndex + 1 + i];
        }

        int i = 0 , j = 0 , k = lIndex;

        while (i < lSize && j < rSize) 
        {
            if (lArr[i] <= rArr[j])
            {
                arr[k++] = lArr[i++];
            }
            else
            {
                arr[k++] = rArr[j++];
            }
        }
        while (i < lSize)
        {
            arr[k++] = lArr[i++];
        }
        while (j < rSize)
        {
            arr[k++] = rArr[j++];
        }
    }

    public static void heapSort(int[] arr) 
    {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--)
        {
            heapify(arr, n, i);
        }
            
        for (int i = n - 1; i > 0; i--) 
        {
            int temp = arr[0] ;
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }

    private static void heapify(int[] arr, int size, int root) 
    {
        int largest = root;
        int left = root * 2 + 1;
        int right = root * 2 + 2;

        if (left < size && arr[left] > arr[largest])
        {
            largest = left;
        }
        if (right < size && arr[right] > arr[largest])
        {
            largest = right;
        }

        if (largest != root) 
        {
            int temp = arr[root];
            arr[root] = arr[largest];
            arr[largest] = temp;
            heapify(arr, size, largest);
        }
    }

    public static void quickSort(int[] arr) 
    {
    quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) 
    {
    while (low < high) 
    {
        int pivot = partition(arr, low, high);

        if (pivot - low < high - pivot) 
        {
            quickSort(arr, low, pivot - 1);
            low = pivot + 1;
        } 
        else
        {
            quickSort(arr, pivot + 1, high);
            high = pivot - 1; 
        }
    }
    }

    private static int partition(int[] arr, int low, int high) 
    {
    int pivot = arr[high];
    int i = low - 1;

    for (int j = low; j < high; j++) 
    {
        if (arr[j] <= pivot) 
        {
            i++;
            int temp = arr[i]; 
            arr[i] = arr[j]; 
            arr[j] = temp;
        }
    }
    int temp = arr[i + 1];
    arr[i + 1] = arr[high];
    arr[high] = temp;
    return i + 1;
    }


    public static void randomizedQuickSort(int[] arr) 
    {
        randomizedQuickSortRec(arr, 0, arr.length - 1);
    }

    private static void randomizedQuickSortRec(int[] arr, int low, int high) 
    {
        if (low >= high) return;

        int randomIndex = low + (int)(Math.random() * (high - low + 1));
        int temp = arr[randomIndex]; arr[randomIndex] = arr[high]; arr[high] = temp;

        int p = partition(arr, low, high);

        randomizedQuickSortRec(arr, low, p - 1);
        randomizedQuickSortRec(arr, p + 1, high);
    }

    public static void SortingBenchmark() 
    {
        int[] DataSize = {1000 , 10000 , 50000 , 100000};
        String[] dataType = {"random" , "sorted" , "reverse" , "few_unique"};

        System.out.println("<<Starting benchmark...>>\n");
      
        for (int i = 0; i < dataType.length; i++) 
        {
            String type = dataType[i];
            System.out.println(" -*-*-*- " + type + " Data -*-*-*- ");
            System.out.println("|  Size   |  Insert  |  Merge  |  Heap  |  Quick   |   RandQuick  |");
            System.out.println("|---------|----------|---------|--------|----------|--------------|");
            
        for (int j = 0; j < DataSize.length; j++) 
        {
            int size = DataSize[j];
            int[] data = generateDataset(type, size);
            double t1 = benchmark(data, "insertion");
            double t2 = benchmark(data, "merge");
            double t3 = benchmark(data, "heap");
            double t4 = benchmark(data, "quick");
            double t5 = benchmark(data, "randquick");

            System.out.printf("| %-7d | %-8.3f | %-7.3f | %-6.3f | %-8.3f | %-12.3f |\n", size, t1, t2, t3, t4, t5);      
        }
            System.out.println();
        }
    }

    private static double benchmark(int[] original, String algorithm) 
    {
        int[] arr = copyArray(original);
        long start = System.nanoTime();

        switch (algorithm) 
        {
            case "insertion": insertionSort(arr); break;
            case "merge": mergeSort(arr); break;
            case "heap": heapSort(arr); break;
            case "quick": quickSort(arr); break;
            case "randquick": randomizedQuickSort(arr); break;
        }

        long end = System.nanoTime();
        return (end - start) / 1_000_000.0;
    }

    private static int[] generateDataset(String type, int size) 
    {
        int[] arr = new int[size];

        switch (type) 
        {
            case "random":
                for (int i = 0; i < size; i++)
                    arr[i] = 1 + (int)(Math.random() * 1_000_000);
                break;

            case "sorted":
                for (int i = 0; i < size; i++) arr[i] = i;
                break;

            case "reverse":
                for (int i = 0; i < size; i++) arr[i] = size - i;
                break;

            case "few_unique":
                for (int i = 0; i < size; i++) arr[i] = i % 5;
                break;
        }
        return arr;
    }

    private static int[] copyArray(int[] arr) 
    {
        int[] c = new int[arr.length];
        for (int i = 0; i < arr.length; i++)
        {
            c[i] = arr[i];
        }
        return c;
 }
}