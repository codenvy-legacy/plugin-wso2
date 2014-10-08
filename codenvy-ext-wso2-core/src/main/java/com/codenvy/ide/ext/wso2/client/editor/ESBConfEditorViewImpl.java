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
package com.codenvy.ide.ext.wso2.client.editor;

import com.codenvy.ide.api.editor.AbstractEditorPresenter;
import com.codenvy.ide.api.editor.CodenvyTextEditor;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;

/**
 * The implementation of {@link ESBConfEditorView}.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class ESBConfEditorViewImpl extends Composite implements ESBConfEditorView {

    @Singleton
    interface ESBConfEditorViewImplUiBinder extends UiBinder<Widget, ESBConfEditorViewImpl> {
    }

    @UiField
    SimplePanel          mainPanel;
    @UiField
    Button               textEditorChoose;
    @UiField
    Button               graphicalEditorChoose;
    @UiField
    Button               associateEditorChoose;
    @UiField
    Button               toolbarBtn;
    @UiField(provided = true)
    LocalizationConstant locale;

    private ActionDelegate  delegate;
    private DockLayoutPanel bothEditorPanel;
    private SimplePanel     graphicalEditorPanel;
    private SimplePanel     textEditorPanel;

    @Inject
    public ESBConfEditorViewImpl(ESBConfEditorViewImplUiBinder ourUiBinder, LocalizationConstant locale) {
        this.locale = locale;

        initWidget(ourUiBinder.createAndBindUi(this));

        bothEditorPanel = new DockLayoutPanel(Style.Unit.PCT);
        bothEditorPanel.setSize("100%", "100%");

        graphicalEditorPanel = new SimplePanel();
        textEditorPanel = new SimplePanel();

        bothEditorPanel.addWest(textEditorPanel, 50);
        bothEditorPanel.add(graphicalEditorPanel);
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void setEnableSourceViewButton(boolean enable) {
        textEditorChoose.setEnabled(enable);
    }

    /** {@inheritDoc} */
    @Override
    public void setEnableDesignViewButton(boolean enable) {
        graphicalEditorChoose.setEnabled(enable);
    }

    /** {@inheritDoc} */
    @Override
    public void setEnableDualViewButton(boolean enable) {
        associateEditorChoose.setEnabled(enable);
    }

    /** {@inheritDoc} */
    @Override
    public void showSourceView(@Nonnull CodenvyTextEditor textEditor) {
        textEditor.go(mainPanel);
    }

    /** {@inheritDoc} */
    @Override
    public void showDesignView(@Nonnull AbstractEditorPresenter graphicalEditor) {
        graphicalEditor.go(mainPanel);
    }

    /** {@inheritDoc} */
    @Override
    public void showDualView(@Nonnull AbstractEditorPresenter graphicalEditor, @Nonnull CodenvyTextEditor textEditor) {
        graphicalEditor.go(graphicalEditorPanel);
        textEditor.go(textEditorPanel);

        mainPanel.setWidget(bothEditorPanel);
    }

    @UiHandler("textEditorChoose")
    public void onTextEditorButtonClicked(ClickEvent event) {
        delegate.onSourceViewButtonClicked();
    }

    @UiHandler("graphicalEditorChoose")
    public void onGraphicalEditorButtonClicked(ClickEvent event) {
        delegate.onDesignViewButtonClicked();
    }

    @UiHandler("associateEditorChoose")
    public void onAssociateEditorButtonClicked(ClickEvent event) {
        delegate.onDualViewButtonClicked();
    }

    @UiHandler("toolbarBtn")
    public void onChangeToolbarVisibilityClicked(@SuppressWarnings("UnusedParameters") ClickEvent event){
        delegate.onChangeToolbarVisibilityClicked();
    }

}