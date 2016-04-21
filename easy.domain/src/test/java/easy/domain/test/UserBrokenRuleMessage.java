package easy.domain.test;

import easy.domain.base.BrokenRuleMessage;

public class UserBrokenRuleMessage extends BrokenRuleMessage {

	public static final String USER_NAME_ERROR ="user name error";
	public static final String AGE_ERROR = "age error";
	
	@Override
	protected void populateMessage() {
		this.getMessages().put(USER_NAME_ERROR, "用户史错误");
		this.getMessages().put(AGE_ERROR, "年龄不能大于100岁");
	}

}
