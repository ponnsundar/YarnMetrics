package com.cloudera.YarnMetricsSink;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import com.cloudera.YarnMetricsAccumulatorDTO.AppConfig;

public class HDFSSink implements Sink {

	protected Configuration conf;

	public HDFSSink(AppConfig appconfig) {

		if (appconfig.isSecured()) {

			System.setProperty("java.security.krb5.conf", "/etc/krb5.conf");
			System.setProperty("javax.security.auth.useSubjectCredsOnly",
					"false");
			this.conf = new Configuration();
			this.conf.set("hadoop.security.authentication", "Kerberos");
		
		} else {
			this.conf = new Configuration();
		}
	}

	@Override
	public boolean saveData(AppConfig appConfig, String FileContent) {
		if (appConfig.isSecured()) {
			UserGroupInformation.setConfiguration(conf);
			try {
				 UserGroupInformation.loginUserFromKeytab(
				 appConfig.getPrincipal(), appConfig.getKeytabPath());	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String FilePath = appConfig.getOutputPath()
				+ new SimpleDateFormat("yyyyMMddHH").format(new Date())
						.toString();
		// System.out.println("File Content before storing is" + FileContent);
		FSDataOutputStream outputStream = null;
		Path path = new Path(FilePath);
		String configPath = appConfig.getConfigPath();
		conf.addResource(new Path(configPath + "/core-site.xml"));
		conf.addResource(new Path(configPath + "/hdfs-site.xml"));
		this.conf.set("fs.hdfs.impl", 
	            org.apache.hadoop.hdfs.DistributedFileSystem.class.getName()
	        );
		this.conf.set("fs.file.impl",
	            org.apache.hadoop.fs.LocalFileSystem.class.getName()
	        );
		System.out.println("saveToHDFSFile :: Connecting to -- "
				+ conf.get("fs.defaultFS") + " :: Writing to file -- "
				+ FilePath);
		FileSystem fs;
		
		try {
			fs = FileSystem.get(conf);
			if (!fs.exists(path)) {
				outputStream = fs.create(path);
				outputStream.writeBytes(FileContent);
				outputStream.close();
			} else {
				outputStream = fs.append(path);
				outputStream.writeBytes(FileContent);
				outputStream.close();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

}
