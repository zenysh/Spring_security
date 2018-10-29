package com.total.Security.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.total.Security.model.ExceltestFile;
import com.total.Security.repository.ExcelFileRepository;
import com.total.Security.response.ExcelResponse;

@Service
public class ExcelService {

	@Autowired
	ExcelFileRepository excelrepo;

	public List<ExcelResponse> getexceldetails(String[] sort, Integer size, Integer page, String search,
			Pageable pageable) {
		List<ExcelResponse> excelresponse = new ArrayList<>();
		List<ExceltestFile> exceltestfile = null;

		if (search == null)
			throw new ServiceException("Laganikarta Not Found :");

		if (search != null)
			exceltestfile = excelrepo.findByUserNames(search, pageable);
		for (ExceltestFile e : exceltestfile) {
			excelresponse.add(getdetails(e));
		}
		return excelresponse;
	}

	private ExcelResponse getdetails(ExceltestFile ex) {
		ExcelResponse exee = new ExcelResponse();
		exee.setListid(ex.getListid());
		exee.setAddress(ex.getAddress());
		exee.setUserNames(ex.getuserNames());
		exee.setPhonenumber(ex.getPhonenumber());
		return exee;
	}

}
