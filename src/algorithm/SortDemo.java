package algorithm;

import java.util.Random;

public class SortDemo {
    static int[] arr;

    public static void main(String[] args) {
        init();
//        sort05(arr, arr.length);
//        check();
        int value = findK(arr, 10);
        System.out.println("k:::" + value);
    }

    //第K大的数
    private static int findK(int[] arr, int k) {
        int n;
        if (arr == null || (n = arr.length) == 0 || k > n) return 0;
        int l = 0;
        int r = n - 1;
        int p;
        do {
            //复用了快排的分区函数， 小于p...，p，大于p...
            //所以中止条件为： 'p+k-1==n-1' --> 'p==n-k'
            p = divide(arr, l, r);
            if (p < n - k) {//在右侧，移动左边界
                l = p + 1;
            } else {//在左侧，移动右边界
                r = p - 1;
            }
        } while (p != n - k);
        return arr[p];
    }


    //快排 1' 主方法
    private static void sort05(int[] arr, int n) {
        if (arr == null || arr.length <= 1 || arr.length != n) return;
        quickSort(arr, 0, n - 1);
    }

    //快排 2' 递归方法
    private static void quickSort(int[] arr, int l, int r) {
        if (r - l <= 0) return;
        int p = divide(arr, l, r);
        quickSort(arr, l, p - 1);
        quickSort(arr, p + 1, r);
    }

    //快排 3' 分区函数
    private static int divide(int[] arr, int l, int r) {
        //随机p值，注意nextInt为不包含bound
        int random = new Random().nextInt(r - l + 1) + l;
        swap(arr, random, r);
        int i = l;
        for (int j = l; j <= r; j++) {
            if (arr[j] <= arr[r]) {
                swap(arr, i, j);
                i++;
            }
        }
        return --i;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    //归并排序 1' 主方法
    private static void sort04(int[] arr, int n) {
        if (arr == null || arr.length <= 1 || arr.length != n) return;
        mergeSort(arr, 0, n - 1);
    }

    //归并排序 2' 递归方法
    private static void mergeSort(int[] arr, int left, int right) {
        if (right - left <= 0) return;
        int middle = (right + left) / 2;
        mergeSort(arr, left, middle);
        mergeSort(arr, middle + 1, right);
        merge(arr, left, middle, right);
    }

    //归并排序 3' 合并有序数组，利用额外空间
    private static void merge(int[] arr, int left, int middle, int right) {
        int[] temp = new int[right - left + 1];
        int l = left;
        int r = middle + 1;
        int t = 0;
        while (l <= middle && r <= right) {
            if (arr[l] <= arr[r]) {
                temp[t++] = arr[l++];
            } else {
                temp[t++] = arr[r++];
            }
        }
        //处理左数组剩余
        while (l <= middle) {
            temp[t++] = arr[l++];
        }
        //处理右数组剩余
        while (r <= right) {
            temp[t++] = arr[r++];
        }
        //Java中可以用System.arrayCopy(...)提高效率
        for (int i = 0; i < right - left + 1; i++) {
            arr[i + left] = temp[i];
        }
    }

    //选择排序--不稳定--遍历比较、获得最小值、放入数组头部的已排列区域的队尾
    private static void sort03(int[] arr, int n) {
        if (arr == null || n <= 1 || arr.length != n) return;
        int temp = 0;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) minIndex = j;
            }
            if (i == minIndex) continue;
            temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    //插入排序--稳定--抽取、比较、移动、插入
    private static void sort02() {
        if (arr == null || arr.length <= 1) return;
        int out = 0;
        for (int i = 1; i < arr.length; i++) {
            out = arr[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (arr[j] > out) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            arr[j + 1] = out;
        }
    }

    //冒泡排序--稳定--相邻比较、大值向后冒泡
    private static void sort01() {
        if (arr == null || arr.length <= 1) return;
        int temp = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            boolean changed = false;//用于提前结束
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    changed = true;
                }
            }
            if (!changed) break;
        }
    }

    private static void init() {
        arr = new int[]{
                13, 14, 21, 27, 28, 6, 7,
                24, 19, 20, 8, 9, 10,
                15, 16, 17, 18, 22, 3, 4, 23,
                25, 26, 29, 0, 1, 2, 5, 11, 12
        };
//        Random random = new Random();
//        for (int i = 0; i < num; i++) {
//            arr[i] = random.nextInt(num);
//        }

    }

    private static void check() {
//        int printIndex = 0;
        for (int value : arr) {
//            if (printIndex == 50) System.out.println();
            System.out.print(value + ",");
//            printIndex++;
        }
        boolean succeed = true;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                succeed = false;
                break;
            }
        }
        System.out.println("\nsort succeed is " + succeed);
    }

}
