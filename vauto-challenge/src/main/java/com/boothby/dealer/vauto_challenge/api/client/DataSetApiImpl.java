package com.boothby.dealer.vauto_challenge.api.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.boothby.dealer.vauto_challenge.api.model.Answer;
import com.boothby.dealer.vauto_challenge.api.model.AnswerResponse;
import com.boothby.dealer.vauto_challenge.api.model.ApiException;
import com.boothby.dealer.vauto_challenge.api.model.DatasetIdResponse;
import com.boothby.dealer.vauto_challenge.config.AppConfig;

public class DataSetApiImpl implements DataSetApi {

    private AppConfig appConfig;
    private RestTemplate restTemplate;
    
    public DataSetApiImpl(AppConfig appConfig, RestTemplate restTemplate) {
        this.appConfig = appConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public DatasetIdResponse getDataSetId() throws ApiException {
        ResponseEntity<DatasetIdResponse> response;
        try {
            response = restTemplate.getForEntity(String.format("%s/datasetId", appConfig.getApiBaseUrl()), DatasetIdResponse.class);
        } catch (RestClientException e) {
            throw new ApiException("Error received get dataset id", e);
        }
        return response.getBody();
    }

    @Override
    public AnswerResponse postAnswer(String datasetId, Answer answer)  throws ApiException{
        AnswerResponse answerResponse;
        try {
            HttpEntity<Answer> request = new HttpEntity<>(answer);
            answerResponse = restTemplate.postForObject(String.format("%s/%s/answer", appConfig.getApiBaseUrl(), datasetId), 
                    request, AnswerResponse.class);
        } catch (RestClientException e) {
            throw new ApiException("Error received posting answer", e);
        }
        return answerResponse;
    }
}
