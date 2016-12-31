package com.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.beans.Batch;
import com.beans.BatchTemplate;
import com.beans.BatchType;
import com.beans.Location;
import com.beans.Trainer;
import com.exceptions.InvalidLocationException;

public class TrainingPlannerServiceImpl implements TrainingPlannerService {
	static Connection con;
	static {
		con = new ConnectionManagerImpl().getConnection();
	}

	public final Map<BatchType, Map<String, Integer>> getTrainingRequirement() {
		Map<BatchType, Map<String, Integer>> trainingRequirement = new HashMap<>();
		Map<String, Integer> locationWiseCount = new HashMap<>();
		// location wise count of candidates for Standard Java batch
		locationWiseCount.put("Hinjawadi", 80);
		locationWiseCount.put("Goa", 15);
		locationWiseCount.put("Bangalore", 10);
		locationWiseCount.put("Nagpur", 10);
		trainingRequirement.put(BatchType.STANDARD_JAVA, locationWiseCount);

		locationWiseCount = new HashMap<>();
		// location wise count of candidates for QE batch
		locationWiseCount.put("Hinjawadi", 10);
		locationWiseCount.put("Goa", 5);
		locationWiseCount.put("Bangalore", 4);
		locationWiseCount.put("Nagpur", 8);
		trainingRequirement.put(BatchType.QE, locationWiseCount);

		locationWiseCount = new HashMap<>();
		// location wise count of candidates for full stack batch
		locationWiseCount.put("Hinjawadi", 10);
		locationWiseCount.put("Goa", 8);
		locationWiseCount.put("Nagpur", 9);
		trainingRequirement.put(BatchType.FULL_STACK, locationWiseCount);
		return trainingRequirement;

	}

	@Override
	public List<Location> populateTrainingLocations() {
		List<Location> ll = new ArrayList<>();
		try {
			Location l;
			PreparedStatement ps = con
					.prepareStatement("Select * from location_tbl");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				l = new Location();
				l.setLocationName(rs.getString(2));
				l.setClassRoomName(rs.getString(3));
				l.setCapacity(rs.getInt(4));

				ll.add(l);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ll;
	}

	@Override
	public List<Trainer> populateTrainerDetails() {
		Trainer t;
		List<Trainer> lt = new ArrayList<>();
		int id;
		PreparedStatement ps;
		PreparedStatement ps2;
		Map<String,Integer>mp;
		try {
			ps = con
					.prepareStatement("Select * from trainer_tbl");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				t=new Trainer();
				id=rs.getInt(1);
				t.setTrainerId(id);
				t.setTrainerName(rs.getString(2));
				ps2=con.prepareStatement("select * from skill_tbl where trainer_id = ?");
				ps2.setInt(1, id);
				ResultSet rs2=ps2.executeQuery();
				mp=new LinkedHashMap<>();
				while(rs2.next()){
					
					mp.put(rs2.getString(2), rs2.getInt(3));
				}
				t.setSkillPrefenceMap(mp);
				lt.add(t);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return lt;
	}

	@Override
	public List<BatchTemplate> populateBatchTemplate(File file) {
		List<BatchTemplate>lbt =new ArrayList<>();
		BatchTemplate bt;
		try {
			Scanner sc=new Scanner(file);
			while(sc.hasNext()){
				bt=new BatchTemplate();
				String line=sc.nextLine().trim();
				String []val=line.split(",");
				bt.setBatchType(BatchType.valueOf(val[0].trim().toUpperCase()));
				Map<String,Integer>mp=new HashMap<>();
				for(int i=1;i<val.length;i++){
				String [] val2=val[i].trim().split("-");
				mp.put(val2[0].trim(), Integer.valueOf(val2[1].trim()));
				}
				bt.setCoursesWithDuration(mp);
				lbt.add(bt);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lbt;
	}

	@Override
	public Map<String, Integer> searchLocationsWiseClassroomsAlongWithCapacity(
			String locationName, List<Location> locations)
			throws InvalidLocationException {
			Map<String,Integer>mp=new LinkedHashMap<>();
		class MyComp implements Comparator<Location>{
			
		

		@Override
		public int compare(Location o1, Location o2) {
			int i=o2.getCapacity()-o1.getCapacity();
				//if(i==0)
			return i;
		}
		}
		Collections.sort(locations, new MyComp());
		
				for (Location location : locations) {
					if(locationName.trim().equalsIgnoreCase(location.getLocationName())){
						mp.put(location.getClassRoomName(), location.getCapacity());
					}
				}
				if(mp.size()==0)
					throw new InvalidLocationException();
	
		return mp;
	}

	@Override
	public List<Batch> createBatches(
			Map<BatchType, Map<String, Integer>> trainingRequirement,
			List<Location> location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Batch generateBatchSchedule(Batch batch,
			List<BatchTemplate> batchTemplate, Date batchStartDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Batch assignTrainersToBatch(Batch batch, List<Trainer> trainers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findUnassignedCourses(Batch batch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String raiseTrainerRequirement(List<String> courses) {
		// TODO Auto-generated method stub
		return null;
	}

}
