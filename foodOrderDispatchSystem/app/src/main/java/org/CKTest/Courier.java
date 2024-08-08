package org.CKTest;

public class Courier {
    public Order matchedOrder;
    public long prepDuration;

    public Courier(Order matchedOrder, long prepDuration) {
        this.matchedOrder = matchedOrder;
        this.matchedOrder.matchedCourier = this;
        this.prepDuration = prepDuration;
    }


    public Order order;
    public volatile long arriveTime = -1L;
    public volatile long waitDuration = -1L;
    public volatile long pickUpTime = -1L;


    public void pickUp(Order order, long pickUpTime) {
        this.order = order;
        this.pickUpTime = pickUpTime;
        this.waitDuration = this.pickUpTime - this.arriveTime;

        this.order.courier = this;
        this.order.foodWaitDuration = this.pickUpTime - this.order.foodReadyTime;

    }

    @Override
    public String toString() {
        return "Courier:" + (order == null ? "" : ", order=" + order.id) +
                "matchedOrder=" + matchedOrder.id +
                ", prepDuration=" + prepDuration +
                ", arriveTime=" + arriveTime +
                ", waitDuration=" + waitDuration +
                ", pickUpTime=" + pickUpTime;
    }

}
