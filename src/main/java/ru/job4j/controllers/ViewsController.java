package ru.job4j.controllers;


import java.io.File;
import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.job4j.Validator.CarValidator;
import ru.job4j.Validator.UserValidator;
import ru.job4j.dao.CarStoreageDB;
import ru.job4j.dao.UserStorageDB;
import ru.job4j.domain.Car;
import ru.job4j.domain.User;




@Controller
public class ViewsController {
	
	@Autowired
    private UserValidator userValidator;

	@Autowired
	private CarValidator carValidator;
	
	@Autowired
	private CarStoreageDB carStorage;
	
	@Autowired
	private UserStorageDB userStorage;
	
	@GetMapping("/main")
	public String main(Principal principal, Model model) {
		return "main";
	}
	
	@RequestMapping(value="/login")
	public String login(HttpServletRequest request, Model model){
		return "login";
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
		userValidator.validate(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		userStorage.addUser(userForm);
		return "redirect: main";
	}

	@RequestMapping(value="/user/{login}", method=RequestMethod.GET)
	public String showSpitterProfile(@PathVariable("login") String login, 
			Model model) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		User user = userStorage.getUserByLogin(login);
		String result = mapper.writeValueAsString(user);
		model.addAttribute("user", result);
		return "user";
	}
	
	@RequestMapping(value = "/carView/{id}", method = RequestMethod.GET)
	private String carView(@PathVariable("id") long id, Model model) throws JsonProcessingException {
		Car car = carStorage.getById(id);
		User user = car.getUser();
		ObjectMapper mapper = new ObjectMapper();
		String carJson = mapper.writeValueAsString(car);
		String userJson = mapper.writeValueAsString(user);
		model.addAttribute("car", carJson);
		model.addAttribute("user", userJson);
		return "carView";
	}
	
	@RequestMapping(value = "/sell", method = RequestMethod.POST)
	private String makeInactive(@RequestParam("carId") long id, String login) {
		Car car = carStorage.getById(id);
		car.setActive(false);
		carStorage.update(car);
		return "redirect: user/" + login;
	}
	
	@RequestMapping(value="/denied")
	public String denied(){
		return "denied";
	}
	
	
	
	
	@RequestMapping(value="/addCarForm", method=RequestMethod.GET)
	public String addCar(final Model model, @ModelAttribute("carForm") final Car car) {
		if (model.asMap().containsKey("carFormBindingResult")) {
			model.addAttribute("org.springframework.validation.BindingResult.carForm",
					model.asMap().get("carFormBindingResult"));
		}
		return "addCar";
	}
	
	@RequestMapping(value="/addCar", method=RequestMethod.POST, 
			consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public String addCarp(Model model, 
				@ModelAttribute("carForm") final Car car,
				final BindingResult bindingResult, 
				final RedirectAttributes redirectAttributes,
				HttpServletRequest servletRequest,
				final Principal principal) throws IllegalStateException, IOException {
		
		carValidator.validate(car, bindingResult);
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("carFormBindingResult", bindingResult);
            redirectAttributes.addFlashAttribute("carForm", car);
			return "redirect:/addCarForm";
		}
		ServletContext context = servletRequest.getSession().getServletContext();
		String userLogin = principal.getName();
		User user = userStorage.getUserByLogin(userLogin);
		car.setUser(user);
		this.saveImages(car, context);
		carStorage.save(car);
		return "redirect:/main";
	}
	
	private void saveImages(Car car, ServletContext context) throws IllegalStateException, IOException {
		String uploadFolder = context.getRealPath("/upload/photo");
		for (MultipartFile img : car.getImgFiles()) {
			if (!img.isEmpty()) {
				String fileName = img.getOriginalFilename();
				File imageFile = new File(uploadFolder, fileName);
				imageFile.createNewFile();
				img.transferTo(imageFile);
				car.addImage(fileName);
			}
		}
	}

	
	
	
	
}
