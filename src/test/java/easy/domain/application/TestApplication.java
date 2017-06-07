package easy.domain.application;


import easy.domain.application.result.IBaseResult;
import easy.domain.application.subscriber.IDomainEventSubscriber;
import easy.domain.event.EventName;

public class TestApplication extends BaseApplication {

    private final EventName eventName = null;

    public TestApplication() {

        this.registerDomainEvent(TestDomainEvent.class);
        this.registerSubscriber(new IDomainEventSubscriber<TestDomainEvent>() {
            @Override
            public void handleEvent(TestDomainEvent aDomainEvent) {
                System.out.println("xxxxxxxx");
            }

            @Override
            public Class<TestDomainEvent> subscribedToEventType() {
                return TestDomainEvent.class;
            }
        });

    }

    public IBaseResult<Order> add(long orderId, long userId) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId(userId);

        TestDomainEvent event = new TestDomainEvent();
        event.orderId = 100_00;
        event.userId = 20000;

        return this.writeAndPublishEvent(order, event);
    }
}
