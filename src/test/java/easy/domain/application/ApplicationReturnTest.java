package easy.domain.application;


import easy.domain.application.result.IBaseResult;
import easy.domain.application.result.IResultTransformer;
import easy.domain.application.result.ITypeResultTransformer;
import easy.domain.event.IDomainEvent;
import easy.domain.event.ISubscriber;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ApplicationReturnTest {
    @Test
    public void test1() throws Exception {
        TestApplication testApplication = new TestApplication();

        IBaseResult<Order> orderIBaseResult = testApplication.add(100, 100);

        Assert.assertEquals(orderIBaseResult.result().getOrderId(), 100);
        Assert.assertEquals(orderIBaseResult.result().getUserId(), 100);

        Long order = orderIBaseResult.result(new IResultTransformer<Order, Long>() {
            @Override
            public Long getValue(Order order) throws Exception {
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
            public boolean fitlerType(Class<?> longClass) throws Exception {
                return longClass == Long.class;
            }

            @Override
            public Long getValue(Order order) throws Exception {
                return order.getOrderId();
            }
        });

        list.add(new ITypeResultTransformer<Order>() {
            @Override
            public boolean fitlerType(Class<?> rClass) throws Exception {
                return rClass == String.class;
            }

            @Override
            public String getValue(Order o) throws Exception {
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

        List<Class<?>> domainEvents = new ArrayList<>();
        domainEvents.add(TestDomainEvent.class);

        List<ISubscriber> subscribers = new ArrayList<>();
        subscribers.add(new UpdateEsSubscriber());

        testApplication.registerDomainEvent(domainEvents);
        testApplication.registerSubscriber(subscribers);

        testApplication.add(1000, 10000);
        System.out.println("thread id=" + Thread.currentThread().getId());

        Thread.sleep(1000);


    }
}
