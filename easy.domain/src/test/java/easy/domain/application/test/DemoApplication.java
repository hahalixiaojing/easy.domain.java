package easy.domain.application.test;

import easy.domain.application.BaseApplication;
import easy.domain.application.IReturn;
import easy.domain.application.test.demo.adddomainevents.DemoDomainEvent;
import easy.domain.application.test.demo.savedomainevents.SaveDemoDomainEvent;

public class DemoApplication extends BaseApplication {
	public IReturn add(){
		this.update();
		
		DemoDomainEvent evt = new DemoDomainEvent("addDomainEvents");
		return this.writeAndPublishDomainEvent("add", "add", evt);
		
	}
	private void update(){
		
	}
	
	public IReturn save(){
		SaveDemoDomainEvent evt =new SaveDemoDomainEvent("saveDomainEvents");
		
		return this.writeAndPublishDomainEvent("save", "save", evt);
	}
}
