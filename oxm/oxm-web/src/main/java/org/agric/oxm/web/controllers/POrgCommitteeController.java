package org.agric.oxm.web.controllers;

import org.agric.oxm.model.Committee;
import org.agric.oxm.model.CommitteeMember;
import org.agric.oxm.model.ProducerOrganisation;
import org.agric.oxm.model.Role;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.UserSearchParameters;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.CommitteeService;
import org.agric.oxm.server.service.PositionService;
import org.agric.oxm.server.service.ProducerOrgService;
import org.agric.oxm.server.service.UserService;
import org.agric.oxm.web.WebConstants;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("porg-committee")
public class POrgCommitteeController {

	@Autowired
	private ProducerOrgService producerOrgService;

	@Autowired
	private CommitteeService committeeService;

	@Autowired
	private PositionService positionService;

	@Autowired
	private UserService userService;

	@Autowired
	private POrgController producerOrgController;
	@Autowired
	private UserController userController;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "/{pOrgId}", method = RequestMethod.GET)
	public ModelAndView viewProducerOrgCommitteeHandler(
			@PathVariable("pOrgId") String pOrgId, ModelMap modelMap) {
		producerOrgController.prepareProducerOrgViewsModel(pOrgId,
				"Committees of - ", modelMap);
		return new ModelAndView("pOrgCommitteeView", modelMap);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "/add/{pOrgId}", method = RequestMethod.GET)
	public ModelAndView addPOrgCommitteeHandler(
			@PathVariable("pOrgId") String pOrgId, ModelMap modelMap) {

		ProducerOrganisation pOrg = producerOrgService
				.getProducerOrganisationById(pOrgId);
		modelMap.put("pOrg", pOrg);

		Committee committee = new Committee(pOrg);
		modelMap.put("committee", committee);

		modelMap.put(WebConstants.CONTENT_HEADER,
				"Add Committee to - " + pOrg.getName());
		return new ModelAndView("pOrgCommitteeForm", modelMap);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "/edit/{committeeId}", method = RequestMethod.GET)
	public ModelAndView editPOrgCommitteeHandler(
			@PathVariable("committeeId") String committeeId, ModelMap modelMap) {

		if (!modelMap.containsAttribute("committee")) {
			Committee committee = committeeService
					.getCommitteeById(committeeId);
			modelMap.put("pOrg", committee.getProducerOrg());

			modelMap.put("committee", committee);

			modelMap.put(WebConstants.CONTENT_HEADER,
					"Edit " + committee.getName() + " of "
							+ committee.getProducerOrg().getName());
		}

		return new ModelAndView("pOrgCommitteeForm", modelMap);
	}

	@Secured({ PermissionConstants.ADD_PROD_ORG,
			PermissionConstants.EDIT_PROD_ORG })
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public ModelAndView savePOrgCommitteeHandler(
			@ModelAttribute("committee") Committee committee, ModelMap modelMap)
			throws SessionExpiredException {

		Committee existingCommittee = committee;

		try {

			committeeService.validate(existingCommittee);

			if (StringUtils.isNotEmpty(committee.getId())) {
				existingCommittee = committeeService.getCommitteeById(committee
						.getId());
				existingCommittee.setName(committee.getName());

				if (committee.getMembers() != null) {
					existingCommittee.setMembers(committee.getMembers());
				}
			} else
				existingCommittee.setId(null);

			committeeService.save(existingCommittee);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Committee saved sucessfully.");
		} catch (ValidationException e) {
			log.error("Error Saving Committee: - ", e);

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			modelMap.put("committee", committee);
			modelMap.put("pOrg", committee.getProducerOrg());
			modelMap.put(
					WebConstants.CONTENT_HEADER,
					"Retry Add/Edit "
							+ (StringUtils.isNotBlank(committee.getName()) ? committee
									.getName() : "Committee"));

			return new ModelAndView("pOrgCommitteeForm", modelMap);

		}

		return viewProducerOrgCommitteeHandler(committee.getProducerOrg()
				.getId(), modelMap);
	}

	@Secured({ PermissionConstants.DELETE_PROD_ORG })
	@RequestMapping(method = RequestMethod.GET, value = "/delete/{pOrgId}/{idz}")
	public ModelAndView deletePOrgCommitteesHandler(
			@PathVariable("pOrgId") String pOrgId,
			@PathVariable("idz") String idz, ModelMap modelMap)
			throws SessionExpiredException {

		String[] ids = idz.split(",");
		try {
			committeeService.deleteCommitteesByIds(ids);

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Committee(s) deleted successfully");
		} catch (Exception e) {
			log.error("Error Deleting Committee", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error - "
					+ e.getMessage());
		}

		return viewProducerOrgCommitteeHandler(pOrgId, modelMap);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "/member/{committeeId}", method = RequestMethod.GET)
	public ModelAndView viewPOrgCommitteeMembers(
			@PathVariable("committeeId") String committeeId, ModelMap modelMap) {
		Committee committee = committeeService.getCommitteeById(committeeId);
		modelMap.put("committee", committee);
		modelMap.put("pOrg", committee.getProducerOrg());

		modelMap.put(WebConstants.CONTENT_HEADER, committee.getName()
				+ " - Members");
		return new ModelAndView("pOrgCommitteeMemberView", modelMap);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "/member/add/{committeeId}", method = RequestMethod.GET)
	public ModelAndView addPOrgCommitteeMembers(
			@PathVariable("committeeId") String committeeId, ModelMap modelMap) {
		Committee committee = committeeService.getCommitteeById(committeeId);
		modelMap.put("committee", committee);
		modelMap.put("pOrg", committee.getProducerOrg());

		modelMap.put("committeeMember", new CommitteeMember(committee));

		prepareStaffAndCommitteeMemberFormModel(committee, modelMap);

		modelMap.put(WebConstants.CONTENT_HEADER, committee.getName()
				+ " - Add Member");

		modelMap.put("positions", positionService.getPositions());

		return new ModelAndView("pOrgCommitteeMemberForm", modelMap);
	}

	private void prepareStaffAndCommitteeMemberFormModel(Committee committee,
			ModelMap modelMap) {
		Role roleProducer = userService
				.getRoleById("4836AFAB-3D62-482c-BA9A-D9D15839C68A");
		UserSearchParameters params = new UserSearchParameters(
				committee.getProducerOrg(), roleProducer);
		userController.prepareSearchCommand(modelMap, params);
		userController.prepareSearchModel(params, true, true, 1, modelMap);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "/member/edit/{committeeMemberId}", method = RequestMethod.GET)
	public ModelAndView editPOrgCommitteeMembers(
			@PathVariable("committeeMemberId") String committeeMemberId,
			ModelMap modelMap) {
		CommitteeMember committeeMember = committeeService
				.getCommitteeMemberById(committeeMemberId);
		modelMap.put("committee", committeeMember.getCommittee());
		modelMap.put("pOrg", committeeMember.getCommittee().getProducerOrg());

		modelMap.put("committeeMember", committeeMember);

		prepareStaffAndCommitteeMemberFormModel(committeeMember.getCommittee(),
				modelMap);

		modelMap.put(WebConstants.CONTENT_HEADER, committeeMember
				.getCommittee().getName()
				+ " - Edit Member"
				+ committeeMember.getPositionHolder().getName());

		modelMap.put("positions", positionService.getPositions());

		return new ModelAndView("pOrgCommitteeMemberForm", modelMap);
	}
}
