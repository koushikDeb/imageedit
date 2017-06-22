package koushik.com.imageedit;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.adobe.creativesdk.foundation.AdobeCSDKFoundation;
import com.adobe.creativesdk.foundation.auth.IAdobeAuthClientCredentials;

/**
 * Created by koushikdeb on 06/06/17.
 */

public class MainApplication extends Application implements IAdobeAuthClientCredentials {



    private static final String CREATIVE_SDK_CLIENT_ID      = "fa0a529b941c4b93a3725f2268dc88d0";
    private static final String CREATIVE_SDK_CLIENT_SECRET  = "2ef63733-e333-41d0-8e7a-f59ff0eee773";
    private static final String CREATIVE_SDK_REDIRECT_URI   = "ams+9667d07d8879d941984f09591511423eed2f8d19://adobeid/fa0a529b941c4b93a3725f2268dc88d0";
    private static final String[] CREATIVE_SDK_SCOPES       = {"email", "profile", "address"};

    @Override
    public void onCreate() {
        super.onCreate();
        AdobeCSDKFoundation.initializeCSDKFoundation(getApplicationContext());
    }
    @Override
    public String getClientID() {
        return CREATIVE_SDK_CLIENT_ID;
    }

    @Override
    public String getClientSecret() {
        return CREATIVE_SDK_CLIENT_SECRET;
    }

    @Override
    public String[] getAdditionalScopesList() {
        return CREATIVE_SDK_SCOPES;
    }

    @Override
    public String getRedirectURI() {
        return CREATIVE_SDK_REDIRECT_URI;
    }

}
