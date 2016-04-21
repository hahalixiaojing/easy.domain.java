package easy.domain.application;

import java.util.List;
import java.util.HashMap;
import java.util.Map.Entry;

import easy.domain.event.ISubscriber;
public class ApplicationFactory {
	private static final HashMap<String,IApplication> Application =new HashMap<String, IApplication>();
	
	private static ApplicationFactory factory;
	private IReturnTransformerLoader returnTransfomerLoader;
	private IDomainEventSubscriberLoader domainEventSubscriberLoader;

	private ApplicationFactory(IReturnTransformerLoader transformerLoader,
			IDomainEventSubscriberLoader subscriberLoader) {
		this.returnTransfomerLoader = transformerLoader == null ? new DefaultReturnTransformerLoader() :transformerLoader;
		this.domainEventSubscriberLoader = subscriberLoader == null ? new DefaultDomainEventSubscriberLoader():subscriberLoader;
	}
	
	public void register(IApplication application){
        BaseApplication baseApplication = (BaseApplication)application;
        
        HashMap<String,List<IReturnTransformer>> transformers = returnTransfomerLoader.find(baseApplication);
        
        for(Entry<String, List<IReturnTransformer>> entry: transformers.entrySet()){
        	for(IReturnTransformer t:entry.getValue()){
        		baseApplication.registerReturnTransformer(entry.getKey(), t);
        	}
        }
        
        HashMap<String,List<ISubscriber>> subscribers = domainEventSubscriberLoader.find(baseApplication);
        for(Entry<String, List<ISubscriber>> entry: subscribers.entrySet()){
        	for(ISubscriber s:entry.getValue()){
        		baseApplication.registerDomainEvent(entry.getKey(), s);
        	}
        }
        
        Application.put(application.getClass().getName(), baseApplication);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends IApplication> T get(Class<T> cls){
		
		String clsName = cls.getName();
		
		if(Application.containsKey(clsName)){
			return (T)Application.get(clsName);
		}
		return null;
	}

	public static ApplicationFactory instance(
			IReturnTransformerLoader transformerLoader,
			IDomainEventSubscriberLoader subscriberLoader) {
		if (factory == null) {
			synchronized (ApplicationFactory.class) {
				if (factory == null) {
					factory = new ApplicationFactory(transformerLoader,
							subscriberLoader);
				}
			}
		}
		return factory;
	}

	public static ApplicationFactory instance() {
		return instance(null, null);
	}
}
