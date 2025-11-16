class Solution {
    public int maxOperations(String s) {
        long ones = 0;
        long operations = 0;

        for (char c : s.toCharArray()) {
            if (c == '1') {
                ones++;
            } else { // c == '0'
                operations += ones;
            }
        }

        return (int) operations;
    }
}
