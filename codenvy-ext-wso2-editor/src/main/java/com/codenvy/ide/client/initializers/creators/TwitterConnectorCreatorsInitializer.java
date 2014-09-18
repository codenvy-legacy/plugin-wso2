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

package com.codenvy.ide.client.initializers.creators;

import com.codenvy.ide.client.constants.TwitterConnectorCreatingState;
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
import com.codenvy.ide.client.initializers.Initializer;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Andrey Plotnikov
 */
public class TwitterConnectorCreatorsInitializer implements Initializer {

    private final ElementCreatorsManager          manager;
    private final Provider<DestroyStatus>         destroyStatusProvider;
    private final Provider<GetClosesTrends>       getClosesTrendsProvider;
    private final Provider<GetDirectMessages>     getDirectMessagesProvider;
    private final Provider<GetFollowers>          getFollowersProvider;
    private final Provider<GetFollowersIds>       getFollowersIdsProvider;
    private final Provider<GetFriends>            getFriendsProvider;
    private final Provider<GetFriendsIds>         getFriendsIdsProvider;
    private final Provider<GetHomeTimeLine>       getHomeTimeLineProvider;
    private final Provider<GetMentionsTimeLine>   getMentionsTimeLineProvider;
    private final Provider<GetRetweetsOfMine>     getRetweetsOfMineProvider;
    private final Provider<GetSentDirectMessages> getSentDirectMessagesProvider;
    private final Provider<GetTopTrendPlaces>     getTopTrendPlacesProvider;
    private final Provider<GetUserTimeLine>       getUserTimeLineProvider;
    private final Provider<InitTwitter>           initTwitterProvider;
    private final Provider<Retweet>               retweetProvider;
    private final Provider<SearchTwitter>         searchTwitterProvider;
    private final Provider<SearchPlaces>          searchPlacesProvider;
    private final Provider<SendDirectMessage>     sendDirectMessageProvider;
    private final Provider<ShowStatus>            showStatusProvider;
    private final Provider<UpdateStatus>          updateStatusProvider;

