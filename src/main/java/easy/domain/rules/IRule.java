package easy.domain.rules;

public interface IRule<T>
{
    boolean isSatisfy(T model);
}