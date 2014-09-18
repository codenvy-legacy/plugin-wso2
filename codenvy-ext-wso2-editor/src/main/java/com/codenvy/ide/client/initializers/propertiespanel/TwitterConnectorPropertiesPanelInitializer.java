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

package com.codenvy.ide.client.initializers.propertiespanel;

import com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus;
import com.codenvy.ide.client.elements.connectors.twitter.GetClosesTrends;
import com.codenvy.ide.client.elements.connectors.twitter.GetDirectMessages;
import com.codenvy.ide.client.elements.connectors.twitter.GetFollowers;
import com.codenvy.ide.client.elements.connectors.twitter.GetFollowersIds;
import com.codenvy.ide.client.elements.connectors.twitter.GetFriends;
import com.codenvy.ide.client.elements.connectors.twitter.GetFriendsIds;
import com.codenvy.ide.client.elements.connectors.twitter.GetHomeTimeLine;
import com.codenvy.ide.client.elements.connectors.twitter.GetMentionsTimeLine;
import com.codenvy.ide.client.elements.connectors.twitter.GetRetweetsOfMine;
import com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages;
import com.codenvy.ide.client.elements.connectors.twitter.GetTopTrendPlaces;
import com.codenvy.ide.client.elements.connectors.twitter.GetUserTimeLine;
import com.codenvy.ide.client.elements.connectors.twitter.InitTwitter;
import com.codenvy.ide.client.elements.connectors.twitter.Retweet;
import com.codenvy.ide.client.elements.connectors.twitter.SearchPlaces;
import com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter;
import com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage;
import com.codenvy.ide.client.elements.connectors.twitter.ShowStatus;
import com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus;
import com.codenvy.ide.client.managers.PropertiesPanelManager;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.DestroyStatusConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.GetClosesTrendsConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.GetDirectMessagesConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.GetFollowersConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.GetFollowersIdsConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.GetFriendsConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.GetFriendsIdsConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.GetHomeTimeLineConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.GetMentionsTimeLineConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.GetRetweetsOfMineConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.GetSentDirectMessagesConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.GetTopTrendPlacesConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.GetUserTimeLineConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.InitTwitterConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.RetweetConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.SearchPlacesConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.SearchTwitterConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.SendDirectMessageConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.ShowStatusConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.twitter.UpdateStatusConnectorPresenter;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class TwitterConnectorPropertiesPanelInitializer extends AbstractPropertiesPanelInitializer {

    private final DestroyStatusConnectorPresenter         destroyStatusPropertiesPanel;
    private final GetClosesTrendsConnectorPresenter       getClosesTrendsPropertiesPanel;
    private final GetDirectMessagesConnectorPresenter     getDirectMessagesPropertiesPanel;
    private final GetFollowersConnectorPresenter          getFollowersPropertiesPanel;
    private final GetFollowersIdsConnectorPresenter       getFollowersIdsPropertiesPanel;
    private final GetFriendsConnectorPresenter            getFriendsPropertiesPanel;
    private final GetFriendsIdsConnectorPresenter         getFriendsIdsPropertiesPanel;
    private final GetHomeTimeLineConnectorPresenter       getHomeTimeLinePropertiesPanel;
    private final GetMentionsTimeLineConnectorPresenter   getMentionsTimeLinePropertiesPanel;
    private final GetRetweetsOfMineConnectorPresenter     getRetweetsOfMinePropertiesPanel;
    private final GetSentDirectMessagesConnectorPresenter getSentDirectMessagesPropertiesPanel;
    private final GetTopTrendPlacesConnectorPresenter     getTopTrendPlacesPropertiesPanel;
    private final GetUserTimeLineConnectorPresenter       getUserTimeLinePropertiesPanel;
    private final RetweetConnectorPresenter               retweetPropertiesPanel;
    private final SearchTwitterConnectorPresenter         searchTwitterPropertiesPanel;
    private final SearchPlacesConnectorPresenter          searchPlacesPropertiesPanel;
    private final SendDirectMessageConnectorPresenter     sendDirectMessagePropertiesPanel;
    private final ShowStatusConnectorPresenter            showStatusPropertiesPanel;
    private final UpdateStatusConnectorPresenter          updateStatusPropertiesPanel;
    private final InitTwitterConnectorPresenter           initTwitterPropertiesPanel;

    @Inject
    public TwitterConnectorPropertiesPanelInitializer(PropertiesPanelManager manager,
                                                      DestroyStatusConnectorPresenter destroyStatusPropertiesPanel,
                                                      GetClosesTrendsConnectorPresenter getClosesTrendsPropertiesPanel,
                                                      GetDirectMessagesConnectorPresenter getDirectMessagesPropertiesPanel,
                                                      GetFollowersConnectorPresenter getFollowersPropertiesPanel,
                                                      GetFollowersIdsConnectorPresenter getFollowersIdsPropertiesPanel,
                                                      GetFriendsConnectorPresenter getFriendsPropertiesPanel,
                                                      GetFriendsIdsConnectorPresenter getFriendsIdsPropertiesPanel,
                                                      GetHomeTimeLineConnectorPresenter getHomeTimeLinePropertiesPanel,
                                                      GetMentionsTimeLineConnectorPresenter getMentionsTimeLinePropertiesPanel,
                                                      GetRetweetsOfMineConnectorPresenter getRetweetsOfMinePropertiesPanel,
                                                      GetSentDirectMessagesConnectorPresenter getSentDirectMessagesPropertiesPanel,
                                                      GetTopTrendPlacesConnectorPresenter getTopTrendPlacesPropertiesPanel,
                                                      GetUserTimeLineConnectorPresenter getUserTimeLinePropertiesPanel,
                                                      RetweetConnectorPresenter retweetPropertiesPanel,
                                                      SearchTwitterConnectorPresenter searchTwitterPropertiesPanel,
                                                      SearchPlacesConnectorPresenter searchPlacesPropertiesPanel,
                                                      SendDirectMessageConnectorPresenter sendDirectMessagePropertiesPanel,
                                                      ShowStatusConnectorPresenter showStatusPropertiesPanel,
                                                      UpdateStatusConnectorPresenter updateStatusPropertiesPanel,
                                                      InitTwitterConnectorPresenter initTwitterPropertiesPanel) {
        super(manager);
        this.destroyStatusPropertiesPanel = destroyStatusPropertiesPanel;
        this.getClosesTrendsPropertiesPanel = getClosesTrendsPropertiesPanel;
        this.getDirectMessagesPropertiesPanel = getDirectMessagesPropertiesPanel;
        this.getFollowersPropertiesPanel = getFollowersPropertiesPanel;
        this.getFollowersIdsPropertiesPanel = getFollowersIdsPropertiesPanel;
        this.getFriendsPropertiesPanel = getFriendsPropertiesPanel;
        this.getFriendsIdsPropertiesPanel = getFriendsIdsPropertiesPanel;
        this.getHomeTimeLinePropertiesPanel = getHomeTimeLinePropertiesPanel;
        this.getMentionsTimeLinePropertiesPanel = getMentionsTimeLinePropertiesPanel;
        this.getRetweetsOfMinePropertiesPanel = getRetweetsOfMinePropertiesPanel;
        this.getSentDirectMessagesPropertiesPanel = getSentDirectMessagesPropertiesPanel;
        this.getTopTrendPlacesPropertiesPanel = getTopTrendPlacesPropertiesPanel;
        this.getUserTimeLinePropertiesPanel = getUserTimeLinePropertiesPanel;
        this.retweetPropertiesPanel = retweetPropertiesPanel;
        this.searchTwitterPropertiesPanel = searchTwitterPropertiesPanel;
        this.searchPlacesPropertiesPanel = searchPlacesPropertiesPanel;
        this.sendDirectMessagePropertiesPanel = sendDirectMessagePropertiesPanel;
        this.showStatusPropertiesPanel = showStatusPropertiesPanel;
        this.updateStatusPropertiesPanel = updateStatusPropertiesPanel;
        this.initTwitterPropertiesPanel = initTwitterPropertiesPanel;
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        manager.register(DestroyStatus.class, destroyStatusPropertiesPanel);
        manager.register(GetClosesTrends.class, getClosesTrendsPropertiesPanel);
        manager.register(GetDirectMessages.class, getDirectMessagesPropertiesPanel);
        manager.register(GetFollowers.class, getFollowersPropertiesPanel);
        manager.register(GetFollowersIds.class, getFollowersIdsPropertiesPanel);
        manager.register(GetFriends.class, getFriendsPropertiesPanel);
        manager.register(GetFriendsIds.class, getFriendsIdsPropertiesPanel);
        manager.register(GetHomeTimeLine.class, getHomeTimeLinePropertiesPanel);
        manager.register(GetMentionsTimeLine.class, getMentionsTimeLinePropertiesPanel);
        manager.register(GetRetweetsOfMine.class, getRetweetsOfMinePropertiesPanel);
        manager.register(GetSentDirectMessages.class, getSentDirectMessagesPropertiesPanel);
        manager.register(GetTopTrendPlaces.class, getTopTrendPlacesPropertiesPanel);
        manager.register(GetUserTimeLine.class, getUserTimeLinePropertiesPanel);
        manager.register(InitTwitter.class, initTwitterPropertiesPanel);
        manager.register(Retweet.class, retweetPropertiesPanel);
        manager.register(SearchTwitter.class, searchTwitterPropertiesPanel);
        manager.register(SearchPlaces.class, searchPlacesPropertiesPanel);
        manager.register(SendDirectMessage.class, sendDirectMessagePropertiesPanel);
        manager.register(ShowStatus.class, showStatusPropertiesPanel);
        manager.register(UpdateStatus.class, updateStatusPropertiesPanel);
    }

}