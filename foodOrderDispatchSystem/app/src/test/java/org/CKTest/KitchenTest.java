package org.CKTest;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class KitchenTest {

    @Test
    void testStatsAccumulate() throws InterruptedException {
        Kitchen kitchen = new Kitchen();
        Order order = new Order();
        order.id = "1";
        order.name = "Salad";
        order.prepTime = 5;
        order.foodWaitDuration = 1000L;

        Courier courier = new Courier(order, 3000L);
        courier.waitDuration = 500L;
        order.courier = courier;

        kitchen.statsAccumulate(order);

        assertEquals(1, kitchen.getOrderCount());
        assertEquals(1, kitchen.getTotalCourieCount());
        assertEquals(1000L, kitchen.getTotalFoodWait());
        assertEquals(500L, kitchen.getTotalCourierWait());
    }

    @Test
    void testDispatchFIFO() throws InterruptedException {
        Kitchen kitchen = new Kitchen();
        Order order1 = new Order();
        order1.id = "1";
        order1.name = "Pizza";
        order1.prepTime = 1;

        Order order2 = new Order();
        order2.id = "2";
        order2.name = "Burger";
        order2.prepTime = 1;

        List<Order> orders = Arrays.asList(order1, order2);

        Map<String, Courier> couriers = new HashMap<>();
        couriers.put("1", new Courier(order1, 1000L));
        couriers.put("2", new Courier(order2, 1000L));

        kitchen.dispatchFIFO(orders, couriers);

        assertEquals(2, kitchen.getOrderCount());
        assertEquals(2, kitchen.getTotalCourieCount());
        assertTrue(kitchen.getTotalFoodWait() >= 0);
        assertTrue(kitchen.getTotalCourierWait() >= 0);
    }
}