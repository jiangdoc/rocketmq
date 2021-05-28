package netty;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    public static void main(String[] args) {
        int[] arr1 = {1,2,3};
        int[] arr2 = {3,4,5};
        final int[] ints = mergeTwoLists(arr1, arr2);
        for (int i:ints){
            System.out.print(i);
        }
        Map<String,String> map = new HashMap<>();
        map.put("dd","dd");
        map.put("dd","dd");
        map.put("dd","dd");
        System.out.println(map.size());
    }

    /**
     * 最大礼物
     * @param grid
     * @return
     */
    public int maxValue(int[][] grid){
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] dp = new int[rows][cols];
        // 第一个单元格
        dp[0][0] = grid[0][0];

        // 第0行
        for (int col = 0; col < cols; col++) {
            dp[0][col] = dp[0][col-1] + grid[0][col];
        }

        // 第0列
        for (int row = 0; row < rows; row++) {
            dp[row][0] = dp[row-1][0] + grid[row][0];
        }

        // 中间的
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                dp[row][col] = Math.max(dp[row-1][col],dp[row][col-1])+grid[row][col];
            }
        }

        return dp[rows-1][cols-1];
    }

    /**
     * 合并两个有序数组成一个
     * @param arr1
     * @param arr2
     * @return
     */
    public static int[] mergeTwoLists(int[] arr1,int[] arr2){
        int[] arr = new int[arr1.length+arr2.length];
        int index = 0;
        int len = 0;
        for (int i =0;i<arr1.length;i++){
            if (arr2[len]>=arr1[i]){
                arr[index++] = arr1[i];
            }
            while((i == arr1.length-1 || arr1[i]>=arr2[len]) && len<arr2.length){
                arr[index++] = arr2[len++];
            }
        }
        return arr;
    }

    /**
     * 数组转成二叉树
     * @param array
     * @param index
     * @return
     */
    public TreeNode createTreeNode(Integer[] array, int index) {
        if(index > array.length){
            return null;
        }
        Integer value = array[index - 1];
        if(value == null){
            return null;
        }
        TreeNode node = new TreeNode(value);
        node.left = createTreeNode(array, index * 2);
        node.right = createTreeNode(array, index * 2 + 1);
        return node;
    }

    /**
     * 最长无重复字符
     * @param str
     * @return
     */
    public static String maxLen(String str){
        if (null == str || str.length() == 1){
            return str;
        }

        StringBuilder[] sbs = new StringBuilder[str.length()];

        int len = 0;
        final char[] chars = str.toCharArray();
        for (int i=0;i<chars.length;i++){
            if (sbs[len] == null){
                sbs[len] = new StringBuilder();
            }
            if (sbs[len].indexOf(String.valueOf(chars[i]))>0){
                len++;
                i--;
            }else {
                sbs[len].append(chars[i]);
            }
        }

        String maxLenStr = sbs[0].toString();
        for (StringBuilder sb : sbs){
            if (sb == null){
                break;
            }
            if (sb.length() > maxLenStr.length()){
                maxLenStr = sb.toString();
            }
        }
        return maxLenStr;
    }


    /**
     * 字符串变形
     * @param str
     */
    public void string(String str){
        // 1. 去除空格
        char[] chars = str.toCharArray();
        int cur = 0;
        for(int i=0;i<chars.length;i++){
            if (chars[i] != ' '){
                chars[cur++] = chars[i];
            }else{
                // 处理 第一个是空格 && 上一个不是空格
                if (cur > 0 && chars[cur-1] != ' '){
                    chars[cur++] = chars[i];
                }
            }
        }
        // 获取真实长度：考虑最后一位是空格
        cur = chars[cur-1]==' '? cur-1:cur;
        final String result = String.valueOf(chars).substring(0,cur);

        // 2. 反转顺序
        final String[] strs = result.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = strs.length-1; i>=0; i--){
            sb.append(strs[i] + " ");
        }

        final String substring = sb.substring(0, sb.length() - 1);
        System.out.println(substring);


        // 2.翻转整个字符串
        reverse(chars,0,cur);

        // 3.翻转每个单词
        int prevIdx = -1;
        for (int i = 0; i < cur; i++) {
            if (chars[i] != ' ') continue;
            reverse(chars,prevIdx+1,i);
            prevIdx = i;
        }
        // 翻转最后一个单词
        reverse(chars,prevIdx+1,cur);

        final String s = new String(chars, 0, cur);
        System.out.println(s);
    }

    private void reverse(char[] chars,int li, int ri){
        ri--;
        while(li<ri){
            char tmp = chars[li];
            chars[li] = chars[ri];
            chars[ri] = tmp;
            li++;
        }
    }
    /**
     * 先序，中序，后序
     * @param root
     * @return
     */
    public int flag =0, flag1 =0, flag2 =0;
    public int[][] threeOrders (TreeNode root) {
        // write code here
        int[][] nums = new int[3][getRootSize(root)] ;
        getRootVal(root,nums);
        return nums;
    }
    public void getRootVal(TreeNode root,int[][] nums){
        if(root==null){
            return;
        }
        nums[0][flag++] = root.val;
        getRootVal(root.left,nums);
        nums[1][flag1++] = root.val;
        getRootVal(root.right,nums);
        nums[2][flag2++] = root.val;
    }
    public int getRootSize(TreeNode root){
        if(root == null){
            return 0;
        }
        return 1 + getRootSize(root.left) + getRootSize(root.right);
    }

    /**
     * 二叉树层序遍历
     * @param root
     * @return
     */
    public ArrayList<ArrayList<Integer>> levelOrder (TreeNode root) {
        // write code here
        ArrayList<ArrayList<Integer>> arrayList = new ArrayList<>();
        if(root == null) return arrayList;
        ArrayList<TreeNode> list = new ArrayList<>();
        list.add(root);
        while(!list.isEmpty()){
            int s = list.size();
            ArrayList<Integer> nList = new ArrayList<>();
            for(int i=0;i<s;i++){
                TreeNode t = list.get(0);
                nList.add(t.val);
                if(t.left!=null) list.add(t.left);
                if(t.right!=null) list.add(t.right);
                list.remove(0);
            }
            arrayList.add(nList);
        }
        return arrayList;
    }

    /**
     * 反转链表
     * @param head
     * @return
     */
    public ListNode ReverseList(ListNode head) {
        if(null == head || head.next == null)
            return head;
        ListNode cur = head;
        ListNode next = null,pre = null;
        while(cur != null){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
     * 数组排序
     * @param arr
     * @return
     */
    public int[] arraySort (int[] arr) {
        // write code here
        if(arr == null || arr.length == 0){
            return arr;
        }
        //1.冒泡排序
        /*
        int tmp;
        for(int i = 0; i< arr.length; i++){
            for(int j = i;j<arr.length; j++){
                if(arr[i]>arr[j]){
                    tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        */
        
        //return arr;
        
        
        //2. 快排
        quick(arr,0,arr.length-1);
        return arr;
        
       
        
    }
    public static void quick(int[] arr,int start, int end){
        if(start<end){
            int mid = arr[start];
            int i = start;
            int tmp;
            for(int j=start+1;j<=end;j++){
                if(mid>arr[j]){
                    tmp = arr[++i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
            arr[start] = arr[i];
            arr[i] = mid;
            quick(arr,start,i-1);
            quick(arr,i+1,end);
        }
    }





    class ListNode{
        ListNode next;
        int val;
    }
    class TreeNode{
        TreeNode left;
        TreeNode right;
        Integer val;
        public TreeNode(Integer val){
            this.val = val;
        }
    }
}