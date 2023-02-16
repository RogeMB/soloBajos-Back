package com.solobajos.solobajos.converter;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.SmallIntTypeDescriptor;

import java.time.Year;

public class YearType extends AbstractSingleColumnStandardBasicType<Year> {

    public static final YearType INSTANCE = new YearType();
    public YearType() {
        super(
                SmallIntTypeDescriptor.INSTANCE,
                YearTypeDescriptor.INSTANCE
        );
    }

    public String getName() {
        return "year";
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }
}