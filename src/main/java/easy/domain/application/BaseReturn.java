package easy.domain.application;

public abstract class BaseReturn<T> implements IReturn {
	public abstract Object result(ReturnContext context) throws Exception;

	public abstract T resultDefault() throws Exception;
	
	public abstract <R> R result(IDefaultReturnTansformer<T, R> transformer);
}