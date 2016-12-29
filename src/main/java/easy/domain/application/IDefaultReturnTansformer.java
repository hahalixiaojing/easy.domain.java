package easy.domain.application;

public interface IDefaultReturnTansformer<T,R> {
	 R getValue(T t);
}
