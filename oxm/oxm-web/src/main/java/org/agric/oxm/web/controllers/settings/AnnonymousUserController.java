package org.agric.oxm.web.controllers.settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.agric.oxm.model.Role;
import org.agric.oxm.model.User;
import org.agric.oxm.model.enums.Gender;
import org.agric.oxm.model.enums.UserStatus;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.service.Adminservice;
import org.agric.oxm.server.service.RoleService;
import org.agric.oxm.server.service.UserService;
import org.agric.oxm.web.WebConstants;
import org.agric.oxm.web.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("annoymous")
public class AnnonymousUserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private Adminservice adminService;

	@Autowired
	private UserController userController;

	@RequestMapping("create")
	public ModelAndView annoymousUserHander(ModelMap modelMap)
			throws SessionExpiredException {
		modelMap.put("annoymousUser", new User());
		prepareAnnoymousUser(modelMap);
		return new ModelAndView("createUser", modelMap);
	}

	private void prepareAnnoymousUser(ModelMap modelMap) {
		List<Role> roles = roleService.getRoles();
		List<Role> rz = new ArrayList<Role>();
		Role dd = null;
		for (Role r : roles) {
			if (r.getName().endsWith("ROLE_ANNOYMOUS_USER")) {
				dd = r;
			}

		}
		rz.add(dd);
		modelMap.put("roles", rz);
		modelMap.put("userstatus", new UserStatus[] { UserStatus.ENABLED,
				UserStatus.DISABLED });
		modelMap.put("gender", Gender.values());

		modelMap.put("subcounties", adminService.getSubCounties());
		modelMap.put("parishes", adminService.getParishes());
		modelMap.put("villages", adminService.getVillages());
	}

	@RequestMapping(value = "/annoymous/save", method = RequestMethod.POST)
	public ModelAndView saveAnnoymousUserHandler(
			@ModelAttribute("annoymousUser") User user,
			@RequestParam(value = "userPic", required = false) MultipartFile userPic,
			ModelMap modelMap) throws IOException, SessionExpiredException {

		User existingUser = user;
		try {
			if (!WebConstants.isFileAnImage(userPic)) {
				throw new Exception(
						"error: the file uploaded for the user photo is not an image.");
			}

			if (!WebUtils.isValidSize(userPic,
					WebConstants.DEFAULT_IMAGE_SIZE_IN_BYTES)) {
				throw new Exception(
						String.format(
								"error: the next of kin photo exceeds the maximum size of >> %s",
								WebConstants.DEFAULT_IMAGE_SIZE_IN_BYTES));
			}

		} catch (Exception e) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			modelMap.put("annoymousUser", user);
			prepareAnnoymousUser(modelMap);
			return new ModelAndView("createUser", modelMap);
		}
		if (StringUtils.isNotEmpty(user.getId())) {
			existingUser = userService.getUserById(user.getId());
			userController.copyUserContents(existingUser, user);
			// existingUser.setSubCounty(user.getSubCounty());
			// existingUser.setParish(user.getParish());
			// existingUser.setVillage(user.getVillage());
		} else {
			existingUser.setId(null);
		}

		try {

			if (userPic != null && userPic.getSize() > 0) {
				existingUser.setProfilePicture(userPic.getBytes());
			}
			userService.validate(existingUser);
			userService.saveUser(existingUser);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Thank You for registering. you can now login.");
			return new ModelAndView("sucessfullogin", modelMap);
		} catch (ValidationException e) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			modelMap.put("annoymousUser", user);
			prepareAnnoymousUser(modelMap);
			return new ModelAndView("createUser", modelMap);
		}

	}

}
