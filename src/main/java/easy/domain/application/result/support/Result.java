package easy.domain.application.result.support;

import easy.domain.application.result.IBaseResult;
import easy.domain.application.result.IResultTransformer;
import easy.domain.application.result.ITypeResultTransformer;

import java.util.List;

public class Result<T> implements IBaseResult<T> {
    private T value;

    public Result(T value) {
        this.value = value;
    }


    @Override
    public T result() {
        return value;
    }

    @Override
    public <R> R result(IResultTransformer<T, R> transformer) {
        return transformer.getValue(this.value);
    }

    @Override
    public <R> R result(List<ITypeResultTransformer<T>> transformers, Class<R> rClass)  {

        for (ITypeResultTransformer<T> transformer : transformers) {
            if (transformer.fitlerType(rClass)) {
                return transformer.getValue(this.value);
            }
        }
        throw new NullPointerException();
    }
}
