package com.apap.tu04.controller;

import com.apap.tu04.model.FlightModel;
import com.apap.tu04.model.PilotModel;
import com.apap.tu04.service.FlightService;
import com.apap.tu04.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class PilotController {

    private final PilotService pilotService;
    private final FlightService flightService;

    @Autowired
    public PilotController(PilotService pilotService, FlightService flightService) {
        this.pilotService = pilotService;
        this.flightService = flightService;
    }

    @RequestMapping("/")
    private String home(){
        return "home";
    }

    @GetMapping(value = "/pilot/add")
    private String add(Model model) {
        model.addAttribute("pilot", new PilotModel());
        return "addPilot";
    }

    @PostMapping(value = "/pilot/add")
    private String addPilotSubmit(@ModelAttribute PilotModel pilot){
        pilotService.addPilot(pilot);
        return "redirect:/pilot/view/"+pilot.getLicenseNumber();
    }

    @GetMapping(value = "/pilot/view/{licenseNumber}")
    private String viewPilot(@PathVariable(value = "licenseNumber") String license, Model model){
        PilotModel pilotModel = pilotService.getPilotDetailByLicenseNumber(license);
        model.addAttribute("pilot", pilotModel);
        model.addAttribute("flights", pilotModel.getPilotFlight());

        return "view-pilot";
    }

    @GetMapping(value = "/pilot/edit/{id}")
    private String edit(@PathVariable(value = "id") Long id, ModelMap model){

        PilotModel pilotModel = pilotService.getPilotDetailByID(id);

        model.addAttribute("pilot", pilotModel);

        return "editPilot";

    }

    @PostMapping(value = "/pilot/edit")
    private String doedit(@ModelAttribute PilotModel pilotModel){

        pilotService.addPilot(pilotModel);
        return "redirect:/pilot/view/"+pilotModel.getLicenseNumber();

    }

    @GetMapping(value = "/pilot/delete/{id}")
    private String delete(@PathVariable(value = "id") Long id, ModelMap model){

        PilotModel pilotModel = pilotService.getPilotDetailByID(id);

        model.addAttribute("pilot", pilotModel);

        return "deletePilot";

    }

    @PostMapping(value = "/pilot/delete")
    private String dodelete(@ModelAttribute PilotModel pilotModel){

        pilotModel = pilotService.getPilotDetailByID(pilotModel.getId());

        for (FlightModel f :
                pilotModel.getPilotFlight()) {
            flightService.deleteFlight(f);
        }

        pilotService.delete(pilotModel);

        return "home";
    }
}
