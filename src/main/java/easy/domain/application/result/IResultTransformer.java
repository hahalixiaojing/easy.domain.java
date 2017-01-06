package easy.domain.application.result;


public interface IResultTransformer<T,R> {
    R getValue(T t) throws Exception;
}
