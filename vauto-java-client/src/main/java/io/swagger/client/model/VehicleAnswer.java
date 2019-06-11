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


package io.swagger.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * VehicleAnswer
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-06-05T00:39:50.613Z")
public class VehicleAnswer {
  @SerializedName("vehicleId")
  private Integer vehicleId = null;

  @SerializedName("year")
  private Integer year = null;

  @SerializedName("make")
  private String make = null;

  @SerializedName("model")
  private String model = null;

  public VehicleAnswer vehicleId(Integer vehicleId) {
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

  public VehicleAnswer year(Integer year) {
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

  public VehicleAnswer make(String make) {
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

  public VehicleAnswer model(String model) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VehicleAnswer vehicleAnswer = (VehicleAnswer) o;
    return Objects.equals(this.vehicleId, vehicleAnswer.vehicleId) &&
        Objects.equals(this.year, vehicleAnswer.year) &&
        Objects.equals(this.make, vehicleAnswer.make) &&
        Objects.equals(this.model, vehicleAnswer.model);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vehicleId, year, make, model);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VehicleAnswer {\n");
    
    sb.append("    vehicleId: ").append(toIndentedString(vehicleId)).append("\n");
    sb.append("    year: ").append(toIndentedString(year)).append("\n");
    sb.append("    make: ").append(toIndentedString(make)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
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

