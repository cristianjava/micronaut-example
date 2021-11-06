package com.example.services.impl;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.ChangePasswordResult;
import com.amazonaws.services.cognitoidp.model.GetUserResult;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.example.vo.*;
import com.example.vo.factory.InitiateAuthFactory;
import io.micronaut.context.annotation.Value;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

@Singleton
public class CognitoService {

    @Value("${aws.cognito.client-id}")
    private String clientIdCons;
    @Value("${aws.cognito.client-secret}")
    private String clientSecretCons;
    @Value("${aws.cognito.user-pool-id}")
    private String userPoolIdCons;

    private CognitoIdentityProviderClient cognitoIdentityProviderClient;
    private final AWSCognitoIdentityProvider client;
    private final InitiateAuthFactory initiateAuthFactory;

    private static SecureRandom SECURE_RANDOM;

    private final static String HMAC_SHAC256_ALGORIT = "HmacSHA256";
    private final static String CALCULATE_HASH_ERROR = "Error while calculating the secret hash";
    private final static String USERNAME = "USERNAME";
    private final static String PASSWORD = "PASSWORD";
    private final static String SECRET_HASH = "SECRET_HASH";

    public CognitoService(AWSCognitoIdentityProvider client, InitiateAuthFactory initiateAuthFactory) {
        this.client = client;
        this.initiateAuthFactory = initiateAuthFactory;
    }

    AwsBasicCredentials awsCred = AwsBasicCredentials.create("AKIAVYM47NFPNMOQGQRJ", "9PIG0K4Uy/wUPMRsk+A+UHcD0Jj0fPo/agy6mZ29");
    CognitoIdentityProviderClient cognitoClient = CognitoIdentityProviderClient.builder()
            .credentialsProvider(StaticCredentialsProvider.create(awsCred))
            .region(Region.US_EAST_2)
            .build();

