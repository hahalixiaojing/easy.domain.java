package easy.domain.application.test.demo.save;

import easy.domain.application.IReturnTransformer;
import easy.domain.application.ReturnContext;

public class SaveDemoReturnTransformer implements IReturnTransformer {

	@Override
	public boolean isMapped(ReturnContext context) {
		return true;
	}

	@Override
	public Object getValue(ReturnContext context, Object data) {
		return data;
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
