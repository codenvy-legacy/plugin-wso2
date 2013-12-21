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
package com.codenvy.ide.ext.wso2.client.patchers;

import com.codenvy.ide.texteditor.api.TextEditorConfiguration;
import com.codenvy.ide.texteditor.api.parser.CmParser;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;

import static org.mockito.Mockito.mock;

/**
 * The patcher for {@link TextEditorConfiguration}. Replace native method in it.
 *
 * @author Andrey Plotnikov
 */
@PatchClass(TextEditorConfiguration.class)
public class TextEditorConfigurationPatcher {

    @PatchMethod(override = true)
    public static CmParser getParserForMime(String mime) {
        return mock(CmParser.class);
    }
}