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
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

/**
 * The class which describes state of LoopBack mediator and also has methods for changing it. Also the class contains the business
 * logic that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. For more information about LoopBack mediator go to
 * <a href=" https://docs.wso2.com/display/ESB460/LoopBack+Mediator"> https://docs.wso2.com/display/ESB460/LoopBack+Mediator</a>
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class LoopBack extends AbstractElement {
    public static final String ELEMENT_NAME       = "LoopBack";
    public static final String SERIALIZATION_NAME = "loopback";

    public static final Key<String> DESCRIPTION = new Key<>("Description");

    private static final String DESCRIPTION_ATTRIBUTE = "description";

    private static final List<String> PROPERTIES = emptyList();

    @Inject
    public LoopBack(EditorResources resources, Provider<Branch> branchProvider, ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.loopBack(),
              branchProvider,
              elementCreatorsManager);

        putProperty(DESCRIPTION, "");
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        Map<String, String> prop = new LinkedHashMap<>();

        prop.put(DESCRIPTION_ATTRIBUTE, getProperty(DESCRIPTION));

        return convertAttributesToXML(prop);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        if (!DESCRIPTION_ATTRIBUTE.equals(attributeName)) {
            return;
        }

        putProperty(DESCRIPTION, String.valueOf(attributeValue));
    }
}