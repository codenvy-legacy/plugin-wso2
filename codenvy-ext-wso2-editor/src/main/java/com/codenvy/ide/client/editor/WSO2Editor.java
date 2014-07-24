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
package com.codenvy.ide.client.editor;

import com.codenvy.ide.client.EditorState;
import com.codenvy.ide.client.SelectionManager;
import com.codenvy.ide.client.State;
import com.codenvy.ide.client.MetaModelValidator;
import com.codenvy.ide.client.elements.Call;
import com.codenvy.ide.client.elements.CallTemplate;
import com.codenvy.ide.client.elements.Connection;
import com.codenvy.ide.client.elements.Enrich;
import com.codenvy.ide.client.elements.Filter;
import com.codenvy.ide.client.elements.Header;
import com.codenvy.ide.client.elements.Log;
import com.codenvy.ide.client.elements.LoopBack;
import com.codenvy.ide.client.elements.PayloadFactory;
import com.codenvy.ide.client.elements.Property;
import com.codenvy.ide.client.elements.Respond;
import com.codenvy.ide.client.elements.Send;
import com.codenvy.ide.client.elements.Sequence;
import com.codenvy.ide.client.elements.Switch_mediator;
import com.codenvy.ide.client.inject.EditorFactory;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertiespanel.PropertiesPanelManager;
import com.codenvy.ide.client.propertiespanel.call.CallPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.calltemplate.CallTemplatePropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.empty.EmptyPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.enrich.EnrichPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.filter.FilterPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.header.HeaderPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.log.LogPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.loopback.LoopBackPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.payloadfactory.PayloadFactoryPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.property.PropertyPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.respond.RespondPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.send.SendPropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.sequence.SequencePropertiesPanelPresenter;
import com.codenvy.ide.client.propertiespanel.switch_mediator.Switch_mediatorPropertiesPanelPresenter;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.codenvy.ide.client.toolbar.ToolbarPresenter;
import com.codenvy.ide.client.workspace.WorkspacePresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.State.CREATING_NOTHING;

/**
 * @author Andrey Plotnikov
 */
