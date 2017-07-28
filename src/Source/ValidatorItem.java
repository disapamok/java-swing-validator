package Source;

/**
 * @author Sahan Dissanayake (Disapamok)
 * www.sahan.xyz
 * https://github.com/disapamok
 * https://twitter.com/disapamok
 */
public class ValidatorItem {

    private final String rule;
    private final Object component;
    private final String name;

    public ValidatorItem(String arg_rule, Object arg_component, String arg_name) {
        this.rule = arg_rule;
        this.component = arg_component;
        this.name = arg_name;
    }

    public String getRule() {
        return this.rule;
    }

    public Object getField() {
        return this.component;
    }

    public String getName() {
        return this.name;
    }
}
