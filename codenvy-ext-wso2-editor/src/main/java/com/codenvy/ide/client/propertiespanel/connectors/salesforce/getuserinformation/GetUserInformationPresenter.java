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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.getuserinformation;

import com.codenvy.ide.client.elements.connectors.salesforce.GeneralPropertyManager;
import com.codenvy.ide.client.elements.connectors.salesforce.GetUserInformation;
import com.codenvy.ide.client.managers.PropertyTypeManager;
import com.codenvy.ide.client.propertiespanel.connectors.base.GeneralConnectorPanelPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.base.GeneralConnectorPanelView;
import com.codenvy.ide.client.propertiespanel.connectors.base.parameter.ParameterPresenter;
import com.google.inject.Inject;

/**
 * The class provides the business logic that allows editor to react on user's action and to change state of GetUserInformation connector
 * depending on user's changes of properties.
 *
 * @author Dmitry Shnurenko
 */
public class GetUserInformationPresenter extends GeneralConnectorPanelPresenter<GetUserInformation> {

    @Inject
    public GetUserInformationPresenter(GeneralConnectorPanelView generalView,
                                       ParameterPresenter parameterPresenter,
                                       GeneralPropertyManager generalPropertyManager,
                                       PropertyTypeManager propertyTypeManager) {

        super(generalView, generalPropertyManager, parameterPresenter, propertyTypeManager);

    }

}
