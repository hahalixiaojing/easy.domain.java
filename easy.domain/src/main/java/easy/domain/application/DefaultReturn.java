package easy.domain.application;

import java.util.List;

public class DefaultReturn<T> extends BaseReturn<T> {
	private T obj;
	private List<IReturnTransformer> transformer;
	private static final NotFoundReturnTransformer noFound = new NotFoundReturnTransformer();
	public DefaultReturn(T obj, List<IReturnTransformer> transformers) {
		this.obj = obj;
		this.transformer = transformers;
	}

	@Override
	public Object result(ReturnContext context) throws Exception {

		for (IReturnTransformer trans : transformer) {

			if (trans.isMapped(context)) {
				return trans.getValue(context, obj);
			}
		}
		return noFound.getValue(context, obj);
	}

	@Override
	public T resultDefault() throws Exception {
		return this.obj;
	}
	@Override
	public <R> R result(IDefaultReturnTansformer<T, R> transformer) {
		return transformer.getValue(this.obj);
	}
}
