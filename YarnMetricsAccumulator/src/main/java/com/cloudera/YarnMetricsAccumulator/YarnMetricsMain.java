package com.cloudera.YarnMetricsAccumulator;

import java.util.List;

import com.cloudera.YarnMetricsAccumulator.YarnMetricsExtractor;
import com.cloudera.YarnMetricsAccumulatorDTO.*;
import com.cloudera.YarnMetricsDAO.*;

import org.apache.commons.cli.*;

public class YarnMetricsMain {
	// Name of this 'program'
	static final String NAME = "YarnMetrics";
	private Options options = new Options();
	YarnMetricsExtractor extractor = new YarnMetricsExtractor();
	private static AppConfig appConfig = new AppConfig();
	private static String yarn_rm_server_url;
	private static String configPath;
	private static String outputPath;
	private static boolean isWriteToHDFS;
	private static boolean isWriteTolocaFS;
	private static boolean isWriteToKafka;
	private static String kafkaBootstrapServers;
	private static String kafkaTopic;
	private static String delimiter;
	private static boolean isSecured;
	private static String principal;
	private static String keytabPath;

	public static void main(String args[]) throws Exception {

		YarnMetricsMain metricsMain = new YarnMetricsMain();
		metricsMain.init(args);

		// yarn_rm_server_url
		// ="http://mstestspark-2.vpc.cloudera.com:8088/ws/v1/cluster/metrics";
		// yarn_rm_server_url
		// ="http://mstestspark-2.vpc.cloudera.com:8088/ws/v1/cluster/scheduler";
		// hdfsURI = "hdfs://mstestspark-2.vpc.cloudera.com/";
		// outputPath ="/tmp";
		// isWriteToHDS = true;
		appConfig.setYarn_history_server_url(yarn_rm_server_url);
		appConfig.setBasePath(outputPath);
		appConfig.setConfigPath(configPath);
		;
		appConfig.setWriteToHDFS(isWriteToHDFS);
		appConfig.setWriteToLocalFS(isWriteTolocaFS);
		appConfig.setWriteToKafka(isWriteToKafka);
		appConfig.setKafkaBootstrapServers(kafkaBootstrapServers);
		appConfig.setKafkaTopic(kafkaTopic);
		appConfig.setDelimiter(delimiter);
		appConfig.setSecured(isSecured);
		appConfig.setKeytabPath(keytabPath);
		appConfig.setPrincipal(principal);
		metricsMain.saveQueueMetrics(appConfig);
		metricsMain.saveClusterMetrics(appConfig);
		System.out.println("Successfully completed collecting metrics");
	}

	public void saveClusterMetrics(AppConfig appconfig) throws Exception {
		ClusterMetric cls_metric = YarnMetricsExtractor.extractClusterMetrics(
				appconfig.getYarn_history_server_url() + "metrics", appconfig);
		ClusterMetricDAO.saveMetrics(cls_metric, appConfig);

	}

	public void saveQueueMetrics(AppConfig appconfig) throws Exception {
		List<QueueMetric> queue_metric = YarnMetricsExtractor
				.extractQueueMetrics(appconfig.getYarn_history_server_url()
						+ "scheduler", appconfig);
		QueueMetricDAO.saveQueueMetrics(queue_metric, appConfig);

	}

