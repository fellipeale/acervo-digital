package com.fellipe.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

	private String basePath = "/tmp/acervo-digital";

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public @ResponseBody String provideUploadInfo() {
		return "You can upload a file by posting to this same URL.";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(
			@RequestParam("libraryId") Long libraryId,
			@RequestParam("fileName") String fileName,
			@RequestParam("file") MultipartFile file) throws Exception {
		if (!file.isEmpty()) {
			try {
				String filePath = "/" + libraryId;
				String newFileName = new SimpleDateFormat("yyyyMMddhhmmssSSS")
						.format(new Date())
						+ fileName.substring(fileName.lastIndexOf('.'));
				if (!Files.exists(Paths.get(basePath + filePath))) {
					Files.createDirectories(Paths.get(basePath + filePath));
				}
				file.transferTo(new File(basePath + filePath + "/"
						+ newFileName));
				return "{ \"path\": \"" + filePath + "/" + newFileName
						+ "\", \"name\": \"" + newFileName + "\" }";
			} catch (Exception e) {
				throw e;
			}
		} else {
			throw new Exception("You failed to upload " + fileName
					+ " because the file was empty.");
		}
	}

	@RequestMapping(value = "/download/{libraryId}/{fileName:.+}", method = RequestMethod.GET)
	@ResponseBody
	public void handleFileDownload(HttpServletResponse response,
			@PathVariable("libraryId") Long libraryId,
			@PathVariable("fileName") String fileName) throws IOException {
		String filePath = "/" + libraryId;

		File file = new File(basePath + filePath + "/" + fileName);

		if (!file.exists()) {
			String errorMessage = "Sorry. The file you are looking for does not exist";
			System.out.println(errorMessage);
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}

		String mimeType = URLConnection
				.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			System.out.println("mimetype is not detectable, will take default");
			mimeType = "application/octet-stream";
		}

		System.out.println("mimetype : " + mimeType);

		response.setContentType(mimeType);

		/*
		 * "Content-Disposition : inline" will show viewable types [like
		 * images/text/pdf/anything viewable by browser] right on browser while
		 * others(zip e.g) will be directly downloaded [may provide save as
		 * popup, based on your browser setting.]
		 */
		response.setHeader("Content-Disposition",
				String.format("inline; filename=\"" + file.getName() + "\""));

		/*
		 * "Content-Disposition : attachment" will be directly download, may
		 * provide save as popup, based on your browser setting
		 */
		// response.setHeader("Content-Disposition",
		// String.format("attachment; filename=\"%s\"", file.getName()));

		response.setContentLength((int) file.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(
				file));

		// Copy bytes from source to destination(outputstream in this example),
		// closes both streams.
		FileCopyUtils.copy(inputStream, response.getOutputStream());

	}

}
