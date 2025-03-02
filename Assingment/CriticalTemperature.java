package Assingment;

//Approach
//1.Binary Search (1 sample, k=1k=1k=1)
//If we only have one sample, we must check each temperature level sequentially from 1 to nnn, which takes nnn measurements in the worst case.
//2.Optimized Strategy (More than 1 sample, k>1k > 1k>1)
//When we have multiple samples, we can use a dynamic programming approach to minimize the number of tests. The key idea is to use a strategic drop approach, where each test gives us maximum information.
//If a sample does not react, we continue testing at higher temperatures.
//If a sample reacts, we use the remaining samples to check lower temperatures one by one.
//We use DP-based optimization where:
//Let dp[k][m]dp[k][m]dp[k][m] be the number of temperature levels that can be tested with kkk samples and mmm measurements.
//The recurrence relation is:
//dp[k][m]=dp[k−1][m−1]+dp[k][m−1]+1dp[k][m] = dp[k-1][m-1] + dp[k][m-1] + 1dp[k][m]=dp[k−1][m−1]+dp[k][m−1]+1
//dp[k−1][m−1]dp[k-1][m-1]dp[k−1][m−1] represents the case where the material reacts, and we continue testing the lower range with k−1k-1k−1 samples.
//dp[k][m−1]dp[k][m-1]dp[k][m−1] represents the case where the material does not react, and we continue testing the higher range.
//+1+1+1 accounts for the current test.
//We increment mmm until we can cover all nnn temperature levels.

//Understanding the Problem
//if we have a material that can be tested at different temperature levels (1 to n). There exists a critical temperature f such that:
//If the material is tested at or below f, it remains unchanged.
//If the material is tested above f, it reacts or changes its properties, meaning we can’t use it again for further tests.
//Your goal is to find the critical temperature f using the fewest number of tests while following these rules:
//1.If the material changes its properties, you cannot use it for further testing.
//2.If the material does not change, you can reuse it for further testing.
//You are given:
//k = number of identical material samples.
//n = number of temperature levels.
//You need to find f using the minimum number of measurements.

//Step 2: Understanding the Examples
//Example 1: k = 1, n = 2
//We have one sample and two temperature levels:
//n=2(Temperatures: 1 and 2)n = 2 \quad \text{(Temperatures: 1 and 2)}n=2(Temperatures: 1 and 2)
//How to test?
//First, test at temperature 1:
//If it reacts, then f = 0 (i.e., the material reacts even at the lowest temperature).
//If it does not react, we can test at temperature 2.
//Test at temperature 2:
//If it reacts, then f = 1.
//If it does not react, then f = 2.
//Conclusion
//We need at least 2 tests in the worst case.
//Example 2: k = 2, n = 6
//We have two samples and six temperature levels:
//n=6(Temperatures: 1, 2, 3, 4, 5, 6)n = 6 \quad \text{(Temperatures: 1, 2, 3, 4, 5, 6)}n=6(Temperatures: 1, 2, 3, 4, 5, 6)
//How to test efficiently?
//Instead of testing one by one (which would take 6 tests), we use a strategic approach:
//Test at temperature 3 first (mid-point).
//If it reacts, then f is in [0,2], and we use the second sample to test in that range (test at 1, then 2).
//If it does not react, then f is in [3,6], and we test at 5, and so on.
//Conclusion
//By using smart testing, we need only 3 measurements instead of 6.
//Example 3: k = 3, n = 14
//We have three samples and 14 temperature levels.
//1.First test at 5:
//If it reacts, f is in [0,4] → use remaining samples to test there.
//If it does not react, f is in [5,14] → continue testing in that range.
//Next test at 9.
//Then test at 12, etc.
//Conclusion
//We only need 4 measurements.

//Step 3: Observing the Pattern
//Instead of testing one by one (which would take nnn tests for k=1k = 1k=1), we spread out the tests to reduce the number of required measurements.
//If we have 1 sample, we must test every level sequentially → Worst case: nnn tests.
//If we have 2 samples, we can use a binary-like approach → O(n)O(\sqrt{n})O(n​) tests.
//If we have more samples, we can use a more efficient dropping strategy → O(log⁡n)O(\log n)O(logn) tests.

//Step 4: Finding the Minimum Number of Measurements Using DP
//We can define:
//dp[k][m]dp[k][m]dp[k][m] = Maximum temperature levels we can test with kkk samples in mmm measurements.
//The recurrence relation:
//dp[k][m]=dp[k−1][m−1]+dp[k][m−1]+1dp[k][m] = dp[k-1][m-1] + dp[k][m-1] + 1dp[k][m]=dp[k−1][m−1]+dp[k][m−1]+1
//Where:
//dp[k−1][m−1]dp[k-1][m-1]dp[k−1][m−1] = When the material reacts, we use one fewer sample and one fewer test.
//dp[k][m−1]dp[k][m-1]dp[k][m−1] = When the material does not react, we continue testing with the same samples and one fewer test.
//+1 for the current test.
//We increase mmm until dp[k][m]≥ndp[k][m] \geq ndp[k][m]≥n, meaning we can check all temperature levels.

public class CriticalTemperature {
    public static int minMeasurements(int k, int n) {
        if (k == 1) {
            return n; // If we have only one sample, we must do a linear search.
        }

        int[][] dp = new int[k + 1][n + 1];
        int m = 0; // Number of measurements

        while (dp[k][m] < n) {
            m++;
            for (int i = 1; i <= k; i++) {
                dp[i][m] = dp[i - 1][m - 1] + dp[i][m - 1] + 1;
            }
        }

        return m;
    }

    public static void main(String[] args) {
        System.out.println(minMeasurements(1, 2));  // Output: 2
        System.out.println(minMeasurements(2, 6));  // Output: 3
        System.out.println(minMeasurements(3, 14)); // Output: 4
        System.out.println(minMeasurements(4, 30)); // Output: 5
        System.out.println(minMeasurements(5, 50)); // Output: 6
    }
}
