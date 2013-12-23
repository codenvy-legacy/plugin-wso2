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
package com.codenvy.ide.ext.wso2.client.editor;

import com.codenvy.ide.texteditor.api.AutoEditStrategy;
import com.codenvy.ide.texteditor.api.TextEditorPartView;
import com.codenvy.ide.texteditor.api.parser.CmParser;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithMockito;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Answers.RETURNS_MOCKS;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Here we're testing {@link XmlEditorConfiguration}.
 *
 * @author Andrey Plotnikov
 */
@GwtModule("com.codenvy.ide.ext.wso2.WSO2")
public class XmlEditorConfigurationTest extends GwtTestWithMockito {

    @Mock
    private TextEditorPartView     view;
    @Mock(answer = RETURNS_MOCKS)
    private AutoCompleterFactory   autoCompleterFactory;
    @InjectMocks
    private XmlEditorConfiguration xmlEditorConfiguration;

    @Test
    public void parserShouldBeInitialized() throws Exception {
        CmParser parser = (CmParser)xmlEditorConfiguration.getParser(view);

        assertNotNull(parser);
    }

    @Test
    public void configurationShouldContainTagAutoCompleter() throws Exception {
        AutoEditStrategy[] autoEditStrategies = xmlEditorConfiguration.getAutoEditStrategies(view, "contentType");

        assertEquals(autoEditStrategies.length, 1);

        AutoEditStrategy autoEditStrategy = autoEditStrategies[0];
        assertTrue(autoEditStrategy instanceof TagAutoCompleter);

        verify(autoCompleterFactory).createAutoCompleter(eq(view));
    }
}