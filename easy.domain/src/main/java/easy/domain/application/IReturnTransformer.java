package easy.domain.application;

public interface IReturnTransformer {
	boolean isMapped(ReturnContext context);
    Object getValue(ReturnContext context, Object data);
    /**
     * 优先级，数字越大优先级越高
     * @return
     */
    int getOrder();
}
