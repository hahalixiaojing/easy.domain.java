package easy.domain.base;

import java.io.Serializable;

public interface IEntity<T extends Serializable> extends Serializable {
	T getId();
}
