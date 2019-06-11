# VehiclesApi

All URIs are relative to *http://api.coxauto-interview.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**vehiclesGetIds**](VehiclesApi.md#vehiclesGetIds) | **GET** /api/{datasetId}/vehicles | Get a list of all vehicleids in dataset
[**vehiclesGetVehicle**](VehiclesApi.md#vehiclesGetVehicle) | **GET** /api/{datasetId}/vehicles/{vehicleid} | Get information about a vehicle


<a name="vehiclesGetIds"></a>
# **vehiclesGetIds**
> VehicleIdsResponse vehiclesGetIds(datasetId)

Get a list of all vehicleids in dataset

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VehiclesApi;


VehiclesApi apiInstance = new VehiclesApi();
String datasetId = "datasetId_example"; // String | 
try {
    VehicleIdsResponse result = apiInstance.vehiclesGetIds(datasetId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VehiclesApi#vehiclesGetIds");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datasetId** | **String**|  |

### Return type

[**VehicleIdsResponse**](VehicleIdsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/json, text/html, application/xml, text/xml

<a name="vehiclesGetVehicle"></a>
# **vehiclesGetVehicle**
> VehicleResponse vehiclesGetVehicle(datasetId, vehicleid)

Get information about a vehicle

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.VehiclesApi;


VehiclesApi apiInstance = new VehiclesApi();
String datasetId = "datasetId_example"; // String | 
Integer vehicleid = 56; // Integer | 
try {
    VehicleResponse result = apiInstance.vehiclesGetVehicle(datasetId, vehicleid);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VehiclesApi#vehiclesGetVehicle");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datasetId** | **String**|  |
 **vehicleid** | **Integer**|  |

### Return type

[**VehicleResponse**](VehicleResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/json, text/html, application/xml, text/xml

