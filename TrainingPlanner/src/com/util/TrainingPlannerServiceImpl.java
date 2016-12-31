package com.util;

import java.util.HashMap;
import java.util.Map;

import com.beans.BatchType;

public class TrainingPlannerServiceImpl implements TrainingPlannerService {

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

}
