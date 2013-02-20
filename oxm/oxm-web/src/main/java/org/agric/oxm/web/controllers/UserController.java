package org.agric.oxm.web.controllers;

import java.util.List;

import org.agric.oxm.model.Role;
import org.agric.oxm.model.User;
import org.agric.oxm.model.UserStatus;
import org.agric.oxm.server.service.UserService;
import org.agric.oxm.web.OXMUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    // @Secured(PermissionConstants.PERM_EDIT_STAFF_MEMBER)
    @RequestMapping(value = "/user/{userid}/edit/{qpage}", method = RequestMethod.GET)
    public ModelAndView editUserHandler(@PathVariable("userid") String id,
	    @PathVariable("qpage") String userPage,
	    ModelMap model) {

	User user = userService.getUserById(id);
	if (user == null)
	    user = new User();

	if (user.getProfilePicture() == null) {
	    model.put("profile_Img", "empty");
	}

	prepareUserFormModel(model);
	model.put("user", user);
	model.put("qUserPage", userPage);
	model.put("profile_Img", "empty");
	return new ModelAndView("editORAddUser", model);
    }

    private void prepareUserFormModel(ModelMap model) {
	List<Role> roles = userService.getRoles();
	model.put("roles", roles);
	model.put("userstatus", new UserStatus[] { UserStatus.ENABLED, UserStatus.DISABLED });
	model.put("gender", OXMUtil.getGenderList());
    }

}
