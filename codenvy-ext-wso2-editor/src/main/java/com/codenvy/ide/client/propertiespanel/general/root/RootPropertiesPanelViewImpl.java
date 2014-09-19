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
package com.codenvy.ide.client.propertiespanel.general.root;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.mvp.AbstractView;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;

/**
 * Provides a graphical representation of 'Root' property panel for editing property of 'Root' element.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class RootPropertiesPanelViewImpl extends AbstractView<RootPropertiesPanelView.ActionDelegate> implements RootPropertiesPanelView {

    @Singleton
    interface RootPropertiesPanelViewImplUiBinder extends UiBinder<Widget, RootPropertiesPanelViewImpl> {
    }

    @UiField
    TextBox name;
    @UiField
    TextBox onError;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;

    @Inject
    public RootPropertiesPanelViewImpl(RootPropertiesPanelViewImplUiBinder ourUiBinder,
                                       EditorResources res,
                                       WSO2EditorLocalizationConstant locale) {
        this.res = res;
        this.locale = locale;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getName() {
        return name.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setName(@Nonnull String name) {
        this.name.setText(name);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getOnError() {
        return onError.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setOnError(@Nonnull String onError) {
        this.onError.setText(onError);
    }

    @UiHandler("name")
    public void onNameChanged(KeyUpEvent event) {
        delegate.onNameChanged();
    }

    @UiHandler("onError")
    public void onOnErrorChanged(KeyUpEvent event) {
        delegate.onOnErrorChanged();
    }

}