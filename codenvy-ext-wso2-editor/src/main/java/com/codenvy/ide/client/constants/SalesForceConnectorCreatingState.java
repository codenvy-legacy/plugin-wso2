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

package com.codenvy.ide.client.constants;

/**
 * @author Andrey Plotnikov
 */
public interface SalesForceConnectorCreatingState {

    String INIT                 = "InitSalesForceConnectorCreatingState";
    String CREATE               = "CreateSalesForceConnectorCreatingState";
    String UPDATE               = "UpdateSalesForceConnectorCreatingState";
    String DELETE               = "DeleteSalesForceConnectorCreatingState";
    String EMPTY_RECYCLE_BIN    = "EmptyRecycleBinSalesForceConnectorCreatingState";
    String LOGOUT               = "LogoutSalesForceConnectorCreatingState";
    String GET_USER_INFORMATION = "GetUserInformationSalesForceConnectorCreatingState";
    String DESCRIBE_GLOBAL      = "DescribeGlobalSalesForceConnectorCreatingState";
    String DESCRIBE_SUBJECT     = "DescribeSubjectSalesForceConnectorCreatingState";
    String DESCRIBE_SUBJECTS    = "DescribeSubjectsSalesForceConnectorCreatingState";
    String QUERY                = "QuerySalesForceConnectorCreatingState";
    String QUERY_ALL            = "QueryAllSalesForceConnectorCreatingState";
    String QUERY_MORE           = "QueryMoreSalesForceConnectorCreatingState";
    String RESET_PASSWORD       = "ResetPasswordSalesForceConnectorCreatingState";
    String RETRIVE              = "RetriveSalesForceConnectorCreatingState";
    String SEARCH               = "SearchSalesForceConnectorCreatingState";
    String SEND_EMAIL           = "SendEmailSalesForceConnectorCreatingState";
    String SEND_EMAIL_MESSAGE   = "SendEmailMessageSalesForceConnectorCreatingState";
    String SET_PASSWORD         = "SetPasswordSalesForceConnectorCreatingState";
    String UNDELETE             = "UnDeleteSalesForceConnectorCreatingState";
    String UPSET                = "UpsetSalesForceConnectorCreatingState";

}