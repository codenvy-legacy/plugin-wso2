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
package com.codenvy.ide.ext.wso2.client.upload;

import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ui.window.Window;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;

import static com.google.gwt.user.client.ui.FormPanel.ENCODING_MULTIPART;
import static com.google.gwt.user.client.ui.FormPanel.METHOD_POST;

/**
 * The implementation of {@link ImportFileView}.
 *
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 */
public class ImportFileViewImpl extends Window implements ImportFileView {

    @Singleton
    interface ImportFileViewImplUiBinder extends UiBinder<Widget, ImportFileViewImpl> {
    }

    @UiField
    FormPanel   uploadForm;
    @UiField
    TextBox     url;
    @UiField
    RadioButton useUrl;
    @UiField
    RadioButton useLocalPath;
    @UiField
    HTML        message;

    @UiField(provided = true)
    final WSO2Resources        res;
    @UiField(provided = true)
    final LocalizationConstant locale;

    private final Button         btnImport;
    private       FileUpload     file;
    private       ActionDelegate delegate;

    @Inject
    public ImportFileViewImpl(ImportFileViewImplUiBinder ourUiBinder,
                              LocalizationConstant locale,
                              WSO2Resources res) {
        this.locale = locale;
        this.res = res;

        this.setTitle(locale.wso2ImportDialogTitle());
        this.setWidget(ourUiBinder.createAndBindUi(this));

        Button btnCancel = createButton(locale.wso2ButtonCancel(), "esb-conf-import-cancel", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onCancelClicked();
            }
        });
        getFooter().add(btnCancel);

        btnImport = createButton(locale.wso2ButtonImport(), "esb-conf-import", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onImportClicked();
            }
        });
        getFooter().add(btnImport);

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
    @Nonnull
    @Override
    public String getUrl() {
        return url.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setUrl(@Nonnull String url) {
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

    /** {@inheritDoc} */
    @Override
    public void setUseUrl(boolean isUseUrl) {
        useUrl.setValue(isUseUrl);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getFileName() {
        String fileName = file.getFilename();
        if (fileName.indexOf("\\") > 0) {
            return fileName.substring(fileName.lastIndexOf('\\') + 1, fileName.length());
        } else {
            return fileName;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setMessage(@Nonnull String message) {
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
    public void setAction(@Nonnull String url) {
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
        hide();

        uploadForm.remove(file);
        file = null;

        url.setText("");
    }

    /** {@inheritDoc} */
    @Override
    public void showDialog() {
        uploadForm.setEncoding(ENCODING_MULTIPART);
        uploadForm.setMethod(METHOD_POST);

        file = new FileUpload();
        file.setName("file");
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

        uploadForm.setWidget(file);

        show();
    }

    /** {@inheritDoc} */
    @Override
    protected void onClose() {
        hide();
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(ActionDelegate actionDelegate) {
        this.delegate = actionDelegate;
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