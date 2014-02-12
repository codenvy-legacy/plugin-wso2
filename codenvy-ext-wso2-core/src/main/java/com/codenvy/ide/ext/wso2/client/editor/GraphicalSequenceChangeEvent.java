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
package com.codenvy.ide.ext.wso2.client.editor;

import org.wso2.developerstudio.eclipse.gmf.esb.EsbSequence;

import com.google.web.bindery.event.shared.Event;

/**
 * Event used when an ESB sequence has changed, i.e where there's any change on the diagram and the model
 * 
 * @author Thomas Legrand
 */
public class GraphicalSequenceChangeEvent extends Event<GraphicalSequenceChangeHandler> {

    public static final Type<GraphicalSequenceChangeHandler> TYPE = new Type<GraphicalSequenceChangeHandler>();

    // the ESB sequence model
    private EsbSequence                                      esbSequence;

    public GraphicalSequenceChangeEvent(EsbSequence esbSequence)
    {
        this.esbSequence = esbSequence;
    }

    @Override
    public Type<GraphicalSequenceChangeHandler> getAssociatedType() {
        return GraphicalSequenceChangeEvent.TYPE;
    }

    @Override
    protected void dispatch(GraphicalSequenceChangeHandler handler) {
        handler.hasChanged(this.esbSequence);
    }

}