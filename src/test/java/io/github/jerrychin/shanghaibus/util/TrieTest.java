package io.github.jerrychin.shanghaibus.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.Arrays;


public class TrieTest {
    private static final Logger log = LogManager.getLogger(TrieTest.class);

    //@Test
    public void list() throws Exception {


        log.error("{} {}", "你好".charAt(0), "你好".charAt(1));
        Trie trie = new Trie();

        trie.add("abcd");
        trie.add("abce");
        trie.add("abee");
        trie.add("aeee");
        trie.add("eeee");


        trie.print();
    }
}
