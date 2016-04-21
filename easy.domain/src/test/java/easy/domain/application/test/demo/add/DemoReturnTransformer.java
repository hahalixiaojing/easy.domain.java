package easy.domain.application.test.demo.add;

import org.apache.commons.lang3.StringUtils;

import easy.domain.application.IReturnTransformer;
import easy.domain.application.ReturnContext;

public class DemoReturnTransformer implements IReturnTransformer {

	@Override
	public boolean isMapped(ReturnContext context) {
		int v = Integer.parseInt(StringUtils.defaultIfBlank(context.versionId, "0"));
		return v >= this.getOrder();
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
