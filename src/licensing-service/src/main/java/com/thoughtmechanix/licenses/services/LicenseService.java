package com.thoughtmechanix.licenses.services;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtmechanix.licenses.clients.OrganizationDiscoveryClient;
import com.thoughtmechanix.licenses.clients.OrganizationFeignClient;
import com.thoughtmechanix.licenses.clients.OrganizationRestTemplateClient;
import com.thoughtmechanix.licenses.config.ServiceConfig;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.repository.LicenseRepository;
import com.thoughtmechanix.organization.model.Organization;

@Service
public class LicenseService {

	@Autowired
	private LicenseRepository licenseRepository;
	
	@Autowired
	private ServiceConfig config;
	
	@Autowired
	private OrganizationDiscoveryClient discoveryClient;
	
	@Autowired
	private OrganizationRestTemplateClient restClient;
	
	@Autowired
	private OrganizationFeignClient feignClient;
	
	final private Logger log = LoggerFactory.getLogger(LicenseService.class);
	
	public License getLicense(String organizationId, String licenseId) {
		final License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		return license.withComment(config.getExampleProperty());
	}
	
	public License getLicense(String organizationId, String licenseId, ServiceClientType clientType) {
		final License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		final Organization org = retrieveOrgInfo(organizationId, clientType);
		
		return license
				.withOrganizationName(org.getName())
				.withContactName(org.getContactName())
				.withContactEmail(org.getContactEmail())
				.withContactPhone(org.getContactPhone())
				.withComment(config.getExampleProperty());
	}
	
	public List<License> getLicenses(String organizationId) {
		return licenseRepository.findByOrganizationId(organizationId);
	}
	
	public void saveLicense(License license) {
		license.withLicenseId(UUID.randomUUID().toString());
		licenseRepository.save(license);
	}
	
	public void deleteLicense(License license) {
		licenseRepository.delete(license);
	}
	
	public void updateLicense(License license) {
		licenseRepository.save(license);
	}
	
	private Organization retrieveOrgInfo(String organizationId, ServiceClientType clientType) {
		
		switch (clientType) {
		
		case DISCOVERY:
			log.info("Using Discovery Client");
			return discoveryClient.getOrganization(organizationId);
		case REST:
			log.info("Using Rest Template Client");
			return restClient.getOrganization(organizationId);
		case FEIGN:
			log.info("Using Feign Client");
			return feignClient.getOrganization(organizationId);
		default:
			return new Organization();
		
		}
		
	}
}
