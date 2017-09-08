package easy.domain.base;

import java.io.Serializable;

public abstract class ConcurrentEntityBase<T extends Serializable> extends EntityBase<T> {
    private long oldVersion = 1;

    public long getOldVersion() {
        return oldVersion;
    }

    protected void setOldVersion(long oldVersion) {
        this.oldVersion = oldVersion;
    }

    public long getNewVersion() {
        return ++this.oldVersion;
    }


    @Override
    protected abstract BrokenRuleMessage getBrokenRuleMessages();

    @Override
    public abstract Boolean validate();
}
