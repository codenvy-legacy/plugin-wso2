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
import com.codenvy.ide.collections.Array;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;
import static com.codenvy.ide.collections.Collections.createArray;

/**
 * The Class describes ImportCSV connector for GoogleSpreadsheet group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 */
public class ImportCSV extends AbstractConnector {

    public static final String ELEMENT_NAME       = "ImportCSV";
    public static final String SERIALIZATION_NAME = "googlespreadsheet.importCSV";

    private static final String SPREADSHEET_NAME = "spreadsheetName";
    private static final String WORKSHEET_COUNT  = "worksheetName";
    private static final String FILE_PATH        = "filePath";
    private static final String BATCH_ENABLE     = "batchEnable";
    private static final String BATCH_SIZE       = "batchSize";

    private static final List<String> PROPERTIES = Arrays.asList(SPREADSHEET_NAME, WORKSHEET_COUNT, FILE_PATH, BATCH_ENABLE, BATCH_SIZE);

    private String spreadsheetName;
    private String worksheetName;
    private String filePath;
    private String batchEnable;
    private String batchSize;

    private String spreadsheetNameExpression;
    private String worksheetNameExpression;
    private String filePathExpression;
    private String batchEnableExpression;
    private String batchSizeExpression;

    private Array<NameSpace> spreadsheetNameNS;
    private Array<NameSpace> worksheetNameNS;
    private Array<NameSpace> filePathNS;
    private Array<NameSpace> batchEnableNS;
    private Array<NameSpace> batchSizeNS;

    @Inject
    public ImportCSV(EditorResources resources,
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

        spreadsheetName = "";
        worksheetName = "";
        filePath = "";
        batchEnable = "";
        batchSize = "";

        spreadsheetNameExpression = "";
        worksheetNameExpression = "";
        filePathExpression = "";
        batchEnableExpression = "";
        batchSizeExpression = "";

        spreadsheetNameNS = createArray();
        worksheetNameNS = createArray();
        filePathNS = createArray();
        batchEnableNS = createArray();
        batchSizeNS = createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        Map<String, String> properties = new LinkedHashMap<>();

        boolean isInline = parameterEditorType.equals(Inline);

        properties.put(SPREADSHEET_NAME, isInline ? spreadsheetName : spreadsheetNameExpression);
        properties.put(WORKSHEET_COUNT, isInline ? worksheetName : worksheetNameExpression);
        properties.put(FILE_PATH, isInline ? filePath : filePathExpression);
        properties.put(BATCH_ENABLE, isInline ? batchEnable : batchEnableExpression);
        properties.put(BATCH_SIZE, isInline ? batchSize : batchSizeExpression);

        return convertPropertiesToXMLFormat(properties);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();
        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case SPREADSHEET_NAME:
                if (isInline) {
                    spreadsheetName = nodeValue;
                } else {
                    spreadsheetNameExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case WORKSHEET_COUNT:
                if (isInline) {
                    worksheetName = nodeValue;
                } else {
                    worksheetNameExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case FILE_PATH:
                if (isInline) {
                    filePath = nodeValue;
                } else {
                    filePathExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case BATCH_ENABLE:
                if (isInline) {
                    batchEnable = nodeValue;
                } else {
                    batchEnableExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;

            case BATCH_SIZE:
                if (isInline) {
                    batchSize = nodeValue;
                } else {
                    batchSizeExpression = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getSpreadsheetName() {
        return spreadsheetName;
    }

    public void setSpreadsheetName(@Nonnull String spreadsheetName) {
        this.spreadsheetName = spreadsheetName;
    }

    @Nonnull
    public String getWorksheetName() {
        return worksheetName;
    }

    public void setWorksheetName(@Nonnull String worksheetCount) {
        this.worksheetName = worksheetCount;
    }

    @Nonnull
    public String getSpreadsheetNameExpression() {
        return spreadsheetNameExpression;
    }

    public void setSpreadsheetNameExpression(@Nonnull String spreadsheetNameExpression) {
        this.spreadsheetNameExpression = spreadsheetNameExpression;
    }

    @Nonnull
    public String getWorksheetNameExpression() {
        return worksheetNameExpression;
    }

    public void setWorksheetCountExpression(@Nonnull String worksheetCountExpression) {
        this.worksheetNameExpression = worksheetCountExpression;
    }

    @Nonnull
    public Array<NameSpace> getSpreadsheetNameNS() {
        return spreadsheetNameNS;
    }

    public void setSpreadsheetNameNS(@Nonnull Array<NameSpace> spreadsheetNameNS) {
        this.spreadsheetNameNS = spreadsheetNameNS;
    }

    @Nonnull
    public Array<NameSpace> getWorksheetNameNS() {
        return worksheetNameNS;
    }

    public void setWorksheetNameNS(@Nonnull Array<NameSpace> worksheetCountNS) {
        this.worksheetNameNS = worksheetCountNS;
    }

    @Nonnull
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(@Nonnull String filePath) {
        this.filePath = filePath;
    }

    @Nonnull
    public String getBatchEnable() {
        return batchEnable;
    }

    public void setBatchEnable(@Nonnull String batchEnable) {
        this.batchEnable = batchEnable;
    }

    @Nonnull
    public String getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(@Nonnull String batchSize) {
        this.batchSize = batchSize;
    }

    @Nonnull
    public String getFilePathExpression() {
        return filePathExpression;
    }

    public void setFilePathExpression(@Nonnull String filePathExpression) {
        this.filePathExpression = filePathExpression;
    }

    @Nonnull
    public String getBatchEnableExpression() {
        return batchEnableExpression;
    }

    public void setBatchEnableExpression(@Nonnull String batchEnableExpression) {
        this.batchEnableExpression = batchEnableExpression;
    }

    @Nonnull
    public String getBatchSizeExpression() {
        return batchSizeExpression;
    }

    public void setBatchSizeExpression(@Nonnull String batchSizeExpression) {
        this.batchSizeExpression = batchSizeExpression;
    }

    @Nonnull
    public Array<NameSpace> getFilePathNS() {
        return filePathNS;
    }

    public void setFilePathNS(@Nonnull Array<NameSpace> filePathNS) {
        this.filePathNS = filePathNS;
    }

    @Nonnull
    public Array<NameSpace> getBatchEnableNS() {
        return batchEnableNS;
    }

    public void setBatchEnableNS(@Nonnull Array<NameSpace> batchEnableNS) {
        this.batchEnableNS = batchEnableNS;
    }

    @Nonnull
    public Array<NameSpace> getBatchSizeNS() {
        return batchSizeNS;
    }

    public void setBatchSizeNS(@Nonnull Array<NameSpace> batchSizeNS) {
        this.batchSizeNS = batchSizeNS;
    }

}