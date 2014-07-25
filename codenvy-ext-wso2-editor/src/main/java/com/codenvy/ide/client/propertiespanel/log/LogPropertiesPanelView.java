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
import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * @author Andrey Plotnikov
 */
@ImplementedBy(LogPropertiesPanelViewImpl.class)
public abstract class LogPropertiesPanelView extends AbstractView<LogPropertiesPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        void onLogCategoryChanged();

        void onLogLevelChanged();

        void onLogSeparatorChanged();

        void onLogPropertiesChanged();

        void onDescriptionChanged();

        void showEditPropertyConfigurationWindow();

    }

    public abstract String getLogCategory();

    public abstract void selectLogCategory(String logCategory);

    public abstract void setLogCategory(List<String> logCategory);

    public abstract String getLogLevel();

    public abstract void selectLogLevel(String logLevel);

    public abstract void setLogLevel(List<String> logLevel);

    public abstract String getLogSeparator();

    public abstract void setLogSeparator(String logSeparator);

    public abstract String getLogProperties();

    public abstract void setLogProperties(String logProperties);

    public abstract String getDescription();

    public abstract void setDescription(String description);

}