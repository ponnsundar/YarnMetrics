package com.cloudera.YarnMetricsSink;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cloudera.YarnMetricsAccumulatorDTO.AppConfig;

public class LocalFSSink implements Sink {

	@Override
	public boolean saveData(AppConfig appConfig, String FileContent) {

		String FilePath = appConfig.getOutputPath()
				+ new SimpleDateFormat("yyyyMMddHH").format(new Date())
						.toString();
		BufferedWriter writer;
		// System.out.println(" :: Base Path-- " + appConfig.getOutputPath());
		// System.out.println(" :: Writing to file -- " + FilePath);
		try {
			File file = new File(FilePath);
			File parentFile = new File(file.getParent());
			if (file.exists()) {
				writer = new BufferedWriter(new FileWriter(FilePath, true));
				writer.write(FileContent);
				writer.close();
			} else {
				parentFile.mkdirs();
				writer = new BufferedWriter(new FileWriter(FilePath, true));
				writer.write(FileContent);
				writer.close();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}
}