    @Inject
    public TwitterConnectorCreatorsInitializer(ElementCreatorsManager manager,
                                               Provider<DestroyStatus> destroyStatusProvider,
                                               Provider<GetClosesTrends> getClosesTrendsProvider,
                                               Provider<GetDirectMessages> getDirectMessagesProvider,
                                               Provider<GetFollowers> getFollowersProvider,
                                               Provider<GetFollowersIds> getFollowersIdsProvider,
                                               Provider<GetFriends> getFriendsProvider,
                                               Provider<GetFriendsIds> getFriendsIdsProvider,
                                               Provider<GetHomeTimeLine> getHomeTimeLineProvider,
                                               Provider<GetMentionsTimeLine> getMentionsTimeLineProvider,
                                               Provider<GetRetweetsOfMine> getRetweetsOfMineProvider,
                                               Provider<GetSentDirectMessages> getSentDirectMessagesProvider,
                                               Provider<GetTopTrendPlaces> getTopTrendPlacesProvider,
                                               Provider<GetUserTimeLine> getUserTimeLineProvider,
                                               Provider<InitTwitter> initTwitterProvider,
                                               Provider<Retweet> retweetProvider,
                                               Provider<SearchTwitter> searchTwitterProvider,
                                               Provider<SearchPlaces> searchPlacesProvider,
                                               Provider<SendDirectMessage> sendDirectMessageProvider,
                                               Provider<ShowStatus> showStatusProvider,
                                               Provider<UpdateStatus> updateStatusProvider) {
        this.manager = manager;
        this.destroyStatusProvider = destroyStatusProvider;
        this.getClosesTrendsProvider = getClosesTrendsProvider;
        this.getDirectMessagesProvider = getDirectMessagesProvider;
        this.getFollowersProvider = getFollowersProvider;
        this.getFollowersIdsProvider = getFollowersIdsProvider;
        this.getFriendsProvider = getFriendsProvider;
        this.getFriendsIdsProvider = getFriendsIdsProvider;
        this.getHomeTimeLineProvider = getHomeTimeLineProvider;
        this.getMentionsTimeLineProvider = getMentionsTimeLineProvider;
        this.getRetweetsOfMineProvider = getRetweetsOfMineProvider;
        this.getSentDirectMessagesProvider = getSentDirectMessagesProvider;
        this.getTopTrendPlacesProvider = getTopTrendPlacesProvider;
        this.getUserTimeLineProvider = getUserTimeLineProvider;
        this.initTwitterProvider = initTwitterProvider;
        this.retweetProvider = retweetProvider;
        this.searchTwitterProvider = searchTwitterProvider;
        this.searchPlacesProvider = searchPlacesProvider;
        this.sendDirectMessageProvider = sendDirectMessageProvider;
        this.showStatusProvider = showStatusProvider;
        this.updateStatusProvider = updateStatusProvider;
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        manager.register(DestroyStatus.ELEMENT_NAME,
                         DestroyStatus.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.DESTROY_STATUS,
                         destroyStatusProvider);

        manager.register(GetClosesTrends.ELEMENT_NAME,
                         GetClosesTrends.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.GET_CLOTHES_TRENDS,
                         getClosesTrendsProvider);

        manager.register(GetDirectMessages.ELEMENT_NAME,
                         GetDirectMessages.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.GET_DIRECT_MESSAGES,
                         getDirectMessagesProvider);

        manager.register(GetFollowers.ELEMENT_NAME,
                         GetFollowers.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.GET_FOLLOWERS,
                         getFollowersProvider);

        manager.register(GetFollowersIds.ELEMENT_NAME,
                         GetFollowersIds.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.GET_FOLLOWERS_IDS,
                         getFollowersIdsProvider);

        manager.register(GetFriends.ELEMENT_NAME,
                         GetFriends.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.GET_FRIENDS,
                         getFriendsProvider);

        manager.register(GetFriendsIds.ELEMENT_NAME,
                         GetFriendsIds.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.GET_FRIENDS_IDS,
                         getFriendsIdsProvider);

        manager.register(GetHomeTimeLine.ELEMENT_NAME,
                         GetHomeTimeLine.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.GET_HOME_TIME_LINE,
                         getHomeTimeLineProvider);

        manager.register(GetMentionsTimeLine.ELEMENT_NAME,
                         GetMentionsTimeLine.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.GET_MENTIONS_TIME_LINE,
                         getMentionsTimeLineProvider);

        manager.register(GetRetweetsOfMine.ELEMENT_NAME,
                         GetRetweetsOfMine.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.GET_RETWEETS_OF_MINE,
                         getRetweetsOfMineProvider);

        manager.register(GetSentDirectMessages.ELEMENT_NAME,
                         GetSentDirectMessages.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.GET_SENT_DIRECT_MESSAGE,
                         getSentDirectMessagesProvider);

        manager.register(GetTopTrendPlaces.ELEMENT_NAME,
                         GetTopTrendPlaces.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.GET_TOP_TREND_PLACES,
                         getTopTrendPlacesProvider);

        manager.register(GetUserTimeLine.ELEMENT_NAME,
                         GetUserTimeLine.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.GET_USER_TIME_LINE,
                         getUserTimeLineProvider);

        manager.register(InitTwitter.ELEMENT_NAME,
                         InitTwitter.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.INIT,
                         initTwitterProvider);

        manager.register(Retweet.ELEMENT_NAME,
                         Retweet.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.RETWEET,
                         retweetProvider);

        manager.register(SearchTwitter.ELEMENT_NAME,
                         SearchTwitter.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.SEARCH,
                         searchTwitterProvider);

        manager.register(SearchPlaces.ELEMENT_NAME,
                         SearchPlaces.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.SEARCH_PLACES,
                         searchPlacesProvider);

        manager.register(SendDirectMessage.ELEMENT_NAME,
                         SendDirectMessage.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.SEND_DIRECT_MESSAGE,
                         sendDirectMessageProvider);

        manager.register(ShowStatus.ELEMENT_NAME,
                         ShowStatus.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.SHOW_STATUS,
                         showStatusProvider);

        manager.register(UpdateStatus.ELEMENT_NAME,
                         UpdateStatus.SERIALIZATION_NAME,
                         TwitterConnectorCreatingState.UPDATE_STATUS,
                         updateStatusProvider);
    }

}
