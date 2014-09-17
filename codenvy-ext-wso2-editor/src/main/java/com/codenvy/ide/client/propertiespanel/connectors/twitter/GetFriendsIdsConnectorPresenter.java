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
package com.codenvy.ide.client.propertiespanel.connectors.twitter;

import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.twitter.GetFriendsIds;
import com.codenvy.ide.client.elements.connectors.twitter.TwitterPropertyManager;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.common.namespace.NameSpaceEditorPresenter;
import com.codenvy.ide.client.propertiespanel.common.propertyconfig.AddNameSpacesCallBack;
import com.codenvy.ide.client.propertiespanel.connectors.base.AbstractConnectorPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.GeneralPropertiesPanelView;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.codenvy.ide.collections.Array;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 */
public class GetFriendsIdsConnectorPresenter extends AbstractConnectorPropertiesPanelPresenter<GetFriendsIds> {

    private final WSO2EditorLocalizationConstant locale;
    private final NameSpaceEditorPresenter       nameSpacePresenter;
    private final AddNameSpacesCallBack          screenNameCallBack;
    private final AddNameSpacesCallBack          userIdCallBack;
    private final AddNameSpacesCallBack          cursorCallBack;

    @Inject
    public GetFriendsIdsConnectorPresenter(WSO2EditorLocalizationConstant locale,
                                           NameSpaceEditorPresenter nameSpacePresenter,
                                           GeneralPropertiesPanelView view,
                                           TwitterPropertyManager twitterPropertyManager,
                                           ParameterPresenter parameterPresenter,
                                           PropertyTypeManager propertyTypeManager) {
        super(view, twitterPropertyManager, parameterPresenter, propertyTypeManager);

        this.locale = locale;

        this.nameSpacePresenter = nameSpacePresenter;

        this.screenNameCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setScreenNameNS(nameSpaces);
                element.setScreenNameExpr(expression);

                GetFriendsIdsConnectorPresenter.this.view.setFirstTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.userIdCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setUserIdNS(nameSpaces);
                element.setUserIdExpr(expression);

                GetFriendsIdsConnectorPresenter.this.view.setSecondTextBoxValue(expression);

                notifyListeners();
            }
        };

        this.cursorCallBack = new AddNameSpacesCallBack() {
            @Override
            public void onNameSpacesChanged(@Nonnull Array<NameSpace> nameSpaces, @Nonnull String expression) {
                element.setCursorNS(nameSpaces);
                element.setCursorExpr(expression);

                GetFriendsIdsConnectorPresenter.this.view.setThirdTextBoxValue(expression);

                notifyListeners();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void onParameterEditorTypeChanged() {
        redrawPropertiesPanel();

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstTextBoxValueChanged() {
        element.setScreenName(view.getFirstTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondTextBoxValueChanged() {
        element.setUserId(view.getSecondTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdTextBoxValueChanged() {
        element.setCursor(view.getThirdTextBoxValue());

        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onFirstButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getScreenNameNS(),
                                                    screenNameCallBack,
                                                    locale.connectorExpression(),
                                                    element.getScreenNameExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onSecondButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getUserIdNS(),
                                                    userIdCallBack,
                                                    locale.connectorExpression(),
                                                    element.getUserIdExpr());
    }

    /** {@inheritDoc} */
    @Override
    public void onThirdButtonClicked() {
        nameSpacePresenter.showWindowWithParameters(element.getCursorNS(),
                                                    cursorCallBack,
                                                    locale.connectorExpression(),
                                                    element.getCursorExpr());
    }

    /** {@inheritDoc} */
    @Override
    protected void redrawPropertiesPanel() {
        ParameterEditorType editorType = ParameterEditorType.valueOf(view.getParameterEditorType());
        element.setParameterEditorType(editorType);

        boolean isEquals = NamespacedPropertyEditor.equals(editorType);

        view.setVisibleFirstButton(isEquals);
        view.setVisibleSecondButton(isEquals);
        view.setVisibleThirdButton(isEquals);

        view.setEnableFirstTextBox(!isEquals);
        view.setEnableSecondTextBox(!isEquals);
        view.setEnableThirdTextBox(!isEquals);

        view.setFirstTextBoxValue(isEquals ? element.getScreenNameExpr() : element.getScreenName());
        view.setSecondTextBoxValue(isEquals ? element.getUserIdExpr() : element.getUserId());
        view.setThirdTextBoxValue(isEquals ? element.getCursorExpr() : element.getCursor());
    }

    private void redesignViewToCurrentConnector() {
        view.setVisibleFirstPanel(true);
        view.setVisibleSecondPanel(true);
        view.setVisibleThirdPanel(true);

        view.setFirstLabelTitle(locale.twitterScreenName());
        view.setSecondLabelTitle(locale.twitterUserId());
        view.setThirdLabelTitle(locale.twitterCursor());
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        redesignViewToCurrentConnector();
    }
}
