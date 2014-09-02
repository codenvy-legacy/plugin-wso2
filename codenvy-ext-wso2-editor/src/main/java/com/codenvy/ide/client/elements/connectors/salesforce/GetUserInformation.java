/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client.elements.connectors.salesforce;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractShape;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.salesforce.GeneralProperty.ParameterEditorType.Inline;

/**
 * The Class describes GetUserInformation connector for Salesforce group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Dmitry Shnurenko
 */
public class GetUserInformation extends AbstractShape {

    public static final String ELEMENT_NAME       = "GetUserInfo";
    public static final String SERIALIZATION_NAME = "salesforce.getUserInformation";
    public static final String CONFIG_KEY         = "configKey";

    private static final List<String> PROPERTIES = Collections.emptyList();

    private String                              configKey;
    private GeneralProperty.ParameterEditorType parameterEditorType;

    @Inject
    public GetUserInformation(EditorResources resources,
                              Provider<Branch> branchProvider,
                              MediatorCreatorsManager mediatorCreatorsManager) {

        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        parameterEditorType = Inline;
        configKey = "";
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        return configKey == null || configKey.isEmpty() ? "" : CONFIG_KEY + "=\"" + configKey + "\"";
    }

    /** {@inheritDoc} */
    @Override
    public void applyAttributes(@Nonnull Node node) {
        if (node.hasAttributes()) {
            Node attribute = node.getAttributes().item(0);

            configKey = attribute.getNodeValue();
        }
    }

    @Nonnull
    public String getConfigRef() {
        return configKey;
    }

    public void setConfigRef(@Nonnull String configRef) {
        this.configKey = configRef;
    }

    @Nonnull
    public GeneralProperty.ParameterEditorType getParameterEditorType() {
        return parameterEditorType;
    }

    public void setParameterEditorType(@Nonnull GeneralProperty.ParameterEditorType parameterEditorType) {
        this.parameterEditorType = parameterEditorType;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.salesforce();
    }

}
