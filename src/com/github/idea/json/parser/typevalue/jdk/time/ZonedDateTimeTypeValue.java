package com.github.idea.json.parser.typevalue.jdk.time;

import com.github.idea.json.parser.typevalue.TypeDefaultValue;
import com.github.idea.json.parser.typevalue.TypeValueContext;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author wangji
 * @date 2024/5/19 13:42
 */
public class ZonedDateTimeTypeValue implements TypeDefaultValue {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
    private final ZonedDateTime now = ZonedDateTime.now();


    @Override
    public Object getValue(TypeValueContext context) {
        return now.format(formatter);
    }

    @Override
    public String getQualifiedName() {
        return ZonedDateTime.class.getName();
    }
}
