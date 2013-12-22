/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 *  [2012] - [2013] Codenvy, S.A. 
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.ide.ext.wso2.client.upload.overwrite;

import com.codenvy.ide.annotations.NotNull;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ui.Button;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * The implementation of {@link OverwriteFileView}.
 *
 * @author Valeriy Svydenko
 */
public class OverwriteFileViewImpl extends DialogBox implements OverwriteFileView {
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
    @UiField(provided = true)
    final LocalizationConstant locale;
    @UiField
    HTML message;

    @Inject
    public OverwriteFileViewImpl(OverwriteFileViewImplUiBinder ourUiBinder,
                                 LocalizationConstant locale) {
        this.locale = locale;

        Widget widget = ourUiBinder.createAndBindUi(this);

        this.setText(locale.wso2FileOverwriteTitle());
        this.setWidget(widget);

    }

    @Override
    public String getFileName() {
        return fileName.getText();
    }

    @Override
    public void setFileName(@NotNull String fileName) {
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
    public void setMessage(@NotNull String message) {
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