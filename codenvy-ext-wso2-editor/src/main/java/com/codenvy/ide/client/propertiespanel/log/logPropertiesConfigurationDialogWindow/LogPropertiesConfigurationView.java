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
package com.codenvy.ide.client.propertiespanel.log.logPropertiesConfigurationDialogWindow;

import com.codenvy.ide.client.elements.Log;
import com.codenvy.ide.client.mvp.View;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * @author Dmitry Shnurenko
 */
@ImplementedBy(LogPropertiesConfigurationViewImpl.class)
public interface LogPropertiesConfigurationView extends
                                                View<LogPropertiesConfigurationView.ActionDelegate> {

    public interface ActionDelegate {
        void onSelectedProperty(Log.Property property);

        void onCancelButtonClicked();

        void onAddPropertyButtonClicked();

        void onRemovePropertyButtonClicked();

        void onOkButtonClicked();

        void showNameSpaceEditorWindow();

        void onEditPropertyButtonClicked();

        void showPropertyConfigurationWindow();
    }

    void setPropertiesList(List<Log.Property> propertyList);

    void showPropertyConfigurationWindow();

    void hidePropertyConfigurationWindow();

    String getSeparatorText();

    String getNameText();

    String getValueExpression();

    void setSeparatorText(String text);

    void setNameText(String text);

    void setValueExpressionText(String text);
}
