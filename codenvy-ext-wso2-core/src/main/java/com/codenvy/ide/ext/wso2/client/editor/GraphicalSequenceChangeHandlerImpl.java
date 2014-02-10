package com.codenvy.ide.ext.wso2.client.editor;

import org.wso2.developerstudio.eclipse.gmf.esb.EsbSequence;

public class GraphicalSequenceChangeHandlerImpl implements GraphicalSequenceChangeHandler{

    @Override
    public void hasChanged(EsbSequence sequence) {

        ESBToXMLMapper esbToXMLMapper = new ESBToXMLMapper();
        
        try {
            System.out.println(esbToXMLMapper.transform(sequence));
        } catch (Exception e) {
            e.printStackTrace();
        }       
        
    }

}
