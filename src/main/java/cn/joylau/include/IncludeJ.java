/*******************************************************************************
 * Copyright (c) 2017 by JoyLau. All rights reserved
 ******************************************************************************/

package cn.joylau.include;

import freemarker.cache.TemplateLoader;
import freemarker.core.Environment;
import freemarker.core._MiscTemplateException;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by JoyLau on 4/11/2017.
 * cn.joylau.include
 * fa.liu@gtafe.com
 */
public class IncludeJ implements TemplateDirectiveModel {

    private static final String PATH_PARAM = "template";

    private String getParam(Map params) {
        Object value = params.get("name");

        if (value instanceof SimpleScalar) {
            return ((SimpleScalar) value).getAsString();
        }

        return null;
    }

    private String getName(Map params) {
        return getParam(params);
    }

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody
            templateDirectiveBody) throws TemplateException, IOException {

        String key = getName(map);

        if (key == null || key.length() == 0) {
            throw new TemplateModelException("The 'name' tag attribute must be set.");
        }

        TemplateLoader templateLoader = environment.getConfiguration().getTemplateLoader();

        String fullTemplatePath = getFullTemplatePath(environment, map, PATH_PARAM);
        if (templateLoader.findTemplateSource(fullTemplatePath) != null) {

            DefaultObjectWrapper wrapper=new DefaultObjectWrapperBuilder(new Version("2.3.23")).build();

            environment.setVariable("includeJ",wrapper.wrap(key));

            environment.include(environment.getTemplateForInclusion(fullTemplatePath, null, true));
        } else {
            throw new _MiscTemplateException(environment, "Missing template file path:" + fullTemplatePath);
        }

    }

    private String getFullTemplatePath(Environment environment, Map params, String
            templatePath)
            throws MalformedTemplateNameException {
        if (!params.containsKey(templatePath)) {
            throw new MalformedTemplateNameException("missing required parameter '" + templatePath, "'");
        }
        return params.get(templatePath).toString();
    }

}
