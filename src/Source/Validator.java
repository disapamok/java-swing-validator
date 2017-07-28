package Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * @author Sahan Dissanayake. (http://www.github.com/Disapamok);
 */
public class Validator {

    private Border defaultBorder = new JTextField().getBorder();
    private Object ErrorComponent;
    private List<String> errors = new ArrayList();
    private int MIN = 1, MAX = 2;
    private boolean fails = false;
    private String fieldName = "field_name", ruleValue = "value";
    private static Map<String, String> errorMessages = new HashMap<>();
    private static Border errorBorder = javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0), 2);

    public Validator(List<ValidatorItem> items) throws Exception {
        for (ValidatorItem item : items) {
            String ruleString = item.getRule(), field = item.getName();
            Object component = item.getField();
            String[] rules = splitRules(ruleString);

            for (String rule : rules) {
                String ruleStr = getRule(rule), value = getValue(component);
                boolean ruleError = false;
                int ruleVal = 0;
                switch (ruleStr) {
                    case "required":
                        ruleError = isNull(value);
                        break;
                    case "number":
                        ruleError = isntNumber(value);
                        break;
                    case "min":
                        int min = ruleVal = getRuleValue(rule);
                        ruleError = length(value, min, MIN);
                        break;
                    case "max":
                        int max = ruleVal = getRuleValue(rule);
                        ruleError = length(value, max, MAX);
                        break;
                    default:
                        throw new Exception("Validation rule : " + rule + " is not supported yet.");
                }

                if (ruleError == true) {
                    fails = true;
                    errors.add(getMessage(ruleStr, field, ruleVal));
                }
                setBorder(component, ruleError);
            }

        }
    }

    private String[] splitRules(String ruleString) {
        if (ruleString.contains("|")) {
            String[] solitted = ruleString.split("\\|");
            return solitted;
        }
        String[] defaultRule = {ruleString};
        return defaultRule;
    }

    private Border getErrorBorder() {
        return errorBorder;
    }

    public static void setErrorBorder(Border Border) {
        errorBorder = Border;
    }

    public Border getDefaultBorder() {
        return defaultBorder;
    }

    private boolean isTextComponent(Object component) {
        return component.getClass() == JTextField.class | component.getClass() == JTextArea.class;
    }

    private boolean isCombo(Object component) {
        return component.getClass() == JComboBox.class;
    }

    private boolean isPassField(Object component) {
        return component.getClass() == JPasswordField.class;
    }

    private boolean isNull(String value) {
        return (value == null || value.equals(""));
    }

    private boolean isntNumber(String value) {
        if (isNull(value)) {
            return false;
        }
        try {
            Double.parseDouble(value);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private boolean length(String text, int lenght, int mode) {
        if (isNull(text)) {
            return false;
        }
        if (mode == MIN) {
            return text.length() < lenght;
        } else {
            return text.length() > lenght;
        }
    }

    public boolean isFails() {
        return fails;
    }
    
    public boolean isPasses(){
        return !fails;
    }

    private JTextField getTextField(Object component) {
        return (JTextField) component;
    }

    private JComboBox getCombo(Object component) {
        return (JComboBox) component;
    }

    private JPasswordField getPwdField(Object component) {
        return (JPasswordField) component;
    }

    private String getRule(String rule) {
        return (rule.contains(":") ? rule.split(":")[0] : rule);
    }

    private int getRuleValue(String rule) throws Exception {
        if (isntNumber(rule) && rule.contains(":")) {
            return Integer.parseInt(rule.split(":")[1]);
        } else {
            throw new Exception("Validator rule '" + rule + "' required a correct integer value for the validation. Ex: " + rule + ":5.");
        }
    }

    private void setBorder(Object component, boolean isError) {
        if (ErrorComponent != component) {
            if (isTextComponent(component)) {
                getTextField(component).setBorder((isError) ? getErrorBorder() : getDefaultBorder());
            } else if (isCombo(component)) {
                getCombo(component).setBorder((isError) ? getErrorBorder() : getDefaultBorder());
            } else if (isPassField(component)) {
                getPwdField(component).setBorder((isError) ? getErrorBorder() : getDefaultBorder());
            }
        }
        if (isError) {
            ErrorComponent = component;
        }
    }

    private String getValue(Object component) throws Exception {
        String value = null;
        if (isTextComponent(component)) {
            value = getTextField(component).getText();
        } else if (isPassField(component)) {
            value = new String(getPwdField(component).getPassword());
        } else if (isCombo(component)) {
            value = getCombo(component).getSelectedItem().toString();
        } else {
            throw new Exception("This component couldn't be validated.");
        }
        return value;
    }

    private Map<String, String> getErrorMessages() {
        return errorMessages;
    }

    public static void setErrorMessages(Map<String, String> errorMessages) {
        Validator.errorMessages = errorMessages;
    }

    private Map<String, String> getDefaultMessages() {
        Map<String, String> map = new HashMap();
        map.put("required", "The "+fieldName+" is required.");
        map.put("min", "Minimum length for "+fieldName+" is "+ruleValue+".");
        map.put("max", "Maximum length for "+fieldName+" is "+ruleValue+".");
        map.put("number", "Text must be a valid number for the "+fieldName+" field.");
        return map;
    }

    private String getMessage(String rule, String field, int value) throws Exception {
        Map<String, String> msgs = getErrorMessages().isEmpty() ? getDefaultMessages() : getErrorMessages();
        if(msgs.get(rule) == null || msgs.get(rule).equals("")){
            throw new Exception("No defined message for validation rule : "+rule+".");
        }
        return replaceShortcodes(msgs.get(rule), field, value);
    }

    private String replaceShortcodes(String pureMsg, String field, int value) {
        pureMsg = pureMsg.replaceAll(fieldName, field);
        pureMsg = pureMsg.replaceAll(ruleValue, value + "");
        return pureMsg;
    }

    public List getErrors() {
        return errors;
    }
}
