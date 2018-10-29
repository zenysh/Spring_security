package com.total.Security.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.total.Security.model.FilterBeansModel;

@RestController
public class FilterBeans {
	
	//filtering field 3 here and sending only field 1 and field 2
	@GetMapping("/filtering")
	public MappingJacksonValue retrivebeans()
	{
		FilterBeansModel filterbeans = new FilterBeansModel("value1","value2","value3");
		MappingJacksonValue mapping = new MappingJacksonValue(filterbeans);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
		.filterOutAllExcept("field1", "field2");
		FilterProvider filters = new SimpleFilterProvider()
		.addFilter("beanfilter", filter); 
		mapping.setFilters(filters);
		return mapping;
	}
	
	@GetMapping("/filtering-list-beans")
	public MappingJacksonValue retriveBeans()
	{
		List<FilterBeansModel> list = Arrays.asList(new FilterBeansModel("test1","test2","test3"), new FilterBeansModel("values1","values2","values3"));
		MappingJacksonValue mapping = new MappingJacksonValue(list);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
		.filterOutAllExcept("field2","field3");
		FilterProvider filters = new SimpleFilterProvider()
	    .addFilter("beanfilter", filter); 
		mapping.setFilters(filters);
		return mapping;
		
	}

}
