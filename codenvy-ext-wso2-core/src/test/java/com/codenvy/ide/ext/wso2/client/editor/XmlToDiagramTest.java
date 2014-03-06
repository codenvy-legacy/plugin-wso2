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

import java.util.Date;

import org.junit.Test;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbFactory;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbLink;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbSequence;
import org.wso2.developerstudio.eclipse.gmf.esb.FilterMediator;
import org.wso2.developerstudio.eclipse.gmf.esb.LogMediator;
import org.wso2.developerstudio.eclipse.gmf.esb.NamespacedProperty;
import org.wso2.developerstudio.eclipse.gmf.esb.PropertyMediator;
import org.wso2.developerstudio.eclipse.gmf.esb.SendMediator;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

public class XmlToDiagramTest extends GWTTestCase {

    public XmlToDiagramTest() {
        super();
    }

    /**
     * Returns the module name for GWT unit test running.
     */
    @Override
    public String getModuleName() {
        return "com.codenvy.ide.ext.wso2.WSO2";
    }

    /**
     * The main test
     */
    @Test
    public void testTransformModelToXml() {
        long start = new Date().getTime();

        // Create an ESB sequence (EMF model conforming esb.ecore)
        EsbSequence sequence = createEsbSequence();

        // Parse the sequence to render an XML document
        Document document = null;
        try {

            ESBToXMLMapper esbToXMLMapper = new ESBToXMLMapper();
            document = esbToXMLMapper.transform(sequence);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // XML -> model
        try {
            sequence = retrieveSequence(document);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Model -> diagram

        long end = new Date().getTime();
        System.out.println("Duration: " + (end - start) / 1000.0 + "s");
    }


    private EsbSequence retrieveSequence(Document document) {

        Element rootElem = null;
        EsbSequence sequence = null;

        try {
            rootElem = document.getDocumentElement();

            // Create the sequence
            sequence = EsbFactory.eINSTANCE.loadModelObject(rootElem, EsbSequence.class, null);

            // Populate it
            sequence.load(rootElem);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (null != sequence) {
            System.out.println("Load errors: " + sequence.getLoadErrors());
        } else {
            System.out.println("Corrupted or invalid esb configuration file.");
        }

        return sequence;
    }

    private EsbSequence createEsbSequence() {
        EsbSequence sequence = EsbFactory.eINSTANCE.createEsbSequence();
        sequence.setName("sequence 1");

        LogMediator logMediator = EsbFactory.eINSTANCE.createLogMediator();
        logMediator.setDescription("log 1");

        PropertyMediator propertyMediator = EsbFactory.eINSTANCE.createPropertyMediator();
        propertyMediator.setDescription("property 1");

        FilterMediator filterMediator = EsbFactory.eINSTANCE.createFilterMediator();
        filterMediator.setDescription("filter 1");
        filterMediator.setRegex("default_regex");
        NamespacedProperty namespacedProperty = EsbFactory.eINSTANCE.createNamespacedProperty();
        namespacedProperty.setPropertyName("property");
        namespacedProperty.setPropertyValue("value");
        filterMediator.setSource(namespacedProperty);

        SendMediator sendMediator = EsbFactory.eINSTANCE.createSendMediator();
        sendMediator.setDescription("Send");

        EsbLink linkLogToProperty = EsbFactory.eINSTANCE.createEsbLink();
        linkLogToProperty.setSource(logMediator.getOutputConnector());
        linkLogToProperty.setTarget(propertyMediator.getInputConnector());

        EsbLink linkPropertyToFilter = EsbFactory.eINSTANCE.createEsbLink();
        linkPropertyToFilter.setSource(propertyMediator.getOutputConnector());
        linkPropertyToFilter.setTarget(filterMediator.getInputConnector());

        sequence.getChildMediators().add(logMediator);
        sequence.getChildMediators().add(propertyMediator);
        sequence.getChildMediators().add(filterMediator);
        sequence.getChildMediators().add(sendMediator);

        return sequence;
    }
}
