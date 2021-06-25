package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QDemo {
    public static void main(String[] args) {
        Solution solution  = new Solution();
        List<List<String>> res =  solution.solveNQueens(4);
        System.out.println(res.size());
        System.out.println(Solution.planTime);
    }
    static class Solution {
        List<List<String>> res = new ArrayList<>();
        StringBuilder init;
        public List<List<String>> solveNQueens(int n) {
            if(n<=0)return res;
            int[] arr = new int[n];
            for(int i=0;i<n;i++){
                arr[0] = i;
                plan(arr,1,n);
            }
            return res;

        }
        static int planTime = 0;
        private void plan(int[] arr, int index,int n){
            planTime++;
            System.out.println("plan index = "+index+" arr[] = "+ Arrays.toString(arr));
            if(index>=n)return;
            for(int i=0;i<n;i++){
                arr[index] = i;
                if(!isOk(arr, index,i)) {
                    System.out.println("is not ok arr[] = " + Arrays.toString(arr));
                    continue;
                }
                if(index==n-1){
                    print(arr);
                    return;
                }
                plan(arr,index+1,n);
            }
        }
        private boolean isOk(int[] arr,int index,int put){
            int left=put-1,right=put+1;
            for(int i=index-1;i>=0;i--){
                //垂直检查、左上检查、右上检查
                if(arr[i]==put||arr[i]==left--||arr[i]==right++)return false;
            }
            return true;
        }
        private void print(int[] arr){
            if(init==null){
                init = new StringBuilder();
                for(int i=0;i<arr.length-1;i++){
                    init.append(".");
                }
            }
            List<String> list = new ArrayList<>();
            for(int i=0;i<arr.length;i++){
                list.add(init.insert(arr[i],"Q").toString());
                init.deleteCharAt(arr[i]);
            }
            res.add(list);
        }

    }
}
