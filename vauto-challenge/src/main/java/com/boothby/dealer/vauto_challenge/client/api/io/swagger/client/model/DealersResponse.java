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
 * DealersResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-12-03T19:12:24.605Z")
public class DealersResponse {
  @SerializedName("dealerId")
  private Integer dealerId = null;

  @SerializedName("name")
  private String name = null;

  public DealersResponse dealerId(Integer dealerId) {
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

  public DealersResponse name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DealersResponse dealersResponse = (DealersResponse) o;
    return Objects.equals(this.dealerId, dealersResponse.dealerId) &&
        Objects.equals(this.name, dealersResponse.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dealerId, name);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DealersResponse {\n");
    
    sb.append("    dealerId: ").append(toIndentedString(dealerId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

