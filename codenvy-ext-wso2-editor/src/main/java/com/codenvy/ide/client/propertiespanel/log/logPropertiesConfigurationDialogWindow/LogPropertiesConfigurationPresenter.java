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
package com.codenvy.ide.client.propertiespanel.log.logPropertiesConfigurationDialogWindow;

import com.codenvy.ide.client.elements.Log;
import com.codenvy.ide.client.elements.Log.Property;
import com.codenvy.ide.client.mvp.Presenter;
import com.codenvy.ide.client.propertiespanel.log.logPropertiesConfigurationDialogWindow.nameSpaceEditorDialogWindow.NameSpaceEditorPresenter;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shnurenko
 */
public class LogPropertiesConfigurationPresenter implements Presenter,
                                                            LogPropertiesConfigurationView.ActionDelegate {

    private final LogPropertiesConfigurationView logPropertiesConfigurationView;
    private       Array<Property>                propertyArrayTemporary;
    private       Property                       selectedProperty;
    private final NameSpaceEditorPresenter       nameSpaceEditorPresenter;
    private final Log                            log;

    private final List<Property> list;

    @Inject
    public LogPropertiesConfigurationPresenter(@Nonnull LogPropertiesConfigurationView logPropertiesConfigurationView,
                                               @Nonnull NameSpaceEditorPresenter nameSpaceEditorPresenter,
                                               @Nonnull Log log) {
        this.logPropertiesConfigurationView = logPropertiesConfigurationView;
        this.nameSpaceEditorPresenter = nameSpaceEditorPresenter;
        this.log = log;
        this.propertyArrayTemporary = Collections.createArray();

        this.list = new ArrayList<>();

        logPropertiesConfigurationView.setDelegate(this);
    }

    @Override
    public void go(@Nonnull AcceptsOneWidget container) {

    }

    @Override
    public void onSelectedProperty(Property property) {
        this.selectedProperty = property;
    }

    @Override
    public void onCancelButtonClicked() {
        propertyArrayTemporary.clear();
        list.clear();
        logPropertiesConfigurationView.hidePropertyConfigurationWindow();
    }

    @Override
    public void onAddPropertyButtonClicked() {
        String name;
        String type;
        String expression;

        if (logPropertiesConfigurationView.getNameText().isEmpty() ||
            logPropertiesConfigurationView.getValueExpression().isEmpty()) {
            name = "property_name";
            type = "literal";
            expression = "property_value";
        } else {
            name = logPropertiesConfigurationView.getNameText();
            type = "literal";
            expression = logPropertiesConfigurationView.getValueExpression();
        }

        logPropertiesConfigurationView.setNameText("");
        logPropertiesConfigurationView.setValueExpressionText("");

        Property property = new Property(name, type, expression);

        propertyArrayTemporary.add(property);

        for (Property propertyFromArray : propertyArrayTemporary.asIterable()) {
            list.add(propertyFromArray);
        }
        logPropertiesConfigurationView.setPropertiesList(list);
        list.clear();
    }

    @Override
    public void onRemovePropertyButtonClicked() {
        propertyArrayTemporary.remove(selectedProperty);
        for (Property propertyFromArray : propertyArrayTemporary.asIterable()) {
            list.add(propertyFromArray);
        }
        logPropertiesConfigurationView.setPropertiesList(list);
        list.clear();
    }

    @Override
    public void onOkButtonClicked() {
        log.clearPropertyList();
        log.getLogProperties().addAll(propertyArrayTemporary);
        propertyArrayTemporary.clear();
        list.clear();
        logPropertiesConfigurationView.hidePropertyConfigurationWindow();
    }

    @Override
    public void showNameSpaceEditorWindow() {
        nameSpaceEditorPresenter.showNameSpaceEditorWindow(selectedProperty.getNameSpaces());
    }

    @Override
    public void onEditPropertyButtonClicked() {
        logPropertiesConfigurationView.setNameText(selectedProperty.getName());
        logPropertiesConfigurationView.setValueExpressionText(selectedProperty.getExpression());
        propertyArrayTemporary.remove(selectedProperty);
        for (Property property : propertyArrayTemporary.asIterable()) {
            list.add(property);
        }
        logPropertiesConfigurationView.setPropertiesList(list);
        list.clear();
    }

    @Override
    public void showPropertyConfigurationWindow() {
        for (Property property : log.getLogProperties().asIterable()) {
            list.add(property);
            propertyArrayTemporary.add(property.clone());
        }
        logPropertiesConfigurationView.setPropertiesList(list);
        list.clear();
        logPropertiesConfigurationView.showPropertyConfigurationWindow();
    }
}
