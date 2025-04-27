package com.fitnessfriends.service;
import com.fitnessfriends.dto.StravaTokenResponse;
import com.fitnessfriends.entity.StravaAccount;
import com.fitnessfriends.repository.StravaAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StravaAccountService {

    private static final Logger logger = LoggerFactory.getLogger(StravaAccountService.class);

    @Autowired
    private StravaAccountRepository stravaAccountRepository;

    public StravaTokenResponse exchangeAuthorizationCode(String authorizationCode) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
    
        // Prepare the request payload
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", "153559");
        requestBody.add("client_secret", "4af20622bdca9abe169f54226bdb9327c3a32a91");
        requestBody.add("code", authorizationCode);
        requestBody.add("grant_type", "authorization_code");
    
        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    
        // Create the HTTP entity with headers and payload
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestBody, headers);
    
        logger.debug("Client ID: {}", "153559");
        logger.debug("Client Secret: {}", "your_client_secret_here");
        logger.debug("Authorization Code: {}", authorizationCode);
        logger.debug("Request payload: {}", requestBody);
    
        // Send the POST request to Strava's token endpoint
        ResponseEntity<StravaTokenResponse> response = restTemplate.postForEntity(
                "https://www.strava.com/oauth/token",
                httpEntity,
                StravaTokenResponse.class
        );
    
        // Log the response for debugging
        logger.debug("Response status code: {}", response.getStatusCode());
        logger.debug("Response body: {}", response.getBody());
    
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new Exception("Failed to exchange authorization code: " + response.getBody());
        }
    
        return response.getBody();
    }
    public void saveStravaTokens(int userId, StravaTokenResponse tokenResponse) {
        StravaAccount stravaAccount = stravaAccountRepository.findByUserId(userId);

        if (stravaAccount == null) {
            stravaAccount = new StravaAccount();
            stravaAccount.setUserId(userId);
        }

        stravaAccount.setStravaUserId(tokenResponse.getStravaUserId());
        stravaAccount.setAccessToken(tokenResponse.getAccessToken());
        stravaAccount.setRefreshToken(tokenResponse.getRefreshToken());
        stravaAccount.setExpiresAt(tokenResponse.getExpiresAt());

        stravaAccountRepository.save(stravaAccount);
    }

    // Retrieve a Strava account for a specific user
    public StravaAccount getStravaAccountByUserId(int userId) {
        // Corrected method name
        return stravaAccountRepository.findByUserId(userId);
    }

    // Link a new Strava account
    public StravaAccount linkStravaAccount(StravaAccount stravaAccount) {
        return stravaAccountRepository.save(stravaAccount);
    }
}