	public void init(String[] args) {

		options.addOption(this.createOption("u", "url", true,
				"Yarn RM server URL ", true));

		options.addOption(this
				.createOption(
						"s",
						"storageType",
						true,
						"Specify whether metrics to be stored in HDFS or Local FS.  hdfs for HDFS and localfs for Local File System",
						true));

		options.addOption(this.createOption("d", "delimiter", true,
				"Specify the delimiter to split metrics", true));

		options.addOption(this
				.createOption(
						"c",
						"configPath",
						true,
						"Specify config directory path which contains core-site and hdfs-site if metrics to be stored in HDFS.Note: Remove / at the end",
						false));
		options.addOption(this
				.createOption(
						"o",
						"outputPath",
						true,
						"Path to store metrics data. HDFS path in case of metrics to be stored in HDFS",
						false));
		options.addOption(this.createOption("a", "AuthenticationKerberos", false,
				"use this option when writing to an kerberized hdfs", false));

		options.addOption(this
				.createOption(
						"k",
						"keytabPath",
						true,
						"use this option when writing to an kerberized hdfs,provide the path of keytab",
						false));
		options.addOption(this
				.createOption(
						"p",
						"principal",
						true,
						"use this option when writing to an kerberized hdfs,provide the path of full principal name",
						false));

		options.addOption(this.createOption("d", "delimiter", true,
				"Specify the delimiter to split metrics", true));
		options.addOption(this
				.createOption(
						"b",
						"kafka_boostrap_server",
						true,
						"Specify kafka bootsrap servers eg:test1.kafkabroker.com:9092,test.kafkabroker.com:9092,test2.kafkabroker.com:9092.",
						false));
		options.addOption(this.createOption("t", "topic", true,
				"Specify kafka topic when using strage type as kafka", false));
		options.addOption(this.createOption("h", "help", true, "Print Usage",
				false));

		CommandLineParser parser = new org.apache.commons.cli.BasicParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("SparkMetrics", options);
			System.exit(1);
		}
		yarn_rm_server_url = cmd.getOptionValue("url");
		delimiter = cmd.getOptionValue("delimiter");

		if (delimiter.isEmpty()) {
			System.out.println("delimiter must not be empty");
			printUsage();
			System.exit(1);
		}

		if (!cmd.hasOption("storageType")) {
			System.out.println("Storage Type must present");
			printUsage();
			System.exit(1);
		} else {
			if (cmd.getOptionValue("storageType").toString().equals("hdfs")) {
				if (!cmd.hasOption("configPath")) {
					System.out
							.println("ConfigPath and outputPath must present when storage type is HDFS");
					printUsage();
					System.exit(1);
				}
				if (!cmd.hasOption("outputPath")) {
					System.out
							.println("ConfigPath and outputPath must present when storage type is HDFS");
					printUsage();
					System.exit(1);
				}
				if (cmd.hasOption("AuthenticationKerberos")) {
					isSecured = true;
					if (!cmd.hasOption("keytabPath")) {
						System.out
								.println("keytabPath must present when storage type is HDFS and Secured Option is choosen");
						printUsage();
						System.exit(1);
					}

					else {	
						keytabPath = cmd.getOptionValue("keytabPath");
					}

					if (!cmd.hasOption("principal")) {
						System.out
								.println("principal must present when storage type is HDFS and Secured Option is choosen");
						printUsage();
						System.exit(1);
					}

					else {

						principal = cmd.getOptionValue("principal");

					}

				}

				isWriteToHDFS = true;
				configPath = cmd.getOptionValue("configPath");
				outputPath = cmd.getOptionValue("outputPath");
			} else if (cmd.getOptionValue("storageType").toString()
					.equals("localfs")) {
				if (!cmd.hasOption("outputPath")) {
					System.out
							.println("outputPath must present when storage type is localfs");
					printUsage();
					System.exit(1);
				}

				isWriteTolocaFS = true;
				outputPath = cmd.getOptionValue("outputPath");

			} else if (cmd.getOptionValue("storageType").toString()
					.equals("kafka")) {
				if (!cmd.hasOption("kafka_boostrap_server")) {
					System.out
							.println("kafkaBootstrapServers must present when storage type is kafka");
					printUsage();
					System.exit(1);
				}

				if (!cmd.hasOption("topic")) {
					System.out
							.println("kafkaTopic must present when storage type is kafka");
					printUsage();
					System.exit(1);
				}

				isWriteToKafka = true;
			}
			kafkaBootstrapServers = cmd.getOptionValue("kafka_boostrap_server");
			kafkaTopic = cmd.getOptionValue("topic");
		}

	}

	public void printUsage() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("SparkMetrics", options);
	}

	public Option createOption(String opt, String longOpt, boolean hasArg,
			String description, boolean Required) {
		Option option = new Option(opt, longOpt, hasArg, description);
		option.setRequired(Required);
		return option;

	}
}
