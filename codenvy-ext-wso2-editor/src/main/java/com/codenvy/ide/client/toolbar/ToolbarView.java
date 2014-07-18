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
package com.codenvy.ide.client.toolbar;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * @author Andrey Plotnikov
 */
@ImplementedBy(ToolbarViewImpl.class)
public abstract class ToolbarView extends AbstractView<ToolbarView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        void onLogButtonClicked();

        void onPropertyButtonClicked();

        void onPayloadFactoryButtonClicked();

        void onSendButtonClicked();

        void onHeaderButtonClicked();

        void onRespondButtonClicked();

        void onFilterButtonClicked();

        void onSwitch_mediatorButtonClicked();

        void onSequenceButtonClicked();

        void onEnrichButtonClicked();

        void onLoopBackButtonClicked();

        void onCallTemplateButtonClicked();

        void onCallButtonClicked();

        void onConnectionButtonClicked();

    }

    public abstract void showButtons(@Nonnull Set<String> components);

}