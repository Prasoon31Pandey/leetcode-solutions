class Solution {
    private long cuts = 0;
    private List<Integer>[] adj;
    private int k;

    public int maxKDivisibleComponents(int n, int[][] edges, int[] values, int k) {
        this.k = k;
        adj = new ArrayList[n];
        for (int i = 0; i < n; ++i) adj[i] = new ArrayList<>();
        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }
        cuts = 0;
        dfs(0, -1, values);
        // number of components = cuts + 1
        return (int)(cuts + 1);
    }

    // returns subtree sum modulo k
    private long dfs(int node, int parent, int[] values) {
        long sumMod = ((long)values[node]) % k;
        for (int nei : adj[node]) {
            if (nei == parent) continue;
            long childMod = dfs(nei, node, values);
            if (childMod % k == 0) {
                // we can cut the edge between node and nei
                cuts++;
                // do not add child's sum to current sum — it's separated
            } else {
                sumMod = (sumMod + childMod) % k;
            }
        }
        return sumMod % k;
    }
}
