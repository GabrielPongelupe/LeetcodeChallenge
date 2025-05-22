import java.util.ArrayList;
import java.util.Collections;

class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        ArrayList<Integer> numeros1 = new ArrayList<>();
        for (int num : nums1) {
            numeros1.add(num);
        }

        ArrayList<Integer> numeros2 = new ArrayList<>();
        for (int num : nums2) {
            numeros2.add(num);
        }

        // Combine os dois ArrayLists
        numeros1.addAll(numeros2);

        // Ordene a lista combinada
        Collections.sort(numeros1);

        // Calcule a mediana
        int size = numeros1.size();
        double sumMedian;
        if (size % 2 == 0) {
            // Para número par de elementos, calcule a média dos dois elementos centrais
            int mid1 = size / 2 - 1;
            int mid2 = size / 2;
            sumMedian = (numeros1.get(mid1) + numeros1.get(mid2)) / 2.0;
        } else {
            // Para número ímpar de elementos, pegue o elemento central
            int mid = size / 2;
            sumMedian = numeros1.get(mid);
        }

        return sumMedian;
    }
}