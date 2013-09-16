package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.Position;
import org.agric.oxm.model.enums.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.SingleStringSearchParameters;
import org.agric.oxm.server.dao.PositionDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.PositionService;
import org.agric.oxm.utils.BuildSearchUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

/**
 * 
 * @author Job
 * 
 */
@Service("positionService")
@Transactional
public class PositionServiceImpl implements PositionService {

	@Autowired
	private PositionDAO positionDAO;

	@Secured({ PermissionConstants.ADD_COMMITTEE_DETAILS,
			PermissionConstants.EDIT_COMMITTEE__DETAILS })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(Position position) {
		positionDAO.save(position);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(Position position) throws ValidationException {
		if (position.getName() == null)
			throw new ValidationException("Position name can not be null");

		Position existingPositionWithSameName = getPositionByName(position
				.getName());
		if (existingPositionWithSameName != null) {
			if (StringUtils.isBlank(position.getId())
					|| !position.equals(existingPositionWithSameName))
				throw new ValidationException("Another position with name - "
						+ position.getName() + " already exists");
		}
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Position> getPositions() {
		return positionDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Position getPositionById(String id) {
		return positionDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Position getPositionByName(String name) {
		return positionDAO.searchUniqueByPropertyEqual("name", name);
	}

	@Secured({ PermissionConstants.DELETE_COMMITTEE__DETAILS })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deletePositionsByIds(String[] ids) {
		positionDAO.removeByIds(ids);
	}

	@Override
	@Secured({ PermissionConstants.DELETE_COMMITTEE__DETAILS })
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Position> searchWithParams(SingleStringSearchParameters params,
			Integer pageNo) {
		Search search = prepareSearch(params);
		/*
		 * if the page number is less than or equal to zero, no need for paging
		 */
		if (pageNo > 0) {
			search.setPage(pageNo - 1);
		} else {
			search.setPage(0);
		}
		List<Position> positions = positionDAO.search(search);
		return positions;
	}

	private Search prepareSearch(SingleStringSearchParameters params) {
		Search search = new Search();

		BuildSearchUtil.addStringLike(search, "name", params.getQuery());

		search.addSort("index", false, true);

		return search;
	}

	@Override
	public int numberOfPositionsWithSearchParams(
			SingleStringSearchParameters params) {
		Search search = prepareSearch(params);
		return positionDAO.count(search);
	}

}
