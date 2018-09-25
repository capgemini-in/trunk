package com.capgemini.webapp.spring.controller.user;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.seleniumhq.jetty7.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capgemini.webapp.common.constants.IApplicationConstants;
import com.capgemini.webapp.service.api.DealerService;
import com.capgemini.webapp.service.api.model.QuotationModel;
import com.capgemini.webapp.view.beans.FileInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("/files")
public class FileOperationsController {

	@Autowired
	ServletContext context;

	@Autowired
	DealerService dealerService;

	@RequestMapping(value = "/fileupload/", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	public ResponseEntity<FileInfo> upload(@RequestParam("file") MultipartFile inputFile) {

		System.out.println(" Context :" + context.getContextPath());
		FileInfo fileInfo = new FileInfo();
		HttpHeaders headers = new HttpHeaders();
		if (!inputFile.isEmpty()) {
			try {
				String originalFilename = inputFile.getOriginalFilename();
				File destinationFile = new File(context.getRealPath("/uploaded") + File.separator + originalFilename);
				inputFile.transferTo(destinationFile);
				fileInfo.setFileName("/uploaded" + File.separator + originalFilename);
				fileInfo.setFileSize(inputFile.getSize());
				headers.add("File Uploaded Successfully - ", originalFilename);
				return new ResponseEntity<FileInfo>(fileInfo, headers, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/uploadData/", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@ResponseBody
	public boolean executeSampleService(@RequestPart("quot") @Valid QuotationModel quotation,
			@RequestPart("file") @Valid MultipartFile file) {

		System.out.println(quotation.getDiscountedPrice());
		System.out.println(file.getOriginalFilename());

		return false;
	}

	@RequestMapping(value = "/uploadQuotation/", method = RequestMethod.POST)
	public ResponseEntity<String> uploadQuotation(@RequestParam("file") MultipartFile file,
			@RequestParam("quot") String quotationJson) {
		System.out.println("quotation:" + quotationJson);
		ObjectMapper mapper = new ObjectMapper();
		QuotationModel model = null;
		try {
			model = mapper.readValue(quotationJson, QuotationModel.class);
			System.out.println(" Model :-" + model.getDiscountedPrice());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String fileName = file.getOriginalFilename();
		JsonObject responseObj = new JsonObject();
		boolean isUpdated = dealerService.updateQuotationRequest(model);
		if (isUpdated) {

			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_SUCCESS_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "Quotation Requested Uploaded successfully");
			return new ResponseEntity(responseObj.toString(), HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		} else {

			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_ERROR_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "Error Uploaded quotation request");
			return new ResponseEntity(responseObj.toString(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/download/{fileName}", method = RequestMethod.GET)       
    public void downloadFile(HttpServletResponse response, @PathVariable("fileName") String fileName ) 
          throws IOException {
                       
                //  File file = new File("D:\\dev\\test.pdf");
                  
                  File sourceFile = new File(
                                     context.getRealPath("/uploaded") + File.separator + fileName);
                        
                     
                  if(!sourceFile.exists()){
                      String errorMessage = "Sorry. The file you are looking for does not exist";
                      System.out.println(errorMessage);
                      OutputStream outputStream = response.getOutputStream();
                      outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
                      outputStream.close();
                      return;
                  }
                   
                  String mimeType= URLConnection.guessContentTypeFromName(sourceFile.getName());
                  if(mimeType==null){
                      System.out.println("mimetype is not detectable, will take default");
                      mimeType = "application/octet-stream";
                  }
                   
                  System.out.println("mimetype : "+mimeType);
                   
                  response.setContentType(mimeType);
                   
                  /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
                      while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
                  response.setHeader("Content-Disposition", String.format("inline; filename=\"" + sourceFile.getName() +"\""));
          
                   
                  /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
                  //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
                   
                  response.setContentLength((int)sourceFile.length());
          
                  InputStream inputStream = new BufferedInputStream(new FileInputStream(sourceFile));
          
                  //Copy bytes from source to destination(outputstream in this example), closes both streams.
                  FileCopyUtils.copy(inputStream, response.getOutputStream());
              }

}
