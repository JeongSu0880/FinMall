package com.hanaro.finmall.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class AccountNumberSerializer extends StdSerializer<String> {

    public AccountNumberSerializer() {
        super(String.class);
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        if (value == null) {
            gen.writeNull();
            return;
        }

        String number = value.replaceAll("[^0-9]", "");

        String formatted = number.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");

        gen.writeString(formatted);
    }
}