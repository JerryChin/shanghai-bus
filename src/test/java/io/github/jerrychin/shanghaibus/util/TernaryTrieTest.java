package io.github.jerrychin.shanghaibus.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


public class TernaryTrieTest {
    private static final Logger log = LogManager.getLogger(TernaryTrieTest.class);

    @Test
    public void list() throws Exception {

        TernaryTree trie = new TernaryTree(false);

        for (char i = 'a'; i <  ('a' + 10); i++) {

            System.err.println(i);
            trie.insert("" + i);
        }

        trie.printTree();

//        trie = new TernaryTree(true);
//
//        trie.insert("d");
//        trie.insert("e");
//        trie.insert("c");
//        trie.insert("b");
//        trie.insert("a");
//        trie.insert("abc");
//        trie.insert("abcd");
//        trie.printTree();
//
//
//        log.error("{}", trie.search("abc"));
//        log.error("{}", trie.search("a"));
//
//
//  /      log.error("root height: {}, left: {}, right: {}", root.height(), root.leftHeight(), root.rightHeight());
//        log.error("root balance: {}", root.balance());
    }
}
