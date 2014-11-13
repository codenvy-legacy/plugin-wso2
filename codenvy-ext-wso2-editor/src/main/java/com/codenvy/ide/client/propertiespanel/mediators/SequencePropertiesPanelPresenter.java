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
import com.codenvy.ide.client.elements.mediators.Sequence;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.managers.SelectionManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.PropertyPanelFactory;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.mediators.resourcekeyeditor.ChangeResourceKeyCallBack;
import com.codenvy.ide.client.propertiespanel.mediators.resourcekeyeditor.ResourceKeyEditorPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyValueChangedListener;
import com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter;
import com.codenvy.ide.client.propertiespanel.property.group.PropertyGroupPresenter;
import com.codenvy.ide.client.propertiespanel.property.list.ListPropertyPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.Sequence.DYNAMIC_REFERENCE_TYPE;
import static com.codenvy.ide.client.elements.mediators.Sequence.NAMESPACES;
import static com.codenvy.ide.client.elements.mediators.Sequence.REFERRING_TYPE;
import static com.codenvy.ide.client.elements.mediators.Sequence.ReferringType;
import static com.codenvy.ide.client.elements.mediators.Sequence.ReferringType.STATIC;
import static com.codenvy.ide.client.elements.mediators.Sequence.STATIC_REFERENCE_TYPE;
import static com.codenvy.ide.client.propertiespanel.property.complex.ComplexPropertyPresenter.EditButtonClickedListener;

/**
 * The presenter that provides a business logic of 'Sequence' mediator properties panel. It provides an ability to work with all properties
 * of 'Sequence' mediator.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class SequencePropertiesPanelPresenter extends AbstractPropertiesPanel<Sequence> {
    private final ResourceKeyEditorPresenter keyPresenter;
    private final NameSpaceEditorPresenter   nameSpaceEditorPresenter;

    private final ChangeResourceKeyCallBack keyCallBack;
    private final AddNameSpacesCallBack     addNameSpacesCallBack;

    private ListPropertyPresenter    referringType;
    private ComplexPropertyPresenter staticReferenceKey;
    private ComplexPropertyPresenter dynamicReferenceKey;

    @Inject
    public SequencePropertiesPanelPresenter(PropertiesPanelView view,
                                            PropertyTypeManager propertyTypeManager,
                                            ResourceKeyEditorPresenter keyPresenter,
                                            NameSpaceEditorPresenter nameSpaceEditorPresenter,
                                            WSO2EditorLocalizationConstant locale,
                                            PropertyPanelFactory propertyPanelFactory,
                                            SelectionManager selectionManager) {

        super(view, propertyTypeManager, locale, propertyPanelFactory, selectionManager);

        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;
        this.keyPresenter = keyPresenter;

        keyCallBack = new ChangeResourceKeyCallBack() {
            @Override
            public void onFormatKeyChanged(@Nonnull String key) {
                element.putProperty(STATIC_REFERENCE_TYPE, key);

                staticReferenceKey.setProperty(key);

                notifyListeners();
            }
        };

        addNameSpacesCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull List<NameSpace> nameSpaces, @Nonnull String expression) {
                element.putProperty(NAMESPACES, nameSpaces);
                element.putProperty(DYNAMIC_REFERENCE_TYPE, expression);

                dynamicReferenceKey.setProperty(expression);

                notifyListeners();
            }
        };

        prepareView();
    }

    private void prepareView() {
        PropertyGroupPresenter basicGroup = createGroup(locale.miscGroupTitle());

        PropertyValueChangedListener refferingTypeListener = new PropertyValueChangedListener() {
            @Override
            public void onPropertyChanged(@Nonnull String property) {
                ReferringType referringType = ReferringType.getItemByValue(property);
                redrawPropertiesPanel(referringType);

                notifyListeners();
            }
        };
        referringType = createListProperty(basicGroup, locale.referringType(), refferingTypeListener);

        EditButtonClickedListener staticRefKeyListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                String expression = element.getProperty(STATIC_REFERENCE_TYPE);
                if (expression != null) {
                    keyPresenter.showDialog(expression, keyCallBack);
                }

                notifyListeners();
            }
        };
        staticReferenceKey = createComplexProperty(basicGroup, locale.staticReferenceKey(), staticRefKeyListener);

        EditButtonClickedListener dynamicRefKeyListener = new EditButtonClickedListener() {
            @Override
            public void onEditButtonClicked() {
                List<NameSpace> nameSpaces = element.getProperty(NAMESPACES);
                String dynamicRefKey = element.getProperty(DYNAMIC_REFERENCE_TYPE);

                if (dynamicRefKey == null || nameSpaces == null) {
                    return;
                }

                nameSpaceEditorPresenter.showWindowWithParameters(nameSpaces,
                                                                  addNameSpacesCallBack,
                                                                  locale.sequenceExpressionTitle(),
                                                                  dynamicRefKey);
            }
        };
        dynamicReferenceKey = createComplexProperty(basicGroup, locale.dynamicReferenceKey(), dynamicRefKeyListener);
    }

    private void redrawPropertiesPanel(@Nonnull ReferringType property) {
        element.putProperty(REFERRING_TYPE, property);

        boolean isStatic = STATIC.equals(property);

        staticReferenceKey.setVisible(isStatic);
        dynamicReferenceKey.setVisible(!isStatic);

        staticReferenceKey.setProperty(element.getProperty(STATIC_REFERENCE_TYPE));
        dynamicReferenceKey.setProperty(element.getProperty(DYNAMIC_REFERENCE_TYPE));
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        referringType.setValues(propertyTypeManager.getValuesByName(ReferringType.TYPE_NAME));

        ReferringType refType = element.getProperty(REFERRING_TYPE);
        if (refType != null) {
            referringType.selectValue(refType.getValue());
            redrawPropertiesPanel(refType);
        }
    }

}