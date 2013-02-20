/**
 * 
 */
package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.RecordStatus;
import org.agric.oxm.model.SellingPlace;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.dao.SellingPlaceDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.SellingPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Job
 * 
 */
@Service("sellingPlaceService")
@Transactional
public class SellingPlaceServiceImpl implements SellingPlaceService {

	@Autowired
	private SellingPlaceDAO sellingPlaceDAO;

	@Secured({ PermissionConstants.ADD_SELLING_PLACE,
			PermissionConstants.EDIT_SELLING_PLACE })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(SellingPlace sellingPlace) {
		sellingPlaceDAO.save(sellingPlace);
	}

	@Secured({ PermissionConstants.VIEW_SELLING_PLACE })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(SellingPlace sellingPlace) throws ValidationException {

	}

	@Secured({ PermissionConstants.VIEW_SELLING_PLACE })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<SellingPlace> getSellingPlaces() {
		return sellingPlaceDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Secured({ PermissionConstants.VIEW_SELLING_PLACE })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public SellingPlace getSellingPlaceById(String id) {
		return sellingPlaceDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Secured({ PermissionConstants.DELETE_SELLING_PLACE })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteSellingPlacesByIds(String[] ids) {
		sellingPlaceDAO.removeByIds(ids);
	}

}
