class Solution {
    public int sumFourDivisors(int[] nums) {
        // Stores final sum of all valid numbers
        int total = 0;

        // Loop through each number in the array
        for (int x : nums) {
            int cnt = 0;  // number of divisors
            int sum = 0;  // sum of divisors

            // Try all numbers from 1 to x
            for (int d = 1; d <= x; d++) {
                // If d divides x, it's a divisor
                if (x % d == 0) {
                    cnt++;     // increment divisor count
                    sum += d;  // add divisor
                }
            }

            // Add sum only if exactly 4 divisors exist
            if (cnt == 4) total += sum;
        }

        // Return the final result
        return total;
    }
}
