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

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.propertiespanel.property.general.AbstractPropertyPresenter;
import com.codenvy.ide.ui.window.Window;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;

/**
 * Provides a graphical representation of dialog window for editing properties.
 *
 * @author Dmitry Shnurenko
 */
public class AddPropertyViewImpl extends Window implements AddPropertyView {

    @Singleton
    interface EditorAddressPropertyViewImplUiBinder extends UiBinder<Widget, AddPropertyViewImpl> {
    }

    @UiField(provided = true)
    EditorResources res;
    @UiField
    FlowPanel       mainPanel;

    private ActionDelegate delegate;

    @Inject
    public AddPropertyViewImpl(WSO2EditorLocalizationConstant locale,
                               EditorResources res,
                               EditorAddressPropertyViewImplUiBinder uiBinder) {
        this.res = res;

        this.setWidget(uiBinder.createAndBindUi(this));

        Button btnCancel = createButton(locale.buttonCancel(), "args-configuration-cancel", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onCancelButtonClicked();
            }
        });
        getFooter().add(btnCancel);

        Button btnOk = createButton(locale.buttonOk(), "args-configuration-ok", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onOkButtonClicked();
            }
        });
        getFooter().add(btnOk);
    }

    /** {@inheritDoc} */
    @Override
    public void hideWindow() {
        hide();
    }

    /** {@inheritDoc} */
    @Override
    public void showWindow() {
        show();
    }

    /** {@inheritDoc} */
    @Override
    public void addPanel(@Nonnull AbstractPropertyPresenter presenter) {
        mainPanel.add(presenter.getView());
    }

    /** {@inheritDoc} */
    @Override
    public void setDialogHeight(int height) {
        mainPanel.getElement().getStyle().setHeight(height + 60, Style.Unit.PX);
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(@Nonnull ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    protected void onClose() {
        hide();
    }

}