package com.cloudera.YarnMetricsDAO;

import com.cloudera.YarnMetricsSink.HDFSSink;
import com.cloudera.YarnMetricsSink.KafkaSink;
import com.cloudera.YarnMetricsSink.LocalFSSink;
import com.cloudera.YarnMetricsAccumulatorDTO.AppConfig;
import com.cloudera.YarnMetricsAccumulatorDTO.QueueMetric;
import java.util.List;

public class QueueMetricDAO {

	public static void saveQueueMetrics(List<QueueMetric> queue_metric,
			AppConfig appConfig) {
		// TODO Auto-generated method stub
		String ColumnSeperator = appConfig.getDelimiter();
		String queueContent = "QueueMetrics" + ColumnSeperator;
		int i = 0;
		for (QueueMetric q : queue_metric) {
			if (i > 0) {
				queueContent = queueContent + System.lineSeparator();
				queueContent = queueContent + "QueueMetrics" + ColumnSeperator;
			}
			i++;
			queueContent = queueContent + q.getQueueName() + ColumnSeperator
					+ q.getMaxApps() + ColumnSeperator + q.getPreemptable()
					+ ColumnSeperator + q.getNumPendingApps() + ColumnSeperator
					+ q.getNumPendingApps() + ColumnSeperator
					+ q.getAllocatedContainers() + ColumnSeperator
					+ q.getReservedContainers() + ColumnSeperator
					+ q.getClusterResources_memory() + ColumnSeperator
					+ q.getClusterResources_vCores() + ColumnSeperator
					+ q.getMinResources_memory() + ColumnSeperator
					+ q.getMinResources_vCores() + ColumnSeperator
					+ q.getMaxResources_memory() + ColumnSeperator
					+ q.getMaxResources_vCores() + ColumnSeperator
					+ q.getUsedResources_memory() + ColumnSeperator
					+ q.getUsedResources_vCores() + ColumnSeperator
					+ q.getFairResources_memory() + ColumnSeperator
					+ q.getFairResources_vCores() + ColumnSeperator
					+ q.getAmUsedResources_memory() + ColumnSeperator
					+ q.getAmUsedResources_vCores() + ColumnSeperator
					+ q.getAmMaxResources_memory() + ColumnSeperator
					+ q.getAmMaxResources_vCores() + ColumnSeperator
					+ q.getsFairResources_memory() + ColumnSeperator
					+ q.getsFairResources_vCores() + ColumnSeperator
					+ q.getReservedResources_memory() + ColumnSeperator
					+ q.getReservedResources_memory() + ColumnSeperator
					+ System.currentTimeMillis();
		}
		queueContent = queueContent + System.lineSeparator();
		if (appConfig.isWriteToHDFS()) {
			HDFSSink writeToHDFS = new HDFSSink(appConfig);
			appConfig.setOutputPath(appConfig.getBasePath()
					+ "/cluster/queuemetrics");
			writeToHDFS.saveData(appConfig, queueContent);
		}
		if (appConfig.isWriteToLocalFS()) {
			LocalFSSink writeToLocalFS = new LocalFSSink();
			appConfig.setOutputPath(appConfig.getBasePath()
					+ "/cluster/queuemetrics");
			writeToLocalFS.saveData(appConfig, queueContent);
		}
		if (appConfig.isWriteToKafka()) {
			KafkaSink kafkaSink = new KafkaSink();
			kafkaSink.saveData(appConfig, queueContent);
		}

	}
}
