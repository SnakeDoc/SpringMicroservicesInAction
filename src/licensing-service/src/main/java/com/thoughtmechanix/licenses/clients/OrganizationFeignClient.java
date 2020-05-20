package com.thoughtmechanix.licenses.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thoughtmechanix.organization.model.Organization;

/**
 * Uses Netflix's Feign Client
 * 
 * - Dynamically generates proxy classes for annotations and interface methods
 * - Automatically maps to expected return type
 * - Ribbon aware
 *
 */
@Component
@FeignClient("organizationservice")
public interface OrganizationFeignClient {

	@RequestMapping(value = "/v1/organizations/{organizationId}", method = RequestMethod.GET, consumes = "application/json")
	public Organization getOrganization(@PathVariable("organizationId") String organizationId);
	
}
