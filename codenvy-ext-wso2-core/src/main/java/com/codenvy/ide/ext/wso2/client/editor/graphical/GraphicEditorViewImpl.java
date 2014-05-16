/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 * [2012] - [2014] Codenvy, S.A. 
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
package com.codenvy.ide.ext.wso2.client.editor.graphical;

import com.codenvy.ide.client.WSO2Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * The implementation of {@link GraphicEditorView}.
 *
 * @author Andrey Plotnikov
 */
public class GraphicEditorViewImpl extends Composite implements GraphicEditorView {

    interface GEUIBinder extends UiBinder<Widget, GraphicEditorViewImpl> {
    }

    @UiField
    SimpleLayoutPanel editor;

    @Inject
    public GraphicEditorViewImpl(GEUIBinder binder, @Assisted WSO2Editor wso2Editor) {
        initWidget(binder.createAndBindUi(this));

        wso2Editor.go(editor);
    }

    /** {@inheritDoc} */
    @Override
    public void setDiagram(String diagram) {
        // TODO set diagram into widget
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(ActionDelegate delegate) {
        // do nothing for now
    }

}
