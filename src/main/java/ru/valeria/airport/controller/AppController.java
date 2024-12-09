package ru.valeria.airport.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.valeria.airport.entity.Flight;
import ru.valeria.airport.entity.User;
import ru.valeria.airport.service.CustomUserDetailsService;
import ru.valeria.airport.service.FlightService;
import ru.valeria.airport.service.PassengerService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Collection;

@Controller
public class AppController {


    @Autowired
    private FlightService flightService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String viewHomePage(Model model, Principal principal) {
        boolean isAdmin = false;
        boolean isAnonymousUser = true;
        if (principal != null) {
            Authentication authentication = (Authentication) principal;
            isAnonymousUser = false;
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            isAdmin = authorities.stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        }
        model.addAttribute("isAnonymousUser", isAnonymousUser);
        model.addAttribute("isAdmin", isAdmin);
        return "index";
    }

    @RequestMapping("/flights")
    public String listFlights(Model model,
                              Principal principal,
                              @Param("keyword") String keyword,
                              @Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                              @Param("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                              @Param("sortOrder") String sortOrder,
                              @Param("arrivalAirport") String arrivalAirport) {
        List<Flight> listFlights;
        if (startDate != null && endDate != null) {
            listFlights = flightService.filterByDate(startDate, endDate);
        } else if (sortOrder != null) {
            listFlights = flightService.listAllSorted(sortOrder);
        } else if (arrivalAirport != null && !arrivalAirport.isEmpty()) {
            listFlights = flightService.filterByArrivalAirport(arrivalAirport);
        } else {
            listFlights = flightService.listAll(keyword);
        }
        model.addAttribute("listFlights", listFlights);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("arrivalAirport", arrivalAirport);
        model.addAttribute("arrivalAirports", flightService.findAllArrivalAirports());
        return "flights";
    }

    @GetMapping("/user")
    public String getUser(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userDetailsService.getByName(username);
            model.addAttribute("user", user);
        }
        return "user";
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<User> patchFlight(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userDetailsService.get(id);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            existingUser.setUsername(user.getUsername());
        }
        System.out.println(existingUser.getPassword());
        System.out.println(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        if (user.getPassword() != null && !user.getPassword().isEmpty() &&
                !passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userDetailsService.save(existingUser);
        return new ResponseEntity<>(existingUser, HttpStatus.OK);
    }


}