package com.thoughtmechanix.licenses.clients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.thoughtmechanix.organization.model.Organization;

/**
 * Uses Spring Cloud's Discovery Client
 * Fairly "low level"
 * 
 * - Does not handle or make use of Ribbon's client-side load balancing.
 * - Pretty verbose
 * - Manual handling of RestTemplate
 * 		Since we enabled Discovery Client, we need to avoid automatic 
 * 		Ribbon interceptor's from being injected into RestTemplate...
 * 		we do this by manually instantiating and handling RestTemplate.
 * - "Ribbon unaware"
 *
 */
@Component
public class OrganizationDiscoveryClient {

	@Autowired
	private DiscoveryClient discoveryClient;
	
	public Organization getOrganization(String organizationId) {
		final RestTemplate restTemplate = new RestTemplate();
		final List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");
		
		if (instances.size() == 0) return null;
		
		final String serviceUri = String.format("%s/v1/organizations/%s", instances.get(0).getUri().toString(), organizationId);
		
		final ResponseEntity<Organization> restExchange = restTemplate.exchange(serviceUri, HttpMethod.GET, null, Organization.class, organizationId);
		
		return restExchange.getBody();
	}
	
}
