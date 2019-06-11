# DealersApi

All URIs are relative to *http://api.coxauto-interview.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**dealersGetDealer**](DealersApi.md#dealersGetDealer) | **GET** /api/{datasetId}/dealers/{dealerid} | Get information about a dealer


<a name="dealersGetDealer"></a>
# **dealersGetDealer**
> DealersResponse dealersGetDealer(datasetId, dealerid)

Get information about a dealer

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DealersApi;


DealersApi apiInstance = new DealersApi();
String datasetId = "datasetId_example"; // String | 
Integer dealerid = 56; // Integer | 
try {
    DealersResponse result = apiInstance.dealersGetDealer(datasetId, dealerid);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DealersApi#dealersGetDealer");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datasetId** | **String**|  |
 **dealerid** | **Integer**|  |

### Return type

[**DealersResponse**](DealersResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/json, text/html, application/xml, text/xml

