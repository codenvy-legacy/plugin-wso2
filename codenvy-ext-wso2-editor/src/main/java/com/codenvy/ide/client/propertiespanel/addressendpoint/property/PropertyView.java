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
package com.codenvy.ide.client.propertiespanel.addressendpoint.property;

import com.codenvy.ide.client.elements.addressendpoint.Property;
import com.codenvy.ide.client.mvp.View;
import com.codenvy.ide.collections.Array;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
@ImplementedBy(PropertyViewImpl.class)
public interface PropertyView extends View<PropertyView.ActionDelegate> {

    void setProperties(@Nonnull Array<Property> properties);

    void showDialog();

    void hideDialog();

    interface ActionDelegate {

        void onOkButtonClicked();

        void onCancelButtonClicked();

        void onAddButtonClicked();

        void onEditButtonClicked();

        void onRemoveButtonClicked();

        void onPropertySelected(@Nonnull Property property);

    }

}