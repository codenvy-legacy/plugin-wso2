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
package com.codenvy.ide.client.propertiespanel.property.simple;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.propertiespanel.property.general.AbstractPropertyViewImpl;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class SimplePropertyViewImpl extends AbstractPropertyViewImpl<SimplePropertyView.ActionDelegate> implements SimplePropertyView {

    @Singleton
    interface SimplePropertyViewImplUiBinder extends UiBinder<Widget, SimplePropertyViewImpl> {
    }

    @UiField
    Label   title;
    @UiField
    TextBox property;
    @UiField(provided = true)
    final EditorResources res;

    @Inject
    public SimplePropertyViewImpl(SimplePropertyViewImplUiBinder ourUiBinder, EditorResources resources) {
        super(resources.editorCSS());

        res = resources;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Override
    public void setTitle(@Nullable String title) {
        this.title.setText(title);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getProperty() {
        return property.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setProperty(@Nullable String property) {
        this.property.setText(property);
    }

    @UiHandler("property")
    public void onPropertyChanged(@SuppressWarnings("UnusedParameters") KeyUpEvent event) {
        delegate.onPropertyChanged();
    }

}