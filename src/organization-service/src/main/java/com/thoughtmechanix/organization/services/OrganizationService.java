package com.thoughtmechanix.organization.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.repository.OrganizationRepository;

@Service
public class OrganizationService {

	@Autowired
	private OrganizationRepository orgRepository;
	
	final private Logger log = LoggerFactory.getLogger(OrganizationService.class);
	
	public Organization getOrg(String organizationId) {
		log.info("Finding Org with id: '" + organizationId + "'");
		return orgRepository.findById(organizationId);
	}
	
	public void saveOrg(Organization org) {
		org.setId(UUID.randomUUID().toString());
		
		orgRepository.save(org);
	}
	
	public void updateOrg(Organization org) {
		orgRepository.save(org);
	}
	
	public void deleteOrg(Organization org) {
		orgRepository.delete(org.getId());
	}
	
}
