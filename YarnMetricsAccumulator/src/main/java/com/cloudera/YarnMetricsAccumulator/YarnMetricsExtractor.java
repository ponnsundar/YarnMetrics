package com.cloudera.YarnMetricsAccumulator;

import java.util.ArrayList;
import java.util.List;

import com.cloudera.YarnMetricsAccumulatorDTO.*;
import com.cloudera.util.RestAPIUtil;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class YarnMetricsExtractor {

	public static ClusterMetric extractClusterMetrics(String url,
			AppConfig appConfig) throws Exception {
		JSONParser parse = new JSONParser();
		ClusterMetric metrics = new ClusterMetric();
		String inline = RestAPIUtil.makeRESTCall(url);
		org.json.simple.JSONObject yarnMetrics = (org.json.simple.JSONObject) parse
				.parse(inline);
		org.json.simple.JSONObject clusterMetrics = (JSONObject) yarnMetrics
				.get("clusterMetrics");
		System.out.println();
		metrics.setUnhealthyNodes(clusterMetrics.get("unhealthyNodes")
				.toString());
		metrics.setTotalVirtualCores(clusterMetrics.get("totalVirtualCores")
				.toString());
		metrics.setTotalNodes(clusterMetrics.get("totalNodes").toString());
		metrics.setTotalMB(clusterMetrics.get("totalMB").toString());
		//metrics.setShutdownNodes(clusterMetrics.get("shutdownNodes").toString());
		metrics.setShutdownNodes("0".toString());
		metrics.setReservedVirtualCores(clusterMetrics.get(
				"reservedVirtualCores").toString());
		metrics.setReservedMB(clusterMetrics.get("reservedMB").toString());
		metrics.setRebootedNodes(clusterMetrics.get("rebootedNodes").toString());
		metrics.setLostNodes(clusterMetrics.get("lostNodes").toString());
		metrics.setDecommissioningNodes(clusterMetrics.get(
				"decommissioningNodes").toString());
		metrics.setDecommissionedNodes(clusterMetrics
				.get("decommissionedNodes").toString());
		metrics.setContainersReserved(clusterMetrics.get("containersReserved")
				.toString());
		metrics.setContainersPending(clusterMetrics.get("containersPending")
				.toString());
		metrics.setContainersAllocated(clusterMetrics
				.get("containersAllocated").toString());
		metrics.setAvailableVirtualCores(clusterMetrics.get(
				"availableVirtualCores").toString());
		metrics.setAvailableMB(clusterMetrics.get("availableMB").toString());
		metrics.setAppsSubmitted(clusterMetrics.get("appsSubmitted").toString());
		metrics.setAppsRunning(clusterMetrics.get("appsRunning").toString());
		metrics.setAppsPending(clusterMetrics.get("appsPending").toString());
		metrics.setAppsKilled(clusterMetrics.get("appsKilled").toString());
		metrics.setAppsFailed(clusterMetrics.get("appsFailed").toString());
		metrics.setAppsCompleted(clusterMetrics.get("appsCompleted").toString());
		metrics.setAllocatedVirtualCores(clusterMetrics.get(
				"allocatedVirtualCores").toString());
		metrics.setAllocatedMB(clusterMetrics.get("allocatedMB").toString());
		metrics.setActiveNodes(clusterMetrics.get("activeNodes").toString());

		return metrics;
	}

	public static List<QueueMetric> extractQueueMetrics(
			String yarn_history_server_url, AppConfig appconfig)
			throws Exception {
		// List<QueueMetric> queueMetrics=new ArrayList<QueueMetric>();
		// TODO Auto-generated method stub
		JSONParser parse = new JSONParser();
		String inline = RestAPIUtil.makeRESTCall(yarn_history_server_url);
		org.json.simple.JSONObject jsonOutput = (org.json.simple.JSONObject) parse
				.parse(inline);
		org.json.simple.JSONObject queueMetricsInfo = (JSONObject) jsonOutput
				.get("scheduler");
		org.json.simple.JSONObject queueMetric = (JSONObject) queueMetricsInfo
				.get("schedulerInfo");
		org.json.simple.JSONObject rootQueue = (JSONObject) queueMetric
				.get("rootQueue");
		org.json.simple.JSONObject childQueue = (JSONObject) rootQueue
				.get("childQueues");
		List<QueueMetric> queueMetrics = extractchildQueueMetrics(childQueue);

		// System.out.println(inline);
		return queueMetrics;
	}

	private static List<QueueMetric> extractchildQueueMetrics(
			JSONObject queueListJSON) {
		// TODO Auto-generated method stub
		List<QueueMetric> queueMetrics = new ArrayList<QueueMetric>();
		org.json.simple.JSONArray childQueuesList = (org.json.simple.JSONArray) queueListJSON
				.get("queue");
		// System.out.println(childQueuesList.toString());
		for (int i = 0; i < childQueuesList.size(); i++) {
			QueueMetric qMetrics = new QueueMetric();
			org.json.simple.JSONObject queue = (JSONObject) childQueuesList
					.get(i);
			org.json.simple.JSONObject fairResources = (JSONObject) queue
					.get("fairResources");
			org.json.simple.JSONObject minResources = (JSONObject) queue
					.get("minResources");
			org.json.simple.JSONObject usedResources = (JSONObject) queue
					.get("usedResources");
			org.json.simple.JSONObject maxResources = (JSONObject) queue
					.get("maxResources");
			org.json.simple.JSONObject amUsedResources = (JSONObject) queue
					.get("amUsedResources");
			org.json.simple.JSONObject amMaxResources = (JSONObject) queue
					.get("amMaxResources");
			org.json.simple.JSONObject steadyFairResources = (JSONObject) queue
					.get("steadyFairResources");
			org.json.simple.JSONObject clusterResources = (JSONObject) queue
					.get("clusterResources");
			org.json.simple.JSONObject reservedResources = (JSONObject) queue
					.get("reservedResources");
			org.json.simple.JSONObject childQueue = (JSONObject) queue
					.get("childQueues");

			qMetrics.setFairResources_memory(fairResources.get("memory")
					.toString());
			qMetrics.setFairResources_vCores(fairResources.get("vCores")
					.toString());

			qMetrics.setMinResources_memory(minResources.get("memory")
					.toString());
			qMetrics.setMinResources_vCores(minResources.get("vCores")
					.toString());

			qMetrics.setMaxResources_memory(maxResources.get("memory")
					.toString());
			qMetrics.setMaxResources_vCores(maxResources.get("vCores")
					.toString());

			qMetrics.setClusterResources_memory(clusterResources.get("memory")
					.toString());
			qMetrics.setClusterResources_vCores(clusterResources.get("vCores")
					.toString());

			qMetrics.setUsedResources_memory(usedResources.get("memory")
					.toString());
			qMetrics.setUsedResources_vCores(usedResources.get("vCores")
					.toString());

			qMetrics.setAmUsedResources_memory(amUsedResources.get("memory")
					.toString());
			qMetrics.setAmUsedResources_vCores(amUsedResources.get("vCores")
					.toString());

			qMetrics.setAmMaxResources_memory(amMaxResources.get("memory")
					.toString());
			qMetrics.setAmMaxResources_vCores(amMaxResources.get("vCores")
					.toString());

			qMetrics.setsFairResources_memory(steadyFairResources.get("memory")
					.toString());
			qMetrics.setsFairResources_vCores(steadyFairResources.get("vCores")
					.toString());

			qMetrics.setReservedResources_memory(reservedResources
					.get("memory").toString());
			qMetrics.setReservedResources_vCores(reservedResources
					.get("vCores").toString());

			qMetrics.setMaxApps(queue.get("maxApps").toString());
			qMetrics.setQueueName(queue.get("queueName").toString());
			qMetrics.setPreemptable(queue.get("preemptable").toString());
			qMetrics.setReservedContainers(queue.get("reservedContainers")
					.toString());
			qMetrics.setAllocatedContainers(queue.get("allocatedContainers")
					.toString());

			if (queue.get("numPendingApps") != null)
				qMetrics.setNumPendingApps(queue.get("numPendingApps")
						.toString());
			else
				qMetrics.setNumPendingApps("0");
			if (queue.get("numActiveApps") != null)
				qMetrics.setNumActiveApps(queue.get("numActiveApps").toString());
			else
				qMetrics.setNumActiveApps("0");
			if (childQueue != null) {
				// System.out.println(childQueue.toString());
				List<QueueMetric> leafQueue = extractchildQueueMetrics(childQueue);
				for (QueueMetric q : leafQueue) {
					queueMetrics.add(q);
				}
			}
			queueMetrics.add(qMetrics);
			// System.out.println(queue.get("queueName"));
			// System.out.println(fairResources.get("memory"));
			// System.out.println(fairResources.get("vCores"));
			// System.out.println(minResources.get("memory"));
			// System.out.println(minResources.get("vCores"));
			// System.out.println(maxResources.get("memory"));
			// System.out.println(maxResources.get("vCores"));
			// System.out.println(clusterResources.get("memory"));
			// System.out.println(clusterResources.get("vCores"));
			// System.out.println(usedResources.get("memory"));
			// System.out.println(usedResources.get("vCores"));
			// System.out.println(amUsedResources.get("memory"));
			// System.out.println(amUsedResources.get("vCores"));
			// System.out.println(amMaxResources.get("memory"));
			// System.out.println(amMaxResources.get("vCores"));
			// System.out.println(steadyFairResources.get("memory"));
			// System.out.println(steadyFairResources.get("vCores"));
			// System.out.println(reservedResources.get("memory"));
			// System.out.println(reservedResources.get("vCores"));
			// System.out.println(queue.get("maxApps"));
			//
			// System.out.println(queue.get("preemptable"));
			// System.out.println(queue.get("reservedContainers"));
			// System.out.println(queue.get("allocatedContainers"));
			// if(queue.get("numPendingApps") != null)
			// System.out.println(queue.get("numPendingApps"));
			// else
			// System.out.println("0");
			// if(queue.get("numActiveApps") != null)
			// System.out.println(queue.get("numActiveApps"));
			// else
			// System.out.println("0");

		}

		return queueMetrics;
	}

	// private static QueueMetric extractRootQueueMetrics(JSONObject rootQueue)
	// {
	// // TODO Auto-generated method stub
	// //System.out.println(rootQueue.toString());
	//
	// org.json.simple.JSONObject fairResources = (JSONObject)
	// rootQueue.get("fairResources");
	// org.json.simple.JSONObject minResources = (JSONObject)
	// rootQueue.get("minResources");
	// org.json.simple.JSONObject usedResources = (JSONObject)
	// rootQueue.get("usedResources");
	// org.json.simple.JSONObject maxResources = (JSONObject)
	// rootQueue.get("maxResources");
	// org.json.simple.JSONObject amUsedResources = (JSONObject)
	// rootQueue.get("amUsedResources");
	// org.json.simple.JSONObject amMaxResources = (JSONObject)
	// rootQueue.get("amMaxResources");
	// org.json.simple.JSONObject steadyFairResources = (JSONObject)
	// rootQueue.get("steadyFairResources");
	// org.json.simple.JSONObject clusterResources = (JSONObject)
	// rootQueue.get("clusterResources");
	// org.json.simple.JSONObject reservedResources = (JSONObject)
	// rootQueue.get("reservedResources");
	//
	// // System.out.println(rootQueue.get("maxApps"));
	// // System.out.println(rootQueue.get("allocatedContainers"));
	// // System.out.println(rootQueue.get("queueName"));
	// // System.out.println(rootQueue.get("schedulingPolicy"));
	// // System.out.println(rootQueue.get("reservedContainers"));
	// // System.out.println(rootQueue.get("preemptable"));
	//
	//
	// return null;
	// }

}
