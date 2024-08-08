package org.CKTest;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class CourierGeneratorTest {

    @Test
    void testGenerateCouriers() {
        CourierGenerator generator = new CourierGenerator();
        Order order1 = new Order();
        order1.id = "1";
        Order order2 = new Order();
        order2.id = "2";
        List<Order> orders = Arrays.asList(order1, order2);

        Map<String, Courier> couriers = generator.generateCouriers(orders);

        assertEquals(2, couriers.size());
        assertTrue(couriers.containsKey("1"));
        assertTrue(couriers.containsKey("2"));
    }

    @Test
    void testGenerateCourierPreparationDuration() {
        long duration = CourierGenerator.generateCourierPreparationDuration();
        assertTrue(duration >= 3000 && duration < 15000);
    }
}