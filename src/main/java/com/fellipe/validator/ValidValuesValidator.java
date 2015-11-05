package com.fellipe.validator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fellipe.domain.Content;
import com.fellipe.domain.Field;
import com.fellipe.domain.Value;

public class ValidValuesValidator implements ConstraintValidator<ValidValues, Content> {

	@Override
	public void initialize(ValidValues arg0) {
	}

	@Override
	public boolean isValid(Content content, ConstraintValidatorContext ctx) {

		boolean isValid = true;
		List<String> errors = new ArrayList<String>();
		ctx.disableDefaultConstraintViolation();
		
		for (Value value : content.getValues()) {
			Field field = content.getField();
			
			if (field.getMandatory() && value == null) {
				errors.add("{values.mandatory}");
			}
			if (!field.getMultiple() && content.getValues().size() > 1) {
				errors.add("{values.multiple}");
			}
			errors.addAll(value.validate(content));
		}
		
		if (errors.size() > 0) {
			for (String error : errors) {
				ctx.buildConstraintViolationWithTemplate(error).addConstraintViolation();
				isValid = false;
			}
		}
		
		return isValid;
	}

}
