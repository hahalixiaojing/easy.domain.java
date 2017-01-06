package easy.domain.application;


import easy.domain.application.result.IBaseResult;

public class TestApplication extends BaseApplication {

    public IBaseResult<Order> add(long orderId, long userId) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId(userId);

        TestDomainEvent event = new TestDomainEvent();
        event.orderId = 100_00;
        event.userId = 20000;

        return this.writeAndPublishDomainEvent(order, event);
    }
}
