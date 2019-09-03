package io.github.jerrychin.shanghaibus.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class BusLineStore {
    private static Logger log = LogManager.getLogger(BusLineStore.class);

    private final ResourceLoader resourceLoader;

    private final TernaryTree trie = new TernaryTree(true);

    @Autowired
    private BusLineStore(ResourceLoader resourceLoader) throws IOException {
        this.resourceLoader = resourceLoader;


        Resource resource = resourceLoader.getResource("classpath:lines.txt");

        if(!resource.exists()) {
            throw new IllegalArgumentException("lines.txt not found!");
        }

        Set<String> lines = new HashSet<>();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                trie.insert(line.trim());
            }
        }

        log.info("{} lines imported", lines.size());
    }


    void print() {
        trie.printTree();
    }

    List<String> search(String prefix) {
        return trie.search(prefix);
    }
}
