package com.tps.tpi.utils;

import org.springframework.expression.EvaluationException;
import org.springframework.expression.ParseException;

import java.util.Map;

/**
 * User: Bjorn Harvold
 * Date: Dec 18, 2009
 * Time: 10:58:33 PM
 * Responsibility:
 */
public interface ExpressionEvaluator {
    Object evaluate(String script, Map<String, Object> context) throws ParseException, EvaluationException;
}
