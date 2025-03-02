package Assingment;

import java.util.*;


//This problem is about finding the k-th smallest product formed by multiplying elements from two sorted arrays. Let's break it down step by step.

//Understanding the Problem
//We have two sorted arrays: returns1 and returns2.
//We need to find the k-th smallest combined return (product of one element from each array).
//The order is determined by the ascending sorted products.

//Example Breakdown
//Example 1
//plaintext
//CopyEdit
//returns1 = [2,5]
//returns2 = [3,4]
//k = 2
//We calculate all possible products:
//plaintext
//CopyEdit
//2 * 3 = 6
//2 * 4 = 8
//5 * 3 = 15
//5 * 4 = 20
//Sorted order: [6, 8, 15, 20]
//→ 2nd smallest product is 8.
//Example 2
//plaintext
//CopyEdit
//returns1 = [-4, -2, 0, 3]
//returns2 = [2, 4]
//k = 6
//All possible products:
//plaintext
//CopyEdit
//-4 * 2 = -8
//-4 * 4 = -16
//-2 * 2 = -4
//-2 * 4 = -8
//0 * 2 = 0
//0 * 4 = 0
//3 * 2 = 6
//3 * 4 = 12
//Sorted order: [-16, -8, -8, -4, 0, 0, 6, 12]
//→ 6th smallest product is 0.

//Approach to Solve the Problem
//1.Brute Force (O(M × N) Sorting)
//Compute all possible products.
//Sort the products.
//Return the k-th smallest.
//Time Complexity: O(MNlog⁡(MN))O(MN \log(MN))O(MNlog(MN))
//.Optimized Approach (Binary Search on Product Values)
//Instead of generating all products, use binary search on possible product values.
//Count how many products are less than or equal to mid using a two-pointer technique.
//Adjust the search range based on the count.
//Time Complexity: O(Mlog⁡(MN))O(M \log(MN))O(Mlog(MN))






public class KthSmallestProduct {
    public static int kthSmallestProduct(int[] returns1, int[] returns2, int k) {
        int left = returns1[0] * returns2[0];
        int right = returns1[returns1.length - 1] * returns2[returns2.length - 1];

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (countLessEqual(returns1, returns2, mid) < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private static int countLessEqual(int[] arr1, int[] arr2, int mid) {
        int count = 0;
        int j = arr2.length - 1;

        for (int num1 : arr1) {
            while (j >= 0 && num1 * arr2[j] > mid) {
                j--;
            }
            count += (j + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        int[] returns1 = {2, 5};
        int[] returns2 = {3, 4};
        System.out.println(kthSmallestProduct(returns1, returns2, 2)); // Output: 8

        int[] returns3 = {-4, -2, 0, 3};
        int[] returns4 = {2, 4};
        System.out.println(kthSmallestProduct(returns3, returns4, 6)); // Output: 0
    }
}
