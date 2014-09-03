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

import com.codenvy.ide.client.EditorState;
import com.codenvy.ide.client.HasState;
import com.codenvy.ide.client.State;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.State.CREATING_ADDRESS_ENDPOINT;
import static com.codenvy.ide.client.State.CREATING_CALL;
import static com.codenvy.ide.client.State.CREATING_CALLTEMPLATE;
import static com.codenvy.ide.client.State.CREATING_ENRICH;
import static com.codenvy.ide.client.State.CREATING_FILTER;
import static com.codenvy.ide.client.State.CREATING_HEADER;
import static com.codenvy.ide.client.State.CREATING_LOG;
import static com.codenvy.ide.client.State.CREATING_LOOPBACK;
import static com.codenvy.ide.client.State.CREATING_PAYLOADFACTORY;
import static com.codenvy.ide.client.State.CREATING_PROPERTY;
import static com.codenvy.ide.client.State.CREATING_RESPOND;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_CREATE;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_DELETE;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_DESCRIBE_GLOBAL;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_DESCRIBE_SUBJECT;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_DESCRIBE_SUBJECTS;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_EMPTY_RECYCLE_BIN;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_GET_USER_INFORMATION;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_INIT;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_LOGOUT;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_QUERY;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_QUERY_MORE;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_QURY_ALL;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_RESET_PASSWORD;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_RETRIVE;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_SEARCH;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_SEND_EMAIL;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_SEND_EMAIL_MESSAGE;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_SET_PASSWORD;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_UNDELETE;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_UPDATE;
import static com.codenvy.ide.client.State.CREATING_SALESFORCE_UPSET;
import static com.codenvy.ide.client.State.CREATING_SEND;
import static com.codenvy.ide.client.State.CREATING_SEQUENCE;
import static com.codenvy.ide.client.State.CREATING_SWITCH;

/**
 * The presenter that provides a business logic of tool bar. It provides an ability to work with all elements which it contains.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class ToolbarPresenter extends AbstractPresenter<ToolbarView> implements HasState<State>, ToolbarView.ActionDelegate {

    private EditorState<State> state;

    @Inject
    public ToolbarPresenter(ToolbarView view, @Assisted EditorState<State> editorState) {
        super(view);

        this.state = editorState;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public State getState() {
        return state.getState();
    }

    /** {@inheritDoc} */
    @Override
    public void setState(@Nonnull State state) {
        this.state.setState(state);
    }

    /** {@inheritDoc} */
    @Override
    public void onLogButtonClicked() {
        setState(CREATING_LOG);
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyButtonClicked() {
        setState(CREATING_PROPERTY);
    }

    /** {@inheritDoc} */
    @Override
    public void onPayloadFactoryButtonClicked() {
        setState(CREATING_PAYLOADFACTORY);
    }

    /** {@inheritDoc} */
    @Override
    public void onSendButtonClicked() {
        setState(CREATING_SEND);
    }

    /** {@inheritDoc} */
    @Override
    public void onHeaderButtonClicked() {
        setState(CREATING_HEADER);
    }

    /** {@inheritDoc} */
    @Override
    public void onRespondButtonClicked() {
        setState(CREATING_RESPOND);
    }

    /** {@inheritDoc} */
    @Override
    public void onFilterButtonClicked() {
        setState(CREATING_FILTER);
    }

    /** {@inheritDoc} */
    @Override
    public void onSwitchButtonClicked() {
        setState(CREATING_SWITCH);
    }

    /** {@inheritDoc} */
    @Override
    public void onSequenceButtonClicked() {
        setState(CREATING_SEQUENCE);
    }

    /** {@inheritDoc} */
    @Override
    public void onEnrichButtonClicked() {
        setState(CREATING_ENRICH);
    }

    /** {@inheritDoc} */
    @Override
    public void onLoopBackButtonClicked() {
        setState(CREATING_LOOPBACK);
    }

    /** {@inheritDoc} */
    @Override
    public void onCallTemplateButtonClicked() {
        setState(CREATING_CALLTEMPLATE);
    }

    /** {@inheritDoc} */
    @Override
    public void onCallButtonClicked() {
        setState(CREATING_CALL);
    }

    /** {@inheritDoc} */
    @Override
    public void onAddressEndpointButtonClicked() {
        setState(CREATING_ADDRESS_ENDPOINT);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceInitClicked() {
        setState(CREATING_SALESFORCE_INIT);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceCreateClicked() {
        setState(CREATING_SALESFORCE_CREATE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceDeleteClicked() {
        setState(CREATING_SALESFORCE_DELETE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceUpdateClicked() {
        setState(CREATING_SALESFORCE_UPDATE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceEmptyRecycleBinClicked() {
        setState(CREATING_SALESFORCE_EMPTY_RECYCLE_BIN);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceLogOutClicked() {
        setState(CREATING_SALESFORCE_LOGOUT);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceGetUserInfoClicked() {
        setState(CREATING_SALESFORCE_GET_USER_INFORMATION);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceQueryClicked() {
        setState(CREATING_SALESFORCE_QUERY);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceQueryAllClicked() {
        setState(CREATING_SALESFORCE_QURY_ALL);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceQueryMoreClicked() {
        setState(CREATING_SALESFORCE_QUERY_MORE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceResetPasswordClicked() {
        setState(CREATING_SALESFORCE_RESET_PASSWORD);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceRetriveClicked() {
        setState(CREATING_SALESFORCE_RETRIVE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceSearchClicked() {
        setState(CREATING_SALESFORCE_SEARCH);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceSendEmailClicked() {
        setState(CREATING_SALESFORCE_SEND_EMAIL);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceSendEmailMessageClicked() {
        setState(CREATING_SALESFORCE_SEND_EMAIL_MESSAGE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceSetPasswordClicked() {
        setState(CREATING_SALESFORCE_SET_PASSWORD);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceUndeleteClicked() {
        setState(CREATING_SALESFORCE_UNDELETE);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceUpsetClicked() {
        setState(CREATING_SALESFORCE_UPSET);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceDescribeGlobalClicked() {
        setState(CREATING_SALESFORCE_DESCRIBE_GLOBAL);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceDescribeSubjectClicked() {
        setState(CREATING_SALESFORCE_DESCRIBE_SUBJECT);
    }

    /** {@inheritDoc} */
    @Override
    public void onSalesForceDescribeSubjectsClicked() {
        setState(CREATING_SALESFORCE_DESCRIBE_SUBJECTS);
    }

}