package com.lisj;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author etix-2017-3
 * @version 1.0
 * @Date 2017-12-29 11:06
 */
public class DeadLockedThreadTest {

    private static Object lock = new Object();

    private static class A extends Thread {

        public A(String name){
            super(name);
        }
        @Override
        public void run() {
            synchronized (lock) {
                while (!Thread.currentThread().isInterrupted()) {
                }
            }
            System.out.println("exit");
        }
    }

    public static void test() throws InterruptedException {
        synchronized (lock) {
            new Thread("222222222222222222222") {
                @Override
                public void run() {
                    while(true){
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
                        long[] allIds = threadMXBean.getAllThreadIds();
                        System.out.println("ssss:"+threadMXBean.findDeadlockedThreads());
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
                }
            }.start();
            A a = new A("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            a.start();

            Thread.sleep(1000);

            a.interrupt();
            a.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        test();
    }

}
