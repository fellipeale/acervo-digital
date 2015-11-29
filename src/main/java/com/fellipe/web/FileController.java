package com.fellipe.web;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
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
			@RequestParam("recordId") Long recordId,
			@RequestParam("fileName") String fileName,
			@RequestParam("file") MultipartFile file) throws Exception {
		if (!file.isEmpty()) {
			try {
				String filePath = "/" + libraryId + "/" + recordId;
				String newFileName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())
						+ fileName.substring(fileName.lastIndexOf('.'));
				if (!Files.exists(Paths.get(basePath + filePath))) {
					Files.createDirectories(Paths.get(basePath + filePath));
				}
				file.transferTo(new File(basePath + filePath + "/" + newFileName));
				return "{ \"path\": \"" + filePath + "/" + newFileName + "\", \"name\": \"" + newFileName + "\" }";
			} catch (Exception e) {
				throw e;
			}
		} else {
			throw new Exception("You failed to upload " + fileName
					+ " because the file was empty.");
		}
	}
	
	@RequestMapping(value = "/download/{libraryId}/{recordId}/{fileName:.+}", method = RequestMethod.GET)
	@ResponseBody
	public FileSystemResource handleFileDownload(
			@PathVariable("libraryId") Long libraryId,
			@PathVariable("recordId") Long recordId,
			@PathVariable("fileName") String fileName) {
		String filePath = "/" + libraryId + "/" + recordId;
		return new FileSystemResource(new File(basePath + filePath + "/" + fileName));
	}

}
