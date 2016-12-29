package easy.domain.test;

import java.util.List;


import org.junit.Assert;
import org.junit.Test;
import easy.domain.base.BrokenRule;



public class BrokenRuleObjectTest {
	@Test
	public void messageKeyExistsTest(){
		DemoBrokenRuleObject obj =new DemoBrokenRuleObject();
		
		obj.addBrokenRule(DemoBrokenRuleMessage.USER_NAME_ERROR);
		List<BrokenRule> brokenRules = obj.getBrokenRules();
		
		Assert.assertTrue(brokenRules.size()>0);
		Assert.assertEquals(DemoBrokenRuleMessage.USER_NAME_ERROR, brokenRules.get(0).getName());
	}
}




