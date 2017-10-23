package com.chengxin.bfip;

import javax.servlet.http.HttpServletRequest;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class CommonUtil {

	public static final String CAROUSEL_MEDIA_URL = "/Upload/image/carousel/media";
	public static final String CAROUSEL_MEDIA_PATH = "\\..\\Upload\\image\\carousel\\media";
	public static final String CAROUSEL_TEMP_PATH = "\\..\\Upload\\image\\carousel\\temp";

	public CommonUtil() {
	}

	public static String toStringDefault(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	public static int toIntDefault(Object obj) {
		return obj == null ? 0 : Integer.valueOf(obj.toString());
	}

	public static Double toDoubleDefault(Object obj) {
		return obj == null ? 0 : Double.valueOf(obj.toString());
	}

	public static String getRepositoryRealPath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("WEB-INF");
	}
	
	public static String toArrayStringDefault(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	public static int toArrayIntDefault(Object obj) {
		return obj == null ? 0 : Integer.valueOf(obj.toString());
	}

}
