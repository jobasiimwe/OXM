package org.agric.oxm.web.controllers.porg;

import java.util.List;

import org.agric.oxm.model.Committee;
import org.agric.oxm.model.CommitteeMember;
import org.agric.oxm.model.Position;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.UserSearchParameters;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.CommitteeService;
import org.agric.oxm.server.service.PositionService;
import org.agric.oxm.web.WebConstants;
import org.agric.oxm.web.controllers.settings.UserController;
import org.agric.oxm.web.forms.GenericCommand;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("porg-committee-member")
public class POrgCommitteeMemberController {

	@Autowired
	private CommitteeService committeeService;

	@Autowired
	private PositionService positionService;

	@Autowired
	private UserController userController;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "view/{committeeId}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable("committeeId") String committeeId,
			ModelMap modelMap) {
		Committee committee = committeeService.getCommitteeById(committeeId);
		modelMap.put("committee", committee);
		modelMap.put("pOrg", committee.getProducerOrg());

		modelMap.put(WebConstants.CONTENT_HEADER, committee.getName()
				+ " - Members");
		return new ModelAndView("pOrgCommitteeMemberView", modelMap);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "add/{committeeId}", method = RequestMethod.GET)
	public ModelAndView add(@PathVariable("committeeId") String committeeId,
			UserSearchParameters params, ModelMap modelMap) {
		Committee committee = committeeService.getCommitteeById(committeeId);
		modelMap.put("committee", committee);
		modelMap.put("pOrg", committee.getProducerOrg());

		if (!modelMap.containsAttribute("committeeMember"))
			modelMap.put("committeeMember", new CommitteeMember(committee));

		prepareStaffAndCommitteeMemberFormModel(
				params == null ? new UserSearchParameters() : params,
				committee, modelMap);

		modelMap.put("users", committee.getProducerOrg().getProducers());

		modelMap.put(WebConstants.CONTENT_HEADER, committee.getName()
				+ " - Add Member");

		List<Position> positions = positionService.getPositions();
		if (positions != null && positions.size() > 0)
			modelMap.put("positions", positions);
		else {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"First create some Positions in order to Add Committee-Members");
			return view(committeeId, modelMap);
		}

		return new ModelAndView("pOrgCommitteeMemberForm", modelMap);
	}

	private void prepareStaffAndCommitteeMemberFormModel(
			UserSearchParameters params, Committee committee, ModelMap modelMap) {
		// Role roleProducer = userService
		// .getRoleById("4836AFAB-3D62-482c-BA9A-D9D15839C68A");
		// UserSearchParameters params = new UserSearchParameters(
		// committee.getProducerOrg(), roleProducer);

		userController.prepareSearchCommand(modelMap, params);
		userController.prepareSearchModel(params, true, true, 1, modelMap,
				false);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "edit/{committeeMemberId}", method = RequestMethod.GET)
	public ModelAndView edit(
			@PathVariable("committeeMemberId") String committeeMemberId,
			UserSearchParameters params, ModelMap modelMap) {

		CommitteeMember committeeMember = committeeService
				.getCommitteeMemberById(committeeMemberId);
		modelMap.put("committee", committeeMember.getCommittee());
		modelMap.put("pOrg", committeeMember.getCommittee().getProducerOrg());

		if (!modelMap.containsAttribute("committeeMember"))
			modelMap.put("committeeMember", committeeMember);

		// prepareStaffAndCommitteeMemberFormModel(
		// params == null ? new UserSearchParameters() : params,
		// committeeMember.getCommittee(), modelMap);

		modelMap.put("users", committeeMember.getCommittee().getProducerOrg()
				.getProducers());

		modelMap.put(WebConstants.CONTENT_HEADER, committeeMember
				.getCommittee().getName()
				+ " - Edit Member"
				+ committeeMember.getUser().getName());

		modelMap.put("positions", positionService.getPositions());

		return new ModelAndView("pOrgCommitteeMemberForm", modelMap);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ModelAndView searchPossibleCommitteeMembers(
			@ModelAttribute("usersearch") GenericCommand searchCommand,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap modelMap) {

		UserSearchParameters params = userController
				.extractParams(searchCommand);
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		if (StringUtils.isNotBlank(params.getCommitteeMemberId()))
			return edit(params.getCommitteeMemberId(), params, modelMap);
		else
			return add(params.getCommitteeId(), params, modelMap);
	}

	@Secured({ PermissionConstants.ADD_PROD_ORG,
			PermissionConstants.EDIT_PROD_ORG })
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public ModelAndView save(
			@ModelAttribute("committeeMember") CommitteeMember committeeMember,
			ModelMap modelMap) throws SessionExpiredException {

		CommitteeMember existingCommitteeMember = committeeMember;

		try {

			Committee committee = committeeMember.getCommittee();
			committeeService.validate(existingCommitteeMember);

			if (StringUtils.isNotEmpty(committeeMember.getId())) {
				existingCommitteeMember = committeeService
						.getCommitteeMemberById(committeeMember.getId());
				existingCommitteeMember.setPosition(committeeMember
						.getPosition());
				existingCommitteeMember.setUser(committeeMember.getUser());
				existingCommitteeMember.setToDate(committeeMember.getToDate());
				existingCommitteeMember.setToDate(committeeMember.getToDate());

				committee.removemMember(existingCommitteeMember);
				committee.addMember(existingCommitteeMember);
			} else {
				existingCommitteeMember.setId(null);
				committee.addMember(existingCommitteeMember);
			}

			committeeService.save(committee);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Committee Member saved sucessfully.");
		} catch (ValidationException e) {
			log.error("Error Saving Committee: - ", e);

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());

			modelMap.put("users", committeeMember.getCommittee()
					.getProducerOrg().getProducers());

			if (StringUtils.isNotEmpty(committeeMember.getId()))
				return edit(committeeMember.getId(), null, modelMap);
			else
				return add(committeeMember.getCommittee().getId(), null,
						modelMap);

		}

		return view(committeeMember.getCommittee().getId(), modelMap);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "delete/{committeeId}/{memberIds}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable("committeeId") String committeeId,
			@PathVariable("memberIds") String ids, ModelMap modelMap) {

		try {
			String[] idzToDelete = ids.split(",");
			Committee committee = committeeService
					.getCommitteeById(committeeId);
			committee.removeMembersByIds(idzToDelete);

			committeeService.save(committee);

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Committee Member(s) deleted successfully");

			modelMap.put("positions", positionService.getPositions());
		} catch (Exception e) {
			log.error("Error Deleting Committee Member(s)", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error "
					+ e.getMessage());
		}

		return view(committeeId, modelMap);
	}
}
