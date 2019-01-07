package com.github.joffryferrater.pep.security;

import com.github.joffryferrater.pep.client.PdpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
public class AbacWebSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler {

    @Autowired
    private PdpClient pdpClient;

    @Override
    protected StandardEvaluationContext createEvaluationContextInternal(Authentication authentication,
        FilterInvocation invocation) {
        StandardEvaluationContext evaluationContext = super.createEvaluationContextInternal(authentication, invocation);
        evaluationContext.setVariable("abac", new AbacMethodSecurityExpression(pdpClient));
        return evaluationContext;
    }
}
