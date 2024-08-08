package org.CKTest;

import java.util.*;
import java.util.stream.Collectors;

public class CourierGenerator {
    public Map<String, Courier> generateCouriers(List<Order> lstOfOrders) {
        Map<String, Courier> couriersMap = new HashMap<>();
        List<Long> couriers = lstOfOrders.stream()
                                    .map(order -> generateCourierPreparationDuration())
                                    .collect(Collectors.toList());

        for (int i = 0; i < lstOfOrders.size(); i++) {
            Order order = lstOfOrders.get(i);
            long prepDuration = couriers.get(i);
            Courier courier = new Courier(order, prepDuration);
            order.matchedCourier = courier;
            couriersMap.put(order.id, courier);
        }
        return couriersMap;
    }

    public static long generateCourierPreparationDuration() {
        return 3000L + new Random().nextInt(12000);
    }

}
