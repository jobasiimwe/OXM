/**
 * 
 */
package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.RecordStatus;
import org.agric.oxm.model.SellingPlace;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.dao.SellingPlaceDAO;
import org.agric.oxm.server.service.SellingPlaceService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Job
 * 
 */
public class SellingPlaceServiceImpl implements SellingPlaceService {

	@Autowired
	private SellingPlaceDAO sellingPlaceDAO;

	@Override
	public void save(SellingPlace sellingPlace) {
		sellingPlaceDAO.save(sellingPlace);
	}

	@Override
	public void validate(SellingPlace sellingPlace) throws ValidationException {

	}

	@Override
	public List<SellingPlace> getSellingPlaces() {
		return sellingPlaceDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Override
	public SellingPlace getSellingPlaceById(String id) {
		return sellingPlaceDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Override
	public void deleteSellingPlaceByIds(String[] ids) {
		sellingPlaceDAO.removeByIds(ids);
	}

}
