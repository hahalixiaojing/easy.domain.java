package easy.domain.application;

import java.util.HashMap;
import java.util.List;

public interface IReturnTransformerLoader {
	 HashMap<String, List<IReturnTransformer>> find(IApplication application);
}
