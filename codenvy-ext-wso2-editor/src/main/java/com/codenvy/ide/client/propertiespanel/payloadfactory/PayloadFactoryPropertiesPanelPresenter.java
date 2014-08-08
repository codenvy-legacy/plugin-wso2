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
package com.codenvy.ide.client.propertiespanel.payloadfactory;

import com.codenvy.ide.client.elements.payload.Arg;
import com.codenvy.ide.client.elements.payload.PayloadFactory;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.arguments.AddArgumentCallBack;
import com.codenvy.ide.client.propertiespanel.arguments.ArgumentsConfigPresenter;
import com.codenvy.ide.client.propertiespanel.inline.ChangeInlineFormatCallBack;
import com.codenvy.ide.client.propertiespanel.inline.InlineConfigurationPresenter;
import com.codenvy.ide.client.propertiespanel.resourcekeyeditor.ChangeResourceKeyCallBack;
import com.codenvy.ide.client.propertiespanel.resourcekeyeditor.ResourceKeyEditorPresenter;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;

import static com.codenvy.ide.client.elements.payload.PayloadFactory.FormatType;
import static com.codenvy.ide.client.elements.payload.PayloadFactory.FormatType.Inline;
import static com.codenvy.ide.client.elements.payload.PayloadFactory.MediaType;

/**
 * The property panel of PayloadFactory mediator.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class PayloadFactoryPropertiesPanelPresenter extends AbstractPropertiesPanel<PayloadFactory>
        implements PayloadFactoryPropertiesPanelView.ActionDelegate {

    private final ChangeInlineFormatCallBack   changeInlineFormatCallBack;
    private final ChangeResourceKeyCallBack    changeResourceKeyCallBack;
    private final AddArgumentCallBack          argumentCallBack;
    private final InlineConfigurationPresenter inlineConfigurationPresenter;
    private final ResourceKeyEditorPresenter   resourceKeyEditorPresenter;
    private final ArgumentsConfigPresenter     argumentsConfigPresenter;

    @Inject
    public PayloadFactoryPropertiesPanelPresenter(PayloadFactoryPropertiesPanelView view,
                                                  PropertyTypeManager propertyTypeManager,
                                                  InlineConfigurationPresenter inlineConfigurationPresenter,
                                                  ResourceKeyEditorPresenter resourceKeyEditorPresenter,
                                                  ArgumentsConfigPresenter argumentsConfigPresenter) {
        super(view, propertyTypeManager);

        this.inlineConfigurationPresenter = inlineConfigurationPresenter;
        this.resourceKeyEditorPresenter = resourceKeyEditorPresenter;
        this.argumentsConfigPresenter = argumentsConfigPresenter;

        this.changeInlineFormatCallBack = new ChangeInlineFormatCallBack() {
            @Override
            public void onInlineChanged(@Nonnull String inline) {
                element.setFormat(inline);

                ((PayloadFactoryPropertiesPanelView)PayloadFactoryPropertiesPanelPresenter.this.view).setFormat(inline);

                notifyListeners();
            }
        };

        this.changeResourceKeyCallBack = new ChangeResourceKeyCallBack() {
            @Override
            public void onFormatKeyChanged(@Nonnull String key) {
                element.setFormatKey(key);

                ((PayloadFactoryPropertiesPanelView)PayloadFactoryPropertiesPanelPresenter.this.view).setFormatKey(key);

                notifyListeners();
            }
        };

        this.argumentCallBack = new AddArgumentCallBack() {
            @Override
            public void onArgumentsChanged(@Nonnull Array<Arg> arg) {
                element.setArgs(arg);

                ((PayloadFactoryPropertiesPanelView)PayloadFactoryPropertiesPanelPresenter.this.view).setArgs("Payload Factory Arguments");

                notifyListeners();
            }
        };

    }

    /** {@inheritDoc} */
    @Override
    public void onPayloadFormatChanged() {
        String payloadFormat = ((PayloadFactoryPropertiesPanelView)view).getPayloadFormat();

        element.setPayloadFormat(payloadFormat);

        boolean isInline = Inline.name().equals(payloadFormat);

        ((PayloadFactoryPropertiesPanelView)view).setVisibleFormatPanel(isInline);
        ((PayloadFactoryPropertiesPanelView)view).setVisibleFormatKeyPanel(!isInline);

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFormatChanged() {
        element.setFormat(((PayloadFactoryPropertiesPanelView)view).getFormat());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onMediaTypeChanged() {
        element.setMediaType(((PayloadFactoryPropertiesPanelView)view).getMediaType());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {
        element.setDescription(((PayloadFactoryPropertiesPanelView)view).getDescription());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void showFormatConfigurationWindow(@Nonnull String content, @Nonnull String title) {
        inlineConfigurationPresenter.showDialog(content, title, changeInlineFormatCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void showArgsConfigurationWindow() {
        argumentsConfigPresenter.showConfigWindow(element.getArgs(), argumentCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void showKeyEditorWindow(@NotNull String key) {
        resourceKeyEditorPresenter.showDialog(key, changeResourceKeyCallBack);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        ((PayloadFactoryPropertiesPanelView)view).setPayloadFormat(propertyTypeManager.getValuesOfTypeByName(FormatType.TYPE_NAME));
        ((PayloadFactoryPropertiesPanelView)view).selectPayloadFormat(element.getPayloadFormat());
        ((PayloadFactoryPropertiesPanelView)view).setFormat(element.getFormat());
        ((PayloadFactoryPropertiesPanelView)view).setFormatKey(element.getFormatKey());
        ((PayloadFactoryPropertiesPanelView)view).setMediaType(propertyTypeManager.getValuesOfTypeByName(MediaType.TYPE_NAME));
        ((PayloadFactoryPropertiesPanelView)view).selectMediaType(element.getMediaType());
        ((PayloadFactoryPropertiesPanelView)view).setDescription(element.getDescription());

        boolean isInline = Inline.name().equals(((PayloadFactoryPropertiesPanelView)view).getPayloadFormat());

        ((PayloadFactoryPropertiesPanelView)view).setVisibleFormatPanel(isInline);
        ((PayloadFactoryPropertiesPanelView)view).setVisibleFormatKeyPanel(!isInline);
        ((PayloadFactoryPropertiesPanelView)view).setArgs(element.getArgs().isEmpty() ? "" : "Payload Factory Arguments");
    }

}