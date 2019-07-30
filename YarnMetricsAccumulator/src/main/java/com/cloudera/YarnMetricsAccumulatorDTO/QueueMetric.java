package com.cloudera.YarnMetricsAccumulatorDTO;

public class QueueMetric {

	String maxApps;
	String minResources_memory;
	String minResources_vCores;
	String maxResources_memory;
	String maxResources_vCores;
	String usedResources_memory;
	String usedResources_vCores;
	String amUsedResources_memory;
	String amUsedResources_vCores;
	String amMaxResources_memory;
	String amMaxResources_vCores;
	String fairResources_memory;
	String fairResources_vCores;
	String sFairResources_memory;
	String sFairResources_vCores;
	String reservedResources_memory;
	String reservedResources_vCores;
	String clusterResources_memory;
	String clusterResources_vCores;
	String queueName;
	String preemptable;
	String reservedContainers;
	String allocatedContainers;
	String numPendingApps;
	String numActiveApps;

	public String getClusterResources_memory() {
		return clusterResources_memory;
	}

	public void setClusterResources_memory(String clusterResources_memory) {
		this.clusterResources_memory = clusterResources_memory;
	}

	public String getClusterResources_vCores() {
		return clusterResources_vCores;
	}

	public void setClusterResources_vCores(String clusterResources_vCores) {
		this.clusterResources_vCores = clusterResources_vCores;
	}

	public String getMaxApps() {
		return maxApps;
	}

	public void setMaxApps(String maxApps) {
		this.maxApps = maxApps;
	}

	public String getMinResources_memory() {
		return minResources_memory;
	}

	public void setMinResources_memory(String minResources_memory) {
		this.minResources_memory = minResources_memory;
	}

	public String getMinResources_vCores() {
		return minResources_vCores;
	}

	public void setMinResources_vCores(String minResources_vCores) {
		this.minResources_vCores = minResources_vCores;
	}

	public String getMaxResources_memory() {
		return maxResources_memory;
	}

	public void setMaxResources_memory(String maxResources_memory) {
		this.maxResources_memory = maxResources_memory;
	}

	public String getMaxResources_vCores() {
		return maxResources_vCores;
	}

	public void setMaxResources_vCores(String maxResources_vCores) {
		this.maxResources_vCores = maxResources_vCores;
	}

	public String getUsedResources_memory() {
		return usedResources_memory;
	}

	public void setUsedResources_memory(String usedResources_memory) {
		this.usedResources_memory = usedResources_memory;
	}

	public String getUsedResources_vCores() {
		return usedResources_vCores;
	}

	public void setUsedResources_vCores(String usedResources_vCores) {
		this.usedResources_vCores = usedResources_vCores;
	}

	public String getAmUsedResources_memory() {
		return amUsedResources_memory;
	}

	public void setAmUsedResources_memory(String amUsedResources_memory) {
		this.amUsedResources_memory = amUsedResources_memory;
	}

	public String getAmUsedResources_vCores() {
		return amUsedResources_vCores;
	}

	public void setAmUsedResources_vCores(String amUsedResources_vCores) {
		this.amUsedResources_vCores = amUsedResources_vCores;
	}

	public String getAmMaxResources_memory() {
		return amMaxResources_memory;
	}

	public void setAmMaxResources_memory(String amMaxResources_memory) {
		this.amMaxResources_memory = amMaxResources_memory;
	}

	public String getAmMaxResources_vCores() {
		return amMaxResources_vCores;
	}

	public void setAmMaxResources_vCores(String amMaxResources_vCores) {
		this.amMaxResources_vCores = amMaxResources_vCores;
	}

	public String getFairResources_memory() {
		return fairResources_memory;
	}

	public void setFairResources_memory(String fairResources_memory) {
		this.fairResources_memory = fairResources_memory;
	}

	public String getFairResources_vCores() {
		return fairResources_vCores;
	}

	public void setFairResources_vCores(String fairResources_vCores) {
		this.fairResources_vCores = fairResources_vCores;
	}

	public String getsFairResources_memory() {
		return sFairResources_memory;
	}

	public void setsFairResources_memory(String sFairResources_memory) {
		this.sFairResources_memory = sFairResources_memory;
	}

	public String getsFairResources_vCores() {
		return sFairResources_vCores;
	}

	public void setsFairResources_vCores(String sFairResources_vCores) {
		this.sFairResources_vCores = sFairResources_vCores;
	}

	public String getReservedResources_memory() {
		return reservedResources_memory;
	}

	public void setReservedResources_memory(String reservedResources_memory) {
		this.reservedResources_memory = reservedResources_memory;
	}

	public String getReservedResources_vCores() {
		return reservedResources_vCores;
	}

	public void setReservedResources_vCores(String reservedResources_vCores) {
		this.reservedResources_vCores = reservedResources_vCores;
	}

	public String getAllocatedContainers() {
		return allocatedContainers;
	}

	public void setAllocatedContainers(String allocatedContainers) {
		this.allocatedContainers = allocatedContainers;
	}

	public String getReservedContainers() {
		return reservedContainers;
	}

	public void setReservedContainers(String reservedContainers) {
		this.reservedContainers = reservedContainers;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getPreemptable() {
		return preemptable;
	}

	public void setPreemptable(String preemptable) {
		this.preemptable = preemptable;
	}

	public String getNumPendingApps() {
		return numPendingApps;
	}

	public void setNumPendingApps(String numPendingApps) {
		this.numPendingApps = numPendingApps;
	}

	public String getNumActiveApps() {
		return numActiveApps;
	}

	public void setNumActiveApps(String numActiveApps) {
		this.numActiveApps = numActiveApps;
	}

}
