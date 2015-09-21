package io.redspark.email.overview.editor;
import java.beans.PropertyEditorSupport;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class LocalDatePropertyEditor extends PropertyEditorSupport {

	private DateTimeFormatter formatter;

	public LocalDatePropertyEditor(String pattern) {
		formatter = DateTimeFormat.forPattern(pattern);
	}

	@Override
	public void setAsText(String value) throws IllegalArgumentException {
		if(!value.equals("")){
			setValue(formatter.parseLocalDate(value));
		}
	}

	@Override
	public String getAsText() {
		LocalDate date = (LocalDate) getValue();
		return formatter.print(date);
	}
}
