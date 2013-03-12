package org.agric.oxm.web.controllers;

import java.util.List;

import org.agric.oxm.model.Role;
import org.agric.oxm.model.exception.OperationFailedException;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.UserService;
import org.agric.oxm.web.WebConstants;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoleController {
    @Autowired
    private UserService userService;

    @Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
    @RequestMapping(value = "/role/view", method = RequestMethod.GET)
    public ModelAndView viewRoleHandler(
	    @RequestParam(value = "page", required = false) Integer pageNo,
	    ModelMap model) throws SessionExpiredException {
	if (pageNo == null || (pageNo != null && pageNo <= 0)) {
	    pageNo = 0;
	}

	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	List<Role> roles = userService.getRolesByPage(pageNo);
	model.put("roles", roles);
	return new ModelAndView("viewRole", model);
    }

    @Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
    @RequestMapping(value = "/role/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteHandler(@PathVariable("id") String roleId, ModelMap model)
	    throws SessionExpiredException {
	Role role = userService.getRoleById(roleId);
	if (role != null) {
	    try {
		userService.deleteRole(role);
		model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE, "Role deleted sucessfully");
	    } catch (OperationFailedException e) {
		model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, e.getMessage());
		return viewRoleHandler(null, model);
	    }
	}
	return viewRoleHandler(null, model);
    }

    @Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
    @RequestMapping(value = "/role/add/", method = RequestMethod.GET)
    public ModelAndView addHandler(ModelMap model) throws SessionExpiredException {
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	prepareRoleFormModel(model);
	model.put("role", new Role());
	return new ModelAndView("addOREditRole", model);
    }

    @Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
    @RequestMapping(value = "/role/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editHandler(@PathVariable("id") String roleId, ModelMap model)
	    throws SessionExpiredException {
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	prepareRoleFormModel(model);
	
	Role role = userService.getRoleById(roleId);
	model.put("role", role);
	return new ModelAndView("addOREditRole", model);
    }

    @Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
    @RequestMapping(value = "/role/save/", method = RequestMethod.POST)
    public ModelAndView saveHandler(@ModelAttribute("role") Role role, ModelMap model)
	    throws SessionExpiredException {
	Role existingRole = role;

	if (StringUtils.isNotEmpty(role.getId())) {
	    existingRole = userService.getRoleById(role.getId());
	    if(existingRole.getName().equals("ROLE_ANNOYMOUS_USER")){
		if(!existingRole.getName().equalsIgnoreCase(role.getName())){
		    WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
		    prepareRoleFormModel(model);
		    model.put("role", role);
		    model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Can't edit the name for the default user role");
		    return new ModelAndView("addOREditRole", model);
		}
	    }
	    copyRoleContents(existingRole, role);
	}else{
	    existingRole.setId(null);
	}

	try {
	    userService.validate(existingRole);
	    userService.saveRole(existingRole);
	    model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE, "Role saved sucessfully");
	    return viewRoleHandler(null, model);
	} catch (ValidationException e) {
	    WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	    prepareRoleFormModel(model);
	    model.put("role", existingRole);
	    model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, e.getMessage());
	    return new ModelAndView("addOREditRole", model);
	}
    }

    private void copyRoleContents(Role existingRole, Role role) {
	existingRole.setDescription(role.getDescription());
	existingRole.setName(role.getName());
	existingRole.setPermissions(role.getPermissions());
	existingRole.setUsers(role.getUsers());
    }

    private void prepareRoleFormModel(ModelMap model) {
	
	model.put("permissions", userService.getPermissions());
    }
}
