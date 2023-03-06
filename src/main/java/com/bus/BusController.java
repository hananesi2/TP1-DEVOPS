package com.bus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.bus.beans.Trajet;
import com.bus.beans.TrajetAbonnement;
import com.bus.repository.AbonnementRepository;
import com.bus.service.BusService;
import com.bus.service.SocietyService;
import com.bus.service.TrajetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bus.beans.Customer;
import com.bus.service.CustomerDao;


@Controller
public class BusController {

	@Autowired
	private CustomerDao dao;

	@Autowired
	private TrajetService trajetService;

	@Autowired
	private BusService busService;

	@Autowired
	private SocietyService societyService;

	@Autowired
	private AbonnementRepository abonnementRepository;
	// Opening home page
	@GetMapping("/")
	public String home(Model m, HttpSession session) {

		List<Trajet> trajets = trajetService.getAllTrajets();
		m.addAttribute("listTrajets", trajets);
		m.addAttribute("menu", "home");

		return "index";
	}

//Registeration User
	@GetMapping("/register")
	public String register(Model m) {

		m.addAttribute("menu", "register");
		return "register";
	}

//	Login form
	@GetMapping("/loginForm")
	public String loginForm(Model m) {
		m.addAttribute("menu", "login");
		return "login";
	}

//	User save process
	@PostMapping("/save")
	public String save(@ModelAttribute("customer") Customer customer) {
		dao.save(customer);

		return "redirect:/loginForm";

	}

//	Login process
	@PostMapping("/processing")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session, Model m) {

		Customer object = (Customer) session.getAttribute("user");
		if (object != null) {
			return "redirect:/home";
		} else {

			Customer customer = dao.login(email, password);

			if (customer == null) {
				m.addAttribute("failed", "Invalied login");
				return "login";
			} else {
				session.setAttribute("user", customer);
				session.setAttribute("role", customer.getRole());
			}
			 return "redirect:/home";
		}
	}

	@GetMapping("/subscribe/{id}")
	public String subscribe(@PathVariable("id") Long id, HttpSession session, Model m){
		Customer object = (Customer) session.getAttribute("user");
		if (object == null) {
			return "redirect:/loginForm";
		} else {
			m.addAttribute("trajet", trajetService.findById(id));
			return "add-abonnement";
		}
	}

	@PostMapping("/subscribe/new")
	public String newSubscribe(
			@RequestParam("dateDebut") String dateDebut,
			@RequestParam("dateFin") String dateFin, @RequestParam("trajetId") Long trajetId, HttpSession session, Model m){
		Customer object = (Customer) session.getAttribute("user");
		if (object == null) {
			return "redirect:/loginForm";
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			Trajet trajet = trajetService.findById(trajetId);
			TrajetAbonnement trajetAbonnement = new TrajetAbonnement();
			trajetAbonnement.setTrajet(trajet);
			trajetAbonnement.setDateDepart(LocalDate.parse(dateDebut, formatter));
			trajetAbonnement.setDateFin(LocalDate.parse(dateFin, formatter));
			trajetAbonnement.setPrix(trajet.getPrix());
			abonnementRepository.save(trajetAbonnement);
			List<TrajetAbonnement> abonnements = object.getAbonnements();
			abonnements.add(trajetAbonnement);
			object.setAbonnements(abonnements);
			dao.save(object);
			return "redirect:/home";
		}
	}

	@GetMapping("/home")
	public String mainDashboard(HttpSession session, Model m) {
		session.removeAttribute("bookingdate");
		session.removeAttribute("bookingtime");
		m.addAttribute("menu", "home");

		String message = (String) session.getAttribute("msg");
		m.addAttribute("message", message);
		session.removeAttribute("msg");
//		System.out.println(message);
		List<Trajet> trajets = trajetService.getAllTrajets();
		m.addAttribute("listTrajets", trajets);

		return "main-dashboard";
	}
//	Logout process
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");

		session.removeAttribute("bookingdate");
		session.removeAttribute("bookingtime");
		session.removeAttribute("offerName");

		return "redirect:/";
	}

//	Admin can see all Customers
	@GetMapping("/all-customers-records")
	public String allRecords(Model m, HttpSession session) {
		Customer object = (Customer) session.getAttribute("user");
		long id = object.getId();
		List<Customer> all = dao.getAll();
		m.addAttribute("records", all);
		m.addAttribute("menu", "allusers");
		return "user_records";
	}

