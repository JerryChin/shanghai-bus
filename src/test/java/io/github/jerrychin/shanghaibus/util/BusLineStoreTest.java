package io.github.jerrychin.shanghaibus.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class BusLineStoreTest {
    private static final Logger log = LogManager.getLogger(BusLineStoreTest.class);

    @Autowired
    private BusLineStore store;

    //@Test
    public void list() throws Exception {
        this.store.print();
    }

    //@Test
    public void search() throws Exception {
       log.info("search result: {}", this.store.search("闵行"));
    }
}
