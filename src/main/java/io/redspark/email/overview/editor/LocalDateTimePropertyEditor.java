package io.redspark.email.overview.editor;
import java.beans.PropertyEditorSupport;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class LocalDateTimePropertyEditor extends PropertyEditorSupport {

	private DateTimeFormatter formatter;

	public LocalDateTimePropertyEditor(String pattern) {
		formatter = DateTimeFormat.forPattern(pattern);
	}

	@Override
	public void setAsText(String value) throws IllegalArgumentException {
		setValue(formatter.parseLocalDateTime(value));
	}

	@Override
	public String getAsText() {
		LocalDateTime date = (LocalDateTime) getValue();
		return formatter.print(date);
	}
}
