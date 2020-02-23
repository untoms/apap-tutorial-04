package com.apap.tu04.controller;

import com.apap.tu04.model.FlightModel;
import com.apap.tu04.model.PilotModel;
import com.apap.tu04.service.FlightService;
import com.apap.tu04.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FlightController {

    private final FlightService flightService;
    private final PilotService pilotService;

    @Autowired
    public FlightController(FlightService flightService, PilotService pilotService) {
        this.flightService = flightService;
        this.pilotService = pilotService;
    }

    @GetMapping(value = "/flight/add/{licenseNumber}")
    private String add(@PathVariable(value = "licenseNumber") String license, Model model){

        FlightModel flightModel = new FlightModel();
        PilotModel pilotModel = pilotService.getPilotDetailByLicenseNumber(license);

        flightModel.setPilot(pilotModel);

        model.addAttribute("flight", flightModel);

        return "addFlight";

    }

    @PostMapping(value = "/flight/add")
    private String addFlightSubmit(@ModelAttribute FlightModel flightModel){
        flightService.addFlight(flightModel);
        return "redirect:/pilot/view/"+flightModel.getPilot().getLicenseNumber();
    }

    @GetMapping(value = "/flight/edit/{id}")
    private String edit(@PathVariable(value = "id") Long id, ModelMap model){

        FlightModel flightModel = flightService.getById(id);

        model.addAttribute("flight", flightModel);

        return "editFlight";

    }

    @PostMapping(value = "/flight/edit")
    private String doedit(@ModelAttribute FlightModel flightModel){

        flightService.addFlight(flightModel);
        return "redirect:/pilot/view/"+flightModel.getPilot().getLicenseNumber();

    }

    @GetMapping(value = "/flight/delete/{id}")
    private String delete(@PathVariable(value = "id") Long id, ModelMap model){

        FlightModel flightModel = flightService.getById(id);

        model.addAttribute("flight", flightModel);

        return "deleteFlight";

    }

    @PostMapping(value = "/flight/delete")
    private String dodelete(@ModelAttribute FlightModel flightModel){

        String license = flightModel.getPilot().getLicenseNumber();
        flightService.deleteFlight(flightModel);

        return "redirect:/pilot/view/"+license;
    }
}
