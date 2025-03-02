package Assingment;


import java.util.*;

//The goal of this code is to determine the minimum number of rewards needed to distribute among employees based on their performance ratings while ensuring the following conditions:
//solution
//1.Every employee gets at least one reward.
//2.Employees with a higher rating than their adjacent colleagues must get more rewards than them.

public class EmployeeRewards {
    public static int minRewards(int[] ratings) {
        int n = ratings.length;
        int[] rewards = new int[n];
        
        // Step 1: Give each employee at least one reward
        Arrays.fill(rewards, 1);

        // Step 2: Left to right pass - Give more rewards if right neighbor has a higher rating
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                rewards[i] = rewards[i - 1] + 1;
            }
        }

        // Step 3: Right to left pass - Ensure left neighbor gets more rewards if it has a higher rating
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1);
            }
        }

        // Step 4: Sum up all rewards to get the minimum total rewards needed
        int totalRewards = 0;
        for (int reward : rewards) {
            totalRewards += reward;
        }
        return totalRewards;
    }

    public static void main(String[] args) {
        // Test cases to check correctness
        System.out.println(minRewards(new int[]{1, 0, 2})); // Output: 5
        System.out.println(minRewards(new int[]{1, 2, 2})); // Output: 4
    }
}

//Example:
//ratings = [1, 0, 2]
//Initial rewards: [1, 1, 1]
//After left-to-right pass: [2, 1, 2]

//Example:
//ratings = [1, 2, 2]
//After left-to-right pass: [1, 2, 1]
//After right-to-left pass: [1, 2, 1] (unchanged)
 //Example:
//ratings = [1, 0, 2]
//After left-to-right pass: [2, 1, 2]
//After right-to-left pass: [2, 1, 2] (unchanged)