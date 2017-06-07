package easy.domain.base;

public class BrokenRule {

    private String name;
    private String description;
    private String property;
    private String alias;


    public BrokenRule(String name, String description) {
        this(name, description, "");
    }

    public BrokenRule(String name, String description, String property) {
        this(name, description, property, "");
    }

    public BrokenRule(String name, String description, String property, String alias) {
        this.name = name;
        this.description = description;
        this.property = property;
        this.alias = alias == null || alias.equals("") ? property : alias;
    }

    public String getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getProperty() {
        return property;
    }
}
