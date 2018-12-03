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
 * AnswerResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-12-03T19:12:24.605Z")
public class AnswerResponse {
  @SerializedName("success")
  private Boolean success = null;

  @SerializedName("message")
  private String message = null;

  @SerializedName("totalMilliseconds")
  private Integer totalMilliseconds = null;

  public AnswerResponse success(Boolean success) {
    this.success = success;
    return this;
  }

   /**
   * Get success
   * @return success
  **/
  @ApiModelProperty(value = "")
  public Boolean isSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public AnswerResponse message(String message) {
    this.message = message;
    return this;
  }

   /**
   * Get message
   * @return message
  **/
  @ApiModelProperty(value = "")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public AnswerResponse totalMilliseconds(Integer totalMilliseconds) {
    this.totalMilliseconds = totalMilliseconds;
    return this;
  }

   /**
   * Get totalMilliseconds
   * @return totalMilliseconds
  **/
  @ApiModelProperty(value = "")
  public Integer getTotalMilliseconds() {
    return totalMilliseconds;
  }

  public void setTotalMilliseconds(Integer totalMilliseconds) {
    this.totalMilliseconds = totalMilliseconds;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AnswerResponse answerResponse = (AnswerResponse) o;
    return Objects.equals(this.success, answerResponse.success) &&
        Objects.equals(this.message, answerResponse.message) &&
        Objects.equals(this.totalMilliseconds, answerResponse.totalMilliseconds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, message, totalMilliseconds);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AnswerResponse {\n");
    
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    totalMilliseconds: ").append(toIndentedString(totalMilliseconds)).append("\n");
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

