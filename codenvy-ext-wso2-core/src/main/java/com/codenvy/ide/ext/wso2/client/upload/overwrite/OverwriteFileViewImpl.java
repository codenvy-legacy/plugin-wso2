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
import com.codenvy.ide.ui.window.Window;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
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
 * @author Andrey Plotnikov
 */
public class OverwriteFileViewImpl extends Window implements OverwriteFileView {

    @Singleton
    interface OverwriteFileViewImplUiBinder extends UiBinder<Widget, OverwriteFileViewImpl> {
    }

    @UiField
    TextBox fileName;
    @UiField
    HTML    message;

    @UiField(provided = true)
    final LocalizationConstant locale;
    @UiField(provided = true)
    final WSO2Resources        res;

    private final Button         btnRename;
    private       ActionDelegate delegate;

    @Inject
    public OverwriteFileViewImpl(OverwriteFileViewImplUiBinder ourUiBinder,
                                 LocalizationConstant locale,
                                 WSO2Resources res) {
        this.locale = locale;
        this.res = res;

        this.setTitle(locale.wso2FileOverwriteTitle());
        this.setWidget(ourUiBinder.createAndBindUi(this));

        Button btnCancel = createButton(locale.wso2ButtonCancel(), "esb-conf-file-cancel", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onCancelButtonClicked();
            }
        });
        getFooter().add(btnCancel);

        btnRename = createButton(locale.wso2ButtonRename(), "esb-conf-file-rename", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onRenameButtonClicked();
            }
        });
        getFooter().add(btnRename);

        Button btnOverwrite = createButton(locale.wso2ButtonOverwrite(), "esb-conf-file-overwrite", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onOverwriteButtonClicked();
            }
        });
        getFooter().add(btnOverwrite);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getFileName() {
        return fileName.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setFileName(@Nonnull @NotNull String fileName) {
        this.fileName.setText(fileName);
    }

    /** {@inheritDoc} */
    @Override
    public void close() {
        hide();
    }

    /** {@inheritDoc} */
    @Override
    public void showDialog() {
        show();
    }

    /** {@inheritDoc} */
    @Override
    protected void onClose() {
        hide();
    }

    /** {@inheritDoc} */
    @Override
    public void setEnabledRenameButton(boolean enabled) {
        btnRename.setEnabled(enabled);
    }

    /** {@inheritDoc} */
    @Override
    public void setMessage(@Nonnull @NotNull String message) {
        this.message.setHTML(message);
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(ActionDelegate actionDelegate) {
        this.delegate = actionDelegate;
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("fileName")
    public void onFileNameChanged(KeyUpEvent event) {
        delegate.onNameChanged();
    }

}