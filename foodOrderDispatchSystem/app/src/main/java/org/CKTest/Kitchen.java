package org.CKTest;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Kitchen {
    private long totalFoodWait;
    private long totalCourierWait;
    private int totalCourieCount;
    private int orderCount;

    public int getTotalCourieCount() {
        return totalCourieCount;
    }
    public void setTotalCourieCount(int totalCourieCount) {
        this.totalCourieCount = totalCourieCount;
    }
    
    public long getTotalFoodWait() {
        return totalFoodWait;
    }
    public void setTotalFoodWait(long totalFoodWait) {
        this.totalFoodWait = totalFoodWait;
    }
    public long getTotalCourierWait() {
        return totalCourierWait;
    }
    public void setTotalCourierWait(long totalCourierWait) {
        this.totalCourierWait = totalCourierWait;
    }
    public int getOrderCount() {
        return orderCount;
    }
    public void setOrderCount(int totalOrderWait) {
        this.orderCount = totalOrderWait;
    }

    public void dispatchFIFO(List<Order> lstOfOrders, Map<String, Courier> couriers) throws InterruptedException{
        LinkedBlockingQueue<Order> ordersQueue = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<Courier> couriersQueue = new LinkedBlockingQueue<>();
        final int numOfOrders = lstOfOrders.size();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(50);
        AtomicInteger orderIdx = new AtomicInteger(0);
    
        ScheduledFuture<?> task = executor.scheduleAtFixedRate(() -> {
            int idx = orderIdx.getAndIncrement();
            if (idx < numOfOrders) {
                Order order = lstOfOrders.get(idx);
                Courier courier = couriers.get(order.id);
                order.orderReceivedTime = Instant.now().toEpochMilli();
    
                executor.schedule(() -> {
                    order.foodReadyTime = Instant.now().toEpochMilli();
                    ordersQueue.offer(order);
                }, order.prepTime * 1000, TimeUnit.MILLISECONDS);
    
                executor.schedule(() -> {
                    courier.arriveTime = Instant.now().toEpochMilli();
                    couriersQueue.offer(courier);
                }, courier.prepDuration, TimeUnit.MILLISECONDS);
            }
        }, 1, 500, TimeUnit.MILLISECONDS);
    
        int counter = 0;
        while (true) {
            Order order = ordersQueue.poll(500, TimeUnit.SECONDS);
            Courier courier = couriersQueue.poll(500, TimeUnit.SECONDS);
            if (order != null && courier != null) {
                long pickUpTime = Instant.now().toEpochMilli();
                courier.pickUp(order, pickUpTime);
                statsAccumulate(order);
                counter++;
            }
            if (counter == numOfOrders) {
                break;
            }
        }
    
        executor.shutdown();  
        printResult();
    }


     synchronized void statsAccumulate(Order order) {
        orderCount++;
        totalCourieCount++;
        totalFoodWait += order.foodWaitDuration;
        totalCourierWait += order.courier.waitDuration;
        System.out.println(String.format("Stats for order number %s: %s", orderCount,order));
    }

    public void printResult() {
        System.out.println(String.format("Stats for Total food orders dispatching system - avg food wait milliseconds: %s , avg courier wait milliseconds: %s", totalFoodWait/orderCount, totalCourierWait/totalCourieCount));
    }

}
