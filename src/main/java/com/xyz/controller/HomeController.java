package com.xyz.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.xyz.entity.User;
import com.xyz.repository.UserRepo;
import com.xyz.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {


	@Autowired
	private UserService userService;


	@Autowired
	private UserRepo userRepo;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}

	}

	@GetMapping("/")
	public String index() {

		return "index";
	}

	@GetMapping("/register")


	public String register() {
		return "register";
	}

	@GetMapping("/signin")

	public String login() {
		return "login";
	}


	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session, Model m, HttpServletRequest request) {



		String url = request.getRequestURL().toString();


		url = url.replace(request.getServletPath(), "");






		User u = userService.saveUser(user, url);

		if (u != null) { // System.out.println("save sucess");
			session.setAttribute("msg", "Register successfully");

		} else { // System.out.println("error in server");
			session.setAttribute("msg", "Something wrong server");
		}

		return "redirect:/register";
	}

	@GetMapping("/verify")
	public String verifyAccount(@Param("code") String code, Model m) {
		boolean f = userService.verifyAccount(code);

		if (f) {
			m.addAttribute("msg", "Sucessfully your account is verified");
		} else {
			m.addAttribute("msg", "may be your vefication code is incorrect or already veified ");
		}

		return "message";
	}

}