package io.redspark.email.overview.json;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author marcos.fernandes
 * 
 */
public class LocalDateJsonSerializer extends JsonSerializer<LocalDate> {

    /**
     * (non-Javadoc) {@inheritDoc}
     * 
     * @see org.codehaus.jackson.map.JsonSerializer#serialize(java.lang.Object,
     *      org.codehaus.jackson.JsonGenerator,
     *      org.codehaus.jackson.map.SerializerProvider)
     */
    @Override
    public void serialize(final LocalDate value, final JsonGenerator jgen, final SerializerProvider provider)
            throws IOException {
        DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy");
        jgen.writeString(format.print(value));

    }
}