package com.apap.tu04.service;

import com.apap.tu04.model.PilotModel;

public interface PilotService {
    PilotModel getPilotDetailByLicenseNumber(String licenseNumber);
    PilotModel getPilotDetailByID(Long id);
    void delete(PilotModel pilot);
    void addPilot(PilotModel pilot);
}
