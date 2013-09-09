package org.agric.oxm.web.controllers.porg;

import org.agric.oxm.model.ProducerOrg;
import org.agric.oxm.model.User;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.ProducerOrgService;
import org.agric.oxm.server.service.UserService;
import org.agric.oxm.web.WebConstants;
import org.agric.oxm.web.controllers.settings.UserController;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class POrgProducerController {

	@Autowired
	private ProducerOrgService producerOrgService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserController userController;

	@Autowired
	private POrgController porgController;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@RequestMapping(value = "/producers/{pOrgId}", method = RequestMethod.GET)
	public ModelAndView viewProducerOrgMembersHandler(
			@PathVariable("pOrgId") String pOrgId, ModelMap modelMap) {

		ProducerOrg pOrg = producerOrgService
				.getProducerOrganisationById(pOrgId);
		modelMap.put("pOrg", pOrg);

		modelMap.put(WebConstants.CONTENT_HEADER,
				"Producers in - " + pOrg.getName());

		return new ModelAndView("viewPOrgProducers", modelMap);
	}

	@Secured({ PermissionConstants.ADD_PRODUCER })
	@RequestMapping(value = "/producers/add/{pOrgId}", method = RequestMethod.GET)
	public ModelAndView addProducerOrgProducersHandler(
			@PathVariable("pOrgId") String pOrgId, ModelMap modelMap) {

		try {
			ProducerOrg pOrg = producerOrgService
					.getProducerOrganisationById(pOrgId);
			modelMap.put("pOrg", pOrg);

			User producer = new User(pOrg);
			modelMap.put("producer", producer);

			modelMap.put(WebConstants.CONTENT_HEADER,
					"Add Producer to " + pOrg.getName());

			userController.prepareUserFormModel(modelMap);
			return new ModelAndView("formPOrgProducer", modelMap);
		} catch (Exception e) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
		}
		return porgController.viewProducerOrgHandler(modelMap);
	}

	@Secured({ PermissionConstants.ADD_PRODUCER })
	@RequestMapping(value = "/producers/edit/{pOrgId}/{producerId}", method = RequestMethod.GET)
	public ModelAndView editProducerOrgProducersHandler(
			@PathVariable("pOrgId") String pOrgId,
			@PathVariable("producerId") String producerId, ModelMap modelMap) {

		ProducerOrg pOrg = producerOrgService
				.getProducerOrganisationById(pOrgId);
		modelMap.put("pOrg", pOrg);
		User producer = userService.getUserById(producerId);
		modelMap.put("producer", producer);

		modelMap.put(WebConstants.CONTENT_HEADER,
				"Edit Producer " + producer.getName() + " in Producer org. "
						+ pOrg.getName());

		userController.prepareUserFormModel(modelMap);

		return new ModelAndView("formPOrgProducer", modelMap);
	}

	@RequestMapping(method = RequestMethod.POST, value = "producers/save")
	public ModelAndView saveProducerOrgProducersHandler(
			@ModelAttribute("producer") User producer,
			@RequestParam(value = "userPic", required = false) MultipartFile userPic,
			ModelMap modelMap) throws SessionExpiredException {

		User existingProducer = producer;

		try {

			WebConstants.loadLoggedInUserProfile(
					OXMSecurityUtil.getLoggedInUser(), modelMap);

			userController.validatePic(userPic);

			if (userPic != null && userPic.getSize() > 0) {
				existingProducer.setProfilePicture(userPic.getBytes());
			}

			if (StringUtils.isNotEmpty(producer.getId())) {
				existingProducer = userService.getUserById(producer.getId());
				userController.copyUserContents(existingProducer, producer);
			} else {
				existingProducer.setId(null);
			}

			userService.validate(existingProducer);
			userService.saveUser(existingProducer);

		} catch (Exception e) {
			log.error("Error Saving producerOrg Member - ", e);

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error "
					+ e.getMessage());

			modelMap.put("producer", producer);
			modelMap.put(
					WebConstants.CONTENT_HEADER,
					"Retry Add/Edit Producer "
							+ (StringUtils.isNotBlank(producer.getName()) ? producer
									.getName() : "") + " in Producer org. "
							+ producer.getProducerOrg().getName());

			userController.prepareUserFormModel(modelMap);
			return new ModelAndView("formPOrgProducer", modelMap);
		}

		if (producer.getProducerOrg() != null)
			return viewProducerOrgMembersHandler(producer.getProducerOrg()
					.getId(), modelMap);
		else
			return porgController.viewProducerOrgHandler(modelMap);
	}

}
