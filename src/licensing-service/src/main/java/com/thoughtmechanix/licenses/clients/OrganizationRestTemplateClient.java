package com.thoughtmechanix.licenses.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.thoughtmechanix.organization.model.Organization;

/**
 * Uses a Ribbon backed RestTemplate bean.
 * 
 * - Uses application id to find services in Eureka
 * 		http://{applicationid}/v1/organizations/{organizationId}
 * - Never needs to know actual resolved locations for services
 * - "Ribbon aware"
 * - Ribbon will automagically Round-Robbin requests to load balance
 *
 */
@Component
public class OrganizationRestTemplateClient {

	@Autowired
	private RestTemplate restTemplate;
	
	public Organization getOrganization(String organizationId) {
		final ResponseEntity<Organization> restExchange = 
				restTemplate.exchange("http://organizationservice/v1/organizations/{organizationId}", HttpMethod.GET, null, Organization.class, organizationId);
		return restExchange.getBody();
	}
	
}
