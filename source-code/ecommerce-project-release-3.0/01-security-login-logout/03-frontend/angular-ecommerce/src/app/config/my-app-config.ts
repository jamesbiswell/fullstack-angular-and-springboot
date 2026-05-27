export default {

    oidc: {
        clientId: '<<UPDATE-WITH-YOUR-APP-CLIENT-ID>>',
        // issuer: 'https://dev-12345678.okta.com/oauth2/default',
        issuer: 'https://<<UPDATE-WITH-YOUR-DEV-DOMAIN>>/oauth2/default',
        redirectUri: 'http://localhost:4200/login/callback',
        scopes: ['openid', 'profile', 'email']
    }

}