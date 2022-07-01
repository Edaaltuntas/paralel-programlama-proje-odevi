package com.edaaltuntas;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    private static ArrayList<Group> groups = new ArrayList<>();

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        final long bestThreadSize = calculateBestThreadSize();
        final long lineCount = 113426;
        final long batchSize = lineCount / bestThreadSize;
        final ExecutorService executor = createPoolExecutor();

        List<Future<ArrayList<Group>>> queue = new ArrayList<>();

        for (int i = 1; i <= bestThreadSize; i++) {
            Future<ArrayList<Group>> future = executor.submit(new Worker(batchSize, i));
            queue.add(future);
        }

        combineQueueResult(queue);
        groups.stream().limit(5).forEach(System.out::println);
        executor.shutdown();
    }

    private static void combineQueueResult(List<Future<ArrayList<Group>>> queue) throws ExecutionException, InterruptedException {
        for(Future<ArrayList<Group>> fut : queue){
            for(Group g : fut.get()) {
                int x = groups.indexOf(g);
                if(x > -1) {
                    groups.get(x).sum += g.sum;
                }else{
                    groups.add(g);
                }
            }
        }
        Map<String, Group> m = new LinkedHashMap<>();
        for (Group group : groups) {
            if (m.containsKey(group.customer_city)) {
                if (m.get(group.customer_city).sum < group.sum) {
                    m.put(group.customer_city, group);
                }
            }else {
                m.put(group.customer_city, group);
            }
        }
        groups = new ArrayList<>(m.values());
        groups.sort(Comparator.comparingInt(Group::getSum).reversed());
    }

    private static int calculateBestThreadSize() {
        final int numThreads = Runtime.getRuntime().availableProcessors();
        return numThreads * (1 + (2 / 5));
    }

    private static ExecutorService createPoolExecutor() {
        return Executors.newFixedThreadPool(calculateBestThreadSize());
    }
}
