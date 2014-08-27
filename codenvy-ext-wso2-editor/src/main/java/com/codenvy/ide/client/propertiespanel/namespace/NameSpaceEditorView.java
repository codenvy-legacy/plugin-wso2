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
package com.codenvy.ide.client.propertiespanel.namespace;

import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.mvp.View;
import com.codenvy.ide.collections.Array;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The presentation of Name Space Editor view. It contains a general places for name spaces properties.
 *
 * @author Dmitry Shnurenko
 */
@ImplementedBy(NameSpaceEditorViewImpl.class)
public interface NameSpaceEditorView extends View<NameSpaceEditorView.ActionDelegate> {

    public interface ActionDelegate {

        /** Performs any actions appropriate in response to the user having pressed the Add button. */
        void onAddNameSpaceButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Ok button. */
        void onOkButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Edit button. */
        void onEditButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Remove button. */
        void onRemoveButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Cancel button. */
        void onCancelButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Select XPath button. */
        void onSelectXPathButtonClicked();

        /**
         * Performs any actions appropriate in response to the user selected the element.
         *
         * @param nameSpace
         *         Selected element
         */
        void onSelectedNameSpace(@Nonnull NameSpace nameSpace);
    }

    /** Shows name space dialog window. */
    void showWindow();

    /** Hides name space dialog window */
    void hideWindow();

    /** @return prefix value from the special place on the view which uses for showing prefix */
    @Nonnull
    public String getPrefix();

    /**
     * Set prefix value to the special place on the view which uses for showing prefix.
     *
     * @param text
     *         value of prefix which need set to element
     */
    public void setPrefix(@Nonnull String text);

    /** @return uri value from the special place on the view which uses for showing uri */
    @Nonnull
    public String getUri();

    /**
     * Set uri value to the special place on the view which uses for showing uri.
     *
     * @param text
     *         value of uri which need set to element
     */
    public void setUri(@Nonnull String text);

    /** @return expression value from the special place on the view which uses for showing expression */
    @Nonnull
    public String getExpression();

    /**
     * Set expression value to the special place on the view which uses for showing expression.
     *
     * @param expression
     *         value of expression which need set to element
     */
    public void setExpression(@Nullable String expression);

    /**
     * Sets the list to table on view.
     *
     * @param nameSpaces
     *         namespaces which need to be displayed.
     */
    public void setNameSpaces(@Nonnull Array<NameSpace> nameSpaces);

    /** Set name NameSpace dialog window. */
    public void setNameSpaceLabelName(@Nonnull String nameSpace);
}
