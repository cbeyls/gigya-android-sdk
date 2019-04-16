package com.gigya.android.sdk.providers;

import android.content.Context;

import com.gigya.android.sdk.Config;
import com.gigya.android.sdk.GigyaLoginCallback;
import com.gigya.android.sdk.account.IAccountService;
import com.gigya.android.sdk.api.IApiObservable;
import com.gigya.android.sdk.persistence.IPersistenceService;
import com.gigya.android.sdk.providers.provider.FacebookProvider;
import com.gigya.android.sdk.providers.provider.GoogleProvider;
import com.gigya.android.sdk.providers.provider.LineProvider;
import com.gigya.android.sdk.providers.provider.Provider;
import com.gigya.android.sdk.providers.provider.WeChatProvider;
import com.gigya.android.sdk.providers.provider.WebLoginProvider;
import com.gigya.android.sdk.session.ISessionService;

import static com.gigya.android.sdk.GigyaDefinitions.Providers.FACEBOOK;
import static com.gigya.android.sdk.GigyaDefinitions.Providers.GOOGLE;
import static com.gigya.android.sdk.GigyaDefinitions.Providers.LINE;
import static com.gigya.android.sdk.GigyaDefinitions.Providers.WECHAT;

public class ProviderFactory implements IProviderFactory {

    final private Context _context;
    final private Config _config;
    final private ISessionService _sessionService;
    final private IAccountService _accountService;
    final private IPersistenceService _psService;

    public ProviderFactory(Context context, Config config, ISessionService sessionService, IAccountService accountService,
                           IPersistenceService persistenceService) {
        _context = context;
        _config = config;
        _sessionService = sessionService;
        _accountService = accountService;
        _psService = persistenceService;
    }

    @Override
    public Provider providerFor(String name, IApiObservable observable, GigyaLoginCallback gigyaLoginCallback) {
        if (name != null) {
            switch (name) {
                case FACEBOOK:
                    if (FacebookProvider.isAvailable(_context)) {
                        return new FacebookProvider(_config, _sessionService, _accountService, _psService, observable, gigyaLoginCallback);
                    }
                    break;
                case GOOGLE:
                    if (GoogleProvider.isAvailable(_context)) {
                        return new GoogleProvider(_config, _sessionService, _accountService, _psService, observable, gigyaLoginCallback);
                    }
                    break;
                case LINE:
                    if (LineProvider.isAvailable(_context)) {
                        return new LineProvider(_config, _sessionService, _accountService, _psService, observable, gigyaLoginCallback);
                    }
                    break;
                case WECHAT:
                    if (WeChatProvider.isAvailable(_context)) {
                        return new WeChatProvider(_config, _sessionService, _accountService, _psService, observable, gigyaLoginCallback);
                    }
                    break;
            }
        }
        return new WebLoginProvider(_config, _sessionService, _accountService, _psService, observable, gigyaLoginCallback);
    }
}
