/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache  License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Singleton;

import org.vectomatic.dom.svg.ui.SVGResource;

/**
 * Class contains references to resources which need to correct displaying of WSO2 plugin.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
@Singleton
public interface EditorResources extends ClientBundle {

    interface EditorCSS extends CssResource {

        String fullSize();

        String selectedElement();

        String selectErrorElementBelowCursor();

        String selectElementBelowCursor();

        String errorCursor();

        String applyCursor();

        String property();

        String labelStyle();

        String fieldStyle();

        String expandedImage();

        String normalImage();

        String topBorder();

    }

    @Source("editor.css")
    EditorCSS editorCSS();

    @Source("icons/Iterate.gif")
    ImageResource iterate();

    @Source("icons/СonditionalRouter.gif")
    ImageResource сonditionalRouter();

    @Source("icons/SequenceToolbar.gif")
    ImageResource sequenceToolbar();

    @Source("icons/Aggregate.png")
    ImageResource aggregate();

    @Source("icons/RuleToolbar.gif")
    ImageResource ruleToolbar();

    @Source("icons/Ejb.gif")
    ImageResource ejb();

    @Source("icons/Proxy.gif")
    ImageResource proxy();

    @Source("icons/Recipientlist.gif")
    ImageResource recipientlist();

    @Source("icons/SmooksToolbar.gif")
    ImageResource smooksToolbar();

    @Source("icons/EnqueueToolbar.gif")
    ImageResource enqueueToolbar();

    @Source("icons/EjbToolbar.gif")
    ImageResource ejbToolbar();

    @Source("icons/FaultToolbar.gif")
    ImageResource faultToolbar();

    @Source("icons/NamedToolbar.gif")
    ImageResource namedToolbar();

    @Source("icons/UrlRewrite.gif")
    ImageResource urlRewrite();

    @Source("icons/Rule.gif")
    ImageResource rule();

    @Source("icons/WsdlToolbar.gif")
    ImageResource wsdlToolbar();

    @Source("icons/Drop.gif")
    ImageResource drop();

    @Source("icons/Bam.gif")
    ImageResource bam();

    @Source("icons/Wsdl.gif")
    ImageResource wsdl();

    @Source("icons/CacheToolbar.gif")
    ImageResource cacheToolbar();

    @Source("icons/DropToolbar.gif")
    ImageResource dropToolbar();

    @Source("icons/SwitchToolbar.gif")
    ImageResource switchToolbar();

    @Source("icons/Throttle.gif")
    ImageResource throttle();

    @Source("icons/ConditionalRouterToolbar.gif")
    ImageResource conditionalRouterToolbar();

    @Source("icons/RmSequenceToolbar.gif")
    ImageResource rmSequenceToolbar();

    @Source("icons/FilterToolbar.gif")
    ImageResource filterToolbar();

    @Source("icons/Address.png")
    ImageResource address();

    @Source("icons/XsltToolbar.gif")
    ImageResource xsltToolbar();

    @Source("icons/Xquery.gif")
    ImageResource xquery();

    @Source("icons/ValidateToolbar.gif")
    ImageResource validateToolbar();

    @Source("icons/Clone.gif")
    ImageResource clone();

    @Source("icons/CommandToolbar.gif")
    ImageResource commandToolbar();

    @Source("icons/Named.gif")
    ImageResource named();

    @Source("icons/AddressingToolbar.gif")
    ImageResource addressingToolbar();

    @Source("icons/Fault.gif")
    ImageResource fault();

    @Source("icons/ScriptToolbar.gif")
    ImageResource scriptToolbar();

    @Source("icons/BuilderToolbar.gif")
    ImageResource builderToolbar();

    @Source("icons/Cache.gif")
    ImageResource cache();

    @Source("icons/Store.gif")
    ImageResource store();

    @Source("icons/EntitlementToolbar.gif")
    ImageResource entitlementToolbar();

    @Source("icons/DefaultToolbar.gif")
    ImageResource defaultToolbar();

    @Source("icons/XqueryToolbar.gif")
    ImageResource xqueryToolbar();

    @Source("icons/UrlRewriteToolbar.gif")
    ImageResource urlRewriteToolbar();

    @Source("icons/PayloadFactoryToolbar.gif")
    ImageResource payloadFactoryToolbar();

    @Source("icons/Dblookup.gif")
    ImageResource dblookup();

    @Source("icons/EnrichToolbar.gif")
    ImageResource enrichToolbar();

    @Source("icons/CallTemplate.gif")
    ImageResource callTemplate();

    @Source("icons/HttpToolbar.gif")
    ImageResource httpToolbar();

    @Source("icons/Transaction.gif")
    ImageResource transaction();

    @Source("icons/BamToolbar.gif")
    ImageResource bamToolbar();

    @Source("icons/PropertyToolbar.gif")
    ImageResource propertyToolbar();

    @Source("icons/LogToolbar.gif")
    ImageResource logToolbar();

    @Source("icons/Failover.gif")
    ImageResource failover();

    @Source("icons/CalloutToolbar.gif")
    ImageResource calloutToolbar();

    @Source("icons/CloneToolbar.gif")
    ImageResource cloneToolbar();

    @Source("icons/SendToolbar.gif")
    ImageResource sendToolbar();

    @Source("icons/LoopBack.gif")
    ImageResource loopBack();

    @Source("icons/Bean.png")
    ImageResource bean();

    @Source("icons/Loadbalance.gif")
    ImageResource loadbalance();

    @Source("icons/Xslt.gif")
    ImageResource xslt();

    @Source("icons/Header.gif")
    ImageResource header();

    @Source("icons/LoadBalanceToolbar.gif")
    ImageResource loadBalanceToolbar();

    @Source("icons/Call.gif")
    ImageResource call();

    @Source("icons/AddressToolbar.gif")
    ImageResource addressToolbar();

    @Source("icons/PayloadFactory.gif")
    ImageResource payloadFactory();

    @Source("icons/Spring.gif")
    ImageResource spring();

    @Source("icons/Script.gif")
    ImageResource script();

    @Source("icons/Entitlement.gif")
    ImageResource entitlement();

    @Source("icons/Dbreport.gif")
    ImageResource dbreport();

    @Source("icons/Switch.gif")
    ImageResource switchMediator();

    @Source("icons/DBReportToolbar.gif")
    ImageResource dBReportToolbar();

    @Source("icons/RespondToolbar.gif")
    ImageResource respondToolbar();

    @Source("icons/Filter.gif")
    ImageResource filter();

    @Source("icons/Respond.gif")
    ImageResource respond();

    @Source("icons/HeaderToolbar.gif")
    ImageResource headerToolbar();

    @Source("icons/Send.gif")
    ImageResource send();

    @Source("icons/DbLookupToolbar.gif")
    ImageResource dbLookupToolbar();

    @Source("icons/CallToolbar.gif")
    ImageResource callToolbar();

    @Source("icons/TemplateToolbar.gif")
    ImageResource templateToolbar();

    @Source("icons/StoreToolbar.gif")
    ImageResource storeToolbar();

    @Source("icons/Builder.gif")
    ImageResource builder();

    @Source("icons/Defalut.gif")
    ImageResource defalut();

    @Source("icons/AggregateToolbar.gif")
    ImageResource aggregateToolbar();

    @Source("icons/ClassToolbar.gif")
    ImageResource classToolbar();

    @Source("icons/FailoverToolbar.gif")
    ImageResource failoverToolbar();

    @Source("icons/Smooks.gif")
    ImageResource smooks();

    @Source("icons/TransactionToolbar.gif")
    ImageResource transactionToolbar();

    @Source("icons/Connection.png")
    ImageResource connection();

    @Source("icons/Command.gif")
    ImageResource command();

    @Source("icons/Enrich.gif")
    ImageResource enrich();

    @Source("icons/Sequence.gif")
    ImageResource sequence();

    @Source("icons/SpringToolbar.gif")
    ImageResource springToolbar();

    @Source("icons/OAuthToolbar.gif")
    ImageResource oAuthToolbar();

    @Source("icons/Log.gif")
    ImageResource log();

    @Source("icons/Validate.gif")
    ImageResource validate();

    @Source("icons/Template.gif")
    ImageResource template();

    @Source("icons/LoopBackToolbar.gif")
    ImageResource loopBackToolbar();

    @Source("icons/ThrottleToolbar.gif")
    ImageResource throttleToolbar();

    @Source("icons/Http.gif")
    ImageResource http();

    @Source("icons/Enqueue.gif")
    ImageResource enqueue();

    @Source("icons/BeanToolbar.gif")
    ImageResource beanToolbar();

    @Source("icons/CallTemplateToolbar.gif")
    ImageResource callTemplateToolbar();

    @Source("icons/Property.gif")
    ImageResource property();

    @Source("icons/EventToolbar.gif")
    ImageResource eventToolbar();

    @Source("icons/Event.gif")
    ImageResource event();

    @Source("icons/Callout.gif")
    ImageResource callout();

    @Source("icons/ClassMediator.gif")
    ImageResource classMediator();

    @Source("icons/RecipientListToolbar.gif")
    ImageResource recipientListToolbar();

    @Source("icons/Rmsequence.gif")
    ImageResource rmsequence();

    @Source("icons/Salesforce.gif")
    ImageResource salesforce();

    @Source("icons/jiraIconToolbar.gif")
    ImageResource jiraConnectorToolbar();

    @Source("icons/jiraIcon.gif")
    ImageResource jiraIcon();

    @Source("icons/twitterIconToolbar.gif")
    ImageResource twitterToolbar();

    @Source("icons/twitterIcon.gif")
    ImageResource twitterElement();

    @Source("icons/GoogleSpreadsheetToolbar.gif")
    ImageResource googleSpreadsheetToolbar();

    @Source("icons/GoogleSpreadsheet.gif")
    ImageResource googleSpreadsheetElement();

    @Source("icons/SalesforceToolbar.gif")
    ImageResource salesforceConnectorToolbar();

    @Source("icons/toolbar/expansionIcon.svg")
    SVGResource expandIcon();

    @Source("icons/cursors/AddElementCursor.png")
    DataResource applyCursor();

    @Source("icons/cursors/NotAllowedCursor.png")
    DataResource errorCursor();

}