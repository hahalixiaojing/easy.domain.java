package easy.domain.application.test;

import easy.domain.application.BaseApplication;
import easy.domain.application.BaseReturn;
import easy.domain.application.test.demo.adddomainevents.DemoDomainEvent;
import easy.domain.application.test.demo.savedomainevents.SaveDemoDomainEvent;

public class DemoApplication extends BaseApplication {
	public BaseReturn<String> add(){
		this.update();
		
		DemoDomainEvent evt = new DemoDomainEvent("addDomainEvents");
		return this.writeAndPublishDomainEvent("add", "add", evt);
		
	}
	private void update(){
		
		
	}
	
	public BaseReturn<String> defaultValueTest(){
		return this.write("defaultValueTest", "default value");
	}
	
	public BaseReturn<String> save(){
		SaveDemoDomainEvent evt =new SaveDemoDomainEvent("saveDomainEvents");
		
		return this.writeAndPublishDomainEvent("save", "save", evt);
	}
}
