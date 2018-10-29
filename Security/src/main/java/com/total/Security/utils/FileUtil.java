package com.total.Security.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@SuppressWarnings("restriction")
public class FileUtil {

	private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);
	private static final String FILE_DIRECTORY = "uploaded-files";

	/**
	 * Writes file in specified location.
	 * 
	 * @param fileName The file name that can not be null and saves file with this given
	 * name.
	 * @param location The location can not be null and uses to save file in this given
	 * location.
	 * @param value The value that should not be null and must containg Base64 string.
	 * @return <code>true</code> if written successfully otherwise <code>false</code>
	 */
	@Deprecated
	public static boolean write(String fileName, String location, String value) {
		
		LOG.info("File Uploading...");
		InputStream is = null;
		OutputStream os = null;
		location += "//" + fileName;
		try {
			File file = new File(location);
			is = new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8));
			fileName = null;
			value = null;
			os = new FileOutputStream(file);
			int bytesRead = 0;
			int batchLength = 10000000;
			byte[] batch = new byte[batchLength];
			while ((bytesRead = is.read(batch)) > 0) {
				byte[] ba = new byte[bytesRead];

				for (int i = 0; i < ba.length; i++) {
					ba[i] = batch[i];
				}
				ba = Base64.decode(new String(ba));
				os.write(ba, 0, ba.length);
			}
			LOG.info("File uploaded to: " + file.getAbsolutePath());
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.info(e.toString());
			return false;
		}
		finally {
			try {
				is.close();
			}
			catch (IOException e) {
				e.printStackTrace();
				LOG.info(e.toString());
			}
			try {
				os.close();
			}
			catch (IOException e) {
				e.printStackTrace();
				LOG.info(e.toString());
			}
		}
	}

	/**
	 * Writes file in default location.
	 * 
	 * @param fileName The file name that can not be null and saves file with this given
	 * name.
	 * @param value The value that should not be null and must contain Base64 string.
	 * @return <code>true</code> if written successfully otherwise <code>false</code>
	 */
	public static File write(String fileName, String value) {
		LOG.info("File Uploading...");
		InputStream is = null;
		OutputStream os = null;
		File directory = new File(FILE_DIRECTORY);
		File file = null;
		try {

			if (!directory.exists()) {
				directory.mkdir();
			}
			file = new File(directory.getPath().concat(File.separator).concat(fileName));
			is = new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8));
			fileName = null;
			value = null;
			os = new FileOutputStream(file);
			int bytesRead = 0;
			int batchLength = 10000000;
			byte[] batch = new byte[batchLength];
			while ((bytesRead = is.read(batch)) > 0) {
				byte[] ba = new byte[bytesRead];

				for (int i = 0; i < ba.length; i++) {
					ba[i] = batch[i];
				}
				ba = Base64.decode(new String(ba));
				if (os != null && ba != null) {
					os.write(ba, 0, ba.length);
				}
			}
			LOG.info("File uploaded to: " + file.getAbsolutePath());

		}
		catch (Exception e) {
			e.printStackTrace();
			LOG.info(e.toString());
		}
		finally {
			try {
				is.close();
			}
			catch (IOException e) {
				e.printStackTrace();
				LOG.info(e.toString());
			}
			try {
				os.close();
			}
			catch (IOException e) {
				e.printStackTrace();
				LOG.info(e.toString());
			}
		}
		return file;
	}
	/**
	 * 
	 *<<This methods deletes the file with the url specified>>
	 * @param url
	 * @author Deependra
	 * @since 27/03/2018, Modified In: @version, By @author
	 */
	public static void deleteFile(String url) {
			File directory =new File(url);
			if(directory.delete())
				System.out.println("---------->>>>>>>>>>>>>>>Succesfully Deleted");
			else
				System.out.println("---------->>>>>>>>>>>>>>>Not Deleted");
	}
}

