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
package com.codenvy.ide.client.propertiespanel.property.list;

import com.codenvy.ide.client.EditorResources;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Andrey Plotnikov
 */
public class ListPropertyViewImpl extends ListPropertyView {

    @Singleton
    interface ListPropertyViewImplUiBinder extends UiBinder<Widget, ListPropertyViewImpl> {
    }

    @UiField
    Label   title;
    @UiField
    ListBox property;

    @UiField(provided = true)
    final EditorResources res;

    @Inject
    public ListPropertyViewImpl(ListPropertyViewImplUiBinder ourUiBinder, EditorResources resources) {
        this.res = resources;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Override
    public void setTitle(@Nullable String title) {
        this.title.setText(title);
    }

    /** {@inheritDoc} */
    @Override
    public void setPropertyValues(@Nullable List<String> values) {
        setTypes(property, values);
    }

    /** {@inheritDoc} */
    @Override
    public void selectPropertyValue(@Nullable String value) {
        selectType(property, value);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getProperty() {
        return getSelectedItem(property);
    }

    @UiHandler("property")
    public void onPropertyChanged(ChangeEvent event) {
        delegate.onPropertyChanged();
    }

}