package com.cloudera.YarnMetricsSink;

import com.cloudera.YarnMetricsAccumulatorDTO.AppConfig;

public interface Sink {
	public boolean saveData(AppConfig config, String FileContent);

}
