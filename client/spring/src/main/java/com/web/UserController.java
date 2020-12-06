package com.web;

import com.model.Event;
import com.model.User;
import com.service.EventService;
import com.service.SecurityService;
import com.service.UserService;
import com.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private EventService eventService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
	model.addAttribute("userForm", new User());
	return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
	userValidator.validate(userForm, bindingResult);

	if (bindingResult.hasErrors()) {
	    return "registration";
	}

	userService.save(userForm);
	securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

	return "redirect:/welcome";
    }
    
    
    @RequestMapping(value = { "/user/events", "user/events/{pageNumber}" }, method = RequestMethod.GET)
    public String getRunbookPage(@PathVariable Integer pageNumber, Model model) {
        Page<Event> page = eventService.getEventLog(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("events", page.getContent());
        model.addAttribute("eventLog", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        return "user/eventLog";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
	if (error != null)
	    model.addAttribute("error", "Your username or password is invalid.");

	if (logout != null)
	    model.addAttribute("message", "You have been logged out successfully.");

	return "login";
    }

    @RequestMapping(value = { "/welcome" }, method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public String welcome(Model model) {
	return "welcome";
    }

    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String home(Model model) {
	return "home";
    }

    @RequestMapping(value = { "/about" }, method = RequestMethod.GET)
    public String about(Model model) {
	return "about";
    }

    @RequestMapping(value = { "/contact" }, method = RequestMethod.GET)
    public String contact(Model model) {
	return "contact";
    }

    @RequestMapping(value = { "/gallery" }, method = RequestMethod.GET)
    public String gallery(Model model) {
	return "gallery";
    }

    /* MAPPING GUNS */
    @RequestMapping(value = { "/guns/cz75" }, method = RequestMethod.GET)
    public String gunCz75(Model model) {
	return "guns/cz75";
    }

    @RequestMapping(value = { "/guns/czShadow" }, method = RequestMethod.GET)
    public String gunCzShadow(Model model) {
	return "guns/czshadow";
    }

    @RequestMapping(value = { "/guns/kalashnikov" }, method = RequestMethod.GET)
    public String gunKalashnikov(Model model) {
	return "guns/kalashnikov";
    }

    @RequestMapping(value = { "/guns/shotgun" }, method = RequestMethod.GET)
    public String gunShotgun(Model model) {
	return "guns/shotgun";
    }

    @RequestMapping(value = { "/guns/glock" }, method = RequestMethod.GET)
    public String gunGlock(Model model) {
	return "guns/glock";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied(User user) {
	ModelAndView model = new ModelAndView();
	if (user != null) {
	    model.addObject("msg", "Hi " + user.getName() + ", you do not have permission to access this page!");
	} else {
	    model.addObject("msg", "You do not have permission to access this page!");
	}

	model.setViewName("403");
	return model;
    }

}
