/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 * [2012] - [2013] Codenvy, S.A. 
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.ide.ext.wso2.client.wizard.files.view;

import com.codenvy.ide.annotations.NotNull;
import com.codenvy.ide.api.mvp.View;
import com.google.inject.ImplementedBy;

/**
 * The view for creating WSO2 resources. Provides an ability to input resource name.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(CreateResourceViewImpl.class)
public interface CreateResourceView extends View<CreateResourceView.ActionDelegate> {

    /** Required for delegating functions in the view. */
    public interface ActionDelegate {

        /** Performs some actions in response to a user's changing resource name. */
        void onValueChanged();
    }

    void setResourceNameTitle(@NotNull String title);

    @NotNull
    String getResourceName();

    void setResourceName(@NotNull String name);

}