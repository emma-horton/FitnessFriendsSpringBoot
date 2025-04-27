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

@Service
public class StravaAccountService {

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

        // Send the POST request to Strava's token endpoint
        ResponseEntity<StravaTokenResponse> response = restTemplate.postForEntity(
                "https://www.strava.com/oauth/token",
                new HttpEntity<>(requestBody),
                StravaTokenResponse.class
        );

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