package easy.domain.base;

import java.io.Serializable;

public abstract class EntityBase<T extends Serializable> extends BrokenRuleObject implements
		IEntity<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3851243975849363906L;
	private T id;

	@Override
	protected abstract BrokenRuleMessage getBrokenRuleMessages();

	@Override
	public T getId() {
		return this.id;
	}
	protected void setId(T id) {
		this.id = id;
	}
}
