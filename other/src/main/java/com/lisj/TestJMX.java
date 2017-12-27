package com.lisj;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.LongStream;

/**
 * @author etix-2017-3
 * @version 1.0
 * @Date 2017-12-28 15:18
 */
public class TestJMX {

    public static void main(String[] arg) {
        Thread t1 = new Thread("11111111111111111111") {
            @Override
            public void run() {
                double value = LongStream.range(1, 600000000l).mapToDouble(x -> Double.parseDouble(x + "")).reduce(1, (x, y) -> x * y);
                System.out.println(value);
            }
        };
        t1.start();

        while (t1.isAlive()) {
            try {
                Thread.sleep(2000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread t2 = new Thread("333333333") {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t2.start();
            t2.interrupt();
            new Thread("222222222222222222222") {
                @Override
                public void run() {
                    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
                    long[] allIds = threadMXBean.getAllThreadIds();
                    ThreadGroup masterGroup = Thread.currentThread().getThreadGroup();
                    while (masterGroup.getParent() != null) {
                        masterGroup = masterGroup.getParent();
                    }
                    Thread[] threads = new Thread[masterGroup.activeCount()];
                    masterGroup.enumerate(threads);
                    Arrays.stream(allIds).forEach(id -> {
                        ThreadInfo threadInfo = threadMXBean.getThreadInfo(id, 3);
                        Optional<Thread> current = Arrays.stream(threads).filter(thread -> thread.getId() == id).findAny();
                        if (current.isPresent() && !current.get().isDaemon()) {
                            System.out.print(threadInfo.getThreadId() + ":");
                            System.out.print(threadInfo.getThreadName() + ":");
                            System.out.print(threadInfo.getThreadState() + ":");
                            System.out.print(threadInfo.getBlockedCount() + ":");
                            System.out.print(threadInfo.getWaitedCount() + "\n");
                            System.out.print(threadMXBean.getThreadCpuTime(id) + ":");
                            System.out.print(threadMXBean.getThreadUserTime(id) + ":");
                            System.out.print(threadMXBean.getCurrentThreadCpuTime() + ":");
                            System.out.print(threadMXBean.getCurrentThreadUserTime() + "\n");
                            System.out.println("--------------------------------------------");
                        }
                    });
                }
            }.start();
        }

    }
}
