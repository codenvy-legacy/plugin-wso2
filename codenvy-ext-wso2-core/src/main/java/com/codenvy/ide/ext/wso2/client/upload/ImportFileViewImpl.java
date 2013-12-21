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
package com.codenvy.ide.ext.wso2.client.upload;

import com.codenvy.ide.annotations.NotNull;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ui.Button;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * The implementation of {@link ImportFileView}.
 *
 * @author Valeriy Svydenko
 */
public class ImportFileViewImpl extends DialogBox implements ImportFileView {
    interface ImportFileViewImplUiBinder extends UiBinder<Widget, ImportFileViewImpl> {
    }

    private ActionDelegate delegate;
    @UiField
    Button    btnCancel;
    @UiField
    Button    btnImport;
    @UiField
    FormPanel uploadForm;
    @UiField
    TextBox   url;
    @UiField(provided = true)
    final LocalizationConstant locale;
    @UiField
    RadioButton useUrl;
    @UiField
    RadioButton useLocalPath;
    @UiField
    HTML        message;
    FileUpload file;

    @Inject
    public ImportFileViewImpl(ImportFileViewImplUiBinder ourUiBinder,
                              LocalizationConstant locale) {
        this.locale = locale;

        Widget widget = ourUiBinder.createAndBindUi(this);

        this.setText(locale.wso2ImportDialogTitle());
        this.setWidget(widget);

        bind();
    }

    /** Bind handlers. */
    private void bind() {
        uploadForm.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                delegate.onSubmitComplete(event.getResults());
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public String getUrl() {
        return url.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setUrl(@NotNull String url) {
        this.url.setText(url);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isUseUrl() {
        return useUrl.getValue();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isUseLocalPath() {
        return useLocalPath.getValue();
    }

    /** {@inheritDoc} */
    @Override
    public void setUseLocalPath(boolean isUseLocalPath) {
        useLocalPath.setValue(isUseLocalPath);
    }

    @Override
    public void setUseUrl(boolean isUseUrl) {
        useUrl.setValue(isUseUrl);
    }

    /** {@inheritDoc} */
    @Override
    public String getFileName() {
        String fileName = file.getFilename();
        if (fileName.indexOf("\\") > 0) {
            return fileName.substring(fileName.lastIndexOf('\\') + 1, fileName.length());
        } else {
            return fileName;
        }
    }

    @Override
    public void setMessage(@NotNull String message) {
        this.message.setHTML(message);
    }

    /** {@inheritDoc} */
    @Override
    public void setEnabledImportButton(boolean enabled) {
        btnImport.setEnabled(enabled);
    }

    /** {@inheritDoc} */
    @Override
    public void setEnterUrlFieldEnabled(boolean enabled) {
        url.setEnabled(enabled);
    }

    /** {@inheritDoc} */
    @Override
    public void setAction(@NotNull String url) {
        uploadForm.setAction(url);
    }

    /** {@inheritDoc} */
    @Override
    public void submit() {
        uploadForm.submit();
    }

    /** {@inheritDoc} */
    @Override
    public void close() {
        this.hide();

        uploadForm.remove(file);
        file = null;

        url.setText("");
    }

    /** {@inheritDoc} */
    @Override
    public void showDialog() {
        uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
        uploadForm.setMethod(FormPanel.METHOD_POST);

        VerticalPanel panel = new VerticalPanel();
        uploadForm.setWidget(panel);

        file = new FileUpload();
        file.setName("ImportFile");
        file.setHeight("26px");
        file.setWidth("100%");
        file.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                if (!file.getFilename().endsWith(".xml")) {
                    delegate.onFileNameChangedWithInvalidFormat();
                } else {
                    delegate.onFileNameChanged();
                }
            }
        });
        panel.add(file);
        this.center();
        this.show();
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(ActionDelegate actionDelegate) {
        this.delegate = actionDelegate;
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("btnCancel")
    public void onCancelClicked(ClickEvent event) {
        delegate.onCancelClicked();
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("btnImport")
    public void onImportClicked(ClickEvent event) {
        delegate.onImportClicked();
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("useUrl")
    public void onUseUrlClicked(ClickEvent event) {
        file.setEnabled(false);
        delegate.onUseUrlChosen();
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("useLocalPath")
    public void onUseLocalPathClicked(ClickEvent event) {
        file.setEnabled(true);
        delegate.onUseLocalPathChosen();
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("url")
    public void onUrlChanged(KeyUpEvent event) {
        delegate.onUrlChanged();
    }
}