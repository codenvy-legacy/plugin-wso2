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
package com.codenvy.ide.client.propertiespanel.log;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The abstract view's representation of 'Log' mediator properties panel. It provides an ability to show all available properties of
 * the mediator and edit it.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
@ImplementedBy(LogPropertiesPanelViewImpl.class)
public abstract class LogPropertiesPanelView extends AbstractView<LogPropertiesPanelView.ActionDelegate> {

    /**
     * Interface defines methods of {@link LogPropertiesPanelPresenter} which calls from view. These methods defines
     * some actions when user changes properties of Log mediator.
     */
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

    /**
     * Sets selected log category in special place on the view which corresponds to the transferred value
     *
     * @param logCategory
     *         value of log category which need to set
     */
    public abstract void selectLogCategory(@Nullable String logCategory);

    /**
     * Sets category value to the special place on the view which uses for showing category parameter.
     *
     * @param logCategories
     *         list which contains value of log category which need to set
     */
    public abstract void setLogCategories(@Nullable List<String> logCategories);

    /** @return category value from the special place on the view which uses for showing category parameter */
    @Nonnull
    public abstract String getLogLevel();

    /**
     * Sets selected log level in special place on the view which corresponds to the transferred value
     *
     * @param logLevel
     *         value of log level which need to set
     */
    public abstract void selectLogLevel(@Nullable String logLevel);

    /**
     * Sets level value to the special place on the view which uses for showing level parameter.
     *
     * @param logLevels
     *         list which contains value of log levels which need to set
     */
    public abstract void setLogLevels(@Nullable List<String> logLevels);

    /** @return separator value from the special place on the view which uses for showing separator parameter */
    @Nonnull
    public abstract String getLogSeparator();

    /**
     * Sets separator value to the special place on the view which uses for showing separator parameter.
     *
     * @param logSeparator
     *         value of log separator which need to set
     */
    public abstract void setLogSeparator(@Nullable String logSeparator);

    /** @return description value from the special place on the view which uses for showing description parameter */
    @Nonnull
    public abstract String getDescription();

    /**
     * Sets description value to the special place on the view which uses for showing description parameter.
     *
     * @param description
     *         value of log description which need to set
     */
    public abstract void setDescription(@Nullable String description);

}