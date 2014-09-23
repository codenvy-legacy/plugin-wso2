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
package com.codenvy.ide.client.elements.connectors.googlespreadsheet;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.AbstractConnector;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;

/**
 * The Class describes GetSpreadsheetsByTitle connector for GoogleSpreadsheet group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 */
public class GetSpreadsheetsByTitle extends AbstractConnector {

    public static final String ELEMENT_NAME       = "GetSpreadsheetsByTitle";
    public static final String SERIALIZATION_NAME = "googlespreadsheet.getSpreadsheetsByTitle";

    private static final String TITLE = "title";

    private static final List<String> PROPERTIES = Arrays.asList(TITLE);

    private String title;

    private String titleExpression;

    private List<NameSpace> titleNS;

    @Inject
    public GetSpreadsheetsByTitle(EditorResources resources,
                                  Provider<Branch> branchProvider,
                                  ElementCreatorsManager elementCreatorsManager) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.googleSpreadsheetElement(),
              branchProvider,
              elementCreatorsManager);

        title = "";

        titleExpression = "";

        titleNS = Collections.emptyList();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(TITLE, isInline ? title : titleExpression);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();
        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case TITLE:
                if (isInline) {
                    title = nodeValue;
                } else {
                    titleExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getSpreadsheetTitle() {
        return title;
    }

    public void setSpreadsheetTitle(@Nonnull String title) {
        this.title = title;
    }

    @Nonnull
    public String getTitleExpression() {
        return titleExpression;
    }

    public void setTitleExpression(@Nonnull String titleExpression) {
        this.titleExpression = titleExpression;
    }

    @Nonnull
    public List<NameSpace> getTitleNS() {
        return titleNS;
    }

    public void seTtitleNS(@Nonnull List<NameSpace> titleNS) {
        this.titleNS = titleNS;
    }

}