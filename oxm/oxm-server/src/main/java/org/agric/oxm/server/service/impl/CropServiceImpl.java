package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.Crop;
import org.agric.oxm.model.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.dao.CropDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Job
 *
 */
@Transactional
@Service("cropService")
public class CropServiceImpl implements CropService {

	@Autowired
	private CropDAO cropDAO;

	@Secured({ PermissionConstants.ADD_CROP, PermissionConstants.EDIT_CROP })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(Crop crop) {
		cropDAO.save(crop);
	}

	@Secured({ PermissionConstants.VIEW_CROP })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(Crop crop) throws ValidationException {

	}

	@Secured({ PermissionConstants.VIEW_CROP })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Crop> getCrops() {
		return cropDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Secured({ PermissionConstants.VIEW_CROP })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Crop getCropById(String id) {
		return cropDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Secured({ PermissionConstants.DELETE_CROP })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteCropsByIds(String[] ids) {
		cropDAO.removeByIds(ids);
	}

}
