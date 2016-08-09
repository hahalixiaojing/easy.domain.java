package easy.domain.application;

public interface IReturn {
	Object result(ReturnContext context) throws Exception;
	Object resultDefault() throws Exception;
}
