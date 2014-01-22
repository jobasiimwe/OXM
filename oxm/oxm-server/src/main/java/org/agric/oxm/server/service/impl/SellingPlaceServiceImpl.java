/**
 * 
 */
package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.SellingPlace;
import org.agric.oxm.model.enums.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.OXMConstants;
import org.agric.oxm.server.dao.SellingPlaceDAO;
import org.agric.oxm.server.service.SellingPlaceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

/**
 * @author Job
 * 
 */
@Service("sellingPlaceService")
@Transactional
public class SellingPlaceServiceImpl implements SellingPlaceService {

	@Autowired
	private SellingPlaceDAO sellingPlaceDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(SellingPlace sellingPlace) {
		sellingPlaceDAO.save(sellingPlace);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(SellingPlace sellingPlace) throws ValidationException {
		if (StringUtils.isEmpty(sellingPlace.getName()))
			throw new ValidationException("Name is required");

		// if (sellingPlace.getall == null)
		//	throw new ValidationException("Selling types required");
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<SellingPlace> getAll() {
		return sellingPlaceDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public SellingPlace getSellingPlaceById(String id) {
		return sellingPlaceDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteSellingPlacesByIds(String[] ids) {
		sellingPlaceDAO.removeByIds(ids);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<SellingPlace> getAnnonymouslyViewableSellingPlaces() {
		Search search = new Search();
		search.setMaxResults(OXMConstants.MAX_NUM_PRE_LOGIN_PAGE_RECORD);

		search.addSort("name", false, true);
		search.setPage(0);

		List<SellingPlace> sellingPlaces = sellingPlaceDAO.search(search);
		return sellingPlaces;
	}
}
