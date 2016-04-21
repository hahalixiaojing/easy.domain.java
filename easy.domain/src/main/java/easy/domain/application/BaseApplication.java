package easy.domain.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import easy.domain.event.DomainEventPublisher;
import easy.domain.event.IDomainEvent;
import easy.domain.event.IDomainEventSubscriber;
import easy.domain.event.ISubscriber;

public class BaseApplication implements IApplication {

	private final HashMap<String, List<IReturnTransformer>> TRANSFORMER = new HashMap<String, List<IReturnTransformer>>();

	private final HashMap<String, List<ISubscriber>> DOMAINEVENTS = new HashMap<String, List<ISubscriber>>();

	public BaseApplication() {
		this.registerReturnTransformer();
		this.registerDomainEvents();
	}

	public void registerReturnTransformer() {
	}

	public void registerDomainEvents() {
	}

	public void registerReturnTransformer(String name,
			IReturnTransformer transformer) {

		if (this.TRANSFORMER.containsKey(name)) {
			this.TRANSFORMER.get(name).add(transformer);
		}
		else {
			List<IReturnTransformer> list = new ArrayList<IReturnTransformer>();
			list.add(transformer);
			this.TRANSFORMER.put(name, list);
		}
	}

	public void registerDomainEvent(String name, ISubscriber item) {
		if (this.DOMAINEVENTS.containsKey(name)) {
			this.DOMAINEVENTS.get(name).add(item);
		}
		else {
			List<ISubscriber> list = new ArrayList<ISubscriber>();
			list.add(item);
			this.DOMAINEVENTS.put(name, list);
		}
	}
	
	private List<IReturnTransformer> getTransformer(String name){
		return this.TRANSFORMER.get(name);
	}
	
	private List<ISubscriber> getDomainEvents(String name){
		return this.DOMAINEVENTS.get(name);
	}
	
	
	protected <T> IReturn write(String mName,T obj) {
		DefaultReturn<T> ret = new DefaultReturn<T>(obj, this.getTransformer(mName));
		return ret;
	}
	
	protected <T,EventDATA extends IDomainEvent> IReturn writeAndPublishDomainEvent(String mName, T obj, EventDATA eventData) {
		try{
			this.publishEvent(mName, eventData);
		}
		finally{}
		
		return this.write(mName, obj);
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends IDomainEvent> void publishEvent(String mName,T obj) {
		List<ISubscriber> subscribers = this.getDomainEvents(mName);
		
		DomainEventPublisher publisher =new DomainEventPublisher();
		
		for(ISubscriber sub : subscribers){
			publisher.subscribe((IDomainEventSubscriber<T>)sub);
		}
		
		publisher.publish(obj);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
