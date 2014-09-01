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
package com.codenvy.ide.ext.wso2.client.wizard.files.view;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;

/**
 * Provides a graphical representation of dialog window for creating resource.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class CreateResourceViewImpl extends Composite implements CreateResourceView {

    @Singleton
    interface CreateResourceViewImplUiBinder extends UiBinder<Widget, CreateResourceViewImpl> {
    }

    @UiField
    Label   resourceNameTitle;
    @UiField
    TextBox resourceName;
    private ActionDelegate delegate;

    @Inject
    public CreateResourceViewImpl(CreateResourceViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void setResourceNameTitle(@Nonnull String title) {
        resourceNameTitle.setText(title);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getResourceName() {
        return resourceName.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setResourceName(@Nonnull String name) {
        resourceName.setText(name);
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("resourceName")
    public void onValueChanged(KeyUpEvent event) {
        delegate.onValueChanged();
    }
}