//	Admin can see all Customers and their offers
	@GetMapping("/all-subs/{id}")
	public String allSubscipritions(@PathVariable("id") long id, Model m, HttpSession session) {
		Customer object = (Customer) session.getAttribute("user");
		long bid = object.getId();
		m.addAttribute("subscribes", trajetService.getSubscribesById(id));
		m.addAttribute("menu", "allusers");
		return "subscribe-records";

	}

	@GetMapping("/my-subs")
	@Transactional
	public String mySubscipritions(Model m, HttpSession session) {
		Customer object = (Customer) session.getAttribute("user");
		long bid = object.getId();
		m.addAttribute("subscribes", object.getAbonnements());
		return "subscribe-records";
	}

	@GetMapping("/all-subs")
	@Transactional
	public String allSubsciprition(Model m, HttpSession session) {
		Customer object = (Customer) session.getAttribute("user");
		long bid = object.getId();
		m.addAttribute("subscribes", abonnementRepository.findAll());
		return "subscribe-records";
	}

	//	Admin can see all Customers and their offers
	@GetMapping("/all-customer-subs/{id}")
	public String allCustomerSubscriptions(@PathVariable("id") long id, Model m, HttpSession session) {
		Customer object = (Customer) session.getAttribute("user");
		m.addAttribute("trajetList", object.getTrajets());
		return "";
	}

//	User infoo
	@GetMapping("/setting")
	public String getSetting(Model m, HttpSession session) {
		Customer customer = (Customer) session.getAttribute("user");
		m.addAttribute("user", customer);
		m.addAttribute("menu", "setting");
		return "setting";
	}

	@GetMapping("/bus")
	public String getBus(Model m, HttpSession session) {
		Customer customer = (Customer) session.getAttribute("user");
		m.addAttribute("user", customer);
		m.addAttribute("busList", busService.findAll());
		return "bus";
	}

	@GetMapping("/societies")
	public String getSocieties(Model m, HttpSession session) {
		Customer customer = (Customer) session.getAttribute("user");
		m.addAttribute("user", customer);
		m.addAttribute("societyList", societyService.findAll());
		return "society";
	}

//	User update form
	@GetMapping("/setting/update/{id}")
	public String updateForm(@PathVariable("id") long id, Model m,  HttpSession session) {
		Customer customer = (Customer) session.getAttribute("user");
		m.addAttribute("customer", customer);
		m.addAttribute("menu", "setting");
		return "update-details";

	}

//	update Details
	@PostMapping("/setting/update-details")
	public String updateDetails(@ModelAttribute("customer") Customer cust, HttpSession session) {
		String name = cust.getName();
		String email = cust.getEmail();
		Customer customer = (Customer) session.getAttribute("user");
		customer.setName(name);
		customer.setEmail(email);
		customer.setAdresse(cust.getAdresse());
		customer.setCin(cust.getCin());
		customer.setPhone(cust.getPhone());
		customer.setPrenom(cust.getPrenom());
		dao.updateDetail(customer);

		return "redirect:/setting";

	}

	@PostMapping("/check")
	public String checkDate(@RequestParam("localdate") String date, @RequestParam("localtime") String time, Model m,
			HttpSession session) {
		Customer object = (Customer) session.getAttribute("user");
		String offer = (String) session.getAttribute("offerName");
		LocalDate monthLimit = LocalDate.now();
		if (offer.equals(null)) {
			return "home";

		} else if (object == null) {
			LocalDate now = LocalDate.parse(date);
			List<String> seatNo1 = new ArrayList<String>();

			session.setAttribute("bookingdate", now);
			session.setAttribute("bookingtime", time);
			m.addAttribute("date", now);
			m.addAttribute("max", monthLimit.plusMonths(1));
			m.addAttribute("min", monthLimit);
			m.addAttribute("time", time);
			m.addAttribute("seats", seatNo1);

			return "home";
		} else {
			LocalDate now = LocalDate.parse(date);
			List<String> seatNo1 = new ArrayList<String>();

			session.setAttribute("bookingdate", now);
			session.setAttribute("bookingtime", time);
			m.addAttribute("date", now);
			m.addAttribute("max", monthLimit.plusMonths(1));
			m.addAttribute("min", monthLimit);
			m.addAttribute("time", time);
			m.addAttribute("seats", seatNo1);

			return "dashboard";
		}

	}

//	Exception handling
	@ExceptionHandler(Exception.class)
	public String handleError(Exception ex, Model m, HttpSession session) {
		Customer object = (Customer) session.getAttribute("user");
		if (object == null) {
			return "redirect:/loginForm";
		} else {
			return "redirect:/home";
		}
	}

}
