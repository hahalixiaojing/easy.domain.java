package easy.domain.test;

import java.util.Map;

import easy.domain.base.BrokenRuleMessage;

public class DemoBrokenRuleMessage extends BrokenRuleMessage{
	
	public static final String USER_NAME_ERROR ="user name error";
	
	@Override
	protected void populateMessage() {
		Map<String, String> maps = this.getMessages();
		
		maps.put(USER_NAME_ERROR, "用户名错误");
	}
	
}
