package easy.domain.application.result;


public interface ITypeResultTransformer<T>  {

    boolean fitlerType(Class<?> rClass);

    <R> R getValue(T value);
}
