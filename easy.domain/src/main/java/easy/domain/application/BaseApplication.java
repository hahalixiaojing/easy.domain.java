package easy.domain.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import easy.domain.event.IDomainEvent;
import easy.domain.event.ISubscriber;

public class BaseApplication implements IApplication {

	private final HashMap<String, List<IReturnTransformer>> TRANSFORMER = new HashMap<String, List<IReturnTransformer>>();

	private final IDomainEventManager manager;

	public BaseApplication() {
		this.manager = new TaskDomainEventManager();
	}

	public BaseApplication(IDomainEventManager manager) {
		this.manager = manager;
	}

	public void registerReturnTransformer(String name,
			List<IReturnTransformer> transformer) {
		this.TRANSFORMER.put(name, transformer);
	}

	public void registerSubscriber(String name, List<ISubscriber> item) {
		this.manager.registerSubscriber(name, item);
	}
	public void registerDomainEvent(List<Class<?>> events){
		this.manager.registerDomainEvent(events);
	}

	private List<IReturnTransformer> getTransformer(String name) {
		return ObjectUtils.defaultIfNull(this.TRANSFORMER.get(name),
				new ArrayList<IReturnTransformer>(0));
	}
	
	protected <T> BaseReturn<T> write(String mName, T obj) {
		DefaultReturn<T> ret = new DefaultReturn<T>(obj,
				this.getTransformer(mName));
		return ret;
	}

	protected <T, EventDATA extends IDomainEvent> BaseReturn<T> writeAndPublishDomainEvent(
			String mName, T obj, EventDATA eventData) {
		try {
			this.publishEvent(mName, eventData);
		} finally {
		}

		return this.write(mName, obj);
	}

	protected <T extends IDomainEvent> void publishEvent(String mName, T obj) {
		this.manager.publishEvent(mName, obj);
	}
}