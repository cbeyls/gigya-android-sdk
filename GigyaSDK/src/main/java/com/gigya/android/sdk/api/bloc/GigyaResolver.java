package com.gigya.android.sdk.api.bloc;

import android.support.annotation.StringDef;

import com.gigya.android.sdk.GigyaLogger;
import com.gigya.android.sdk.GigyaLoginCallback;
import com.gigya.android.sdk.model.account.GigyaAccount;
import com.gigya.android.sdk.network.GigyaApiResponse;
import com.gigya.android.sdk.network.GigyaError;
import com.gigya.android.sdk.services.ApiService;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.SoftReference;

public abstract class GigyaResolver<A extends GigyaAccount> {

    private static final String LOG_TAG = "GigyaResolver";

    final ApiService<A> _apiService;

    final SoftReference<GigyaLoginCallback<? extends GigyaAccount>> _loginCallback;

    final GigyaApiResponse _originalResponse;

    final String _regToken;

    GigyaResolver(ApiService<A> apiService, GigyaApiResponse originalResponse, GigyaLoginCallback<? extends GigyaAccount> loginCallback) {
        _apiService = apiService;
        _originalResponse = originalResponse;
        _regToken = originalResponse.getField("regToken", String.class);
        _loginCallback = new SoftReference<GigyaLoginCallback<? extends GigyaAccount>>(loginCallback);
    }

    protected abstract void init();

    /**
     * Clear the resolver from data. Release login callback.
     */
    public abstract void clear();

    boolean checkCallback() {
        if (_loginCallback.get() != null) {
            return true;
        }
        GigyaLogger.debug(LOG_TAG, "checkCallback: GigyaLoginCallback reference is null -> resolver flow broken");
        return false;
    }

    void forwardError(GigyaError error) {
        if (checkCallback()) {
            _loginCallback.get().onError(error);
            // Forwarding an error must result in clearing the login callback reference.
            // Login/Register flow will have to restart.
            clear();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({TFA, LINK_ACCOUNTS})
    public @interface ResolverType {

    }

    public static final String TFA = "tfa_resolver";
    public static final String LINK_ACCOUNTS = "link_accounts_resolver";
}
