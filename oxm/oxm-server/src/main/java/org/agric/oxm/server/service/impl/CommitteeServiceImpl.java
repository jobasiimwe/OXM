package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.Committee;
import org.agric.oxm.model.CommitteeMember;
import org.agric.oxm.model.enums.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.dao.CommitteeDAO;
import org.agric.oxm.server.dao.CommitteeMemberDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.CommitteeService;
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
@Service("committeeService")
@Transactional
public class CommitteeServiceImpl implements CommitteeService {

	@Autowired
	private CommitteeDAO committeeDAO;

	@Autowired
	private CommitteeMemberDAO committeeMemberDAO;

	@Secured({ PermissionConstants.ADD_COMMITTEE_DETAILS,
			PermissionConstants.EDIT_COMMITTEE__DETAILS })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(Committee committee) {
		committeeDAO.save(committee);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(Committee committee) throws ValidationException {
		if (committee.getProducerOrg() == null)
			throw new ValidationException(
					"Ooops, Technical Error - Producer Organisation can not be null");

		if (StringUtils.isBlank(committee.getName()))
			throw new ValidationException("Committee name can not be Blank");

		Committee existingCommitteeWithSameName = getCommitteeByName(committee
				.getName());
		if (existingCommitteeWithSameName != null) {
			if (StringUtils.isBlank(committee.getId())
					|| !committee.equals(existingCommitteeWithSameName))
				throw new ValidationException("Another committee with name - "
						+ committee.getName() + " already exists");
		}
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Committee> getCommittees() {
		return committeeDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Committee getCommitteeById(String id) {
		return committeeDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Committee getCommitteeByName(String name) {
		return committeeDAO.searchUniqueByPropertyEqual("name", name);
	}

	@Secured({ PermissionConstants.DELETE_COMMITTEE__DETAILS })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteCommitteesByIds(String[] ids) {
		committeeDAO.removeByIds(ids);
	}

	@Override
	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public CommitteeMember getCommitteeMemberById(String id) {
		return committeeMemberDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(CommitteeMember committeeMember)
			throws ValidationException {
		if (committeeMember.getCommittee() == null)
			throw new ValidationException(
					"Ooops, Technical Error - Committee can not be null");

		if (committeeMember.getPosition() == null)
			throw new ValidationException("Position can not be null");

		if (committeeMember.getUser() == null)
			throw new ValidationException("Position-Holder can not be null");

		if (committeeMember.getFromDate() == null)
			throw new ValidationException("From (Date) can not be null");

		if (committeeMember.getToDate() != null)
			if (committeeMember.getToDate().before(
					committeeMember.getFromDate()))
				throw new ValidationException(
						"To (Date) can not be BEFORE From (Date)");

	}
}
