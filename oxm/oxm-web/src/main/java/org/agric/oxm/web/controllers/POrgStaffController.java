package org.agric.oxm.web.controllers;

import org.agric.oxm.model.StaffMember;
import org.agric.oxm.model.ProducerOrganisation;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.UserSearchParameters;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.PositionService;
import org.agric.oxm.server.service.ProducerOrgService;
import org.agric.oxm.web.WebConstants;
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
@RequestMapping("porg-staff")
public class POrgStaffController {

	@Autowired
	private ProducerOrgService producerOrgService;

	@Autowired
	private PositionService positionService;

	@Autowired
	private POrgController producerOrgController;
	@Autowired
	private UserController userController;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "/{pOrgId}", method = RequestMethod.GET)
	public ModelAndView viewPOrgStaffHandler(
			@PathVariable("pOrgId") String pOrgId, ModelMap modelMap) {
		producerOrgController.prepareProducerOrgViewsModel(pOrgId,
				"Staff of - ", modelMap);
		return new ModelAndView("pOrgStaffView", modelMap);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "/add/{committeeId}", method = RequestMethod.GET)
	public ModelAndView addPOrgStaffMembers(
			@PathVariable("pOrgId") String pOrgId, UserSearchParameters params,
			ModelMap modelMap) {
		ProducerOrganisation pOrg = producerOrgService
				.getProducerOrganisationById(pOrgId);
		modelMap.put("pOrg", pOrg);

		if (!modelMap.containsAttribute("staffMember"))
			modelMap.put("staffMember", new StaffMember(pOrg));

		prepareStaffFormModel(params == null ? new UserSearchParameters()
				: params, pOrg, modelMap);

		modelMap.put(WebConstants.CONTENT_HEADER, pOrg.getName()
				+ " - Add Staff Member");

		modelMap.put("positions", positionService.getPositions());

		return new ModelAndView("pOrgStaffMemberForm", modelMap);
	}

	private void prepareStaffFormModel(UserSearchParameters params,
			ProducerOrganisation porg, ModelMap modelMap) {
		// Role roleProducer = userService
		// .getRoleById("4836AFAB-3D62-482c-BA9A-D9D15839C68A");
		// UserSearchParameters params = new UserSearchParameters(
		// committee.getProducerOrg(), roleProducer);

		userController.prepareSearchCommand(modelMap, params);
		userController.prepareSearchModel(params, true, true, 1, modelMap);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "/edit/{staffMemberId}", method = RequestMethod.GET)
	public ModelAndView editPOrgStaffMembers(
			@PathVariable("staffMemberId") String staffMemberId,
			UserSearchParameters params, ModelMap modelMap) {

		StaffMember staffMember = producerOrgService
				.getStaffMemberById(staffMemberId);
		modelMap.put("pOrg", staffMember.getProducerOrg());

		if (!modelMap.containsAttribute("staffMember"))
			modelMap.put("staffMember", staffMember);

		prepareStaffFormModel(params == null ? new UserSearchParameters()
				: params, staffMember.getProducerOrg(), modelMap);

		modelMap.put(WebConstants.CONTENT_HEADER, staffMember.getProducerOrg()
				.getName()
				+ " - Edit Staff Member"
				+ staffMember.getUser().getName());

		modelMap.put("positions", positionService.getPositions());

		return new ModelAndView("pOrgStaffMemberForm", modelMap);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "/member/search", method = RequestMethod.POST)
	public ModelAndView searchPossibleStaffMembers(
			@ModelAttribute("usersearch") GenericCommand searchCommand,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap modelMap) {

		UserSearchParameters params = userController
				.extractSearchParamsFromCommand(searchCommand);
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		if (StringUtils.isNotBlank(params.getStaffMemberId()))
			return editPOrgStaffMembers(params.getStaffMemberId(), params,
					modelMap);
		else
			return addPOrgStaffMembers(params.getStaffMemberId(), params,
					modelMap);
	}

	@Secured({ PermissionConstants.ADD_PROD_ORG,
			PermissionConstants.EDIT_PROD_ORG })
	@RequestMapping(method = RequestMethod.POST, value = "/member/save")
	public ModelAndView savePOrgStaffMemberHandler(
			@ModelAttribute("staffMember") StaffMember staffMember,
			ModelMap modelMap) throws SessionExpiredException {

		StaffMember existingStaffMember = staffMember;

		try {

			ProducerOrganisation pOrg = staffMember.getProducerOrg();
			producerOrgService.validate(existingStaffMember);

			if (StringUtils.isNotEmpty(staffMember.getId())) {
				existingStaffMember = producerOrgService
						.getStaffMemberById(staffMember.getId());
				existingStaffMember.setPosition(staffMember.getPosition());
				existingStaffMember.setUser(staffMember.getUser());
				existingStaffMember.setAppointmentDate(staffMember
						.getAppointmentDate());
				existingStaffMember.setDateLeft(staffMember.getDateLeft());

				pOrg.removeStaffMember(existingStaffMember);
				pOrg.addStaffMember(existingStaffMember);
			} else {
				existingStaffMember.setId(null);
				pOrg.addStaffMember(existingStaffMember);
			}

			producerOrgService.save(pOrg);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Staff Member saved sucessfully.");
		} catch (ValidationException e) {
			log.error("Error Saving Staff: - ", e);

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			if (StringUtils.isNotEmpty(staffMember.getId()))
				return editPOrgStaffMembers(staffMember.getId(), null, modelMap);
			else
				return addPOrgStaffMembers(
						staffMember.getProducerOrg().getId(), null, modelMap);

		}

		return viewPOrgStaffHandler(staffMember.getProducerOrg().getId(),
				modelMap);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "/member/delete/{pOrgId}/{memberIds}", method = RequestMethod.GET)
	public ModelAndView deletePOrgStaffMembers(
			@PathVariable("pOrgId") String pOrgId,
			@PathVariable("memberIds") String ids, ModelMap modelMap) {

		try {
			String[] idzToDelete = ids.split(",");
			ProducerOrganisation pOrg = producerOrgService
					.getProducerOrganisationById(pOrgId);
			pOrg.removeStaffMembersByIds(idzToDelete);

			producerOrgService.save(pOrg);

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Staff Member(s) deleted successfully");
		} catch (Exception e) {
			log.error("Error Deleting Staff Member(s)", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error "
					+ e.getMessage());
		}

		return viewPOrgStaffHandler(pOrgId, modelMap);
	}
}
