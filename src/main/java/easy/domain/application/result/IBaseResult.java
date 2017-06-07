package easy.domain.application.result;


import java.util.List;

public interface IBaseResult<T> extends IResult {
    T result() throws Exception;

    <R> R result(IResultTransformer<T, R> transformer);

    <R> R result(List<ITypeResultTransformer<T>> transformers, Class<R> rClass);
}
