/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package com.codenvy.ide.client.propertiespanel.switchmediator;

import com.codenvy.ide.client.mvp.AbstractView;
import com.codenvy.ide.client.propertiespanel.switchmediator.branch.BranchFiledPresenter;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The abstract view's representation of 'Switch' mediator properties panel. It provides an ability to show all available properties of
 * the mediator and edit it.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(SwitchPropertiesPanelViewImpl.class)
public abstract class SwitchPropertiesPanelView extends AbstractView<SwitchPropertiesPanelView.ActionDelegate> {

    /**
     * Interface defines methods of {@link SwitchPropertiesPanelPresenter} which calls from view. These methods defines
     * some actions when user changes properties of Switch mediator.
     */
    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs some actions in response to user's editing xpath. */
        void onEditXpathButtonClicked();

        /** Performs some actions in response to widget is being detached. */
        void onWidgetDetached();

    }

    /**
     * Changes content of the source xpath field.
     *
     * @param sourceXpath
     *         new content of the field
     */
    public abstract void setSourceXpath(@Nullable String sourceXpath);

    /**
     * Adds branch regexp field on the view.
     *
     * @param branchFiled
     *         field that needs to be added
     */
    public abstract void addBranchField(@Nonnull BranchFiledPresenter branchFiled);

    /** Removes all regexp fields. */
    public abstract void removeBranchFields();

}