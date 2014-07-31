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

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Andrey Plotnikov
 */
@ImplementedBy(LogPropertiesPanelViewImpl.class)
public abstract class LogPropertiesPanelView extends AbstractView<LogPropertiesPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs any actions appropriate in response to the user having changed category on properties panel. */
        void onLogCategoryChanged();

        /** Performs any actions appropriate in response to the user having changed level on properties panel. */
        void onLogLevelChanged();

        /** Performs any actions appropriate in response to the user having changed separator on properties panel. */
        void onLogSeparatorChanged();

        /** Performs any actions appropriate in response to the user having changed description on properties panel. */
        void onDescriptionChanged();

        /** Performs any actions appropriate in response to the user click edit property button. */
        void onEditButtonClicked();
    }

    /** @return category value from the special place on the view which uses for showing category parameter */
    public abstract String getLogCategory();

    /** Sets selected log category in special place on the view which corresponds to the transferred value */
    public abstract void selectLogCategory(@Nullable String logCategory);

    /** Sets category value to the special place on the view which uses for showing category parameter. */
    public abstract void setLogCategory(@Nullable List<String> logCategory);

    /** @return category value from the special place on the view which uses for showing category parameter */
    public abstract String getLogLevel();

    /** Sets selected log level in special place on the view which corresponds to the transferred value */
    public abstract void selectLogLevel(@Nullable String logLevel);

    /** Sets level value to the special place on the view which uses for showing level parameter. */
    public abstract void setLogLevel(@Nullable List<String> logLevel);

    /** @return separator value from the special place on the view which uses for showing separator parameter */
    public abstract String getLogSeparator();

    /** Sets separator value to the special place on the view which uses for showing separator parameter. */
    public abstract void setLogSeparator(@Nullable String logSeparator);

    /** @return description value from the special place on the view which uses for showing description parameter */
    public abstract String getDescription();

    /** Sets description value to the special place on the view which uses for showing description parameter. */
    public abstract void setDescription(@Nullable String description);

}