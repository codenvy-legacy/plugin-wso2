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
package com.codenvy.ide.client.propertiespanel.common.addpropertydialog.general;

import com.codenvy.ide.api.mvp.View;
import com.codenvy.ide.client.propertiespanel.property.general.AbstractPropertyPresenter;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * The presentation of Editor Address Property view. It contains a general places for properties of address endpoint.
 *
 * @author Dmitry Shnurenko
 */
@ImplementedBy(AddPropertyViewImpl.class)
public interface AddPropertyView extends View<AddPropertyView.ActionDelegate> {

    /** Hides dialog window. */
    void hideWindow();

    /** Shows dialog window. */
    void showWindow();

    /**
     * Sets title to dialog window.
     *
     * @param title
     *         value of title which need set
     */
    void setTitle(@Nonnull String title);

    /**
     * Method adds panel to main panel.
     *
     * @param presenter
     *         list of panels which need add to main panel
     */
    void addPanel(@Nonnull AbstractPropertyPresenter presenter);

    /**
     * Methods sets height of dialog window.
     *
     * @param height
     *         size of height in "px"
     */
    void setDialogHeight(int height);

    /**
     * Interface defines methods of EditorAddressProperty's presenter which calls from view. These methods defines
     * some actions when user click the button or change value of parameters in list box.
     */
    public interface ActionDelegate {

        /** Performs any actions appropriate in response to the user clicked ok button. */
        void onOkButtonClicked();

        /** Performs any actions appropriate in response to the user clicked cancel button. */
        void onCancelButtonClicked();
    }

}