package easy.domain.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BrokenRuleObject {

    private transient List<BrokenRule> brokenRules;
    private transient BrokenRuleMessage brokenRuleMessage;

    private static EmptyBrokenRule emptyBrokenRule = new EmptyBrokenRule();


    public BrokenRuleObject() {
        this.brokenRules = new ArrayList<BrokenRule>();
        this.brokenRuleMessage = this.getBrokenRuleMessages();
    }

    protected abstract BrokenRuleMessage getBrokenRuleMessages();

    public abstract Boolean validate();

    public List<BrokenRule> getBrokenRules() {
        return Collections.unmodifiableList(this.brokenRules);
    }

    public void addBrokenRule(String messageKey) {
        String message = this.brokenRuleMessage.getRuleDescription(messageKey);
        BrokenRule rule = new BrokenRule(messageKey, message);
        this.brokenRules.add(rule);
    }

    public void addBrokenRule(String messageKey, String property) {
        String message = this.brokenRuleMessage.getRuleDescription(messageKey);
        BrokenRule rule = new BrokenRule(messageKey, message, property);
        this.brokenRules.add(rule);
    }

    public void addBrokenRule(String messageKey, String property, String alias) {
        String message = this.brokenRuleMessage.getRuleDescription(messageKey);
        BrokenRule rule = new BrokenRule(messageKey, message, property, alias);
        this.brokenRules.add(rule);
    }

    public BrokenRule findBrokenRule(String property) {
        BrokenRule rule = null;
        for (BrokenRule b : this.brokenRules) {
            if (b.getProperty().equals(property)) {
                rule = b;
                break;
            }
        }
        if (rule == null) {
            return emptyBrokenRule;
        }
        return rule;
    }

    public void throwBrokenRuleException() {
        if (this.getBrokenRules().size() > 0) {
            BrokenRule brokenRule = this.getBrokenRules().get(0);
            throw new BrokenRuleException(brokenRule.getName(), brokenRule.getDescription());
        }
    }

    public void throwBrokeRuleAggregateException() {
        if (this.getBrokenRules().size() > 0) {

            List<BrokenRuleException> brokenRuleExceptions = new ArrayList<>();

            for (BrokenRule message : this.getBrokenRules()) {
                BrokenRuleException brokenRuleException = new BrokenRuleException(message.getName(), message.getDescription());

                brokenRuleExceptions.add(brokenRuleException);
            }
            throw new BrokeRuleAggregateException(brokenRuleExceptions);

        }
    }

    public void clearBrokenRules() {
        this.brokenRules.clear();
    }
}
