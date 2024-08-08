package org.CKTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testOrderCreation() {
        Order order = new Order();
        order.id = "1";
        order.name = "Pizza";
        order.prepTime = 15;

        assertEquals("1", order.id);
        assertEquals("Pizza", order.name);
        assertEquals(15, order.prepTime);
    }

    @Test
    void testToString() {
        Order order = new Order();
        order.id = "2";
        order.name = "Burger";
        order.prepTime = 10;
        order.orderReceivedTime = 1000L;
        order.foodReadyTime = 2000L;
        order.foodWaitDuration = 1000L;

        String expected = "Order:id='2', name='Burger', prepTime=10, orderReceivedTime=1000, foodReadyTime=2000, foodWaitDuration=1000";
        assertEquals(expected, order.toString());
    }
}