package easy.domain.test;

import easy.domain.base.BrokenRuleMessage;
import easy.domain.base.BrokenRuleObject;

public class DemoBrokenRuleObject extends BrokenRuleObject{

	@Override
	protected BrokenRuleMessage getBrokenRuleMessages() {
		return new DemoBrokenRuleMessage();
	}

	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		return null;
	}
}