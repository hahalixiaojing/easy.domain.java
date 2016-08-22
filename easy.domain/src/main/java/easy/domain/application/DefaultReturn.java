package easy.domain.application;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

		Optional<IReturnTransformer> result = this.transformer.stream()
				.filter(m -> m.isMapped(context)).findFirst();

		if (result.isPresent()) {

			return result.get().getValue(context, obj);
		}
		
		return noFound.getValue(context, obj);
	}

	@Override
	public T resultDefault() throws Exception {
		return this.obj;
	}

	@Override
	public <R> R resultToConvert(Function<T, R> func) throws Exception {
		return func.apply(this.obj);
	}
	
}
