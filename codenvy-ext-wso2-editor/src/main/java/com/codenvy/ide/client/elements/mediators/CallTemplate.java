/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache  License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client.elements.mediators;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.mediators.log.Property;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The class which describes state of CallTemplate mediator and also has methods for changing it. Also the class contains the business
 * logic that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. For more information about CallTemplate mediator go to
 * <a href=" https://docs.wso2.com/display/ESB460/CallTemplate+Mediator"> https://docs.wso2.com/display/ESB460/CallTemplate+Mediator</a>
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class CallTemplate extends AbstractElement {
    public static final String ELEMENT_NAME       = "CallTemplate";
    public static final String SERIALIZATION_NAME = "callTemplate";

    public static final Key<AvailableTemplates> AVAILABLE_TEMPLATES = new Key<>("AvailableTemplates");
    public static final Key<String>             TARGET_TEMPLATES    = new Key<>("TargetTemplates");
    public static final Key<String>             DESCRIPTION         = new Key<>("CallTemplateDescription");
    public static final Key<Array<Property>>    PARAMETERS          = new Key<>("CallTemplateParameters");

    private static final String TARGET_ATTRIBUTE_NAME      = "target";
    private static final String DESCRIPTION_ATTRIBUTE_NAME = "description";
    private static final String PARAMETERS_PROPERTY_NAME   = "with-param";

    private static final List<String> PROPERTIES = Arrays.asList(PARAMETERS_PROPERTY_NAME);

    private final Provider<Property> propertyProvider;

    @Inject
    public CallTemplate(EditorResources resources,
                        Provider<Branch> branchProvider,
                        ElementCreatorsManager elementCreatorsManager,
                        Provider<Property> propertyProvider) {

        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.callTemplate(),
              branchProvider,
              elementCreatorsManager);

        this.propertyProvider = propertyProvider;

        putProperty(AVAILABLE_TEMPLATES, AvailableTemplates.EMPTY);
        putProperty(TARGET_TEMPLATES, "");
        putProperty(DESCRIPTION, "");
        putProperty(PARAMETERS, Collections.<Property>createArray());
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        Map<String, String> prop = new LinkedHashMap<>();

        prop.put(TARGET_ATTRIBUTE_NAME, getProperty(TARGET_TEMPLATES));
        prop.put(DESCRIPTION_ATTRIBUTE_NAME, getProperty(DESCRIPTION));

        return convertAttributesToXMLFormat(prop);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Array<Property> parameters = getProperty(PARAMETERS);

        if (parameters == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        for (Property property : parameters.asIterable()) {
            result.append(property.serializeWithParam());
        }

        return result.toString();
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        Property property = propertyProvider.get();
        property.applyAttributes(node);

        Array<Property> parameters = getProperty(PARAMETERS);
        if (parameters != null) {
            parameters.add(property);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        if (TARGET_ATTRIBUTE_NAME.equals(attributeName)) {
            putProperty(TARGET_TEMPLATES, attributeValue);

        } else {
            putProperty(DESCRIPTION, attributeValue);
        }
    }

    public enum AvailableTemplates {
        SELECT_FROM_TEMPLATE("Select From Templates"), SDF("sdf"), EMPTY("");

        public static final String TYPE_NAME = "AvailableTemplatesType";

        private final String value;

        AvailableTemplates(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }
    }

}