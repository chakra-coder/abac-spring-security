package com.github.joffryferrater.pep.security;

import com.github.joffryferrater.pep.client.PdpClient;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.core.Authentication;

@Configuration
public class AbacMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    @Autowired
    private PdpClient pdpClient;

    @Override
    public StandardEvaluationContext createEvaluationContextInternal(Authentication auth, MethodInvocation mi) {
        StandardEvaluationContext evaluationContext = super.createEvaluationContextInternal(auth, mi);
        evaluationContext.setVariable("abac", new AbacMethodSecurityExpression(pdpClient));
        return evaluationContext;
    }
}
