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
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.enrich.Enrich;
import com.codenvy.ide.client.elements.mediators.enrich.Source;
import com.codenvy.ide.client.elements.mediators.enrich.Target;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
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

import static com.codenvy.ide.client.elements.mediators.enrich.Enrich.DESCRIPTION;
import static com.codenvy.ide.client.elements.mediators.enrich.Enrich.SOURCE;
import static com.codenvy.ide.client.elements.mediators.enrich.Enrich.TARGET;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.InlineType;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SOURCE_CLONE;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SOURCE_INLINE_REGISTER_KEY;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SOURCE_INLINE_TYPE;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SOURCE_NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SOURCE_PROPERTY;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SOURCE_TYPE;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SOURCE_XML;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SOURCE_XPATH;
import static com.codenvy.ide.client.elements.mediators.enrich.Source.SourceType;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TARGET_ACTION;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TARGET_NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TARGET_PROPERTY;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TARGET_TYPE;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TARGET_XPATH;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetAction;
import static com.codenvy.ide.client.elements.mediators.enrich.Target.TargetType;
import static com.codenvy.ide.client.initializers.propertytype.CommonPropertyTypeInitializer.BOOLEAN_TYPE_NAME;
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Call mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class EnrichPropertiesPanelPresenter extends AbstractPropertiesPanel<Enrich> {

    private final NameSpaceEditorPresenter     nameSpacePresenter;
    private final ResourceKeyEditorPresenter   keyPresenter;
    private final InlineConfigurationPresenter inlinePresenter;

    private final AddNameSpacesCallBack      sourceXPathCallBack;
    private final AddNameSpacesCallBack      targetXPathCallBack;
    private final ChangeInlineFormatCallBack sourceInlineCallback;
    private final ChangeResourceKeyCallBack  sourceKeyCallBack;

    private SimplePropertyPresenter description;

    private ListPropertyPresenter    sourceClone;
    private ListPropertyPresenter    sourceType;
    private SimplePropertyPresenter  sourceProperty;
    private ListPropertyPresenter    sourceInlineType;
    private ComplexPropertyPresenter sourceXPath;
    private ComplexPropertyPresenter sourceXml;

    private ComplexPropertyPresenter sourceInlineRegistryKey;
    private ListPropertyPresenter    targetAction;
    private ListPropertyPresenter    targetType;
    private SimplePropertyPresenter  targetProperty;
    private ComplexPropertyPresenter targetXpath;

    private Source source;
    private Target target;

    @Inject
    public EnrichPropertiesPanelPresenter(PropertiesPanelView view,
                                          PropertyTypeManager propertyTypeManager,
                                          NameSpaceEditorPresenter nameSpacePresenter,
                                          ResourceKeyEditorPresenter keyPresenter,
                                          InlineConfigurationPresenter inlinePresenter,
                                          WSO2EditorLocalizationConstant locale,
                                          PropertyPanelFactory propertyPanelFactory) {

        super(view, propertyTypeManager, locale, propertyPanelFactory);

        this.keyPresenter = keyPresenter;
        this.inlinePresenter = inlinePresenter;
        this.nameSpacePresenter = nameSpacePresenter;

        this.sourceXPathCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                source.putProperty(SOURCE_XPATH, expression);
                source.putProperty(SOURCE_NAMESPACES, nameSpaces);

                sourceXPath.setProperty(expression);

                notifyListeners();
            }
        };

        this.targetXPathCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                target.putProperty(TARGET_XPATH, expression);
                target.putProperty(TARGET_NAMESPACES, nameSpaces);

                targetXpath.setProperty(expression);

                notifyListeners();
            }
        };

        this.sourceInlineCallback = new ChangeInlineFormatCallBack() {
            @Override
            public void onInlineChanged(@Nonnull String inline) {
                source.putProperty(SOURCE_XML, inline);

                sourceXml.setProperty(inline);

                notifyListeners();
            }
        };

        this.sourceKeyCallBack = new ChangeResourceKeyCallBack() {
            @Override
            public void onFormatKeyChanged(@Nonnull String key) {
                source.putProperty(SOURCE_INLINE_REGISTER_KEY, key);

                sourceInlineRegistryKey.setProperty(key);

                notifyListeners();
            }
        };

        prepareView();
    }

    private void prepareView() {
        prepareMiscGroupView();

        prepareSourceGroupView();

        prepareTargetGroupView();
    }

    private void prepareMiscGroupView() {
        PropertyGroupPresenter miscGroup = createGroup(locale.enrichGroupMisc());

        PropertyValueChangedListener descriptionListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                element.putProperty(DESCRIPTION, property);

                notifyListeners();
            }
        };
        description = createSimpleProperty(miscGroup, locale.description(), descriptionListener);
    }

    private void prepareSourceGroupView() {
        PropertyGroupPresenter sourceGroup = createGroup(locale.enrichGroupSource());

        PropertyValueChangedListener sourceCloneListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                source.putProperty(SOURCE_CLONE, Boolean.valueOf(property));

                notifyListeners();
            }
        };
        sourceClone = createListProperty(sourceGroup, locale.enrichCloneSource(), sourceCloneListener);

        PropertyValueChangedListener sourceTypeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                source.putProperty(SOURCE_TYPE, SourceType.getItemByValue(property));

                applySourceType();

                notifyListeners();
            }
        };
        sourceType = createListProperty(sourceGroup, locale.enrichSourceType(), sourceTypeListener);

        PropertyValueChangedListener sourcePropertyListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                source.putProperty(SOURCE_PROPERTY, property);

                notifyListeners();
            }
        };
        sourceProperty = createSimpleProperty(sourceGroup, locale.enrichSourceProperty(), sourcePropertyListener);

        PropertyValueChangedListener inlineTypeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                source.putProperty(SOURCE_INLINE_TYPE, InlineType.getItemByValue(property));

                applySourceInlineType();

                notifyListeners();
            }
        };
        sourceInlineType = createListProperty(sourceGroup, locale.enrichSourceInlineType(), inlineTypeListener);

        EditButtonClickedListener xmlBtnListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                String sourceXml = source.getProperty(SOURCE_XML);

                if (sourceXml == null) {
                    return;
                }

                inlinePresenter.showDialog(sourceXml, locale.titleEnrichInline(), sourceInlineCallback);
            }
        };
        sourceXml = createComplexProperty(sourceGroup, locale.enrichSourceXml(), xmlBtnListener);

        EditButtonClickedListener xpathBtnListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                String sourceXPath = source.getProperty(SOURCE_XPATH);
                List<NameSpace> nameSpaces = source.getProperty(SOURCE_NAMESPACES);

                if (sourceXPath == null || nameSpaces == null) {
                    return;
                }

                nameSpacePresenter.showWindowWithParameters(nameSpaces, sourceXPathCallBack, locale.enrichSrcLabel(), sourceXPath);
            }
        };
        sourceXPath = createComplexProperty(sourceGroup, locale.enrichSourceXPath(), xpathBtnListener);

        EditButtonClickedListener registryKeyBtnListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                String sourceKey = source.getProperty(SOURCE_INLINE_REGISTER_KEY);

                if (sourceKey == null) {
                    return;
                }

                keyPresenter.showDialog(sourceKey, sourceKeyCallBack);
            }
        };
        sourceInlineRegistryKey = createComplexProperty(sourceGroup, locale.enrichInlineRegistryKey(), registryKeyBtnListener);
    }

    private void prepareTargetGroupView() {
        PropertyGroupPresenter targetGroup = createGroup(locale.enrichGroupTarget());

        PropertyValueChangedListener actionListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                target.putProperty(TARGET_ACTION, TargetAction.getItemByValue(property));

                notifyListeners();
            }
        };
        targetAction = createListProperty(targetGroup, locale.enrichTargetAction(), actionListener);

        PropertyValueChangedListener typeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                target.putProperty(TARGET_TYPE, TargetType.getItemByValue(property));

                applyTargetType();

                notifyListeners();
            }
        };
        targetType = createListProperty(targetGroup, locale.enrichTargetType(), typeListener);

        PropertyValueChangedListener targetPropertyListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                target.putProperty(TARGET_PROPERTY, property);

                notifyListeners();
            }
        };
        targetProperty = createSimpleProperty(targetGroup, locale.enrichTargetProperty(), targetPropertyListener);

        EditButtonClickedListener targetXpathBtnListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<NameSpace> targetNamespaces = target.getProperty(TARGET_NAMESPACES);
                String targetXPath = target.getProperty(TARGET_XPATH);

                if (targetNamespaces == null || targetXPath == null) {
                    return;
                }

                nameSpacePresenter
                        .showWindowWithParameters(targetNamespaces, targetXPathCallBack, locale.namespacedProperty(), targetXPath);
            }
        };
        targetXpath = createComplexProperty(targetGroup, locale.enrichTargetXPath(), targetXpathBtnListener);
    }

    /** Sets source type value to element from special place of view and displaying properties panel to a certain value of source type */
    private void applySourceType() {
        SourceType type = source.getProperty(SOURCE_TYPE);

        if (type == null) {
            return;
        }

        setDefaultSourcePanelView();

        sourceInlineRegistryKey.setVisible(false);

        switch (type) {
            case CUSTOM:
                applyCustomType();
                break;

            case PROPERTY:
                applyPropertyType();
                break;

            case INLINE:
                sourceXPath.setVisible(false);
                sourceProperty.setVisible(false);
                break;

            default:
                applyDefaultSourceType();
        }
    }

    private void applyCustomType() {
        sourceProperty.setVisible(false);
        sourceInlineType.setVisible(false);
        sourceXml.setVisible(false);
    }

    private void applyPropertyType() {
        sourceXPath.setVisible(false);
        sourceInlineType.setVisible(false);
        sourceXml.setVisible(false);
    }

    private void applyDefaultSourceType() {
        sourceXPath.setVisible(false);
        sourceProperty.setVisible(false);
        sourceInlineType.setVisible(false);
        sourceXml.setVisible(false);
    }

    /**
     * Sets source inline type value to element from special place of view and displaying properties panel to a certain value of source
     * inline type
     */
    private void applySourceInlineType() {
        InlineType inlineType = source.getProperty(SOURCE_INLINE_TYPE);

        if (inlineType == null) {
            return;
        }

        setDefaultSourcePanelView();

        sourceXPath.setVisible(false);
        sourceProperty.setVisible(false);

        boolean isSourceXMLInlineType = InlineType.SOURCE_XML.equals(inlineType);

        sourceXml.setVisible(isSourceXMLInlineType);
        sourceInlineRegistryKey.setVisible(!isSourceXMLInlineType);
    }

    /** Sets default value of source panel visibility */
    private void setDefaultSourcePanelView() {
        sourceProperty.setVisible(true);
        sourceInlineType.setVisible(true);
        sourceXml.setVisible(true);
        sourceXPath.setVisible(true);
        sourceInlineRegistryKey.setVisible(true);
    }

    /** Sets target type value to element from special place of view and displaying properties panel to a certain value of target type */
    private void applyTargetType() {
        TargetType targetType = target.getProperty(TARGET_TYPE);

        if (targetType == null) {
            return;
        }

        setDefaultTargetPanelView();

        switch (targetType) {
            case CUSTOM:
                targetProperty.setVisible(false);
                targetXpath.setVisible(true);
                break;

            case PROPERTY:
                targetProperty.setVisible(true);
                targetXpath.setVisible(false);
                break;

            default:
                targetProperty.setVisible(false);
                targetXpath.setVisible(false);
        }
    }

    /** Sets default value of target panel visibility */
    private void setDefaultTargetPanelView() {
        targetProperty.setVisible(true);
        targetXpath.setVisible(true);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        source = element.getProperty(SOURCE);
        target = element.getProperty(TARGET);

        if (source == null || target == null) {
            return;
        }

        description.setProperty(element.getProperty(DESCRIPTION));

        displaySourceGroup();

        displayTargetGroup();
    }

    private void displaySourceGroup() {
        InlineType inlineType = source.getProperty(SOURCE_INLINE_TYPE);
        SourceType sourceTypeValue = source.getProperty(SOURCE_TYPE);
        Boolean sourceCloneValue = source.getProperty(SOURCE_CLONE);

        if (inlineType == null || sourceTypeValue == null || sourceCloneValue == null) {
            return;
        }

        sourceClone.setValues(propertyTypeManager.getValuesByName(BOOLEAN_TYPE_NAME));
        sourceClone.selectValue(sourceCloneValue.toString());

        sourceType.setValues(propertyTypeManager.getValuesByName(SourceType.TYPE_NAME));
        sourceType.selectValue(sourceTypeValue.getValue());

        sourceProperty.setProperty(source.getProperty(SOURCE_PROPERTY));

        sourceInlineType.setValues(propertyTypeManager.getValuesByName(InlineType.TYPE_NAME));
        sourceInlineType.selectValue(inlineType.getValue());

        sourceXml.setProperty(source.getProperty(SOURCE_XML));

        sourceXPath.setProperty(source.getProperty(SOURCE_XPATH));

        sourceInlineRegistryKey.setProperty(source.getProperty(SOURCE_INLINE_REGISTER_KEY));

        if (SourceType.INLINE.equals(sourceTypeValue)) {
            applySourceInlineType();
        }

        applySourceType();
    }

    private void displayTargetGroup() {
        TargetAction targetActionValue = target.getProperty(TARGET_ACTION);
        TargetType targetTypeValue = target.getProperty(TARGET_TYPE);

        if (targetActionValue == null || targetTypeValue == null) {
            return;
        }

        targetAction.setValues(propertyTypeManager.getValuesByName(TargetAction.TYPE_NAME));
        targetAction.selectValue(targetActionValue.getValue());

        targetType.setValues(propertyTypeManager.getValuesByName(TargetType.TYPE_NAME));
        targetType.selectValue(targetTypeValue.getValue());

        targetProperty.setProperty(target.getProperty(TARGET_PROPERTY));

        targetXpath.setProperty(target.getProperty(TARGET_XPATH));

        applyTargetType();
    }

}