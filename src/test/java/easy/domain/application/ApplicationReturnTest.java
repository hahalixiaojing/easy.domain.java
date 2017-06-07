package easy.domain.application;


import easy.domain.application.result.IBaseResult;
import easy.domain.application.result.IResultTransformer;
import easy.domain.application.result.ITypeResultTransformer;
import easy.domain.application.subscriber.IDomainEventSubscriber;
import easy.domain.application.subscriber.ISubscriber;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ApplicationReturnTest {
    @Test
    public void test1() throws Exception {
        TestApplication testApplication = new TestApplication();

        IBaseResult<Order> orderIBaseResult = testApplication.add(100, 100);

        Assert.assertEquals(orderIBaseResult.result().getOrderId(), 100);
        Assert.assertEquals(orderIBaseResult.result().getUserId(), 100);

        Long order = orderIBaseResult.result(new IResultTransformer<Order, Long>() {
            @Override
            public Long getValue(Order order) {
                return order.getOrderId();
            }
        });

        Assert.assertEquals(100L, order.longValue());
    }

    @Test
    public void test2() throws Exception {
        List<ITypeResultTransformer<Order>> list = new ArrayList<>();

        list.add(new ITypeResultTransformer<Order>() {

            @Override
            public boolean fitlerType(Class<?> longClass) {
                return longClass == Long.class;
            }

            @Override
            public Long getValue(Order order) {
                return order.getOrderId();
            }
        });

        list.add(new ITypeResultTransformer<Order>() {
            @Override
            public boolean fitlerType(Class<?> rClass) {
                return rClass == String.class;
            }

            @Override
            public String getValue(Order o) {
                return Long.toString(o.getOrderId());
            }
        });
        TestApplication testApplication = new TestApplication();

        IBaseResult<Order> orderIBaseResult = testApplication.add(100, 100);

        String value = orderIBaseResult.result(list, String.class);
        Long longValue = orderIBaseResult.result(list, Long.class);


    }

    @Test
    public void test3() throws Exception {
        TestApplication testApplication = new TestApplication();

        HashSet<Class<?>> domainEvents = new HashSet<>();
        domainEvents.add(TestDomainEvent.class);

        HashSet<ISubscriber> subscribers = new HashSet<>();
        subscribers.add(new UpdateEsSubscriber());
        subscribers.add(new IDomainEventSubscriber<TestDomainEvent>() {

            @Override
            public void handleEvent(TestDomainEvent aDomainEvent) {
                System.out.println("hellow word thread is " + Thread.currentThread().getId());
            }

            @Override
            public Class<?> subscribedToEventType() {
                return TestDomainEvent.class;
            }
        });

        testApplication.registerDomainEvent(domainEvents);
        testApplication.registerSubscriber(subscribers);

        for (int i = 0; i < 2; i++) {

            testApplication.add(1000, 10000);
        }

        System.out.print("ok");

        Thread.sleep(10000);


    }
}
