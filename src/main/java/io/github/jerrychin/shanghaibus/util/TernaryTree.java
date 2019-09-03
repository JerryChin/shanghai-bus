package io.github.jerrychin.shanghaibus.util;

import java.util.*;

/**
 * Self-Balancing Ternary Tree.
 *
 * Use AVL algorithm for implementation.
 */
public class TernaryTree {

    private final boolean balance;

    TernaryTree(final boolean balance) {
        this.balance = balance;
    }

    public static class Node {
        private boolean isTerminal;
        private char keyword; // keyword, a character from the alphabet.

        // Very upset, Java don't support pass-by-reference, so we have go with array.

        // on-demand initialization is space-efficient, but it hurts developer's feelings, because we have to check null every time.
        public Node[] left = new Node[1]; // smaller than this node
        public Node[] right = new Node[1]; // greater than this node
        public Node[] children = new Node[1]; // which equals this node.

//        public Node(Node parent) {
//            this.parent = parent;
//        }


        /**
         * return 0, if the node is balanced, +n means right is n taller than left, -n means the opposite.
         * @return
         */
        public int balance() {
            return rightHeight() - leftHeight();
        }

        public int leftHeight() {
            int leftHeight = 0;
            if(left != null && left[0] != null){
                leftHeight = left[0].height();
            }

            return leftHeight;
        }

        public int rightHeight() {

            int rightHeight = 0;
            if(right != null && right[0] != null){
                rightHeight = right[0].height();
            }

            return rightHeight;
        }


        public int height() {
            return 1 + Math.max(leftHeight(), rightHeight());
        }
    }

    private Node root;

    private Node newNode(char v, Node parent) {
        Node node = new Node();
        node.keyword = v;
        return node;
    }

    private void leftRotate(final Node[] node) {
        // 左旋操作如下：
        // 根节点的右节点成为新的根节点，
        // 根节点成为新根节点的左节点
        // 根节点的右节点的左子树成为根节点的右子树
        // 根节点成为新根节点的右子树

        Node newRoot = node[0].right[0];

        if(newRoot.left != null) {
            node[0].right[0] = newRoot.left[0];
        } else {
            // clear reference
            node[0].right = null;
        }

        if(newRoot.left == null) {
            newRoot.left = new Node[1];
        }

        newRoot.left[0] = node[0];

        node[0] = newRoot;
    }


    private void rightRotate(final Node[] node) {
        // 右旋操作如下：
        // 根节点的左节点成为新的根节点，
        // 根节点成为新根节点的右节点
        // 根节点的左节点的右子树成为根节点的左子树
        // 根节点成为新根节点的左子树

        Node newRoot = node[0].left[0];
        if(newRoot.right != null) {
            node[0].left[0] = newRoot.right[0];
        } else {
            // clear reference
            node[0].left = null;
        }

        if(newRoot.right == null) {
            newRoot.right = new Node[1];
        }
        newRoot.right[0] = node[0];

        node[0] = newRoot;
    }

    private void tryRelanance(final Node[] node) {
        if(!balance) {
            return;
        }
        int balance = node[0].balance();

        // left is heavier, do right rotation, maybe right-left rotaion is required, so check it!
        if(node[0].balance() < -1) {

            // check if the left tree is right heavy
            if(node[0].left != null && node[0].left[0] != null && node[0].left[0].balance() > 0) {
                leftRotate(node[0].left);
                rightRotate(node);
            } else {
                rightRotate(node);
            }


        } else if(node[0].balance() > 1) { // this time right becomes heavier, do mirror operations.
            if(node[0].right != null && node[0].right[0] != null && node[0].right[0].balance() < 0) {
                rightRotate(node[0].right);
                leftRotate(node);
            } else {
                leftRotate(node);
            }
        }
    }
    private void insert0(final String v, final int offset, final Node[] node) {

        if (offset == v.length()) {
            return;
        }

        if(node[0] == null) {
            node[0] = newNode(v.charAt(offset), node[0]);

            int nextOffset = offset + 1;
            if(nextOffset < v.length()) {
                node[0].children = new Node[1];
                insert0(v,  nextOffset, node[0].children);
            } else {
                node[0].isTerminal = true;
            }
        } else {
            char keyword = v.charAt(offset);

            // 当前关键字小于当前节点，转向左子树
            if(keyword < node[0].keyword) {

                if(node[0].left == null) {
                    node[0].left = new Node[1];
                }

                insert0(v, offset, node[0].left);
                tryRelanance(node);
            } else if(keyword > node[0].keyword) {

                if(node[0].right == null) {
                    node[0].right = new Node[1];
                }

                insert0(v, offset, node[0].right);
                tryRelanance(node);
            } else {

                if(node[0].children == null) {
                    node[0].children = new Node[1];
                }

                insert0(v, offset + 1, node[0].children);
            }
        }
    }

