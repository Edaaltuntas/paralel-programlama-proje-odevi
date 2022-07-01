package com.edaaltuntas;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Worker implements Callable<ArrayList<Group>> {

    private final ArrayList<Order> orders = new ArrayList<>();
    private final ArrayList<Group> groups = new ArrayList<>();
    public Worker(long batchSize, long threadNum) throws IOException {
        try (Stream<String> stream = Files.lines(Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("data.csv")).getPath()))
                .skip(Math.max(0, threadNum - 1 ) * batchSize + 1)
                .limit((threadNum) * batchSize + 1)) {
            stream.forEach(s-> {
                String[] x = s.split(",");
                this.orders.add(0, new Order(x[0], x[1], x[2], x[3], x[4], x[5], x[6]));
            });
        }
    }

    @Override
    public ArrayList<Group> call() {
        this.orders.stream().collect(Collectors.groupingBy(
                Order::getKey,
                Collectors.summingInt(Order::getOrderItemId))
        ).forEach((g, v) -> groups.add(new Group(g.split("\\|")[0], g.split("\\|")[1], v)));
        return groups;
    }
}
