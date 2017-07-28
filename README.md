## Java Swing Validator

If you are interested or currently working on some swing project here is a simple form validator method with custom validator error messages and this also highlights the field.

### How to use Validator

Download and add Validator.java and ValidatorItem.java to your class path.

```markdown
Sample usage. (This can use in "save" button code)
`
import Source.Validator;
import Source.ValidatorItem;
import java.util.List;

private void save() {
  try{
      /*
      * If you want to set custom error messages.
      * field_name and value words in custom validator messages will be replaced by the validator class automatically.
      * field_name = What you set in -> new ValidatorItem("required", jTextField1, "First Field") here. (First Field)
      * value = What you set in validator method max:5. In this, 5 is the value.
      */
      Map<String, String> msgs = new HashMap<String, String>();
      msgs.put("required", "field_name required! Custom message.");
      msgs.put("number", "This is a custom message for number validation rule. field_name.");
      msgs.put("min", "Please add at least value.");
      msgs.put("max", "Oh! Maximum length should be value for field_name!");
      Validator.setErrorMessages(msgs);

      /*
      * Normal usage.
      */

      List<ValidatorItem> vals = new ArrayList<>();
      vals.add(new ValidatorItem("required", jTextField1, "First Field"));
      vals.add(new ValidatorItem("required|number|min:5|max:8", jTextField2, "Second Field"));
      vals.add(new ValidatorItem("required|min:5", jTextField3, "Third Field"));
      vals.add(new ValidatorItem("required|max:5", jTextField4, "Fourth Field"));

      Validator validator = new Validator(vals);
      boolean isFormValid = validator.isPasses();

      // Get error messages :
      List<String> errors = validator.getErrors();
      for (String error : errors) {
          System.out.println(error);
      }
    } catch (Exception e) {
            e.printStackTrace();
    }
}

`
```

Thanks. Follow disapamok in github/twitter for more.


