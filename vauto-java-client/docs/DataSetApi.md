# DataSetApi

All URIs are relative to *http://api.coxauto-interview.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**dataSetGetCheat**](DataSetApi.md#dataSetGetCheat) | **GET** /api/{datasetId}/cheat | Get correct answer for dataset (cheat)
[**dataSetGetDataSetId**](DataSetApi.md#dataSetGetDataSetId) | **GET** /api/datasetId | Creates new dataset and returns its ID
[**dataSetPostAnswer**](DataSetApi.md#dataSetPostAnswer) | **POST** /api/{datasetId}/answer | Submit answer for dataset


<a name="dataSetGetCheat"></a>
# **dataSetGetCheat**
> Answer dataSetGetCheat(datasetId)

Get correct answer for dataset (cheat)

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DataSetApi;


DataSetApi apiInstance = new DataSetApi();
String datasetId = "datasetId_example"; // String | 
try {
    Answer result = apiInstance.dataSetGetCheat(datasetId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DataSetApi#dataSetGetCheat");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datasetId** | **String**|  |

### Return type

[**Answer**](Answer.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/json, text/html, application/xml, text/xml

<a name="dataSetGetDataSetId"></a>
# **dataSetGetDataSetId**
> DatasetIdResponse dataSetGetDataSetId()

Creates new dataset and returns its ID

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DataSetApi;


DataSetApi apiInstance = new DataSetApi();
try {
    DatasetIdResponse result = apiInstance.dataSetGetDataSetId();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DataSetApi#dataSetGetDataSetId");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**DatasetIdResponse**](DatasetIdResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/json, text/html, application/xml, text/xml

<a name="dataSetPostAnswer"></a>
# **dataSetPostAnswer**
> AnswerResponse dataSetPostAnswer(datasetId, answer)

Submit answer for dataset

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DataSetApi;


DataSetApi apiInstance = new DataSetApi();
String datasetId = "datasetId_example"; // String | 
Answer answer = new Answer(); // Answer | 
try {
    AnswerResponse result = apiInstance.dataSetPostAnswer(datasetId, answer);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DataSetApi#dataSetPostAnswer");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datasetId** | **String**|  |
 **answer** | [**Answer**](Answer.md)|  |

### Return type

[**AnswerResponse**](AnswerResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, text/json, text/html, application/xml, text/xml, application/x-www-form-urlencoded
 - **Accept**: application/json, text/json, text/html, application/xml, text/xml

