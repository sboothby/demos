package com.boothby.dealer.vauto_challenge.api.client;

import com.boothby.dealer.vauto_challenge.api.model.Answer;
import com.boothby.dealer.vauto_challenge.api.model.AnswerResponse;
import com.boothby.dealer.vauto_challenge.api.model.ApiException;
import com.boothby.dealer.vauto_challenge.api.model.DatasetIdResponse;

public interface DataSetApi {
    
    DatasetIdResponse getDataSetId() throws ApiException;
    
    AnswerResponse postAnswer(String datasetId, Answer answer) throws ApiException;
}
