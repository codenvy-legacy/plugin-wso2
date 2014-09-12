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
package com.codenvy.ide.client.propertiespanel.mediators.log;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.mediators.log.Log;
import com.codenvy.ide.client.elements.mediators.log.Property;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.common.namespace.AddPropertyCallback;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.PropertyConfigPresenter;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.mediators.log.Log.LogCategory;
import static com.codenvy.ide.client.elements.mediators.log.Log.LogLevel;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of Log mediator
 * depending on user's changes of properties.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class LogPropertiesPanelPresenter extends AbstractPropertiesPanel<Log, LogPropertiesPanelView>
        implements LogPropertiesPanelView.ActionDelegate {

    private final PropertyConfigPresenter        propertyConfigPresenter;
    private final AddPropertyCallback            addPropertyCallback;
    private final WSO2EditorLocalizationConstant local;

    @Inject
    public LogPropertiesPanelPresenter(LogPropertiesPanelView view,
                                       PropertyTypeManager propertyTypeManager,
                                       PropertyConfigPresenter propertyConfigPresenter,
                                       WSO2EditorLocalizationConstant local) {
        super(view, propertyTypeManager);

        this.propertyConfigPresenter = propertyConfigPresenter;
        this.local = local;

        this.addPropertyCallback = new AddPropertyCallback() {
            @Override
            public void onPropertiesChanged(@Nonnull Array<Property> properties) {
                element.setLogProperties(properties);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onLogCategoryChanged() {
        element.setLogCategory(LogCategory.valueOf(view.getLogCategory()));

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onLogLevelChanged() {
        element.setLogLevel(LogLevel.valueOf(view.getLogLevel()));

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onLogSeparatorChanged() {
        element.setLogSeparator(view.getLogSeparator());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onDescriptionChanged() {
        element.setDescription(view.getDescription());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onEditButtonClicked() {
        propertyConfigPresenter.showConfigWindow(element.getLogProperties(),
                                                 local.configurationTitle(),
                                                 addPropertyCallback);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        view.setLogCategories(propertyTypeManager.getValuesByName(LogCategory.TYPE_NAME));
        view.selectLogCategory(element.getLogCategory().name());

        view.setLogLevels(propertyTypeManager.getValuesByName(LogLevel.TYPE_NAME));
        view.selectLogLevel(element.getLogLevel().name());

        view.setLogSeparator(element.getLogSeparator());
        view.setDescription(element.getDescription());
    }

}