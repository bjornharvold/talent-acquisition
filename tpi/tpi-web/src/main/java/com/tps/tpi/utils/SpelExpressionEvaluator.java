package com.tps.tpi.utils;

import org.springframework.expression.*;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * User: Bjorn Harvold
 * Date: Dec 18, 2009
 * Time: 10:57:12 PM
 * Responsibility:
 */
public class SpelExpressionEvaluator implements ExpressionEvaluator {
    private ExpressionParser parser = new SpelExpressionParser();

    public Object evaluate(String script, Map<String, Object> context) throws ParseException, EvaluationException {
        Expression expression = parser.parseExpression(script);
        EvaluationContext evalContext = new StandardEvaluationContext();

        for (String key : context.keySet()) {
            evalContext.setVariable(key, context.get(key));
        }
        
        return expression.getValue(evalContext);
    }
}
