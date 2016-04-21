package easy.domain.application;

import java.util.List;
import java.util.Optional;

public class DefaultReturn<T> implements IReturn {
	private T obj;
	private List<IReturnTransformer> transformer;
	private static final NotFoundReturnTransformer noFound = new NotFoundReturnTransformer();

	public DefaultReturn(T obj, List<IReturnTransformer> transformers) {
		this.obj = obj;
		this.transformer = transformers;
	}

	@Override
	public Object result(ReturnContext context) {

		Optional<IReturnTransformer> result = this.transformer.stream()
				.filter(m -> m.isMapped(context)).findFirst();

		if (result.isPresent()) {

			return result.get().getValue(context, obj);
		}
		
		return noFound.getValue(context, obj);
	}
}
