import java.util.*;

class LTC114_FlattenBinaryTreeToLinkedList {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) { this.val = val; }
    }

    public void flatten(TreeNode root) {
        List<TreeNode> list = new LinkedList<>();
        treeToListInPreOrder(root, list);
        for (int i = 1; i < list.size(); i++) {
            TreeNode prev = list.get(i-1);
            TreeNode here = list.get(i);
            prev.right = here;
        }

        if (list.size() >= 2) {
            root.left = null;
            root.right = list.get(1);
        }
    }

    public void treeToListInPreOrder(TreeNode node, List<TreeNode> list) {
        if (node == null) {
            return;
        }
        list.add(new TreeNode(node.val));
        treeToListInPreOrder(node.left, list);
        treeToListInPreOrder(node.right, list);
    }
}