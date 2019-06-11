package io.swagger.client;

import io.swagger.client.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.DataSetApi;

import java.io.File;
import java.util.*;

public class DataSetApiExample {

    public static void main(String[] args) {
        
        DataSetApi apiInstance = new DataSetApi();
        String datasetId = "datasetId_example"; // String | 
        try {
            Answer result = apiInstance.dataSetGetCheat(datasetId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DataSetApi#dataSetGetCheat");
            e.printStackTrace();
        }
    }
}