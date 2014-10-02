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
package com.codenvy.ide.client.propertiespanel.mediators;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.mediators.payload.Arg;
import com.codenvy.ide.client.elements.mediators.payload.Format;
import com.codenvy.ide.client.elements.mediators.payload.PayloadFactory;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.codenvy.ide.client.propertiespanel.mediators.arguments.AddArgumentCallBack;
import com.codenvy.ide.client.propertiespanel.mediators.arguments.ArgumentsConfigPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.inline.ChangeInlineFormatCallBack;
import com.codenvy.ide.client.propertiespanel.mediators.inline.InlineConfigurationPresenter;
import com.codenvy.ide.client.propertiespanel.mediators.resourcekeyeditor.ChangeResourceKeyCallBack;
import com.codenvy.ide.client.propertiespanel.mediators.resourcekeyeditor.ResourceKeyEditorPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.simple.SimplePropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.payload.Format.FORMAT_INLINE;
import static com.codenvy.ide.client.elements.mediators.payload.Format.FORMAT_KEY;
import static com.codenvy.ide.client.elements.mediators.payload.Format.FORMAT_TYPE;
import static com.codenvy.ide.client.elements.mediators.payload.Format.FormatType;
import static com.codenvy.ide.client.elements.mediators.payload.PayloadFactory.ARGS;
import static com.codenvy.ide.client.elements.mediators.payload.PayloadFactory.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.payload.PayloadFactory.FORMAT;
import static com.codenvy.ide.client.elements.mediators.payload.PayloadFactory.MEDIA_TYPE;
import static com.codenvy.ide.client.elements.mediators.payload.PayloadFactory.MediaType;
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of PayloadFactory mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class PayloadFactoryPropertiesPanelPresenter extends AbstractPropertiesPanel<PayloadFactory> {

    private final ChangeInlineFormatCallBack changeInlineFormatCallBack;
    private final ChangeResourceKeyCallBack  changeResourceKeyCallBack;
    private final AddArgumentCallBack        argumentCallBack;

    private final InlineConfigurationPresenter inlineConfigurationPresenter;
    private final ResourceKeyEditorPresenter   resourceKeyEditorPresenter;
    private final ArgumentsConfigPresenter     argumentsConfigPresenter;

    private ListPropertyPresenter    payloadFormat;
    private ComplexPropertyPresenter formatInline;
    private ComplexPropertyPresenter formatKey;
    private ComplexPropertyPresenter args;
    private ListPropertyPresenter    mediaType;
    private SimplePropertyPresenter  description;

    private Format format;

    @Inject
    public PayloadFactoryPropertiesPanelPresenter(PropertiesPanelView view,
                                                  PropertyTypeManager propertyTypeManager,
                                                  InlineConfigurationPresenter inlineConfigurationPresenter,
                                                  ResourceKeyEditorPresenter resourceKeyEditorPresenter,
                                                  ArgumentsConfigPresenter argumentsConfigPresenter,
                                                  final WSO2EditorLocalizationConstant locale,
                                                  PropertyPanelFactory propertyPanelFactory) {

        super(view, propertyTypeManager, locale, propertyPanelFactory);

        this.inlineConfigurationPresenter = inlineConfigurationPresenter;
        this.resourceKeyEditorPresenter = resourceKeyEditorPresenter;
        this.argumentsConfigPresenter = argumentsConfigPresenter;

        this.changeInlineFormatCallBack = new ChangeInlineFormatCallBack() {
            @Override
            public void onInlineChanged(@Nonnull String inline) {
                format.putProperty(FORMAT_INLINE, inline);

                formatInline.setProperty(inline);

                notifyListeners();
            }
        };

        this.changeResourceKeyCallBack = new ChangeResourceKeyCallBack() {
            @Override
            public void onFormatKeyChanged(@Nonnull String key) {
                format.putProperty(FORMAT_KEY, key);

                formatKey.setProperty(key);

                notifyListeners();
            }
        };

        this.argumentCallBack = new AddArgumentCallBack() {
            @Override
            public void onArgumentsChanged(@Nonnull List<Arg> argsArray) {
                element.putProperty(ARGS, argsArray);

                args.setProperty(argsArray.isEmpty() ? "" : locale.payloadFactoryArguments());

                notifyListeners();
            }
        };

        prepareView();
    }

    private void prepareView() {
        PropertyGroupPresenter basicGroup = createGroup(locale.miscGroupTitle());

        PropertyValueChangedListener formatTypeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                format.putProperty(FORMAT_TYPE, FormatType.getItemByValue(property));

                applyPayloadFormat();

                notifyListeners();
            }
        };
        payloadFormat = createListProperty(basicGroup, locale.payloadFormat(), formatTypeListener);

        EditButtonClickedListener formatInlineBtnListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                String inline = format.getProperty(FORMAT_INLINE);

                if (inline == null) {
                    return;
                }

                inlineConfigurationPresenter.showDialog(inline, locale.inlineTitle(), changeInlineFormatCallBack);
            }
        };
        formatInline = createComplexProperty(basicGroup, locale.format(), formatInlineBtnListener);

        EditButtonClickedListener formatKeyBtnListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                String key = format.getProperty(FORMAT_KEY);

                if (key == null) {
                    return;
                }

                resourceKeyEditorPresenter.showDialog(key, changeResourceKeyCallBack);
            }
        };
        formatKey = createComplexProperty(basicGroup, locale.payloadFormatKey(), formatKeyBtnListener);

        EditButtonClickedListener argsPropertyBtnListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<Arg> args = element.getProperty(ARGS);

                if (args == null) {
                    return;
                }

                argumentsConfigPresenter.showConfigWindow(args, argumentCallBack);
            }
        };
        args = createComplexProperty(basicGroup, locale.payloadArgs(), argsPropertyBtnListener);

        PropertyValueChangedListener mediaTypeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(MEDIA_TYPE, MediaType.getItemByValue(property));

                notifyListeners();
            }
        };
        mediaType = createListProperty(basicGroup, locale.payloadMediaType(), mediaTypeListener);

        PropertyValueChangedListener descriptionListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(DESCRIPTION, property);

                notifyListeners();
            }
        };
        description = createSimpleProperty(basicGroup, locale.description(), descriptionListener);
    }

    /**
     * Sets payload format value to element from special place of view and displaying properties panel to a certain value of payload
     * format
     */
    private void applyPayloadFormat() {
        FormatType payloadFormat = format.getProperty(FORMAT_TYPE);

        boolean isInlineType = FormatType.INLINE.equals(payloadFormat);

        formatInline.setVisible(isInlineType);
        formatKey.setVisible(!isInlineType);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);
        format = element.getProperty(FORMAT);

        if (format == null) {
            return;
        }

        FormatType formatType = format.getProperty(FORMAT_TYPE);
        MediaType formatMediaType = element.getProperty(MEDIA_TYPE);

        if (formatType == null || formatMediaType == null) {
            return;
        }

        payloadFormat.setValues(propertyTypeManager.getValuesByName(FormatType.TYPE_NAME));
        payloadFormat.selectValue(formatType.getValue());

        formatInline.setProperty(format.getProperty(FORMAT_INLINE));

        formatKey.setProperty(format.getProperty(FORMAT_KEY));

        List<Arg> argsArray = element.getProperty(ARGS);

        if (argsArray != null && !argsArray.isEmpty()) {
            args.setProperty(locale.payloadFactoryArguments());
        }

        mediaType.setValues(propertyTypeManager.getValuesByName(MediaType.TYPE_NAME));
        mediaType.selectValue(formatMediaType.getValue());

        description.setProperty(element.getProperty(DESCRIPTION));

        applyPayloadFormat();
    }

}