package easy.domain.base;

import java.util.List;

public class BrokeRuleAggregateException extends RuntimeException {

    private List<BrokenRuleException> exceptions;

    public BrokeRuleAggregateException(String message, List<BrokenRuleException> exceptions) {
        super(message);
    }

    public BrokeRuleAggregateException(List<BrokenRuleException> exceptions) {
        this("", exceptions);
    }

    public List<BrokenRuleException> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<BrokenRuleException> exceptions) {
        this.exceptions = exceptions;
    }
}
