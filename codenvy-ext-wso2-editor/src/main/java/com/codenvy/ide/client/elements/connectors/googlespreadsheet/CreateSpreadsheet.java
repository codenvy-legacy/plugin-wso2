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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.INLINE;

/**
 * The Class describes CreateSpreadsheet connector for GoogleSpreadsheet group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 */
public class CreateSpreadsheet extends AbstractConnector {

    public static final String ELEMENT_NAME       = "CreateSpreadsheet";
    public static final String SERIALIZATION_NAME = "googlespreadsheet.createSpreadsheet";

    public static final Key<String>          SPREADSHEET_NAME            = new Key<>("SpreadsheetName");
    public static final Key<String>          WORKSHEET_COUNT             = new Key<>("WorksheetCount");
    public static final Key<String>          SPREADSHEET_NAME_EXPRESSION = new Key<>("SpreadsheetNameExpression");
    public static final Key<String>          WORKSHEET_COUNT_EXPRESSION  = new Key<>("WorksheetCountExpression");
    public static final Key<List<NameSpace>> SPREADSHEET_NAME_NS         = new Key<>("SpreadsheetNameNS");
    public static final Key<List<NameSpace>> WORKSHEET_COUNT_NS          = new Key<>("WorksheetCountNS");

    private static final String SPREADSHEET = "spreadsheetName";
    private static final String WORKSHEET   = "worksheetCount";

    private static final List<String> PROPERTIES = Arrays.asList(SPREADSHEET, WORKSHEET);

    @Inject
    public CreateSpreadsheet(EditorResources resources,
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

        putProperty(SPREADSHEET_NAME, "");
        putProperty(WORKSHEET_COUNT, "");
        putProperty(SPREADSHEET_NAME_EXPRESSION, "");
        putProperty(WORKSHEET_COUNT_EXPRESSION, "");
        putProperty(SPREADSHEET_NAME_NS, new ArrayList<NameSpace>());
        putProperty(WORKSHEET_COUNT_NS, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE));

        properties.put(SPREADSHEET, isInline ? getProperty(SPREADSHEET_NAME) : getProperty(SPREADSHEET_NAME_EXPRESSION));
        properties.put(WORKSHEET, isInline ? getProperty(WORKSHEET_COUNT) : getProperty(WORKSHEET_COUNT_EXPRESSION));

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        if (SPREADSHEET.equals(nodeName)) {
            adaptProperty(nodeValue, SPREADSHEET_NAME, SPREADSHEET_NAME_EXPRESSION);
        }

        if (WORKSHEET.equals(nodeName)) {
            adaptProperty(nodeValue, WORKSHEET_COUNT, WORKSHEET_COUNT_EXPRESSION);
        }
    }

}