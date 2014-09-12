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
package com.codenvy.ide.client.propertiespanel.mediators.filter;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The abstract view's representation of 'Filter' mediator properties panel. It provides an ability to show all available properties of
 * the mediator and edit it.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(FilterPropertiesPanelViewImpl.class)
public abstract class FilterPropertiesPanelView extends AbstractView<FilterPropertiesPanelView.ActionDelegate> {

    /**
     * Interface defines methods of {@link FilterPropertiesPanelPresenter} which calls from view. These methods defines
     * some actions when user change properties of Filter mediator.
     */
    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs some actions in response to user's changing condition type field. */
        void onConditionTypeChanged();

        /** Performs some actions in response to user's changing regular expression field. */
        void onRegularExpressionChanged();

        /** Performs some actions in response to user's editing source. */
        void onEditSourceButtonClicked();

        /** Performs some actions in response to user's editing xpath. */
        void onEditXPathButtonClicked();

    }

    /** @return content of the condition type field */
    @Nonnull
    public abstract String getConditionType();

    /**
     * Selects an item from list of condition types in the condition type field.
     *
     * @param conditionType
     *         new selected type
     */
    public abstract void selectConditionType(@Nonnull String conditionType);

    /**
     * Changes content of the condition type field.
     *
     * @param conditionTypes
     *         new content of the field
     */
    public abstract void setConditionTypes(@Nullable List<String> conditionTypes);

    /**
     * Changes content of the source field.
     *
     * @param source
     *         new content of the field
     */
    public abstract void setSource(@Nullable String source);

    /** @return content of the regular expression field */
    @Nonnull
    public abstract String getRegularExpression();

    /**
     * Changes content of the regular expression field.
     *
     * @param regularExpression
     *         new content of the field
     */

    public abstract void setRegularExpression(@Nonnull String regularExpression);

    /**
     * Changes content of the xpath field.
     *
     * @param xPath
     *         new content of the field
     */
    public abstract void setXPath(@Nullable String xPath);

    /**
     * Changes visible state of the source panel.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    public abstract void setVisibleSourcePanel(boolean visible);

    /**
     * Changes visible state of the regular expression panel.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    public abstract void setVisibleRegularExpressionPanel(boolean visible);

    /**
     * Changes visible state of the xpath panel.
     *
     * @param visible
     *         <code>true</code> the panel will be shown, <code>false</code> it will not
     */
    public abstract void setVisibleXpathPanel(boolean visible);

}