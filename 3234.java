class Solution {
    public int numberOfSubstrings(String s) {
        int n = s.length();
        // collect zero indices and add sentinels
        ArrayList<Integer> Z = new ArrayList<>();
        Z.add(-1);
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') Z.add(i);
        }
        Z.add(n);

        long ans = 0;

        // case z = 0 : substrings with no zeros (pure ones)
        // iterate runs between zeros (gaps)
        for (int k = 1; k < Z.size(); k++) {
            int gap = Z.get(k) - Z.get(k-1) - 1; // number of ones in this gap
            if (gap > 0) {
                ans += (long)gap * (gap + 1) / 2;
            }
        }

        int totalZeros = Z.size() - 2; // exclude sentinels
        if (totalZeros == 0) return (int)ans;

        // upper bound for z: z^2 <= n roughly -> z up to sqrt(n) + 2 for safety
        int maxZ = (int)Math.sqrt(n) + 2;

        for (int z = 1; z <= maxZ; z++) {
            if (z > totalZeros) break;
            // for each window of exactly z zeros: zeros indices at Z[i]..Z[i+z-1]
            for (int i = 1; i + z - 1 <= totalZeros; i++) {
                int j = i + z - 1;
                // left available ones (gap before Z[i])
                int leftOnes = Z.get(i) - Z.get(i-1) - 1;
                // right available ones (gap after Z[j])
                int rightOnes = Z.get(j+1) - Z.get(j) - 1;

                // ones already present inside minimal window [Z[i], Z[j]]
                int onesInside = (Z.get(j) - Z.get(i) + 1) - z;

                int need = z*z - onesInside; // need = additional ones required from a+b
                if (need <= 0) {
                    // all combinations of left and right extensions are valid
                    ans += (long)(leftOnes + 1) * (rightOnes + 1);
                } else {
                    // count pairs (a,b) with 0<=a<=leftOnes, 0<=b<=rightOnes and a+b >= need
                    // total pairs = (L+1)*(R+1)
                    long totalPairs = (long)(leftOnes + 1) * (rightOnes + 1);

                    // count pairs with a+b <= need-1 (invalid), subtract from total
                    int T = need - 1;
                    long invalid = 0;
                    // we can iterate a from 0..leftOnes and sum max(0, min(R, T-a)+1)
                    // but do it in O(1) by splitting range where T-a >= 0
                    if (T >= 0) {
                        // a <= T -> a from 0 to min(leftOnes, T)
                        int aMax = Math.min(leftOnes, T);
                        // For a in [0..aMax], bCount = min(rightOnes, T-a) + 1
                        // Split where T-a >= rightOnes  => a <= T - rightOnes
                        int aFull = Math.min(aMax, T - rightOnes); // for these a, bCount = rightOnes+1
                        if (aFull >= 0) {
                            invalid += (long)(aFull + 1) * (rightOnes + 1);
                        } else {
                            aFull = -1;
                        }
                        // remaining a from aFull+1 .. aMax: bCount = (T - a) + 1
                        int aStart = aFull + 1;
                        if (aStart <= aMax) {
                            // sum_{a = aStart..aMax} (T - a + 1)
                            // = (aMax - aStart + 1) * (T + 1) - sum_{a=aStart..aMax} a
                            int cnt = aMax - aStart + 1;
                            long sumA = (long)(aStart + aMax) * cnt / 2;
                            invalid += (long)cnt * (T + 1) - sumA;
                        }
                    }
                    ans += totalPairs - invalid;
                }
            }
        }

        return (int)ans;
    }
}
