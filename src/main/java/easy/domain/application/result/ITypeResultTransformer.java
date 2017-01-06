package easy.domain.application.result;


public interface ITypeResultTransformer<T> {

    boolean fitlerType(Class<?> rClass) throws Exception;

    <R> R getValue(T value) throws Exception;
}
