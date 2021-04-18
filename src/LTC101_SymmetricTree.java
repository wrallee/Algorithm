class LTC101_SymmetricTree {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) { this.val = val; }
    }

    public boolean isSymmetric(TreeNode root) {
        //TreeNode mirror = mirrorAndClone(root);
        return compareTree(root, root);
    }

    private boolean compareTree(TreeNode originNode, TreeNode mirrorNode) {
        if (originNode != null && mirrorNode != null) {
            return originNode.val == mirrorNode.val
                    && compareTree(originNode.left, mirrorNode.right)
                    && compareTree(originNode.right, mirrorNode.left);
        }
        return originNode == null && mirrorNode == null;
    }
    /*
    private TreeNode mirrorAndClone(TreeNode node) {
        if (node == null) {
            return null;
        }
        TreeNode clone = new TreeNode(node.val);
        clone.left = mirrorAndClone(node.right);
        clone.right = mirrorAndClone(node.left);

        return clone;
    }
    */
}