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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.wso2.developerstudio.eclipse.gmf.esb.EsbSequence;

/**
 * This class illustrates how to handle a GraphicalSequenceChangeEvent It creates an ESBToXMLMapper that transforms the ESB sequence into
 * XML This class is an example, not made to last
 * 
 * @author Thomas Legrand
 */
public class GraphicalSequenceChangeHandlerImpl implements GraphicalSequenceChangeHandler {

    private static final Logger logger = Logger.getLogger(GraphicalSequenceChangeHandlerImpl.class.getName());

    @Override
    public void hasChanged(EsbSequence sequence) {

        ESBToXMLMapper esbToXMLMapper = new ESBToXMLMapper();

        try {
            // do whatever you want: fill the XML text editor
            logger.log(Level.WARNING, "XML: " + esbToXMLMapper.transform(sequence));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
