package com.web;

import com.model.Event;
import com.model.Referee;
import com.service.EventService;
import com.service.RefereeService;
import com.service.UserService;
import com.validator.EventValidator;
import com.validator.RefereeValidator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    @Autowired
    private RefereeService refereeService;
    @Autowired
    private RefereeValidator refereeValidator;
    @Autowired
    private EventValidator eventValidator;

    @RequestMapping(value = { "/admin/welcome", "/admin", "/admin/" }, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String welcomeAdmin(Model model) {
	return "admin/adminWelcome";
    }

    
    @RequestMapping(value = { "/admin/userList" }, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminUserlist(Model model) {
	model.addAttribute("userList", userService.getListOfUsers());
	return "admin/userList";
    }

    
    @RequestMapping(value = { "/admin/edit-{eventId}" }, method = RequestMethod.GET)
    public String addResultToEvent(@PathVariable String eventId, ModelMap model) {
        Event event = eventService.findById(eventId);
        model.addAttribute("gunTypeList", eventService.getListOfGuns());
	model.addAttribute("refereeList", refereeService.getListOfReferees());
        model.addAttribute("eventForm", event);
        model.addAttribute("edit", true);
        return "admin/addEvent";
    }
    
    @RequestMapping(value = { "/admin/edit-{eventId}" }, method = RequestMethod.POST)
    public String updateEvent(@ModelAttribute("eventForm") Event eventForm, BindingResult bindingResult, Model model, @PathVariable String eventId) {
	eventForm.setId(Long.parseLong(eventId));
	eventValidator.setEditable(true);
	eventValidator.validate(eventForm, bindingResult);
	
	if (bindingResult.hasErrors()) {
	    model.addAttribute("gunTypeList", eventService.getListOfGuns());
	    model.addAttribute("refereeList", refereeService.getListOfReferees());
	    return "/admin/addEvent";
	}
	eventService.updateEvent(eventForm);

	return "redirect:/admin/eventList";
    }

    @RequestMapping(value = { "/admin/delete-{eventId}" }, method = RequestMethod.GET)
    public String deleteEvent(@PathVariable String eventId) {
        eventService.deleteEventById(eventId);
        return "redirect:/admin/eventList";
    }
    
    @RequestMapping(value = { "/admin/eventList" }, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminEventList(Model model) {
	model.addAttribute("eventList", eventService.getListOfEvents());
	return "admin/eventList";
    }

    @RequestMapping(value = { "/admin/addEvent" }, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminAddEvent(Model model) {
	model.addAttribute("eventForm", new Event());
	model.addAttribute("gunTypeList", eventService.getListOfGuns());
	model.addAttribute("refereeList", refereeService.getListOfReferees());
	model.addAttribute("edit", false);
	return "admin/addEvent";
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
	binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());

    }

    @RequestMapping(value = "/admin/addEvent", method = RequestMethod.POST)
    public String adminAddEvent(@ModelAttribute("eventForm") Event eventForm, BindingResult bindingResult, Model model,
	    @RequestParam(value = "img") CommonsMultipartFile[] img) throws IOException {
	eventValidator.setEditable(false);
	eventValidator.validate(eventForm, bindingResult);

	if (bindingResult.hasErrors()) {
	    model.addAttribute("gunTypeList", eventService.getListOfGuns());
	    model.addAttribute("refereeList", refereeService.getListOfReferees());
	    return "/admin/addEvent";
	}

	if (img != null && img.length > 0) {
	    for (CommonsMultipartFile aFile : img) {
		eventForm.setImg(aFile.getBytes());
	    }
	}

	eventService.save(eventForm);

	return "redirect:/admin/eventList";
    }

    @RequestMapping(value = { "/admin/refereeList" }, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminRefereeList(Model model) {
	model.addAttribute("refereeList", refereeService.getListOfReferees());
	return "admin/refereeList";
    }

    @RequestMapping(value = { "/admin/addReferee" }, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminAddReferee(Model model) {
	model.addAttribute("refereeForm", new Referee());
	model.addAttribute("titleList", refereeService.getListOfTitles());
	return "admin/addReferee";
    }

    @RequestMapping(value = "/admin/addReferee", method = RequestMethod.POST)
    public String adminAddReferee(@ModelAttribute("refereeForm") Referee refereeForm, BindingResult bindingResult,
	    Model model) {
	refereeValidator.validate(refereeForm, bindingResult);

	if (bindingResult.hasErrors()) {
	    model.addAttribute("titleList", refereeService.getListOfTitles());
	    return "/admin/addReferee";
	}

	refereeService.save(refereeForm);

	return "redirect:/admin/refereeList";
    }
}
