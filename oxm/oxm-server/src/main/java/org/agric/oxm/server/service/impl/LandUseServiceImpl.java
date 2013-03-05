package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.LandUse;
import org.agric.oxm.model.RecordStatus;
import org.agric.oxm.model.User;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.dao.LandUseDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.LandUseService;
import org.agric.oxm.server.service.UserService;
import org.apache.commons.lang.StringUtils;
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
@Service("landUseService")
@Transactional
public class LandUseServiceImpl implements LandUseService {

	@Autowired
	private LandUseDAO landUseDAO;

	@Autowired
	private UserService userService;

	@Secured({ PermissionConstants.ADD_LAND_USE,
			PermissionConstants.EDIT_LAND_USE })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(LandUse landUse) {
		landUseDAO.save(landUse);
	}

	@Secured({ PermissionConstants.VIEW_LAND_USE })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(LandUse landUse) throws ValidationException {

		if (null == landUse.getCrop())
			throw new ValidationException("Crop can not be null");

		if (null == landUse.getSize())
			throw new ValidationException("Size can not be null");

		User user = userService.getUserById(landUse
				.getProducer().getId());

		if (user.getLandSize() == null)
			throw new ValidationException("Land size for "
					+ user.getName() + " is blank");

		Double landUseSum = 0.0;
		if (user.getLandUses() != null) {
			for (LandUse l : user.getLandUses()) {
				if (StringUtils.isNotEmpty(landUse.getId())) {
					if (l.equals(landUse))
						continue;
				}
				landUseSum += l.getSize();
			}
		}

		landUseSum += landUse.getSize();
		if (landUseSum > user.getLandSize())
			throw new ValidationException("Total land in use " + landUseSum
					+ " can not be greater than " + user.getName()
					+ "'s total land " + user.getLandSize());
	}

	@Secured({ PermissionConstants.VIEW_LAND_USE })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<LandUse> getLandUses() {
		return landUseDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Secured({ PermissionConstants.VIEW_LAND_USE })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public LandUse getLandUseById(String id) {
		return landUseDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Secured({ PermissionConstants.DELETE_LAND_USE })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteLandUsesByIds(String[] ids) {
		landUseDAO.removeByIds(ids);
	}

}
