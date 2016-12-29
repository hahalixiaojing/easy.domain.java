package easy.domain.application;

public class NotFoundReturnTransformer implements IReturnTransformer {

	@Override
	public boolean isMapped(ReturnContext context) {
		return true;
	}

	@Override
	public Object getValue(ReturnContext context, Object data) {
		
		return "not found transformer";
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}
}
