/*
 * DealersAndVehicles
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.boothby.dealer.vauto_challenge.client.api.io.swagger.client.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * VehicleResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-12-03T19:12:24.605Z")
public class VehicleResponse {
  @SerializedName("vehicleId")
  private Integer vehicleId = null;

  @SerializedName("year")
  private Integer year = null;

  @SerializedName("make")
  private String make = null;

  @SerializedName("model")
  private String model = null;

  @SerializedName("dealerId")
  private Integer dealerId = null;

  public VehicleResponse vehicleId(Integer vehicleId) {
    this.vehicleId = vehicleId;
    return this;
  }

   /**
   * Get vehicleId
   * @return vehicleId
  **/
  @ApiModelProperty(value = "")
  public Integer getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(Integer vehicleId) {
    this.vehicleId = vehicleId;
  }

  public VehicleResponse year(Integer year) {
    this.year = year;
    return this;
  }

   /**
   * Get year
   * @return year
  **/
  @ApiModelProperty(value = "")
  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public VehicleResponse make(String make) {
    this.make = make;
    return this;
  }

   /**
   * Get make
   * @return make
  **/
  @ApiModelProperty(value = "")
  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public VehicleResponse model(String model) {
    this.model = model;
    return this;
  }

   /**
   * Get model
   * @return model
  **/
  @ApiModelProperty(value = "")
  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public VehicleResponse dealerId(Integer dealerId) {
    this.dealerId = dealerId;
    return this;
  }

   /**
   * Get dealerId
   * @return dealerId
  **/
  @ApiModelProperty(value = "")
  public Integer getDealerId() {
    return dealerId;
  }

  public void setDealerId(Integer dealerId) {
    this.dealerId = dealerId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VehicleResponse vehicleResponse = (VehicleResponse) o;
    return Objects.equals(this.vehicleId, vehicleResponse.vehicleId) &&
        Objects.equals(this.year, vehicleResponse.year) &&
        Objects.equals(this.make, vehicleResponse.make) &&
        Objects.equals(this.model, vehicleResponse.model) &&
        Objects.equals(this.dealerId, vehicleResponse.dealerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vehicleId, year, make, model, dealerId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VehicleResponse {\n");
    
    sb.append("    vehicleId: ").append(toIndentedString(vehicleId)).append("\n");
    sb.append("    year: ").append(toIndentedString(year)).append("\n");
    sb.append("    make: ").append(toIndentedString(make)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    dealerId: ").append(toIndentedString(dealerId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

