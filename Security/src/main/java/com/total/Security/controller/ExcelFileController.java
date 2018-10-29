package com.total.Security.controller;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.regex.Pattern;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.total.Security.model.ExceltestFile;
import com.total.Security.repository.ExcelFileRepository;
import com.total.Security.response.ExcelResponse;
import com.total.Security.service.ExcelService;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/rest/excel")
public class ExcelFileController {

	@Autowired
	ExcelFileRepository excelrepo;
	
	@Autowired
	ExcelService service;

	@SuppressWarnings({ "rawtypes", "resource" })
	@ApiOperation(value = "Upload an excel file for State")
	@RequestMapping(value = "/uploadState", method = RequestMethod.POST)
	ResponseEntity<Object> processExcelSheet(@RequestParam("Users") MultipartFile multipartFile) throws IOException {

		InputStream stream = multipartFile.getInputStream();
		XSSFWorkbook workbook = new XSSFWorkbook(stream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFRow row;
		Iterator rows = sheet.rowIterator();

		while (rows.hasNext()) {
			row = (XSSFRow) rows.next();
			if (row.getRowNum() == 0) {
				continue;
			}
			try {
				ExceltestFile excel = new ExceltestFile();
				String string0 = row.getCell(0).toString();
				byte[] u0 = string0.getBytes("UTF-8");
				string0 = new String(u0, "UTF-8");
				String[] b = string0.split(Pattern.quote("."));
				excel.setListid(b[0]);

				String string1 = row.getCell(1).toString();
				byte[] u1 = string1.getBytes("UTF-8");
				string1 = new String(u1, "UTF-8");
				excel.setuserNames(string1);

				String string2 = row.getCell(2).toString();
				byte[] u2 = string2.getBytes("UTF-8");
				string2 = new String(u2, "UTF-8");
				excel.setAddress(string2);

				String string3 = row.getCell(3).toString();
				byte[] u3 = string3.getBytes("UTF-8");
				string3 = new String(u3, "UTF-8");
				if (string3.contains("E")) {
					String[] temp1 = string3.split("E");
					String[] temp2 = temp1[0].split(Pattern.quote("."));
					String final_number = temp2[0].concat(temp2[1]);
					excel.setPhonenumber(final_number);
				} else {
					String[] a = string3.split(Pattern.quote("."));
					excel.setPhonenumber(a[0]);
				}

				excelrepo.save(excel);

			} catch (UnsupportedEncodingException e) {
				System.out.println(e.getMessage());
			}

		}
		return new ResponseEntity<Object>("Excel File uploaded!", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getexcel", method = RequestMethod.GET)
	public ResponseEntity<Object>getStates(
			@RequestParam(value = "sort", required = false) String[] sort,
			@RequestParam(value = "size", required = false,defaultValue="1000") Integer size,
			@RequestParam(value = "page", required = false,defaultValue="0") Integer page,
			@RequestParam(value = "search", required = false) String search,
			@ApiIgnore Pageable pageable)
	{
		List<ExcelResponse>response = service.getexceldetails(sort, size, page, search, pageable);
		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		responseMap.put("page", page);
		responseMap.put("data", response);
		return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
		
	}
	

}
