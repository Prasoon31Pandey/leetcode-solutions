class Solution {
    public int intersectionSizeTwo(int[][] intervals) {
        Arrays.sort(intervals, (a,b) -> 
        a[1] == b[1] ? b[0] -a[0] : a[1] - b[1]);
        int s1 = -1;
        int s2 = -1;
        int answer = 0;

        for(int [] interval : intervals) {
            int start = interval[0];
            int end = interval[1];
            int need = 2;
            if(s1 >= start) need--;
            if(s2 >= start ) need--;

            for (int x = end - need + 1; x <= end; x++) {
                s2 = s1;
                s1 = x;
                answer++;
            }
        }
        return answer;
    }
}