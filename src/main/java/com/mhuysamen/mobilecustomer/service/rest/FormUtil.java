package com.mhuysamen.mobilecustomer.service.rest;

import org.springframework.util.MultiValueMap;

import com.mhuysamen.mobilecustomer.domain.MobileSubscriber.ServiceType;

public class FormUtil {
    private static String getFormValue(final MultiValueMap<String, String> formData, final String key) {
        String value = formData.getFirst(key);
        if(value == null) {
            throw new MissingParameterException(key);
        }
        return value;
    }

    public static String getFormString(final MultiValueMap<String, String> formData, final String key) {
        return getFormValue(formData, key);
    }

    public static Integer getFormInteger(final MultiValueMap<String, String> formData, final String key) {
        String value = getFormValue(formData, key);
        try {
            return Integer.valueOf(value);
        }
        catch(NumberFormatException e) {
            throw new BadValueException(key, value);
        }

    }

    public static <E extends Enum<E>> E getFormEnum(final MultiValueMap<String, String> formData, final String key, Class<E> clazz) {
        String value = getFormValue(formData, key);
        return toEnum(key, value, clazz);
    }

    private static <E extends Enum<E>> E toEnum(final String param, final String value, Class<E> clazz) {
        try {
            E en = Enum.valueOf(clazz, value.toUpperCase());
            return en;
        }
        catch(IllegalArgumentException e) {
            throw new BadValueException(param, value);
        }
    }

    public static ServiceType toServiceType(final String param, final String value) {
        return toEnum(param, value, ServiceType.class);
    }
}
