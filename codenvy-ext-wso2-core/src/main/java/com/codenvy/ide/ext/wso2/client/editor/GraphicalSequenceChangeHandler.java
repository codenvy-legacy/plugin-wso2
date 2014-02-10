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

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler to warn changes occurring in a ESB graphical sequence
 * @author Thomas Legrand
 *
 */
public interface GraphicalSequenceChangeHandler extends EventHandler {

    public void hasChanged(EsbSequence sequence);
}
