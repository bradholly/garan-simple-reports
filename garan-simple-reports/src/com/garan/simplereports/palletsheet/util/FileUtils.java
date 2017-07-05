package com.garan.simplereports.palletsheet.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtils {
	public static String getTempFile(String extension){
		UUID uuid = UUID.randomUUID();
		
		try {
			File tempFile = File.createTempFile(uuid.toString(), extension);
			return tempFile.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
