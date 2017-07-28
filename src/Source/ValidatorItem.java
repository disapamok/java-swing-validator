/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Source;

/**
 *
 * @author CS003
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