    public void insert(String v) {
        // 插入过程：
        // 使用当前关键字对比当前节点选择合适的分支，如此迭代下来直到....
        // 深入到终端节点，此时在终端节点相应位置插入当前节点

        // 首次插入元素，从根元素自上而下构成所有节点
        if(this.root == null) {
            Node node = newNode(v.charAt(0), null);
            this.root = node;

            this.root.children = new Node[1];
            insert0(v, 1, node.children);
        } else {
            insert0(v, 0, new Node[]{this.root});
        }
    }

    public List<String> search(String prefix) {

        return search0(prefix, 0, new Node[]{root}, null);


    }

    private List<String> search0(final String prefix, final int offset, final Node[] node, final Node parent) {
        if(node[0] == null) {
            // 查找失败
            return Collections.emptyList();
        } else {
            char keyword = prefix.charAt(offset);
            if(keyword < node[0].keyword) {
                return search0(prefix, offset, node[0].left, node[0]);
            } else if(keyword > node[0].keyword) {
                return search0(prefix, offset, node[0].right, node[0]);
            } else {

                int nextOffset = offset + 1;

                if(nextOffset < prefix.length()) {
                    return search0(prefix, offset + 1, node[0].children, node[0]);
                } else {
                    List<String> found = new ArrayList<>();

                    if(node[0].isTerminal) {
                        found.add(prefix);
                    }

                    // 遍历其后代的左右子树和后代
                    return scan(node[0].children, prefix, found);
                }

            }
        }
    }

    private List<String> scan(Node[] node, String prefix, List<String> found) {
        if(node[0] == null) {
            return found;
        } else {

            String match = prefix + node[0].keyword;
            if(node[0].isTerminal) {
                found.add(match);
            }
            scan(node[0].left, prefix, found);
            scan(node[0].children, match, found);
            scan(node[0].right, prefix, found);

            return found;
        }
    }

    public Node root() {
        return this.root;
    }

    public void printTree() {
        BTreePrinter.printNode(root);
    }

    private static class BTreePrinter {

        public static <T extends Comparable<?>> void printNode(Node root) {
            int maxLevel = BTreePrinter.maxLevel(root);

            printNodeInternal(Collections.singletonList(root), 1, maxLevel);
        }

        private static <T extends Comparable<?>> void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
            if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
                return;

            int floor = maxLevel - level;
            int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
            int firstSpaces = (int) Math.pow(2, (floor)) - 1;
            int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

            BTreePrinter.printWhitespaces(firstSpaces);

            List<Node> newNodes = new ArrayList<Node>();
            for (Node node : nodes) {
                if (node != null) {
                    System.out.print(node.keyword);
                    newNodes.add(node.left[0]);
                    newNodes.add(node.right[0]);
                } else {
                    newNodes.add(null);
                    newNodes.add(null);
                    System.out.print(" ");
                }

                BTreePrinter.printWhitespaces(betweenSpaces);
            }
            System.out.println("");

            for (int i = 1; i <= endgeLines; i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    BTreePrinter.printWhitespaces(firstSpaces - i);
                    if (nodes.get(j) == null) {
                        BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                        continue;
                    }

                    if (nodes.get(j).left != null)
                        System.out.print("/");
                    else
                        BTreePrinter.printWhitespaces(1);

                    BTreePrinter.printWhitespaces(i + i - 1);

                    if (nodes.get(j).right != null)
                        System.out.print("\\");
                    else
                        BTreePrinter.printWhitespaces(1);

                    BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
                }

                System.out.println("");
            }

            printNodeInternal(newNodes, level + 1, maxLevel);
        }

        private static void printWhitespaces(int count) {
            for (int i = 0; i < count; i++)
                System.out.print(" ");
        }

        private static <T extends Comparable<?>> int maxLevel(Node node) {
            if (node == null)
                return 0;

            return Math.max(BTreePrinter.maxLevel(node.left[0]), BTreePrinter.maxLevel(node.right[0])) + 1;
        }

        private static <T> boolean isAllElementsNull(List<T> list) {
            for (Object object : list) {
                if (object != null)
                    return false;
            }

            return true;
        }

    }
}
