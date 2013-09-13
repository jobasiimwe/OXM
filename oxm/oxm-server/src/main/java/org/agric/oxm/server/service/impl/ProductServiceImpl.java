/**
 * 
 */
package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.Crop;
import org.agric.oxm.model.Product;
import org.agric.oxm.model.enums.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.ProductSearchParameters;
import org.agric.oxm.server.OXMConstants;
import org.agric.oxm.server.dao.ProductDAO;
import org.agric.oxm.server.service.ProductService;
import org.agric.oxm.utils.MyValidate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

/**
 * @author Job
 * 
 */
@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(Product product) {
		productDAO.save(product);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(Product product) throws ValidationException {
		MyValidate.isNotBlank(product.getName(), "Name");

		if (product.getUnitsOfMeasure() == null
				|| product.getUnitsOfMeasure().size() == 0)
			throw new ValidationException("Units Of Measure can not be empty");
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Product> getAll() {
		return productDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Product> getBy(Crop crop) {
		List<Product> list = productDAO.searchByPropertyEqual("crop", crop);
		return list;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Product getById(String id) {
		return productDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteByIds(String[] ids) {
		productDAO.removeByIds(ids);
	}

	// =================================================================================

	@Override
	public Product searchUniqueWithParams(ProductSearchParameters params)
			throws Exception {
		throw new Exception("Un-implemented");
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Product> searchWithParams(ProductSearchParameters params) {
		Search search = prepareProductSearch(params);
		List<Product> products = productDAO.search(search);
		return products;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Product> searchWithParams(ProductSearchParameters params,
			Integer pageNo) {
		Search search = prepareProductSearch(params);
		search.setMaxResults(OXMConstants.MAX_NUM_PAGE_RECORD);

		/*
		 * if the page number is less than or equal to zero, no need for paging
		 */
		if (pageNo > 0) {
			search.setPage(pageNo - 1);
		} else {
			search.setPage(0);
		}
		List<Product> products = productDAO.search(search);
		return products;
	}

	private Search prepareProductSearch(ProductSearchParameters params) {
		Search search = new Search();

		search.addSort("name", false, true);
		if (params.getCrop() != null) {
			search.addFilterEqual("crop", params.getCrop());
		}

		if (StringUtils.isNotBlank(params.getNameOrDescription())) {
			search.addFilterOr(
					new Filter("name", "%" + params.getNameOrDescription()
							+ "%", Filter.OP_ILIKE), new Filter("description",
							"%" + params.getNameOrDescription() + "%",
							Filter.OP_ILIKE));
		}

		return search;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public long numberInSearch(ProductSearchParameters params) {
		Search search = prepareProductSearch(params);
		return productDAO.count(search);
	}

	// =================================================================================

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Product> getAnnonymouslyViewableProducts() {
		Search search = new Search();
		search.setMaxResults(OXMConstants.MAX_NUM_PRE_LOGIN_PAGE_RECORD);

		search.addSort("date", false, true);
		search.setPage(0);

		List<Product> products = productDAO.search(search);
		return products;
	}

}
