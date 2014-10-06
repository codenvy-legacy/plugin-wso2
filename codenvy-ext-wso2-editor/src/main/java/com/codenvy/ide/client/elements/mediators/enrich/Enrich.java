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
package com.codenvy.ide.client.elements.mediators.enrich;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.mediators.enrich.Source.SOURCE_SERIALIZATION_NAME;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TARGET_SERIALIZATION_NAME;

/**
 * The class which describes state of Send mediator and also has methods for changing it. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. For more information about Send mediator go to
 * <a href=" https://docs.wso2.com/display/ESB460/Enrich+Mediator"> https://docs.wso2.com/display/ESB460/Enrich+Mediator</a>
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class Enrich extends AbstractElement {
    public static final String ELEMENT_NAME       = "Enrich";
    public static final String SERIALIZATION_NAME = "enrich";

    public static final Key<Source> SOURCE      = new Key<>("EnrichSource");
    public static final Key<Target> TARGET      = new Key<>("EnrichTarget");
    public static final Key<String> DESCRIPTION = new Key<>("EnrichDescription");

    private static final String DESCRIPTION_ATTRIBUTE_NAME = "description";

    private static final List<String> PROPERTIES = Arrays.asList(TARGET_SERIALIZATION_NAME, SOURCE_SERIALIZATION_NAME);

    @Inject
    public Enrich(EditorResources resources,
                  Provider<Branch> branchProvider,
                  ElementCreatorsManager elementCreatorsManager,
                  Source source,
                  Target target) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.enrich(),
              branchProvider,
              elementCreatorsManager);

        putProperty(SOURCE, source);
        putProperty(TARGET, target);
        putProperty(DESCRIPTION, "");
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();

        attributes.put(DESCRIPTION_ATTRIBUTE_NAME, getProperty(DESCRIPTION));

        return convertAttributesToXML(attributes);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Source source = getProperty(SOURCE);
        Target target = getProperty(TARGET);

        if (source == null || target == null) {
            return "";
        }

        return source.serialize() + target.serialize();
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull Node node) {
        Source source = getProperty(SOURCE);
        Target target = getProperty(TARGET);

        if (source == null || target == null) {
            return;
        }

        readXMLAttributes(node);

        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            deserializeChildNode(childNodes.item(i), source, target);
        }
    }

    private void deserializeChildNode(@Nonnull Node childNode, @Nonnull Source source, @Nonnull Target target) {
        String nodeName = childNode.getNodeName();

        if (TARGET_SERIALIZATION_NAME.equals(nodeName)) {
            target.deserialize(childNode);
        }

        if (SOURCE_SERIALIZATION_NAME.equals(nodeName)) {
            source.applyProperty(childNode);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        putProperty(DESCRIPTION, attributeValue);
    }
}