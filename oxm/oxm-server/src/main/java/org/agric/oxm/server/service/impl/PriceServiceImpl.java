/**
 * 
 */
package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.Price;
import org.agric.oxm.model.enums.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.PriceSearchParams;
import org.agric.oxm.model.util.MyDateUtil;
import org.agric.oxm.server.OXMConstants;
import org.agric.oxm.server.dao.PriceDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.PriceService;
import org.agric.oxm.utils.MyValidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

/**
 * @author Job
 * 
 */
@Service("priceService")
@Transactional
public class PriceServiceImpl implements PriceService {

	private static Logger log = LoggerFactory.getLogger(MyDateUtil.class);

	@Autowired
	private PriceDAO priceDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(Price price) {
		priceDAO.save(price);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(Price t) throws ValidationException {
		MyValidate.isNotNull(t.getDate(), "Date");
		MyValidate.isNotFutureDate(t.getDate(), "Date");
		MyValidate.isNotNull(t.getProduct(), "Product");
		MyValidate.isNotNull(t.getQty(), "Quantity");
		MyValidate.isNotLessOrEqualToZero(t.getQty(), "Quantity");

		if (t.getRetail() == null && t.getWholeSale() == null)
			throw new ValidationException(
					"Both Retail and Whole sale price can not be null");

		if (t.getRetail() != null)
			MyValidate.isNotLessOrEqualToZero(t.getRetail(), "Retail Price");

		if (t.getWholeSale() != null)
			MyValidate.isNotLessOrEqualToZero(t.getWholeSale(),
					"Wholesale Price");

	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Price> getPrices() {
		return priceDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Price searchUniqueWithParams(PriceSearchParams params)
			throws Exception {
		Search search = preparePriceSearch(params);

		try {
			Price price = priceDAO.searchUnique(search);
			return price;
		} catch (Exception e) {
			log.error("Error picking price", e);
			throw e;
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Price> searchWithParams(PriceSearchParams params, Integer pageNo) {
		Search search = preparePriceSearch(params);
		search.setMaxResults(OXMConstants.MAX_NUM_PAGE_RECORD);

		/*
		 * if the page number is less than or equal to zero, no need for paging
		 */
		if (pageNo > 0) {
			search.setPage(pageNo - 1);
		} else {
			search.setPage(0);
		}
		List<Price> prices = priceDAO.search(search);
		return prices;
	}

	private Search preparePriceSearch(PriceSearchParams params) {
		Search search = new Search();

		search.addSort("date", true, true);
		if (params.getProduct() != null) {
			search.addFilterEqual("product", params.getProduct());
		}

		if (params.getSellingPlace() != null) {
			search.addFilterEqual("sellingPlace", params.getSellingPlace());
		}

		// if (params.getSellType() != null) {
		// search.addFilterEqual("sellType", params.getSellType());
		// }

		if (params.getFromDate() != null && params.getToDate() != null) {
			Filter fromDateFilter = new Filter("date", params.getFromDate(),
					Filter.OP_GREATER_OR_EQUAL);
			Filter toDateFilter = new Filter("date", params.getToDate(),
					Filter.OP_LESS_OR_EQUAL);
			search.addFilterAnd(fromDateFilter, toDateFilter);
		} else if (params.getFromDate() != null) {
			search.addFilterGreaterOrEqual("date", params.getFromDate());
		} else if (params.getToDate() != null) {
			search.addFilterLessOrEqual("date", params.getToDate());
		}

		return search;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public long numberInSearch(PriceSearchParams params) {
		Search search = preparePriceSearch(params);
		return priceDAO.count(search);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Price getPriceById(String id) {
		return priceDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Secured({ PermissionConstants.DELETE_PRICE })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deletePricesByIds(String[] ids) {
		priceDAO.removeByIds(ids);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Price> getAnnonymouslyViewablePrices() {
		Search search = new Search();
		search.setMaxResults(OXMConstants.MAX_NUM_PRE_LOGIN_PAGE_RECORD);

		search.addSort("date", true, true);
		search.setPage(0);

		List<Price> prices = priceDAO.search(search);
		return prices;
	}

}
