package org.agric.oxm.utils;

import java.util.Date;
import java.util.List;

import org.agric.oxm.model.BaseData;
import org.agric.oxm.model.enums.Condition;
import org.agric.oxm.model.util.MyStringUtil;
import org.apache.commons.lang.StringUtils;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

public class BuildSearchUtil {

	public static Search addFloor(Search search, String field, Date date) {
		if (date != null)
			search.addFilter(new Filter(field, date, Filter.OP_GREATER_OR_EQUAL));
		return search;
	}

	public static Search addCeiling(Search search, String field, Date date) {
		if (date != null)
			search.addFilter(new Filter(field, date, Filter.OP_LESS_OR_EQUAL));
		return search;
	}

	public static Search addFloor(Search search, String field, Double number) {
		if (number != null)
			search.addFilter(new Filter(field, number,
					Filter.OP_GREATER_OR_EQUAL));
		return search;
	}

	public static Search addCeiling(Search search, String field, Double number) {
		if (number != null)
			search.addFilter(new Filter(field, number, Filter.OP_LESS_OR_EQUAL));
		return search;
	}

	// ===========================================================================

	public static Search addStringLike(Search search, String field,
			String string) {
		if (StringUtils.isNotBlank(string))
			search.addFilter(new Filter(field, "%" + string + "%",
					Filter.OP_ILIKE));
		return search;
	}

	public static Search addStringsLike(Search search, String fieldsString,
			String fieldSeparator, String string) {

		if (StringUtils.isNotBlank(string)) {
			String[] fields = fieldsString.split(fieldSeparator);

			Filter[] filters = new Filter[fields.length];

			if (StringUtils.isNotBlank(string)) {
				for (int i = 0; i < fields.length; i++)
					filters[i] = new Filter(fields[i],
							MyStringUtil.percent(string), Filter.OP_ILIKE);
			}

			search.addFilterOr(filters);
		}
		
		return search;
	}

	public static Search addEqual(Search search, String field, BaseData b) {
		if (b != null)
			search.addFilterEqual(field, b);
		return search;
	}

	// ===========================================================================

	public static void addCondition(Filter[] condFilters, String field,
			Double number, Condition cond, int index) {
		condFilters[index] = new Filter(field, number, getFilterOption(cond));
	}

	public static void addCondition(List<Filter> condFilters, String field,
			Double number, Condition cond) {
		condFilters.add(new Filter(field, number, getFilterOption(cond)));
	}

	public static Filter[] convertToArray(List<Filter> filters) {

		Filter[] filterArray = new Filter[filters.size()];

		for (int i = 0; i < filters.size(); i++) {
			filterArray[i] = filters.get(i);
		}

		return filterArray;
	}

	public static void addCondition(Search search, String field, Double number,
			Condition cond) {
		search.addFilter(new Filter(field, number, getFilterOption(cond)));
	}

	public static int getFilterOption(Condition cond) {

		int filterOp = Filter.OP_EQUAL;

		switch (cond) {
		case EQUAL_TO:
			filterOp = Filter.OP_EQUAL;
			break;
		case GREATER_OR_EQUAL_TO:
			filterOp = Filter.OP_GREATER_OR_EQUAL;
			break;
		case GREATER_THAN:
			filterOp = Filter.OP_GREATER_THAN;
			break;
		case LESS_OR_EQUAL_TO:
			filterOp = Filter.OP_LESS_OR_EQUAL;
			break;
		case LESS_THAN:
			filterOp = Filter.OP_LESS_THAN;
			break;
		case NOT_EQUAL_TO:
			filterOp = Filter.OP_NOT_EQUAL;
			break;

		default:
			filterOp = Filter.OP_EQUAL;
			break;
		}

		return filterOp;
	}
}
