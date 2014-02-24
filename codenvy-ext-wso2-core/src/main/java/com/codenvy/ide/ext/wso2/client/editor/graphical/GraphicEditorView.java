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

import com.codenvy.ide.api.mvp.View;
import com.genmymodel.ecoreonline.graphic.Diagram;
import com.google.inject.ImplementedBy;
import com.google.web.bindery.event.shared.EventBus;
import org.genmymodel.gmmf.propertypanel.PropertyPresenter;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbSequence;

/**
 * The view of {@link GraphicEditor}.
 *
 * @author Alexis Muller
 * @author Andrey Plotnikov
 */
@ImplementedBy(GraphicEditorViewImpl.class)
public interface GraphicEditorView extends View<GraphicEditorView.ActionDelegate> {

    public interface ActionDelegate {
    }

    void initModelingWidgets(EsbSequence sequence, Diagram diagram);

    void addPropertyForm(PropertyPresenter... forms);

    EventBus getDiagramEventBus();

}