    static {
        try {
            SECURE_RANDOM = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getUserPoolUsers() {
        ListUserPoolsResponse response = null;
        try {
            ListUserPoolsRequest request = ListUserPoolsRequest.builder()
                    .maxResults(10)
                    .build();
            response = cognitoClient.listUserPools(request);
            String token = response.nextToken();
            for (UserPoolDescriptionType userpool : response.userPools()) {
                System.out.println("User pool " + userpool.name() + ", User ID " + userpool.id() + ", Status " + userpool.status());
            }
        } catch (CognitoIdentityProviderException e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        } finally {
            cognitoClient.close();
        }
        return response.userPools().get(1).name();
    }

    public String createPoolClient(String clientName, String userPoolId) {
        CreateUserPoolClientResponse response = null;
        try {
            response = cognitoClient.createUserPoolClient(
                    CreateUserPoolClientRequest.builder()
                            .clientName(clientName)
                            .userPoolId(userPoolId)
                            .build()
            );
            System.out.println("User pool " + response.userPoolClient().clientName() + " created. ID: " + response.userPoolClient().clientId());
        } catch (CognitoIdentityProviderException e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "User pool " + response.userPoolClient().clientName() + " created. ID: " + response.userPoolClient().clientId();
    }

    public void createNewUser(String userPoolId, String name, String email, String password) {
        try{
            AttributeType userAttrs = AttributeType.builder()
                    .name("email").value(email)
                    .name("custom:idcard").value("1038333777")
                    .build();
            AdminCreateUserRequest userRequest = AdminCreateUserRequest.builder()
                    .userPoolId(userPoolId)
                    .username(name)
                    .temporaryPassword(password)
                    .userAttributes(userAttrs)
                    .messageAction("SUPPRESS")
                    .build() ;
            AdminCreateUserResponse response = cognitoClient.adminCreateUser(userRequest);
            System.out.println("User " + response.user().username() + "is created. Status: " + response.user().userStatus());
        } catch (CognitoIdentityProviderException e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        } finally {
            cognitoClient.close();
        }
    }

    public String forgotPassword(String clientId, String clientSecret, String userName) {

        ListUserPoolsRequest listUserPoolsRequest = ListUserPoolsRequest.builder()
                .maxResults(10)
                .build();
        ListUserPoolsResponse response = cognitoClient.listUserPools(listUserPoolsRequest);

        /**ConfirmForgotPasswordRequest forgotPasswordRequest = ConfirmForgotPasswordRequest.builder()
                .clientId("")
                .username("")
                .password("")
                .confirmationCode("")
                .build();
        ConfirmForgotPasswordResponse response2 = cognitoClient.confirmForgotPassword(forgotPasswordRequest);
           */


        /**VerifyUserAttributeRequest verifyUserAttributeRequest = VerifyUserAttributeRequest.builder()
                .accessToken("")
                .code("")
                .attributeName("")
                .build();
        VerifyUserAttributeResponse responseVerify = cognitoClient.verifyUserAttribute(verifyUserAttributeRequest);
        */

        /**AttributeType userAttrs = AttributeType.builder()
                .name("email").value(userName)
                .name("custom:idCardType").value("CC")
                .name("custom:idCardNumber").value("1038333777")
                .name("phone number").value("+573233913832")
                .build();

        SignUpRequest signUpRequest = SignUpRequest.builder()
                .clientId(clientId)
                .username(userName)
                .secretHash(calculateSecretHash(clientId, clientSecret, userName))
                .password("*#")
                .userAttributes(userAttrs)
                .build();
        SignUpResponse responseSignUp = cognitoClient.signUp(signUpRequest);
*/
        /**ConfirmSignUpRequest confirmSignUpRequest = ConfirmSignUpRequest.builder()
                .clientId(clientId)
                .username(userName)
                .secretHash(calculateSecretHash(clientId, clientSecret, userName))
                .build();
        ConfirmSignUpResponse responseConfirmSingUp = cognitoClient.confirmSignUp(confirmSignUpRequest);
*/

        /**AdminSetUserPasswordRequest adminSetUserPasswordRequest = AdminSetUserPasswordRequest.builder()
                .password("Abcdef123*1")
                .permanent(true)
                .username(userName)
                .userPoolId("us-east-2_qb9ks3Rx3")
                .build();
        AdminSetUserPasswordResponse adminSetUserPasswordResponse = cognitoClient.adminSetUserPassword(adminSetUserPasswordRequest);
*/

        AdminSetUserPasswordRequest adminSetUserPasswordRequest = AdminSetUserPasswordRequest.builder()
                .password("Abcdef123*")
                .permanent(true)
                .username("liliana.palacios@avaldigitallabs.com")
                .userPoolId("us-east-2_qb9ks3Rx3")
                .build();
        AdminSetUserPasswordResponse adminSetUserPasswordResponse = cognitoClient.adminSetUserPassword(adminSetUserPasswordRequest);

        HashMap map = new HashMap();
        map.put("USERNAME",userName);
        map.put("PASSWORD","Abcdef123*");
        map.put("SECRET_HASH", calculateSecretHash(clientId, clientSecret, userName));

        InitiateAuthRequest initiateAuthRequest = InitiateAuthRequest.builder()
                .clientId(clientId)
                .authFlow(AuthFlowType.USER_SRP_AUTH)
                .authParameters(map)
                .build();
        InitiateAuthResponse initiateAuthResponse = cognitoClient.initiateAuth(initiateAuthRequest);


        AdminInitiateAuthRequest adminInitiateAuthRequest = AdminInitiateAuthRequest.builder()
                .clientId(clientId)
                .userPoolId("us-east-2_qb9ks3Rx3")
                .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .authParameters(map)
                .build();
        AdminInitiateAuthResponse adminInitiateAuthResponse = cognitoClient.adminInitiateAuth(adminInitiateAuthRequest);

        RespondToAuthChallengeRequest respondToAuthChallengeRequest = RespondToAuthChallengeRequest.builder()
                .clientId(clientId)
                .challengeName(ChallengeNameType.PASSWORD_VERIFIER)
                .challengeResponses(map)
                .build();
        RespondToAuthChallengeResponse respondToAuthChallengeResponse = cognitoClient.respondToAuthChallenge(respondToAuthChallengeRequest);

        ChangePasswordRequest changePasswordRequest = ChangePasswordRequest.builder()
                .previousPassword("Abcdef123*")
                .proposedPassword("Abcdef123*1")
                .accessToken("d74f57ff-df3c-4a53-a0e0-a4cd927ff65e")
                .build();
        ChangePasswordResponse responseChangePasswordResponse = cognitoClient.changePassword(changePasswordRequest);

        /**ConfirmForgotPasswordRequest confirmForgotPasswordRequest = ConfirmForgotPasswordRequest.builder()
                .clientId(clientId)
                .username(userName)
                .secretHash(calculateSecretHash(clientId, clientSecret, userName))
                .confirmationCode("111111")
                .password("PUNKsk8199309*#")
                .build();
        ConfirmForgotPasswordResponse responseConfirmForgotPasswordResponse = cognitoClient.confirmForgotPassword(confirmForgotPasswordRequest);
*/
        cognitoClient.close();
        return "";
    }

    public String changePassword(String accessToken, String previousPass, String proposedPass) {
        com.amazonaws.services.cognitoidp.model.ChangePasswordRequest request = new com.amazonaws.services.cognitoidp.model.ChangePasswordRequest()
                .withAccessToken(accessToken)
                .withPreviousPassword(previousPass)
                .withProposedPassword(proposedPass);
        ChangePasswordResult result = client.changePassword(request);
        return "";
    }

    public String initAuth(String user, String pass) {
        InitiateAuthResult result = null;
        Map<String, String> authParams = new HashMap<>();
        authParams.put(USERNAME, user);
        authParams.put(PASSWORD, pass);
        authParams.put(SECRET_HASH, calculateSecretHash(clientIdCons, clientSecretCons, user));

        com.amazonaws.services.cognitoidp.model.InitiateAuthRequest InitiateAuthRequest = new com.amazonaws.services.cognitoidp.model.InitiateAuthRequest()
                .withClientId(clientIdCons)
                .withAuthFlow(com.amazonaws.services.cognitoidp.model.AuthFlowType.USER_PASSWORD_AUTH)
                .withAuthParameters(authParams);
        try {
            result = client.initiateAuth(InitiateAuthRequest);
        }  catch (com.amazonaws.services.cognitoidp.model.NotAuthorizedException e) {
            System.out.println("Error");
        }
        return result.getAuthenticationResult().getAccessToken();
    }

    public static String calculateSecretHash(String userPoolClientId, String userPoolClientSecret, String userName) {
        final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

        SecretKeySpec signingKey = new SecretKeySpec(
                userPoolClientSecret.getBytes(StandardCharsets.UTF_8),
                HMAC_SHA256_ALGORITHM);
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);
            mac.update(userName.getBytes(StandardCharsets.UTF_8));
            byte[] rawHmac = mac.doFinal(userPoolClientId.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Error while calculating ");
        }
    }

    public String welcomeNotify() {

        return "";
    }

    public String signUp(String clientId, String userPoolClientSecret, String password, String userName) {
        Base64.Decoder userDecoder = Base64.getDecoder();
        byte[] decodedByteArray = userDecoder.decode(userName);
        String userNameDecoder = new String(decodedByteArray);
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .clientId(clientId)
                .username(userNameDecoder)
                .password("Abcdef123")
                .secretHash(calculateSecretHash(clientId, userPoolClientSecret, userNameDecoder))
                .build();
        SignUpResponse signUpResponse = cognitoClient.signUp(signUpRequest);
        return "";
    }

    public String confirmSignUp(String clientId, String userPoolClientSecret, String confirmationCode, String userName) {
        ConfirmSignUpRequest confirmSignUpRequest = ConfirmSignUpRequest.builder()
                .clientId(clientId)
                .confirmationCode(confirmationCode)
                .secretHash(calculateSecretHash(clientId, userPoolClientSecret, userName))
                .username(userName)
                .build();
        ConfirmSignUpResponse confirmSignUpResponse = cognitoClient.confirmSignUp(confirmSignUpRequest);
        return  "";
    }

    public InitiateAuthResponseDTO login(LoginData loginData) {
        LoginModel loginModel = initiateAuthFactory.create(loginData);
        InitiateAuthResult result = null;
        Map<String, String> authParams = new HashMap<>();
        authParams.put(USERNAME, loginModel.getUsername());
        authParams.put(PASSWORD, loginModel.getPassword());
        authParams.put(SECRET_HASH, calculateSecretHash(clientIdCons, clientSecretCons, loginModel.getUsername()));

        com.amazonaws.services.cognitoidp.model.InitiateAuthRequest InitiateAuthRequest = new com.amazonaws.services.cognitoidp.model.InitiateAuthRequest()
                .withClientId(clientIdCons)
                .withAuthFlow(com.amazonaws.services.cognitoidp.model.AuthFlowType.USER_PASSWORD_AUTH)
                .withAuthParameters(authParams);
        try {
            result = client.initiateAuth(InitiateAuthRequest);
        }  catch (com.amazonaws.services.cognitoidp.model.NotAuthorizedException e) {
            throw new RuntimeException("Error login") ;
        }
        InitiateAuthResponseDTO responseDTO = new InitiateAuthResponseDTO(result.getAuthenticationResult().getAccessToken(),
                result.getAuthenticationResult().getRefreshToken(), result.getAuthenticationResult().getExpiresIn());
        return responseDTO;

        /**LoginModel loginModel = initiateAuthFactory.create(loginData);
        AdminInitiateAuthResponse result = null;
        Map<String, String> authParams = new HashMap<>();
        authParams.put(USERNAME, loginModel.getUsername());
        authParams.put(PASSWORD, loginModel.getPassword());
        authParams.put(SECRET_HASH, calculateSecretHash(clientIdCons, clientSecretCons, loginModel.getUsername()));

        AdminInitiateAuthRequest InitiateAuthRequest = AdminInitiateAuthRequest.builder()
                .clientId(clientIdCons)
                .userPoolId(userPoolIdCons)
                .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .authParameters(authParams).build();
        try {
            result = cognitoClient.adminInitiateAuth(InitiateAuthRequest);
        }  catch (NotAuthorizedException e) {
            throw new RuntimeException(loginModel.getUsername()) ;
        }
        InitiateAuthResponseDTO responseDTO = new InitiateAuthResponseDTO(result.authenticationResult().accessToken(),
                result.authenticationResult().refreshToken(), result.authenticationResult().expiresIn());
        return responseDTO;*/
    }

    public UserModel getInfoUser(String authorization) {
        com.amazonaws.services.cognitoidp.model.GetUserRequest getUserRequest = new com.amazonaws.services.cognitoidp.model.GetUserRequest().withAccessToken(authorization);

        GetUserResult getUserResult = client.getUser(getUserRequest);

        for(com.amazonaws.services.cognitoidp.model.AttributeType userResult : getUserResult.getUserAttributes()) {
            System.out.println(userResult.getName() + " - " + userResult.getValue());
        }

        return getUserResult.getUserAttributes().stream()
                .map(attributeType -> UserModel.builder()
                        .dataModelList(new ArrayList<DataModel>((Collection<? extends DataModel>) DataModel.builder().name(attributeType.getName()).build())).build())
                .findAny().orElse(UserModel.builder().build());
    }
}
