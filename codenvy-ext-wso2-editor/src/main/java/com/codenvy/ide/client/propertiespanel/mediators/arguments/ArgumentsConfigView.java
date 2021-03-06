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
package com.codenvy.ide.client.propertiespanel.mediators.arguments;

import com.codenvy.ide.api.mvp.View;
import com.codenvy.ide.client.elements.mediators.payload.Arg;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * The abstract view's representation of dialog window for editing arguments parameter of element. It provides an ability to show
 * and change all available properties of mediator's argument parameter.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 * @author Andrey Plotnikov
 */
@ImplementedBy(ArgumentsConfigViewImpl.class)
public interface ArgumentsConfigView extends View<ArgumentsConfigView.ActionDelegate> {

    /**
     * Sets the list to table on view.
     *
     * @param argsList
     *         list which needs to be displayed
     */
    void setArgs(@Nonnull List<Arg> argsList);

    /** Shows dialog window for editing property of element. */
    void showWindow();

    /** Hides dialog window. */
    void hideWindow();

    /**
     * Interface defines methods of {@link ArgumentsConfigPresenter} which calls from view. These methods defines
     * some actions when user click the button on dialog window for adding, removing or editing arguments.
     */
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

        /** Performs any actions appropriate in response to the user having pressed the Edit button. */
        void onEditButtonClicked();
    }

}
