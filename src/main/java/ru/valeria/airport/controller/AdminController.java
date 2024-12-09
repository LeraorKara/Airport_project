package ru.valeria.airport.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.servlet.ModelAndView;
import ru.valeria.airport.entity.Flight;
import ru.valeria.airport.entity.Passenger;
import ru.valeria.airport.entity.Role;
import ru.valeria.airport.entity.User;
import ru.valeria.airport.repository.RoleRepository;
import ru.valeria.airport.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.valeria.airport.service.FlightService;
import ru.valeria.airport.service.PassengerService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FlightService flightService;

    @Autowired
    private PassengerService passengerService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/users/{id}/toggleRole")
    public String toggleRole(@PathVariable("id") Long id, @RequestParam("roleName") String roleName) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new IllegalArgumentException("Invalid role name: " + roleName));

        Set<Role> roles = new HashSet<>();
        if ("ADMIN".equals(roleName)) {
            roles.add(role);
        } else {
            roles.add(roleRepository.findByName("USER").orElseThrow(() -> new IllegalArgumentException("Role USER not found")));
        }
        user.setRoles(roles);
        userRepository.save(user);

        return "redirect:/admin/users";
    }

    @RequestMapping("/flights/new")
    public String showNewFlightForm(Model model) {
        Flight flight = new Flight();
        model.addAttribute("flight", flight);
        return "new_flight";
    }

    @RequestMapping("/passengers/new")
    public String showNewPassengerForm(Model model) {
        Passenger passenger = new Passenger();
        List<Flight> flights = flightService.listAll();
        model.addAttribute("passenger", passenger);
        model.addAttribute("flights", flights);
        return "new_passenger";
    }

    @RequestMapping("/passengers")
    public String listPassengers(Model model, @Param("keyword") String keyword,
                                 @Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                 @Param("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                 @Param("sortOrder") String sortOrder) {
        List<Passenger> listPassengers;
        if (startDate != null && endDate != null) {
            listPassengers = passengerService.filterByDate(startDate, endDate);
        } else if (sortOrder != null) {
            listPassengers = passengerService.listAllSorted(sortOrder);
        } else {
            listPassengers = passengerService.listAll(keyword);
        }
        model.addAttribute("listPassengers", listPassengers);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("sortOrder", sortOrder);
        return "passengers";
    }

    @RequestMapping("/flights/{id}")
    public ModelAndView showEditFlightForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_flight");
        Flight flight = flightService.get(id);
        mav.addObject("flight", flight);
        return mav;
    }

    @RequestMapping("/passengers/{id}")
    public ModelAndView showEditPassengerForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_passenger");
        Passenger passenger = passengerService.get(id);
        mav.addObject("passenger", passenger);
        return mav;
    }

    @RequestMapping("/statistics")
    public String showStatistics(Model model) {
        List<Map<String, Object>> flightsByDate = flightService.countFlightsByDate();
        List<Map<String, Object>> flightsByArrivalAirport = flightService.countFlightsByArrivalAirport();
        List<Map<String, Object>> passengersByFlightId = passengerService.countPassengersByFlightId();

        model.addAttribute("flightsByDate", flightsByDate);
        model.addAttribute("flightsByArrivalAirport", flightsByArrivalAirport);
        model.addAttribute("passengersByFlightId", passengersByFlightId);

        return "statistics";
    }

}
