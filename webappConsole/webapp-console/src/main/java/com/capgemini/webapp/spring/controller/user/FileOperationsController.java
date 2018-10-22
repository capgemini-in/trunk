package com.capgemini.webapp.spring.controller.user;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capgemini.webapp.common.constants.IApplicationConstants;
import com.capgemini.webapp.service.api.DealerService;
import com.capgemini.webapp.service.api.UserService;
import com.capgemini.webapp.service.api.model.CityModel;
import com.capgemini.webapp.service.api.model.QuotationModel;
import com.capgemini.webapp.service.api.model.UserModel;
import com.capgemini.webapp.service.api.model.UserProfileModel;
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

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private Environment env;

	private Log logger = LogFactory.getLog(FileOperationsController.class.getName());

	@RequestMapping(value = "/fileupload/", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	public ResponseEntity<FileInfo> upload(@RequestParam("file") MultipartFile inputFile) {

		logger.info("::FileOperationsController : upload() Called to Upload File");
		FileInfo fileInfo = new FileInfo();
		HttpHeaders headers = new HttpHeaders();
		String originalFilename = "";
		if (!inputFile.isEmpty()) {
			try {
				originalFilename = inputFile.getOriginalFilename();
				File destinationFile = new File(context.getRealPath("/uploaded") + File.separator + originalFilename);
				inputFile.transferTo(destinationFile);
				fileInfo.setFileName("/uploaded" + File.separator + originalFilename);
				fileInfo.setFileSize(inputFile.getSize());
				headers.add("File Uploaded Successfully - ", originalFilename);
				return new ResponseEntity<FileInfo>(fileInfo, headers, HttpStatus.OK);
			} catch (Exception e) {
				logger.error("While uploading Document " + originalFilename + " Exception occured " + e.getMessage());
				return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
			}
		} else {
			logger.info("User uploaded file is not valid");
			return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/bulkUpload/", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	public ResponseEntity<String> bulkUpload(@RequestParam("userupload") MultipartFile inputFile) {

		logger.info("::FileOperationsController : bulkUpload() Called to add user's in Bulk");
		JsonObject responseObj = new JsonObject();
		List<UserModel> users = userService.findAllUsers();
		List<String> ssoId = new ArrayList<>();
		for (int j = 0; j < users.size(); j++) {
			ssoId.add(users.get(j).getSsoId());
		}

		HttpHeaders headers = new HttpHeaders();
		boolean isUploaded = false;
		if (!inputFile.isEmpty()) {
			isUploaded = true;
			String originalFilename = "";
			try {
				originalFilename = inputFile.getOriginalFilename();
				File destinationFile = new File(context.getRealPath("/uploaded") + File.separator + originalFilename);
				inputFile.transferTo(destinationFile);

				InputStream inputStream = null;
				OutputStream outputStream = null;
				inputStream = inputFile.getInputStream();
				if (!destinationFile.exists()) {
					destinationFile.createNewFile();
				}
				outputStream = new FileOutputStream(destinationFile);
				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}

				// this is excel upload
				List<UserModel> lstUser = new ArrayList<>();

				int i = 1;
				Workbook workbook;

				workbook = WorkbookFactory.create(destinationFile);
				Sheet sheet = workbook.getSheetAt(0);
				Row row;
				while (i <= sheet.getLastRowNum()) {
					UserModel user = new UserModel();
					CityModel city = new CityModel();
					row = sheet.getRow(i++);
					user.setSsoId(row.getCell(0).getStringCellValue());
					user.setPassword(passwordEncoder.encode(row.getCell(1).getStringCellValue()));
					user.setFirstName(row.getCell(2).getStringCellValue());
					user.setLastName(row.getCell(3).getStringCellValue());
					user.setEmail(row.getCell(4).getStringCellValue());

					Double value = row.getCell(5).getNumericCellValue();
					Long longValue = value.longValue();
					String mobileNumber = new String(longValue.toString());
					System.out.println(" Mobile Number " + mobileNumber);

					user.setMobileNumber(mobileNumber);

					row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
					city.setCityId(Integer.valueOf(row.getCell(6).getStringCellValue()));
					user.setCity(city);
					UserProfileModel profile = new UserProfileModel();
					String role = row.getCell(7).getStringCellValue();
					if (IApplicationConstants.ROLE_USER.equalsIgnoreCase(role)) {
						profile.setId(IApplicationConstants.ROLE_ID_FOR_USER);
					}
					if (IApplicationConstants.ROLE_ADMIN.equalsIgnoreCase(role)) {
						profile.setId(IApplicationConstants.ROLE_ID_FOR_ADMIN);
					}
					if (IApplicationConstants.ROLE_DBA.equalsIgnoreCase(role)) {
						profile.setId(IApplicationConstants.ROLE_ID_FOR_DBA);
					}
					if (IApplicationConstants.ROLE_CUSTOMER.equalsIgnoreCase(role)) {
						profile.setId(IApplicationConstants.ROLE_ID_FOR_CUSTOMER);
					}
					if (IApplicationConstants.ROLE_DEALER.equalsIgnoreCase(role)) {
						profile.setId(IApplicationConstants.ROLE_ID_FOR_DEALER);
					}
					profile.setType(row.getCell(7).getStringCellValue());
					user.getUserProfiles().add(profile);
					if (!ssoId.contains(user.getSsoId())) {
						lstUser.add(user);
					}
				}
				workbook.close();
				userService.uploadUser(lstUser);
				headers.add("File Uploaded Successfully - ", originalFilename);
			} catch (Exception e) {
				logger.error("While uploading Document " + originalFilename + " Exception occured " + e.getMessage());
				isUploaded = false;
			}
		}

		if (isUploaded) {
			logger.info("Bulk Upload For Users Requested Uploaded successfully");
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_SUCCESS_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE,
					"Bulk Upload For Users Requested Uploaded successfully");
			return new ResponseEntity(responseObj.toString(), HttpStatus.OK);
		} else {
			logger.info("Error in Bulk Upload For Users request");
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_ERROR_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, " Error in Bulk Uplod For Users request");
			return new ResponseEntity(responseObj.toString(), HttpStatus.OK);
		}
	}

	/*
	 * @RequestMapping(value = "/uploadData/", method = RequestMethod.POST, consumes
	 * = { "multipart/form-data" })
	 * 
	 * @ResponseBody public boolean executeSampleService(@RequestPart("quot") @Valid
	 * QuotationModel quotation,
	 * 
	 * @RequestPart("file") @Valid MultipartFile file) {
	 * 
	 * System.out.println(quotation.getDiscountedPrice());
	 * System.out.println(file.getOriginalFilename());
	 * 
	 * return false; }
	 */

	@RequestMapping(value = "/uploadQuotation/", method = RequestMethod.POST)
	public ResponseEntity<String> uploadQuotation(@RequestParam("file") MultipartFile file,
			@RequestParam("quot") String quotationJson) {
		ObjectMapper mapper = new ObjectMapper();
		QuotationModel model = null;
		logger.info("::FileOperationsController : uploadQuotation() Called to Upload Quotation");
		String fileName = file.getOriginalFilename();
		try {
			model = mapper.readValue(quotationJson, QuotationModel.class);
			// File destinationFile = new File(context.getRealPath("/uploaded") +
			// File.separator + fileName);
			File destinationFile = new File(env.getRequiredProperty("uploaded_quote_path") + File.separator + fileName);
			file.transferTo(destinationFile);
		} catch (IOException e) {
			logger.error("While uploading quotation " + fileName + " Exception occured " + e.getMessage());
		}

		JsonObject responseObj = new JsonObject();
		model.setFilePath(fileName);
		boolean isUpdated = dealerService.updateQuotationRequest(model);
		if (isUpdated) {
			logger.info("::FileOperationsController : uploadQuotation() Called to Upload Quotation");
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_SUCCESS_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "Quotation Requested Uploaded successfully");
			return new ResponseEntity(responseObj.toString(), HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		} else {
			logger.info("::Exception occured while Uploading Quotation ");
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_ERROR_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "Error Uploaded quotation request");
			return new ResponseEntity(responseObj.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/download/", method = RequestMethod.GET)
	public void downloadFile(HttpServletResponse response, @RequestParam("fileName") String fileName)
			throws IOException {

		// File sourceFile = new File(context.getRealPath("/uploaded") + File.separator
		// + fileName);
		logger.info("::FileOperationsController : downloadFile() Called to Download Quotation");
		File sourceFile = new File(env.getRequiredProperty("uploaded_quote_path") + File.separator + fileName);

		if (!sourceFile.exists()) {
			String errorMessage = "Sorry. The file you are looking for does not exist";
			logger.info(" Quotation does not exist ");
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}

		String mimeType = URLConnection.guessContentTypeFromName(sourceFile.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}

		response.setContentType(mimeType);
		/*
		 * "Content-Disposition : inline" will show viewable types [like
		 * images/text/pdf/anything viewable by browser] right on browser while
		 * others(zip e.g) will be directly downloaded [may provide save as popup, based
		 * on your browser setting.]
		 */
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + sourceFile.getName() + "\""));

		/*
		 * "Content-Disposition : attachment" will be directly download, may provide
		 * save as popup, based on your browser setting
		 */
		// response.setHeader("Content-Disposition", String.format("attachment;
		// filename=\"%s\"", file.getName()));

		response.setContentLength((int) sourceFile.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(sourceFile));

		// Copy bytes from source to destination(outputstream in this example), closes
		// both streams.
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}

}
