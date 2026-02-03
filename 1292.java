class Solution {
    public int maxSideLength(int[][] mat, int threshold) {
        int m = mat.length;
        int n = mat[0].length;

        // Prefix sum matrix (extra row & column to handle boundaries safely)
        int[][] prefixSum = new int[m + 1][n + 1];

        // Build the prefix sum matrix
        // prefixSum[i][j] stores sum of elements from (0,0) to (i-1,j-1)
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                prefixSum[i][j] = mat[i - 1][j - 1]
                        + prefixSum[i - 1][j]      // sum from top
                        + prefixSum[i][j - 1]      // sum from left
                        - prefixSum[i - 1][j - 1]; // remove double counted area
            }
        }

        int maxSide = 0; // stores the largest valid square size found

        // Try to expand the square gradually
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                int nextSide = maxSide + 1; // check only the next larger square

                // Make sure the square fits within matrix bounds
                if (i >= nextSide && j >= nextSide) {

                    // Calculate sum of square ending at (i, j) in O(1)
                    int sum = prefixSum[i][j]
                            - prefixSum[i - nextSide][j]
                            - prefixSum[i][j - nextSide]
                            + prefixSum[i - nextSide][j - nextSide];

                    // If sum is within threshold, update answer
                    if (sum <= threshold) {
                        maxSide = nextSide;
                    }
                }
            }
        }

        return maxSide; // return maximum possible square side length
    }
}