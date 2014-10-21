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
package com.codenvy.ide.client.initializers.toolbar;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.constants.ToolbarGroupIds;
import com.codenvy.ide.client.constants.TwitterConnectorCreatingState;
import com.codenvy.ide.client.toolbar.ToolbarPresenter;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class TwitterToolbarInitializer extends AbstractToolbarInitializer {

    @Inject
    public TwitterToolbarInitializer(ToolbarPresenter toolbar, EditorResources resources, WSO2EditorLocalizationConstant locale) {
        super(toolbar, resources, locale);
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        toolbar.addGroup(ToolbarGroupIds.TWITTER_CONNECTORS, locale.toolbarGroupTwitterConnector());

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterDestroyStatusTitle(),
                        locale.toolbarTwitterDestroyStatusTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.DESTROY_STATUS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterGetClosesTrendsTitle(),
                        locale.toolbarTwitterGetClosesTrendsTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_CLOTHES_TRENDS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterGetDirectMessagesTitle(),
                        locale.toolbarTwitterGetDirectMessagesTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_DIRECT_MESSAGES);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterGetFollowersTitle(),
                        locale.toolbarTwitterGetFollowersTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_FOLLOWERS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterGetFollowersIdsTitle(),
                        locale.toolbarTwitterGetFollowersIdsTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_FOLLOWERS_IDS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterGetFriendsTitle(),
                        locale.toolbarTwitterGetFriendsTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_FRIENDS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterGetFriendsIdsTitle(),
                        locale.toolbarTwitterGetFriendsIdsTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_FRIENDS_IDS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterGetHomeTimeLineTitle(),
                        locale.toolbarTwitterGetHomeTimeLineTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_HOME_TIME_LINE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterGetMentionsTimeLineTitle(),
                        locale.toolbarTwitterGetMentionsTimeLineTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_MENTIONS_TIME_LINE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterGetRetweetsOfMineTitle(),
                        locale.toolbarTwitterGetRetweetsOfMineTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_RETWEETS_OF_MINE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterGetSentDirectMessageTitle(),
                        locale.toolbarTwitterGetSentDirectMessageTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_SENT_DIRECT_MESSAGE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterGetTopTrendPlacesTitle(),
                        locale.toolbarTwitterGetTopTrendPlacesTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_TOP_TREND_PLACES);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterGetUserTimeLineTitle(),
                        locale.toolbarTwitterGetUserTimeLineTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_USER_TIME_LINE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterInitTitle(),
                        locale.toolbarTwitterInitTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.INIT);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterRetweetTitle(),
                        locale.toolbarTwitterRetweetTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.RETWEET);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterSearchTitle(),
                        locale.toolbarTwitterSearchTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.SEARCH);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterSearchPlacesTitle(),
                        locale.toolbarTwitterSearchPlacesTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.SEARCH_PLACES);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterSendDirectMessageTitle(),
                        locale.toolbarTwitterSendDirectMessageTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.SEND_DIRECT_MESSAGE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterShowStatusTitle(),
                        locale.toolbarTwitterShowStatusTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.SHOW_STATUS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        locale.toolbarTwitterUpdateStatusTitle(),
                        locale.toolbarTwitterUpdateStatusTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.UPDATE_STATUS);
    }

}