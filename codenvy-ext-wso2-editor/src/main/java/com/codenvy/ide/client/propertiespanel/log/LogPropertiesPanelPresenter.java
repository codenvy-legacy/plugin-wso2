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
package com.codenvy.ide.client.propertiespanel.log;

import com.codenvy.ide.client.elements.Log;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.log.logPropertiesConfigurationDialogWindow.LogPropertiesConfigurationPresenter;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public class LogPropertiesPanelPresenter extends AbstractPropertiesPanel<Log> implements LogPropertiesPanelView.ActionDelegate {

    private final LogPropertiesConfigurationPresenter logPropertiesConfigurationPresenter;

    @Inject
    public LogPropertiesPanelPresenter(LogPropertiesPanelView view,
                                       PropertyTypeManager propertyTypeManager,
                                       LogPropertiesConfigurationPresenter logPropertiesConfigurationPresenter) {
        super(view, propertyTypeManager);
        this.logPropertiesConfigurationPresenter = logPropertiesConfigurationPresenter;
    }

    /** {@inheritDoc} */
    @Override
    public void onLogCategoryChanged() {
        element.setLogCategory(((LogPropertiesPanelView)view).getLogCategory());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onLogLevelChanged() {
        element.setLogLevel(((LogPropertiesPanelView)view).getLogLevel());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onLogSeparatorChanged() {
        element.setLogSeparator(((LogPropertiesPanelView)view).getLogSeparator());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onLogPropertiesChanged() {
       /* element.setLogProperties(((LogPropertiesPanelView)view).getLogProperties());
        notifyListeners();*/
    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {
        element.setDescription(((LogPropertiesPanelView)view).getDescription());
        notifyListeners();
    }

    @Override
    public void showEditPropertyConfigurationWindow() {
        logPropertiesConfigurationPresenter.showPropertyConfigurationWindow();
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        ((LogPropertiesPanelView)view).setLogCategory(propertyTypeManager.getValuesOfTypeByName("LogCategory"));
        ((LogPropertiesPanelView)view).selectLogCategory(element.getLogCategory());
        ((LogPropertiesPanelView)view).setLogLevel(propertyTypeManager.getValuesOfTypeByName("LogLevel"));
        ((LogPropertiesPanelView)view).selectLogLevel(element.getLogLevel());
        ((LogPropertiesPanelView)view).setLogSeparator(element.getLogSeparator());
        //((LogPropertiesPanelView)view).setLogProperties(element.getLogProperties());
        ((LogPropertiesPanelView)view).setDescription(element.getDescription());
    }

}