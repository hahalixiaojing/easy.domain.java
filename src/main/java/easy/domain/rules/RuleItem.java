package easy.domain.rules;

class RuleItem<T> {
    private IRule<T> rule;
    private String messageKey;
    private String alias;


    public RuleItem(IRule<T> rule, String messageKey, String alias) {
        this.rule = rule;
        this.messageKey = messageKey;
        this.alias = alias;
    }

    public IRule<T> getRule() {
        return rule;
    }

    public String getMessageKey() {
        return messageKey;
    }


    public String getAlias() {
        return alias;
    }
}
