package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.Season;
import org.agric.oxm.model.enums.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.dao.SeasonDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.SeasonService;
import org.agric.oxm.utils.MyValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("seasonService")
@Transactional
public class SeasonServiceImpl implements SeasonService {

	@Autowired
	private SeasonDAO seasonDAO;

	@Override
	@Secured({ PermissionConstants.MANAGE_SEASONS,
			PermissionConstants.VIEW_SEASONS })
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Season season) {
		seasonDAO.save(season);
	}

	@Override
	public void validate(Season season) throws ValidationException {
		MyValidate.isNotNull(season.getStartDate(), "Start Date");
		MyValidate.isNotNull(season.getEndDate(), "End Date");

		if (season.getEndDate().before(season.getStartDate()))
			throw new ValidationException(
					"End date cant be before the start date");
	}

	@Override
	@Secured({ PermissionConstants.MANAGE_SEASONS,
			PermissionConstants.VIEW_SEASONS })
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Season> getAll() {
		return seasonDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Override
	@Secured({ PermissionConstants.MANAGE_SEASONS,
			PermissionConstants.VIEW_SEASONS })
	@Transactional(propagation = Propagation.REQUIRED)
	public Season getById(String id) {
		return seasonDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Override
	@Secured({ PermissionConstants.MANAGE_SEASONS,
			PermissionConstants.VIEW_SEASONS })
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteByIds(String[] ids) {
		seasonDAO.removeByIds(ids);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Season> getAnnonymously() {
		return seasonDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

}