public class WSO2Editor extends AbstractPresenter implements WorkspacePresenter.DiagramChangeListener,
                                                             AbstractPropertiesPanel.PropertyChangedListener,
                                                             WSO2EditorView.ActionDelegate {

    private       WorkspacePresenter         workspace;
    private       ToolbarPresenter           toolbar;
    private final List<EditorChangeListener> listeners;

    @Inject
    public WSO2Editor(WSO2EditorView view,
                      EditorFactory editorFactory,
                      SelectionManager selectionManager,
                      MetaModelValidator metaModelValidator,
                      EmptyPropertiesPanelPresenter emptyPropertiesPanelPresenter,
                      LogPropertiesPanelPresenter logPropertiesPanelPresenter,
                      PropertyPropertiesPanelPresenter propertyPropertiesPanelPresenter,
                      PayloadFactoryPropertiesPanelPresenter payloadFactoryPropertiesPanelPresenter,
                      SendPropertiesPanelPresenter sendPropertiesPanelPresenter,
                      HeaderPropertiesPanelPresenter headerPropertiesPanelPresenter,
                      RespondPropertiesPanelPresenter respondPropertiesPanelPresenter,
                      FilterPropertiesPanelPresenter filterPropertiesPanelPresenter,
                      Switch_mediatorPropertiesPanelPresenter switch_mediatorPropertiesPanelPresenter,
                      SequencePropertiesPanelPresenter sequencePropertiesPanelPresenter,
                      EnrichPropertiesPanelPresenter enrichPropertiesPanelPresenter,
                      LoopBackPropertiesPanelPresenter loopBackPropertiesPanelPresenter,
                      CallTemplatePropertiesPanelPresenter callTemplatePropertiesPanelPresenter,
                      CallPropertiesPanelPresenter callPropertiesPanelPresenter,
                      PropertyTypeManager propertyTypeManager) {
        super(view);

        listeners = new ArrayList<>();

        EditorState<State> state = new EditorState<>(CREATING_NOTHING);

        this.workspace = editorFactory.createWorkspace(state, selectionManager);
        this.toolbar = editorFactory.createToolbar(state);

        PropertiesPanelManager propertiesPanelManager = editorFactory.createPropertiesPanelManager(view.getPropertiesPanel());

        propertiesPanelManager.register(Log.class, logPropertiesPanelPresenter);
        logPropertiesPanelPresenter.addListener(this);

        propertiesPanelManager.register(Property.class, propertyPropertiesPanelPresenter);
        propertyPropertiesPanelPresenter.addListener(this);

        propertiesPanelManager.register(PayloadFactory.class, payloadFactoryPropertiesPanelPresenter);
        payloadFactoryPropertiesPanelPresenter.addListener(this);

        propertiesPanelManager.register(Send.class, sendPropertiesPanelPresenter);
        sendPropertiesPanelPresenter.addListener(this);

        propertiesPanelManager.register(Header.class, headerPropertiesPanelPresenter);
        headerPropertiesPanelPresenter.addListener(this);

        propertiesPanelManager.register(Respond.class, respondPropertiesPanelPresenter);
        respondPropertiesPanelPresenter.addListener(this);

        propertiesPanelManager.register(Filter.class, filterPropertiesPanelPresenter);
        filterPropertiesPanelPresenter.addListener(this);

        propertiesPanelManager.register(Switch_mediator.class, switch_mediatorPropertiesPanelPresenter);
        switch_mediatorPropertiesPanelPresenter.addListener(this);

        propertiesPanelManager.register(Sequence.class, sequencePropertiesPanelPresenter);
        sequencePropertiesPanelPresenter.addListener(this);

        propertiesPanelManager.register(Enrich.class, enrichPropertiesPanelPresenter);
        enrichPropertiesPanelPresenter.addListener(this);

        propertiesPanelManager.register(LoopBack.class, loopBackPropertiesPanelPresenter);
        loopBackPropertiesPanelPresenter.addListener(this);

        propertiesPanelManager.register(CallTemplate.class, callTemplatePropertiesPanelPresenter);
        callTemplatePropertiesPanelPresenter.addListener(this);

        propertiesPanelManager.register(Call.class, callPropertiesPanelPresenter);
        callPropertiesPanelPresenter.addListener(this);

        propertiesPanelManager.register(null, emptyPropertiesPanelPresenter);
        emptyPropertiesPanelPresenter.addListener(this);

        propertyTypeManager.register("CallMediatorEndpointType", Arrays.asList("INLINE", "NONE", "REGISTRYKEY", "XPATH"));
        propertyTypeManager.register("LogCategory", Arrays.asList("TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL"));
        propertyTypeManager.register("LogLevel", Arrays.asList("SIMPLE", "HEADERS", "FULL", "CUSTOM"));
        propertyTypeManager.register("PropertyValueType", Arrays.asList("LITERAL", "EXPRESSION"));
        propertyTypeManager.register("PropertyScope", Arrays.asList("Synapse", "transport", "axis2", "axis2-client", "operation"));
        propertyTypeManager.register("PropertyAction", Arrays.asList("set", "remove"));
        propertyTypeManager.register("MediaType", Arrays.asList("xml", "json"));
        propertyTypeManager.register("PayloadFormatType", Arrays.asList("Inline", "Registry"));
        propertyTypeManager.register("EBoolean", Arrays.asList("true", "false"));
        propertyTypeManager.register("ReceivingSequenceType", Arrays.asList("Default", "Static"));
        propertyTypeManager.register("HeaderAction", Arrays.asList("set", "remove"));
        propertyTypeManager.register("HeaderValueType", Arrays.asList("LITERAL", "EXPRESSION", "INLINE"));
        propertyTypeManager.register("ScopeType", Arrays.asList("Synapse", "transport"));
        propertyTypeManager.register("FilterConditionType", Arrays.asList("SOURCE_AND_REGEX", "XPATH"));
        propertyTypeManager.register("ReceivingSequenceType", Arrays.asList("Static", "Dynamic"));
        propertyTypeManager.register("EnrichSourceType", Arrays.asList("custom", "envelope", "body", "property", "inline"));
        propertyTypeManager.register("EnrichTargetAction", Arrays.asList("replace", "child", "sibling"));
        propertyTypeManager.register("EnrichTargetType", Arrays.asList("custom", "envelope", "body", "property"));

        metaModelValidator.register(Switch_mediator.ELEMENT_NAME, Connection.CONNECTION_NAME, Arrays.asList(Log.ELEMENT_NAME,
                                                                                                                Property.ELEMENT_NAME,
                                                                                                                PayloadFactory.ELEMENT_NAME,
                                                                                                                Send.ELEMENT_NAME,
                                                                                                                Header.ELEMENT_NAME,
                                                                                                                Respond.ELEMENT_NAME,
                                                                                                                Filter.ELEMENT_NAME,
                                                                                                                Switch_mediator.ELEMENT_NAME,
                                                                                                                Sequence.ELEMENT_NAME,
                                                                                                                Enrich.ELEMENT_NAME,
                                                                                                                LoopBack.ELEMENT_NAME,
                                                                                                                CallTemplate.ELEMENT_NAME,
                                                                                                                Call.ELEMENT_NAME));
        metaModelValidator.register(Log.ELEMENT_NAME, Connection.CONNECTION_NAME, Arrays.asList(Log.ELEMENT_NAME,
                                                                                                    Property.ELEMENT_NAME,
                                                                                                    PayloadFactory.ELEMENT_NAME,
                                                                                                    Send.ELEMENT_NAME,
                                                                                                    Header.ELEMENT_NAME,
                                                                                                    Respond.ELEMENT_NAME,
                                                                                                    Filter.ELEMENT_NAME,
                                                                                                    Switch_mediator.ELEMENT_NAME,
                                                                                                    Sequence.ELEMENT_NAME,
                                                                                                    Enrich.ELEMENT_NAME,
                                                                                                    LoopBack.ELEMENT_NAME,
                                                                                                    CallTemplate.ELEMENT_NAME,
                                                                                                    Call.ELEMENT_NAME));
        metaModelValidator.register(Call.ELEMENT_NAME, Connection.CONNECTION_NAME, Arrays.asList(Log.ELEMENT_NAME,
                                                                                                     Property.ELEMENT_NAME,
                                                                                                     PayloadFactory.ELEMENT_NAME,
                                                                                                     Send.ELEMENT_NAME,
                                                                                                     Header.ELEMENT_NAME,
                                                                                                     Respond.ELEMENT_NAME,
                                                                                                     Filter.ELEMENT_NAME,
                                                                                                     Switch_mediator.ELEMENT_NAME,
                                                                                                     Sequence.ELEMENT_NAME,
                                                                                                     Enrich.ELEMENT_NAME,
                                                                                                     LoopBack.ELEMENT_NAME,
                                                                                                     CallTemplate.ELEMENT_NAME,
                                                                                                     Call.ELEMENT_NAME));
        metaModelValidator.register(Property.ELEMENT_NAME, Connection.CONNECTION_NAME, Arrays.asList(Log.ELEMENT_NAME,
                                                                                                         Property.ELEMENT_NAME,
                                                                                                         PayloadFactory.ELEMENT_NAME,
                                                                                                         Send.ELEMENT_NAME,
                                                                                                         Header.ELEMENT_NAME,
                                                                                                         Respond.ELEMENT_NAME,
                                                                                                         Filter.ELEMENT_NAME,
                                                                                                         Switch_mediator.ELEMENT_NAME,
                                                                                                         Sequence.ELEMENT_NAME,
                                                                                                         Enrich.ELEMENT_NAME,
                                                                                                         LoopBack.ELEMENT_NAME,
                                                                                                         CallTemplate.ELEMENT_NAME,
                                                                                                         Call.ELEMENT_NAME));
        metaModelValidator.register(Enrich.ELEMENT_NAME, Connection.CONNECTION_NAME, Arrays.asList(Log.ELEMENT_NAME,
                                                                                                       Property.ELEMENT_NAME,
                                                                                                       PayloadFactory.ELEMENT_NAME,
                                                                                                       Send.ELEMENT_NAME,
                                                                                                       Header.ELEMENT_NAME,
                                                                                                       Respond.ELEMENT_NAME,
                                                                                                       Filter.ELEMENT_NAME,
                                                                                                       Switch_mediator.ELEMENT_NAME,
                                                                                                       Sequence.ELEMENT_NAME,
                                                                                                       Enrich.ELEMENT_NAME,
                                                                                                       LoopBack.ELEMENT_NAME,
                                                                                                       CallTemplate.ELEMENT_NAME,
                                                                                                       Call.ELEMENT_NAME));
        metaModelValidator.register(Sequence.ELEMENT_NAME, Connection.CONNECTION_NAME, Arrays.asList(Log.ELEMENT_NAME,
                                                                                                         Property.ELEMENT_NAME,
                                                                                                         PayloadFactory.ELEMENT_NAME,
                                                                                                         Send.ELEMENT_NAME,
                                                                                                         Header.ELEMENT_NAME,
                                                                                                         Respond.ELEMENT_NAME,
                                                                                                         Filter.ELEMENT_NAME,
                                                                                                         Switch_mediator.ELEMENT_NAME,
                                                                                                         Sequence.ELEMENT_NAME,
                                                                                                         Enrich.ELEMENT_NAME,
                                                                                                         LoopBack.ELEMENT_NAME,
                                                                                                         CallTemplate.ELEMENT_NAME,
                                                                                                         Call.ELEMENT_NAME));
        metaModelValidator.register(Send.ELEMENT_NAME, Connection.CONNECTION_NAME, Arrays.asList(Log.ELEMENT_NAME,
                                                                                                     Property.ELEMENT_NAME,
                                                                                                     PayloadFactory.ELEMENT_NAME,
                                                                                                     Send.ELEMENT_NAME,
                                                                                                     Header.ELEMENT_NAME,
                                                                                                     Respond.ELEMENT_NAME,
                                                                                                     Filter.ELEMENT_NAME,
                                                                                                     Switch_mediator.ELEMENT_NAME,
                                                                                                     Sequence.ELEMENT_NAME,
                                                                                                     Enrich.ELEMENT_NAME,
                                                                                                     LoopBack.ELEMENT_NAME,
                                                                                                     CallTemplate.ELEMENT_NAME,
                                                                                                     Call.ELEMENT_NAME));
        metaModelValidator.register(PayloadFactory.ELEMENT_NAME, Connection.CONNECTION_NAME, Arrays.asList(Log.ELEMENT_NAME,
                                                                                                               Property.ELEMENT_NAME,
                                                                                                               PayloadFactory.ELEMENT_NAME,
                                                                                                               Send.ELEMENT_NAME,
                                                                                                               Header.ELEMENT_NAME,
                                                                                                               Respond.ELEMENT_NAME,
                                                                                                               Filter.ELEMENT_NAME,
                                                                                                               Switch_mediator.ELEMENT_NAME,
                                                                                                               Sequence.ELEMENT_NAME,
                                                                                                               Enrich.ELEMENT_NAME,
                                                                                                               LoopBack.ELEMENT_NAME,
                                                                                                               CallTemplate.ELEMENT_NAME,
                                                                                                               Call.ELEMENT_NAME));
        metaModelValidator.register(Header.ELEMENT_NAME, Connection.CONNECTION_NAME, Arrays.asList(Log.ELEMENT_NAME,
                                                                                                       Property.ELEMENT_NAME,
                                                                                                       PayloadFactory.ELEMENT_NAME,
                                                                                                       Send.ELEMENT_NAME,
                                                                                                       Header.ELEMENT_NAME,
                                                                                                       Respond.ELEMENT_NAME,
                                                                                                       Filter.ELEMENT_NAME,
                                                                                                       Switch_mediator.ELEMENT_NAME,
                                                                                                       Sequence.ELEMENT_NAME,
                                                                                                       Enrich.ELEMENT_NAME,
                                                                                                       LoopBack.ELEMENT_NAME,
                                                                                                       CallTemplate.ELEMENT_NAME,
                                                                                                       Call.ELEMENT_NAME));
        metaModelValidator.register(CallTemplate.ELEMENT_NAME, Connection.CONNECTION_NAME, Arrays.asList(Log.ELEMENT_NAME,
                                                                                                             Property.ELEMENT_NAME,
                                                                                                             PayloadFactory.ELEMENT_NAME,
                                                                                                             Send.ELEMENT_NAME,
                                                                                                             Header.ELEMENT_NAME,
                                                                                                             Respond.ELEMENT_NAME,
                                                                                                             Filter.ELEMENT_NAME,
                                                                                                             Switch_mediator.ELEMENT_NAME,
                                                                                                             Sequence.ELEMENT_NAME,
                                                                                                             Enrich.ELEMENT_NAME,
                                                                                                             LoopBack.ELEMENT_NAME,
                                                                                                             CallTemplate.ELEMENT_NAME,
                                                                                                             Call.ELEMENT_NAME));
        metaModelValidator.register(Filter.ELEMENT_NAME, Connection.CONNECTION_NAME, Arrays.asList(Log.ELEMENT_NAME,
                                                                                                       Property.ELEMENT_NAME,
                                                                                                       PayloadFactory.ELEMENT_NAME,
                                                                                                       Send.ELEMENT_NAME,
                                                                                                       Header.ELEMENT_NAME,
                                                                                                       Respond.ELEMENT_NAME,
                                                                                                       Filter.ELEMENT_NAME,
                                                                                                       Switch_mediator.ELEMENT_NAME,
                                                                                                       Sequence.ELEMENT_NAME,
                                                                                                       Enrich.ELEMENT_NAME,
                                                                                                       LoopBack.ELEMENT_NAME,
                                                                                                       CallTemplate.ELEMENT_NAME,
                                                                                                       Call.ELEMENT_NAME));


        selectionManager.addListener(propertiesPanelManager);
        workspace.addDiagramChangeListener(this);
        workspace.addMainElementChangeListener(toolbar);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        toolbar.go(((WSO2EditorView)view).getToolbarPanel());
        workspace.go(((WSO2EditorView)view).getWorkspacePanel());

        super.go(container);
    }

    /** {@inheritDoc} */
    @Override
    public void onChanged() {
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyChanged() {
        notifyListeners();
    }

    public void addListener(@Nonnull EditorChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(@Nonnull EditorChangeListener listener) {
        listeners.remove(listener);
    }

    public void notifyListeners() {
        for (EditorChangeListener listener : listeners) {
            listener.onChanged();
        }
    }

    /** @return a serialized text type of diagram */
    @Nonnull
    public String serialize() {
        return workspace.serialize();
    }

    /** @return a serialized internal text type of diagram */
    @Nonnull
    public String serializeInternalFormat() {
        return workspace.serializeInternalFormat();
    }

    /**
     * Convert a text type of diagram to a graphical type.
     *
     * @param content
     *         content that need to be parsed
     */
    public void deserialize(@Nonnull String content) {
        workspace.deserialize(content);
    }

    /**
     * Convert an internal text type of diagram to a graphical type.
     *
     * @param content
     *         content that need to be parsed
     */
    public void deserializeInternalFormat(@Nonnull String content) {
        workspace.deserializeInternalFormat(content);
    }

    public interface EditorChangeListener {

        void onChanged();

    }

}