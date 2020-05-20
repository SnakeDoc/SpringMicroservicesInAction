package com.thoughtmechanix.licenses.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.services.LicenseService;
import com.thoughtmechanix.licenses.services.ServiceClientType;

@RestController
@RequestMapping(value = "/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {
	
	@Autowired
	private LicenseService licenseService;

	@RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
	public License getLicenses(
			@PathVariable("organizationId") String organizationId, 
			@PathVariable("licenseId") String licenseId) {
		return new License()
				.withLicenseId(licenseId)
				.withProductName("Teleco")
				.withLicenseType("Seat")
				.withOrganizationId("TestOrg");
	}
	
	@RequestMapping(value = "/{licenseId}", method = RequestMethod.PUT)
	public void updateLicense(@PathVariable("licenseId") String licenseId, @RequestBody License license) {
		licenseService.updateLicense(license);
	}
	
	@RequestMapping(value = "/{licenseId}", method = RequestMethod.POST)
	public void saveLicense(@RequestBody License license) {
		licenseService.saveLicense(license);
	}
	
	@RequestMapping(value = "/{licenseId}", method = RequestMethod.DELETE)
	public void deleteLicense(@PathVariable("licenseId") String licenseId, @RequestBody License license) {
		licenseService.deleteLicense(license);
	}
	
	@RequestMapping(value = "/{licenseId}/{clientType}", method = RequestMethod.GET)
	public License getLicensesWithClient(@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId, @PathVariable("clientType") ServiceClientType clientType) {
		return licenseService.getLicense(organizationId, licenseId, clientType);
	}
	
}
