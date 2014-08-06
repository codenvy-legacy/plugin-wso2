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
package com.codenvy.ide.client.propertiespanel.arguments;

import com.codenvy.ide.client.elements.payload.Arg;
import com.codenvy.ide.client.mvp.View;
import com.codenvy.ide.collections.Array;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * The view of {@link ArgumentsConfigPresenter}
 *
 * @author Valeriy Svydenko
 */
@ImplementedBy(ArgumentsConfigViewImpl.class)
public interface ArgumentsConfigView extends View<ArgumentsConfigView.ActionDelegate> {

    /**
     * Sets the list to table on view.
     *
     * @param argsList
     *         list which needs to be displayed
     */
    void setArgs(@Nonnull Array<Arg> argsList);

    /** Shows dialog window for editing property of element. */
    void showWindow();

    /** Hides dialog window. */
    void hideWindow();

    /** @return expression value from the special place on the view which uses for showing expression parameter */
    String getValueExpression();

    /** Sets expression value to the special place on the view which uses for showing expression parameter. */
    void setValueExpression(@Nonnull String text);

    /** @return evaluator value from the special place on the view which uses for showing expression parameter */
    String getEvaluator();

    /** Select evaluator value */
    void selectEvaluator(@Nonnull String text);

    /** Set evaluator value */
    void setEvaluator();

    /** Clear evaluator value */
    void clearEvaluator();

    public interface ActionDelegate {
        /**
         * Performs any actions appropriate in response to the user selected the property.
         *
         * @param property
         *         selected property
         */
        void onSelectedArg(@Nonnull Arg property);

        /** Performs any actions appropriate in response to the user having pressed the Cancel button. */
        void onCancelButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Add button. */
        void onAddArgButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Remove button. */
        void onRemoveArgButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Ok button. */
        void onOkButtonClicked();

        /** Shows the dialog window which needed to be for editing properties of element. */
        void onEditArgsButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Edit button. */
        void onEditButtonClicked();
    }

}
