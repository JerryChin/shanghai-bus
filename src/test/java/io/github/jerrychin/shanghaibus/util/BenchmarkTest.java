package io.github.jerrychin.shanghaibus.util;

import net.bytebuddy.utility.RandomString;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BenchmarkTest {
    private static Logger log = LogManager.getLogger(BenchmarkTest.class);



    @Test
    public void
    launchBenchmark() throws Exception {

        Options opt = new OptionsBuilder()
                // Specify which benchmarks to run.
                // You can be more specific if you'd like to run only one benchmark per test.
                .include(this.getClass().getName() + ".*")
                // Set the following options as needed
                .mode (Mode.Throughput)
                .timeUnit(TimeUnit.MICROSECONDS)
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(3)
                .measurementTime(TimeValue.seconds(1))
                .measurementIterations(5)
                .threads(2)
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                //.jvmArgs("-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintInlining")
                //.addProfiler(WinPerfAsmProfiler.class)
                .build();

        new Runner(opt).run();
    }


    @State(Scope.Benchmark)
    public static class MyState {

        private List<String> lines;

        private TernaryTree imbalancedTrie = new TernaryTree(false);
        private TernaryTree balancedTrie = new TernaryTree(true);


        @Setup(Level.Trial)
        public void doSetup() throws IOException {
//            // load our sample data with old-fashioned way, tedious through
//            ClassLoader loader = Thread.currentThread().getContextClassLoader();
//
//
//            URL linesURL = loader.getResource("lines.txt");
//
//
//            lines = new ArrayList<>();
//            try(BufferedReader reader = new BufferedReader(new InputStreamReader(linesURL.openStream(), "UTF-8"))) {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    lines.add(line.trim());
//                }
//            }
//            System.out.println("lines: " + lines.size());

            for (char i = 0; i < 1_00; i++) {
                imbalancedTrie.insert("" + i);
                balancedTrie.insert("" + i);
            }

            log.error("imbalanced, balance: {} height: {}", imbalancedTrie.root().balance(), imbalancedTrie.root().height());
            log.error("balanced, balance: {} height: {}", balancedTrie.root().balance(), balancedTrie.root().height());

            imbalancedTrie.printTree();
            balancedTrie.printTree();
        }

    }
        @Benchmark
        public void testTernaryTreeWithBalanceOff(MyState state) {
            state.imbalancedTrie.search("10000000");
        }

        @Benchmark
        public void testTernaryTreeWithBalanceOn(MyState state) {
            state.balancedTrie.search("10000000");
        }

}
