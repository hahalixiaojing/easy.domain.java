package easy.domain.application.test.demo.add;

import easy.domain.application.IReturnTransformer;
import easy.domain.application.ReturnContext;

public abstract class BaseReturnTransformer implements IReturnTransformer {

	@Override
	public boolean isMapped(ReturnContext context) {
		return true;
	}

	@Override
	public Object getValue(ReturnContext context, Object data) {
		
		return null;
	}

	@Override
	public int getOrder() {
		
		return 0;
	}

}
