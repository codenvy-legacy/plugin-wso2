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

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

/**
 * The transformation ESB model (instance of wso2-esb) into wso2-specific XML
 *
 * @author Thomas Legrand
 */
public class ESBToXMLMapper {

    /**
     * Transforms a EMF sequence model into XML matching WSO2 XSD.
     * The transformation code is contained in each model element
     * Example: PropertyMediatorImpl
     * @param sequence
     * @return
     * @throws Exception
     */
    public Document transform(EsbSequence sequence) throws Exception
    {
        assert(sequence!=null);
        
        // use gwt.xml to create an XML doc
        Document document = XMLParser.createDocument();

        // append the sequence and its mediators in the document
        sequence.save(document);

        return document;
    }
}
