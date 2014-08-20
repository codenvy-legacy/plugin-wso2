/*
 * Copyright [2014] Codenvy, S.A.
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
package com.codenvy.ide.client.propertiespanel.resourcekeyeditor;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.ui.window.Window;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;

/**
 * The implementation of {@link ResourceKeyEditorView}
 *
 * @author Valeriy Svydenko
 */
public class ResourceKeyEditorViewImpl extends Window implements ResourceKeyEditorView {

    @Singleton
    interface ResourceKeyEditorViewUiBinder extends UiBinder<Widget, ResourceKeyEditorViewImpl> {
    }

    @UiField
    TextBox keyTextBox;

    @UiField(provided = true)
    final EditorResources                res;
    @UiField(provided = true)
    final WSO2EditorLocalizationConstant locale;

    private ActionDelegate delegate;

    @Inject
    public ResourceKeyEditorViewImpl(ResourceKeyEditorViewUiBinder uiBinder,
                                     WSO2EditorLocalizationConstant locale,
                                     EditorResources res) {
        this.locale = locale;
        this.res = res;

        this.setWidget(uiBinder.createAndBindUi(this));
        this.setTitle(locale.resourceKeyEditorTitle());

        Button btnCancel = createButton(locale.buttonCancel(), "resource-key-cancel", new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                delegate.onCancelClicked();
            }
        });
        getFooter().add(btnCancel);

        Button btnOk = createButton(locale.buttonOk(), "resource-key-ok", new ClickHandler() {

            /** {@inheritDoc} */
            @Override
            public void onClick(ClickEvent event) {
                delegate.onOkClicked(keyTextBox.getText());
            }
        });
        getFooter().add(btnOk);

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

    /** {@inheritDoc} */
    @Override
    public void showDialog(@Nonnull String content) {
        keyTextBox.setText(content);

        show();
    }

    /** {@inheritDoc} */
    @Override
    public void closeDialog() {
        hide();
    }

}