package org.CKTest;

public class Order {
    public String id;
    public String name;
    public long prepTime;

    public Courier matchedCourier;
    public Courier courier;

    public volatile long orderReceivedTime = -1L;
    public volatile long foodReadyTime = -1L;
    public volatile long foodWaitDuration = -1L;


        @Override
        public String toString() {

            return "Order:" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", prepTime=" + prepTime +
                    ", orderReceivedTime=" + orderReceivedTime +
                    ", foodReadyTime=" + foodReadyTime +
                    ", foodWaitDuration=" + foodWaitDuration;
        }

}
