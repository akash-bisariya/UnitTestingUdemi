package com.techyourchance.testdoublesfundamentals.exercise4;

import com.techyourchance.testdoublesfundamentals.example4.networking.NetworkErrorException;
import com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync;
import com.techyourchance.testdoublesfundamentals.exercise4.users.User;
import com.techyourchance.testdoublesfundamentals.exercise4.users.UsersCache;

import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.techyourchance.testdoublesfundamentals.exercise4.FetchUserProfileUseCaseSync.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

public class FetchUserProfileUseCaseSyncTest {
    private FetchUserProfileUseCaseSync SUT;
    private UserProfileHttpEndpointSyncTd mUserProfileHttpEndpointSync;
    private UsersCacheTd mUsersCache;
    private static final String USER_ID = "userId";
    private static final String USER_FULL_NAME = "fullName";
    private static final String USER_IMAGE_URL = "url";

    @Before
    public void setUp() throws Exception {
        mUserProfileHttpEndpointSync = new UserProfileHttpEndpointSyncTd();
        mUsersCache = new UsersCacheTd();
        SUT = new FetchUserProfileUseCaseSync(mUserProfileHttpEndpointSync, mUsersCache);
    }

    @Test
    public void fetchUserProfile_success_userId_passed() {
        SUT.fetchUserProfileSync(USER_ID);
        Assert.assertThat(mUserProfileHttpEndpointSync.mUserId, is(USER_ID));
    }

    @Test
    public void fetchUserProfile_success_user_cached() {
        SUT.fetchUserProfileSync(USER_ID);
        User cachedUser = mUsersCache.getUser(USER_ID);
        Assert.assertThat(cachedUser.getUserId(), is(USER_ID));
        Assert.assertThat(cachedUser.getFullName(), is(USER_FULL_NAME));
        Assert.assertThat(cachedUser.getImageUrl(), is(USER_IMAGE_URL));
    }

    @Test
    public void fetchUserProfile_success_success_returned() {
        UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        Assert.assertThat(result, is(UseCaseResult.SUCCESS));
    }

    @Test
    public void fetchUserProfile_general_error_userNotCached() {
        mUserProfileHttpEndpointSync.mIsGeneralError = true;
        SUT.fetchUserProfileSync(USER_ID);
        Assert.assertThat(mUsersCache.getUser(USER_ID), is(nullValue()));
    }

    @Test
    public void fetchUserProfile_auth_error_userNotCached() {
        mUserProfileHttpEndpointSync.mIsAuthError = true;
        SUT.fetchUserProfileSync(USER_ID);
        Assert.assertThat(mUsersCache.getUser(USER_ID), is(nullValue()));
    }

    @Test
    public void fetchUserProfile_sever_error_userNotCached() {
        mUserProfileHttpEndpointSync.mIsServerError = true;
        SUT.fetchUserProfileSync(USER_ID);
        Assert.assertThat(mUsersCache.getUser(USER_ID), is(nullValue()));
    }

    @Test
    public void fetchUserProfile_server_error_failure_returned() {
        mUserProfileHttpEndpointSync.mIsServerError = true;
        UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        Assert.assertThat(result,is(UseCaseResult.FAILURE));
    }

    @Test
    public void fetchUserProfile_auth_error_failure_returned() {
        mUserProfileHttpEndpointSync.mIsAuthError = true;
        UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        Assert.assertThat(result,is(UseCaseResult.FAILURE));
    }

    @Test
    public void fetchUserProfile_general_error__failure_returned() {
        mUserProfileHttpEndpointSync.mIsGeneralError = true;
        UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        Assert.assertThat(result,is(UseCaseResult.FAILURE));
    }

    @Test
    public void fetchUserProfile_network_error_returned() {
        mUserProfileHttpEndpointSync.mIsNetworkError = true;
        UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        Assert.assertThat(result,is(UseCaseResult.NETWORK_ERROR));
    }

    // ------------------------------------------------------------------------------------------

    private static class UserProfileHttpEndpointSyncTd implements UserProfileHttpEndpointSync {

        String mUserId;
        Boolean mIsGeneralError = false;
        Boolean mIsServerError = false;
        Boolean mIsAuthError = false;
        Boolean mIsNetworkError = false;

        @Override
        public EndpointResult getUserProfile(String userId) throws NetworkErrorException {
            mUserId = userId;
            if (mIsGeneralError)
                return new EndpointResult(EndpointResultStatus.GENERAL_ERROR, "", "", "");
            else if (mIsAuthError)
                return new EndpointResult(EndpointResultStatus.AUTH_ERROR, "", "", "");
            else if (mIsServerError)
                return new EndpointResult(EndpointResultStatus.SERVER_ERROR, "", "", "");
            else if(mIsNetworkError)
                throw new NetworkErrorException();
            else
                return new EndpointResult(EndpointResultStatus.SUCCESS, USER_ID, USER_FULL_NAME, USER_IMAGE_URL);
        }
    }


    private static class UsersCacheTd implements UsersCache {

        ArrayList<User> mUsers = new ArrayList<>(1);

        @Override
        public void cacheUser(User user) {
            if (!mUsers.isEmpty()) {
                mUsers.remove(getUser(user.getUserId()));
            }
            mUsers.add(user);
        }

        @Nullable
        @Override
        public User getUser(String userId) {
            for (User user : mUsers) {
                if (user.getUserId().equals(userId))
                    return user;
            }
            return null;
        }
    }
}