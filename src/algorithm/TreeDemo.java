package algorithm;

import test_util.TimeUtil;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TreeDemo {
    public static void main(String[] args) {
        System.out.println(hammingWeight(-100));
    }
    public static int  hammingWeight(int n) {
        int res = 0;
        while(n > 0){
            res += n&1;
            n = n >>> 1;
        }
        return res;
    }

    private void testMaxLen() {
        TreeNode root = getTreeNode();
        List<List<TreeNode>> result = new GetMaxLenList().getMaxLenList(root);
        for (List<TreeNode> list : result) {
            printNodeList(list);
        }
    }

    ///二叉树最长节点集合
    static class GetMaxLenList {
        List<List<TreeNode>> result = new ArrayList<>();
        LinkedList<TreeNode> list = new LinkedList<>();

        private List<List<TreeNode>> getMaxLenList(TreeNode root) {
            if (root == null) return result;
            getMax(root);
            return result;
        }

        private void getMax(TreeNode root) {
            list.offer(root);
            if (root.left == null && root.right == null) {
                int preSize = result.size() == 0 ? 0 : result.get(0).size();
                if (preSize <= list.size()) {
                    if (preSize < list.size()) result.clear();
                    result.add(new ArrayList<>(list));
                }
                list.removeLast();
                return;
            }
            if (root.left != null) getMax(root.left);
            if (root.right != null) getMax(root.right);
            list.removeLast();
        }
    }

    public static TreeNode getTreeNode() {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        TreeNode node8 = new TreeNode(8);
        TreeNode node9 = new TreeNode(9);

        node1.left = node2;
        node1.right = node3;

        node2.left = node4;
        node2.right = node5;

        node3.left = node6;
        node3.right = node7;

        node4.left = node8;

        node6.left = node9;

        return node1;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int v) {
            this.val = v;
        }
    }

    static class Codec2 {
        public String serialize(TreeNode root) {
            if (root == null) return "[]";
            StringBuilder res = new StringBuilder("[");
            Queue<TreeNode> queue = new LinkedList<TreeNode>() {{
                add(root);
            }};
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (node != null) {
                    res.append(node.val + ",");
                    queue.add(node.left);
                    queue.add(node.right);
                } else res.append("null,");
            }
            res.deleteCharAt(res.length() - 1);
            res.append("]");
            return res.toString();
        }

        public TreeNode deserialize(String data) {
            if (data.equals("[]")) return null;
            String[] vals = data.substring(1, data.length() - 1).split(",");
            TreeNode root = new TreeNode(Integer.parseInt(vals[0]));
            Queue<TreeNode> queue = new LinkedList<TreeNode>() {{
                add(root);
            }};
            int i = 1;
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (!vals[i].equals("null")) {
                    node.left = new TreeNode(Integer.parseInt(vals[i]));
                    queue.add(node.left);
                }
                i++;
                if (!vals[i].equals("null")) {
                    node.right = new TreeNode(Integer.parseInt(vals[i]));
                    queue.add(node.right);
                }
                i++;
            }
            return root;
        }
    }

    static class Codec {
        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) return "[]";
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            StringBuilder sb = new StringBuilder("[");
            while (!queue.isEmpty()) {
                root = queue.poll();
                if (root != null) {
                    sb.append(root.val).append(",");
                    queue.offer(root.left);
                    queue.offer(root.right);
                } else {
                    sb.append("null,");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]");
            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if (data == null || data.equals("[]") || data.equals("[null]")) return null;
            String[] arr = data.substring(1, data.length() - 1).split(",");
            int index = 0;
            Queue<TreeNode> queue = new LinkedList<>();
            TreeNode root = parseNode(arr, index++);
            queue.offer(root);
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if ((node.left = parseNode(arr, index++)) != null) queue.add(node.left);
                if ((node.right = parseNode(arr, index++)) != null) queue.add(node.right);
            }
            return root;
        }

        private TreeNode parseNode(String[] arr, int index) {
            String s = arr[index];
            if (s.startsWith("n")) return null;
            return new TreeNode(Integer.parseInt(s));
        }
    }


    static void printNodeList(List<TreeNode> list) {
        StringBuilder sb = new StringBuilder();
        for (TreeNode node : list) {
            sb.append(node.val).append(",");
        }
        System.out.println(sb.toString());
    }

}
