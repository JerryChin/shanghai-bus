package io.github.jerrychin.shanghaibus.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Trie {
    private static final Logger log = LogManager.getLogger(Trie.class);

    private TrieNode root = new TrieNode();

    {
        root.key = 0;
    }

    private static class TrieNode {
        private static AtomicInteger counter = new AtomicInteger(0);

        private final int id;
        // 如果 key 都是 ascii 的話，使用 Character 效率更高，因為有緩存
        private char key;

        private Trie.TrieNode next;
        private Trie.TrieNode children;

        private TrieNode() {
            this.id = counter.incrementAndGet();
        }
    }

    public boolean add(String key) {

        log.error("{}", Arrays.toString(key.toCharArray()));

        // 首先从 ROOT 出发，检查 root 和 next 有无相同
        // 没有将元素插入到 next，然后
        // 有的话，递增 i，前进到 children，相同检查
        // 如果没有，则插入
        //
        // ROOT(NULL).next -> ("a") ->  ("b")
        //                      |         |
        //                    ("b")     ("a")
        int i = 0;
        Trie.TrieNode node = root.next;
        Trie.TrieNode previous = root;
        while (node != null) {
            if (node.key == key.charAt(i)) {
                log.info("key: {} match!, check children", node.key);
                previous = node;
                node = node.children;
                i ++;
                continue;
            } else {
                log.info("key: {} not match!, next", node.key);
            }
            previous = node;
            node = node.next;
        }

        if (i < key.length()) {
            buildNewNode(previous, key, i);
        }
        return true;
    }

    private void buildNewNode(Trie.TrieNode previous, String key, int i) {

        if (i < key.length()) {
            previous.next = new Trie.TrieNode();
            previous.next.key = key.charAt(i);
        }

        Trie.TrieNode node = previous.next;
        for (int k = i + 1; k < key.length(); k ++) {

            node.children = new Trie.TrieNode();

            node.children.key = key.charAt(k);

            node = node.children;
        }
    }

    public void print() {

        if(root.next != null) {
            _print("", root.next);
        } else {
            log.error("nothing to print.");
        }


    }

    private void _print(String value, TrieNode node) {

        // 優先遍歷深度，然後打印，然後廣度

        if(node.children != null) {
            _print(value + node.key, node.children);
        } else {
            log.info("{}", value + node.key);
        }

        if(node.next != null) {
            _print(value, node.next);
        }
    }
}
