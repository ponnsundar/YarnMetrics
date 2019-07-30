package com.cloudera.util;

import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RestAPIUtil {

	public static String makeRESTCall(String base_url_s) throws Exception {

		System.out.println(base_url_s);
		URL url = new URL(base_url_s);
		int responsecode;
		StringBuffer inline;
		if (base_url_s.toLowerCase().startsWith("https")) {
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			responsecode = conn.getResponseCode();
			inline = new StringBuffer();
			if (responsecode != 200) {
				// throw new RuntimeException("HttpResponseCode: " +
				// responsecode);
				System.out.println("Request failed for URL" + base_url_s);
				return "failed";
			} else {
				Scanner sc = new Scanner(conn.getInputStream());
				while (sc.hasNext()) {
					inline.append(sc.nextLine() + "\n");
				}
				sc.close();
			}
		} else {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			responsecode = conn.getResponseCode();
			inline = new StringBuffer();
			if (responsecode != 200) {
				// throw new RuntimeException("HttpResponseCode: " +
				// responsecode);
				System.out.println("Request failed for URL" + base_url_s);
				return "failed";
			} else {
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline.append(sc.nextLine() + "\n");
				}
				sc.close();
			}
		}

		return inline.toString();
	}

}
