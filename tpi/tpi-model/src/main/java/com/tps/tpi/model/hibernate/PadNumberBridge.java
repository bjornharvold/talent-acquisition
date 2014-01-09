package com.tps.tpi.model.hibernate;

import org.hibernate.search.bridge.StringBridge;

/**
 * User: Bjorn Harvold
 * Date: Jan 5, 2010
 * Time: 10:29:41 PM
 * Responsibility:
 */
public class PadNumberBridge implements StringBridge {
    private final int PAD = 8;

    public String objectToString(Object value) {
        if (value == null) {
            return null;
        }

        float num = 0;

        if (value instanceof Float) {
            num = (Float) value;
        } else {
            throw new IllegalArgumentException("PadNumberBridge.class received a non-float type " + value.getClass());
        }
        return pad(num);
    }

    private String pad(float num) {
        String rawInt = Float.toString(num);
        if (rawInt.length() > PAD)
            throw new IllegalArgumentException(rawInt + " float too large to pad");
        StringBuilder paddedInt = new StringBuilder(PAD);
        for (int padIndex = rawInt.length();
             padIndex < PAD; padIndex++)
            paddedInt.append("0");
        return paddedInt.append(rawInt).toString();
    }
}
