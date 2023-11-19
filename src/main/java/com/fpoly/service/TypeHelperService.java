package com.fpoly.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public interface TypeHelperService {

	Date convertObjectTypeDate(Date input);

	Instant convertObjectTypeInstant(Instant input);

	BigDecimal convertObjectTypeBigDecimal(BigDecimal input);

	String convertObjectTypeString(String input);

	List<Long> convertObjectTypeListLong(List<Long> input);

	int convertObjectTypeListInt(Integer input);

	List<Boolean> convertObjectTypeListBoolean(List<Boolean> input);

}
