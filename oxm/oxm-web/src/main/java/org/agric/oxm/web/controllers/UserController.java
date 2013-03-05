package org.agric.oxm.web.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.agric.oxm.model.Role;
import org.agric.oxm.model.User;
import org.agric.oxm.model.UserStatus;
import org.agric.oxm.model.exception.OperationFailedException;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.UserService;
import org.agric.oxm.web.OXMUtil;
import org.agric.oxm.web.WebConstants;
import org.agric.oxm.web.WebUtils;
import org.apache.commons.lang.StringUtils;
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
public class UserController {
	@Autowired
	private UserService userService;

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/user/edit/{userid}/{qpage}", method = RequestMethod.GET)
	public ModelAndView editUserHandler(@PathVariable("userid") String id,
			@PathVariable("qpage") String userPage, ModelMap model)
			throws SessionExpiredException {

		User user = userService.getUserById(id);

		if (user != null) {
			WebConstants.loadLoggedInUserProfile(
					OXMSecurityUtil.getLoggedInUser(), model);

			prepareUserFormModel(model);
			model.put("user", user);
			model.put("qUserPage", userPage);
			return new ModelAndView("editORAddUser", model);
		}

		return WebConstants.DEFAULT_PAGE(OXMSecurityUtil.getLoggedInUser());
	}

	private void prepareUserFormModel(ModelMap model) {
		List<Role> roles = userService.getRoles();
		model.put("roles", roles);
		model.put("userstatus", new UserStatus[] { UserStatus.ENABLED,
				UserStatus.DISABLED });
		model.put("gender", OXMUtil.getGenderList());
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/user/save/{qpage}", method = RequestMethod.POST)
	public ModelAndView saveUserHandler(
			@PathVariable("qpage") String qPage,
			@ModelAttribute("user") User user,
			@RequestParam(value = "userPic", required = false) MultipartFile userPic,
			ModelMap model) throws IOException, SessionExpiredException {

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
			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			return editUserHandler(existingUser.getId(), qPage, model);
		}
		if (StringUtils.isNotEmpty(user.getId())) {
			existingUser = userService.getUserById(user.getId());
			copyUserContents(existingUser, user);
		} else {
			existingUser.setId(null);
		}

		try {

			if (userPic != null && userPic.getSize() > 0) {
				existingUser.setProfilePicture(userPic.getBytes());
			}
			userService.validate(existingUser);
			userService.saveUser(existingUser);
		} catch (ValidationException e) {
			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			return editUserHandler(existingUser.getId(), qPage, model);
		}

		if (qPage.equals("0")) {
			WebConstants.loadLoggedInUserProfile(
					OXMSecurityUtil.getLoggedInUser(), model);
			model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"User saved sucessfully.");
		}

		if (qPage.equals(":")) {

			try {
				User loginedUser = OXMSecurityUtil.getLoggedInUser();
				if (loginedUser.hasAdministrativePrivileges()) {
					return viewUsersHandler(new ModelMap());
				} else {
					model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
							"Invalid path");
				}
			} catch (SessionExpiredException e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("dashboard", model);
	}

	private void copyUserContents(User existingUser, User user) {
		existingUser.setName(user.getName());
		existingUser.setGender(user.getGender());
		existingUser.setDateOfBirth(user.getDateOfBirth());
		existingUser.setPhone1(user.getPhone1());
		existingUser.setPhone2(user.getPhone2());
		existingUser.setUserTypes(user.getUserTypes());
		existingUser.setUsername(user.getUsername());

		if (StringUtils.isNotEmpty(user.getPassword())) {
			existingUser.setPassword(user.getPassword());
		}

		existingUser.setStatus(user.getStatus());
		existingUser.setRoles(user.getRoles());
		existingUser.setSalt(user.getSalt());
		existingUser.setClearTextPassword(user.getClearTextPassword());

	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/user/add/{qpage}", method = RequestMethod.GET)
	public ModelAndView addUserHandler(@PathVariable("qpage") String qPage,
			ModelMap model) throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);
		model.put("user", new User());
		prepareUserFormModel(model);
		model.put("qUserPage", qPage);
		model.put("profile_Img", "empty");
		return new ModelAndView("editORAddUser", model);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/user/pic/{id}", method = RequestMethod.GET)
	public void userPicHandler(@PathVariable("id") String userId,
			HttpServletResponse response) throws IOException {
		User user = userService.getUserById(userId);
		if (user != null) {
			if (user.getProfilePicture() != null
					&& user.getProfilePicture().length > 0) {

				String contentType = "image/jpeg";

				WebConstants.writePictureOnResponse(contentType,
						user.getProfilePicture(), response);
			}
		}
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/user/view", method = RequestMethod.GET)
	public ModelAndView viewUsersHandler(ModelMap model)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);
		List<User> users = userService.getUsers();
		model.put("users", users);
		return new ModelAndView("viewUser", model);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/user/delete/{id}/", method = RequestMethod.GET)
	public ModelAndView deleteUserHandler(@PathVariable("id") String userId,
			ModelMap model) throws SessionExpiredException {
		User user = userService.getUserById(userId);
		if (user != null) {
			try {
				userService.deleteUser(user);
				model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
						"User deleted sucessful");
			} catch (OperationFailedException e) {
				model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
						e.getMessage());
				return viewUsersHandler(model);
			}
		}
		return viewUsersHandler(model);
	}
}
