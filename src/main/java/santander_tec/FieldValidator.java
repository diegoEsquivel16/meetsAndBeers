package santander_tec;

import org.apache.commons.lang3.StringUtils;

import static java.lang.String.format;

public class FieldValidator {

    private FieldValidator(){}

    public static void validateRequestField(String field, String fieldName){
        if(StringUtils.isBlank(field)){
            throw new IllegalArgumentException(format("The field %s is invalid", fieldName));
        }
    }
}
