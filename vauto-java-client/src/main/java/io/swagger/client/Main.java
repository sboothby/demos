package io.swagger.client;

import io.swagger.client.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.DataSetApi;
import io.swagger.client.api.DealersApi;
import io.swagger.client.api.VehiclesApi;
import io.swagger.client.ApiCallback;

import java.io.File;
import java.util.*;

public class Main {

	
    public static void main(String[] args) {
        
        DataSetApi apiInstance = new DataSetApi();
        VehiclesApi vehicleInstance = new VehiclesApi();
        DealersApi dealerInstance = new DealersApi();
        String datasetId = "4ikOM1Xp1gg"; // String | 
        try {
        	
        	 DatasetIdResponse response = apiInstance.dataSetGetDataSetId();
        	 VehicleIdsResponse vIdresponse = vehicleInstance.vehiclesGetIds(response.getDatasetId());
        	 HashMap<Integer, DealerAnswer> dealerTask = new HashMap<>();
        	 Answer taskanswer = new Answer();
        	for (int i =0;i<vIdresponse.getVehicleIds().size();i++)
        	 {
        		 VehicleResponse vresponse = vehicleInstance.vehiclesGetVehicle(response.getDatasetId(), vIdresponse.getVehicleIds().get(i)); //VehicleID year make model
        		 VehicleAnswer vanswer = new VehicleAnswer();
    			 vanswer.setModel(vresponse.getModel());
    			 vanswer.setMake(vresponse.getMake());
    			 vanswer.setVehicleId(vresponse.getVehicleId());
    			 vanswer.setYear(vresponse.getYear());
    			 if(!dealerTask.containsKey(vresponse.getDealerId()))
        		 {
    				 DealersResponse dresponse = dealerInstance.dealersGetDealer(response.getDatasetId(),  vresponse.getDealerId());
        			 DealerAnswer danswer = new DealerAnswer();	 
        			 danswer.setDealerId(dresponse.getDealerId());
        			 danswer.setName(dresponse.getName());
        			 danswer.addVehiclesItem(vanswer);
        			 dealerTask.put(vresponse.getDealerId(), danswer); 
        			 
        		 }
        		 else
        		 {	
        			dealerTask.get(vresponse.getDealerId()).addVehiclesItem(vanswer);
        		 }
        		 
        	 }
  			
        	Collection<DealerAnswer> convertMtoA = dealerTask.values();	
        	ArrayList<DealerAnswer> DealerAnswerlist = new ArrayList<DealerAnswer>(convertMtoA);
        	taskanswer.setDealers(DealerAnswerlist);
        	AnswerResponse finalAnswer = apiInstance.dataSetPostAnswer(response.getDatasetId(), taskanswer);
        	System.out.println(finalAnswer);
        } catch (ApiException e) {
            System.err.println("Exception when calling DataSetApi#dataSetGetCheat");
            e.printStackTrace();
        }
    }
}