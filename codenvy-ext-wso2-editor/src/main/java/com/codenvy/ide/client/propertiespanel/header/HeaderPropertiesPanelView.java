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
package com.codenvy.ide.client.propertiespanel.header;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * @author Andrey Plotnikov
 */
@ImplementedBy(HeaderPropertiesPanelViewImpl.class)
public abstract class HeaderPropertiesPanelView extends AbstractView<HeaderPropertiesPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        void onHeaderActionChanged();

        void onScopeChanged();

        void onValueTypeChanged();

        void onValueLiteralChanged();

        void onHeaderNameChanged();

    }

    public abstract String getHeaderAction();

    public abstract void selectHeaderAction(String headerAction);

    public abstract void setHeaderAction(List<String> headerAction);

    public abstract String getScope();

    public abstract void selectScope(String scope);

    public abstract void setScope(List<String> scope);

    public abstract String getValueType();

    public abstract void selectValueType(String valueType);

    public abstract void setValueType(List<String> valueType);

    public abstract String getValueLiteral();

    public abstract void setValueLiteral(String valueLiteral);

    public abstract String getHeaderName();

    public abstract void setHeaderName(String headerName);

}