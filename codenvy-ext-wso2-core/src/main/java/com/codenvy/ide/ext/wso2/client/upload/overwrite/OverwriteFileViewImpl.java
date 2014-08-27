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
package com.codenvy.ide.ext.wso2.client.upload.overwrite;

import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;

/**
 * The implementation of {@link OverwriteFileView}.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class OverwriteFileViewImpl extends DialogBox implements OverwriteFileView {

    @Singleton
    interface OverwriteFileViewImplUiBinder extends UiBinder<Widget, OverwriteFileViewImpl> {
    }

    private ActionDelegate delegate;
    @UiField
    Button  btnCancel;
    @UiField
    Button  btnRename;
    @UiField
    Button  btnOverwrite;
    @UiField
    TextBox fileName;
    @UiField
    HTML    message;

    @UiField(provided = true)
    final LocalizationConstant locale;
    @UiField(provided = true)
    final WSO2Resources        res;

    @Inject
    public OverwriteFileViewImpl(OverwriteFileViewImplUiBinder ourUiBinder,
                                 LocalizationConstant locale,
                                 WSO2Resources res) {
        this.locale = locale;
        this.res = res;

        Widget widget = ourUiBinder.createAndBindUi(this);

        this.setText(locale.wso2FileOverwriteTitle());
        this.setWidget(widget);

    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getFileName() {
        return fileName.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setFileName(@Nonnull String fileName) {
        this.fileName.setText(fileName);
    }

    /** {@inheritDoc} */
    @Override
    public void close() {
        this.hide();
    }

    /** {@inheritDoc} */
    @Override
    public void showDialog() {
        this.center();
        this.show();
    }

    /** {@inheritDoc} */
    @Override
    public void setEnabledRenameButton(boolean enabled) {
        btnRename.setEnabled(enabled);
    }

    @Override
    public void setMessage(@Nonnull String message) {
        this.message.setHTML(message);
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(ActionDelegate actionDelegate) {
        this.delegate = actionDelegate;
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("btnCancel")
    public void onCancelButtonClicked(ClickEvent event) {
        delegate.onCancelButtonClicked();
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("btnRename")
    public void onRenameButtonClicked(ClickEvent event) {
        delegate.onRenameButtonClicked();
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("btnOverwrite")
    public void onOverwriteButtonClicked(ClickEvent event) {
        delegate.onOverwriteButtonClicked();
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("fileName")
    public void onFileNameChanged(KeyUpEvent event) {
        delegate.onNameChanged();
    }

}