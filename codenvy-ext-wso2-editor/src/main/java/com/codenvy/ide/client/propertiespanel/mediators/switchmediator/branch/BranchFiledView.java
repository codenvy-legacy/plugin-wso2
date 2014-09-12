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
package com.codenvy.ide.client.propertiespanel.mediators.switchmediator.branch;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The abstract view that represents the regexp property of case branch visual part of the widget.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(BranchFiledViewImpl.class)
public abstract class BranchFiledView extends AbstractView<BranchFiledView.ActionDelegate> {

    /**
     * Changes title on the field.
     *
     * @param title
     *         title that needs to be set
     */
    public abstract void setTitle(@Nonnull String title);

    /**
     * Changes content of the regexp field.
     *
     * @param regExp
     *         new content of the field
     */
    public abstract void setRegExp(@Nullable String regExp);

    /** @return content of the referring type field */
    @Nonnull
    public abstract String getRegExp();

    public interface ActionDelegate extends AbstractView.ActionDelegate {
        /** Performs some actions in response to user's changing regexp field. */
        void onRegExpChanged();
    }

}
