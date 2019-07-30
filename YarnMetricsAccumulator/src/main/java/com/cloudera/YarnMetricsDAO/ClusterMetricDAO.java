package com.cloudera.YarnMetricsDAO;

import com.cloudera.YarnMetricsSink.HDFSSink;
import com.cloudera.YarnMetricsSink.KafkaSink;
import com.cloudera.YarnMetricsSink.LocalFSSink;
import com.cloudera.YarnMetricsAccumulatorDTO.AppConfig;
import com.cloudera.YarnMetricsAccumulatorDTO.ClusterMetric;

public class ClusterMetricDAO {

	public static boolean saveMetrics(ClusterMetric metrics, AppConfig appConfig)
			throws Exception {

		String ColumnSeperator = appConfig.getDelimiter();
		String clusterContent = "ClusterMetrics" + ColumnSeperator;
		clusterContent = clusterContent + metrics.getUnhealthyNodes()
				+ ColumnSeperator + metrics.getTotalVirtualCores()
				+ ColumnSeperator + metrics.getTotalNodes() + ColumnSeperator
				+ metrics.getTotalMB() + ColumnSeperator
				+ metrics.getShutdownNodes() + ColumnSeperator
				+ metrics.getReservedVirtualCores() + ColumnSeperator
				+ metrics.getReservedMB() + ColumnSeperator
				+ metrics.getRebootedNodes() + ColumnSeperator
				+ metrics.getLostNodes() + ColumnSeperator
				+ metrics.getDecommissioningNodes() + ColumnSeperator
				+ metrics.getDecommissionedNodes() + ColumnSeperator
				+ metrics.getContainersReserved() + ColumnSeperator
				+ metrics.getContainersPending() + ColumnSeperator
				+ metrics.getContainersAllocated() + ColumnSeperator
				+ metrics.getAvailableVirtualCores() + ColumnSeperator
				+ metrics.getAvailableMB() + ColumnSeperator
				+ metrics.getAppsSubmitted() + ColumnSeperator
				+ metrics.getAppsRunning() + ColumnSeperator
				+ metrics.getAppsPending() + ColumnSeperator
				+ metrics.getAppsKilled() + ColumnSeperator
				+ metrics.getAppsFailed() + ColumnSeperator
				+ metrics.getAppsCompleted() + ColumnSeperator
				+ metrics.getAllocatedVirtualCores() + ColumnSeperator
				+ metrics.getAllocatedMB() + ColumnSeperator
				+ metrics.getActiveNodes() + ColumnSeperator
				+ System.currentTimeMillis() + System.lineSeparator();

		if (appConfig.isWriteToHDFS()) {
		
			HDFSSink writeToHDFS = new HDFSSink(appConfig);
			appConfig.setOutputPath(appConfig.getBasePath()
					+ "/cluster/clustermetrics");
			writeToHDFS.saveData(appConfig, clusterContent);
		}
		if (appConfig.isWriteToLocalFS()) {
			LocalFSSink writeToLocalFS = new LocalFSSink();
			appConfig.setOutputPath(appConfig.getBasePath()
					+ "/cluster/clustermetrics");
			writeToLocalFS.saveData(appConfig, clusterContent);
		}

		if (appConfig.isWriteToKafka()) {
			KafkaSink kafkaSink = new KafkaSink();
			kafkaSink.saveData(appConfig, clusterContent);
		}

		return true;
	}

}
