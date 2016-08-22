package easy.domain.application;

import java.util.function.Function;

public abstract class BaseReturn<T> implements IReturn {
	public abstract Object result(ReturnContext context) throws Exception;

	public abstract T resultDefault() throws Exception;

	public abstract <R> R resultToConvert(Function<T, R> func) throws Exception;
}