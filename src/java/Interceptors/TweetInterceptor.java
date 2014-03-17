/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interceptors;

import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import java.io.Serializable;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import kwetter.domain.Tweet;

/**
 *
 * @author Toon
 */
@CheckWords
@Interceptor
public class TweetInterceptor implements Serializable {

    @AroundInvoke
    public Object modifyGreeting(InvocationContext ctx) throws Exception {
        Object[] parameters = ctx.getParameters();
        String param = (String) parameters[1];
        param = param.replace("vet", "dik");
        param = param.replace("cool", "hard");
        parameters[1] = param;
        ctx.setParameters(parameters);
        try {
            return ctx.proceed();
        } catch (Exception e) {
            logger.warning("Error calling ctx.proceed in modifyGreeting()");
            return null;
        }
    }
}
