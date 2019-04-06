package com.gigya.android.sdk.interruption;

import com.gigya.android.sdk.GigyaLoginCallback;
import com.gigya.android.sdk.interruption.tfa.GigyaTFARegistrationResolver;
import com.gigya.android.sdk.interruption.tfa.GigyaTFAVerificationResolver;
import com.gigya.android.sdk.network.GigyaApiResponse;
import com.gigya.android.sdk.providers.IProviderFactory;
import com.gigya.android.sdk.services.IApiService;

public class InterruptionResolverFactory implements IInterruptionResolverFactory {

    final private IApiService _apiService;
    final private IProviderFactory _providerFactory;

    public InterruptionResolverFactory(IApiService apiService, IProviderFactory providerFactory) {
        _apiService = apiService;
        _providerFactory = providerFactory;
    }

    @Override
    public GigyaLinkAccountsResolver createLinkAccountsResolver(GigyaApiResponse originalResponse, GigyaLoginCallback loginCallback) {
        return new GigyaLinkAccountsResolver(_providerFactory, _apiService, originalResponse, loginCallback);
    }

    @Override
    public GigyaTFARegistrationResolver createTFARegistrationResolver(GigyaApiResponse originalResponse, GigyaLoginCallback loginCallback) {
        return new GigyaTFARegistrationResolver<>(_apiService, originalResponse, loginCallback);
    }

    @Override
    public GigyaTFAVerificationResolver createTFAVerificationResolver(GigyaApiResponse originalResponse, GigyaLoginCallback loginCallback) {
        return new GigyaTFAVerificationResolver<>(_apiService, originalResponse, loginCallback);
    }
}
