package com.client;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.beans.BatchTemplate;
import com.beans.Location;
import com.beans.Trainer;
import com.exceptions.InvalidLocationException;
import com.util.TrainingPlannerService;
import com.util.TrainingPlannerServiceImpl;

public class Client {

	public static void main(String[] args) {

	TrainingPlannerService ob=new TrainingPlannerServiceImpl();

	List<Location>ll = ob.populateTrainingLocations();
	/*	System.out.println(ll);
	
	List<Trainer>lt = ob.populateTrainerDetails();
	System.out.println(lt);
	
	
	List<BatchTemplate>ltmp= ob.populateBatchTemplate(new File("batchTemplates.txt"));
		System.out.println(ltmp);
	*/
	try {
		Map<String,Integer>mp=ob.searchLocationsWiseClassroomsAlongWithCapacity("aryabhatta", ll);
		System.out.println(mp);
	} catch (InvalidLocationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}

}
