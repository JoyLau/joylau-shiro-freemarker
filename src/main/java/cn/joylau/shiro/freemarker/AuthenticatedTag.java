/*******************************************************************************
 * Copyright (c) 2017 by JoyLau. All rights reserved
 ******************************************************************************/

package cn.joylau.shiro.freemarker;

import freemarker.core.Environment;
import freemarker.log.Logger;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;


public class AuthenticatedTag extends SecureTag {
    private static final Logger log = Logger.getLogger("AuthenticatedTag");

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        if (getSubject() != null && getSubject().isAuthenticated()) {
            if (log.isDebugEnabled()) {
                log.debug("Subject exists and is authenticated.  Tag body will be evaluated.");
            }

            renderBody(env, body);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Subject does not exist or is not authenticated.  Tag body will not be evaluated.");
            }
        }
    }
}