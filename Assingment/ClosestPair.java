package Assingment;

import java.util.*;

public class ClosestPair {
    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length;
        int minDistance = Integer.MAX_VALUE;
        int[] result = new int[2];

//n → Number of points.
//minDistance → Stores the minimum Manhattan distance found.
//result[2] → Stores the indices of the closest pair.
        
        // Iterate through all possible pairs

//Loops through all possible pairs (i, j) where i < j to avoid duplicates.
//Calculates Manhattan distance between (x_coords[i], y_coords[i]) and (x_coords[j], y_coords[j]).
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int distance = Math.abs(x_coords[i] - x_coords[j]) + Math.abs(y_coords[i] - y_coords[j]);
                
                // Update the closest pair if a smaller distance is found
                //f the current distance is smaller, update minDistance and store indices i and j in result.
                // If distances are equal, choose the smallest index pair (i, j).
                //Ensures lexicographical order (smallest i, then j).
                if (distance < minDistance || (distance == minDistance && (i < result[0] || (i == result[0] && j < result[1])))) {
                    minDistance = distance;
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }
    

    public static void main(String[] args) {
        int[] x_coords = {1, 2, 3, 2, 4};
        int[] y_coords = {2, 3, 1, 2, 3};
        
        int[] closestPair = findClosestPair(x_coords, y_coords);
        System.out.println(Arrays.toString(closestPair)); // Output: [0, 3]
    }
}
// explanation:we have 5 point
//(1,2), (2,3), (3,1), (2,2), (4,3)
//Closest pair is (1,2) and (2,2) at indices [0, 3].

//input
//x_coords = {1, 2, 3, 2, 4}
//y_coords = {2, 3, 1, 2, 3}
//(i, j)	Point 1	Point 2	Manhattan Distance
//(0,1)	(1,2)	(2,3)	`
//(0,2)	(1,2)	(3,1)	`
//(0,3)	(1,2)	(2,2)	`
//(0,4)	(1,2)	(4,3)	`
//(1,2)	(2,3)	(3,1)	`
//(1,3)	(2,3)	(2,2)	`
//(1,4)	(2,3)	(4,3)	`
//(2,3)	(3,1)	(2,2)	`
//(2,4)	(3,1)	(4,3)	`
//(3,4)	(2,2)	(4,3)	`

