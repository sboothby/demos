package com.boothby.dealer.vauto_challenge.api.client;

import com.boothby.dealer.vauto_challenge.api.model.Answer;
import com.boothby.dealer.vauto_challenge.api.model.AnswerResponse;
import com.boothby.dealer.vauto_challenge.api.model.ApiException;
import com.boothby.dealer.vauto_challenge.api.model.DatasetIdResponse;
import com.boothby.dealer.vauto_challenge.config.AppConfig;

public class DataSetApiImpl implements DataSetApi {

    private AppConfig appConfig;
    
    public DataSetApiImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public DatasetIdResponse getDataSetId() throws ApiException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AnswerResponse postAnswer(String datasetId, Answer answer)  throws ApiException{
        // TODO Auto-generated method stub
        return null;
    }

}
