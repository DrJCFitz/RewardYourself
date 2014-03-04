package com.drjcfitz.rewardyourself.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.drjcfitz.rewardyourself.model.Merchant;
import com.drjcfitz.rewardyourself.model.Portal;
import com.drjcfitz.rewardyourself.model.PortalProfile;
import com.drjcfitz.rewardyourself.process.ProcessMerchant;

@Component("portalService")
public class PortalService {

	@Autowired
	private PortalProfile ebatesProfile;
	@Autowired
	private Portal ebatesPortal;
	@Autowired
	private PortalProfile unitedProfile;
	@Autowired
	private Portal unitedPortal;
	@Autowired
	private PortalProfile marriottProfile;
	@Autowired
	private Portal marriottPortal;
	@Autowired
	private PortalProfile upromiseProfile;
	@Autowired
	private Portal upromisePortal;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private final List<PortalProfile> profileList = new ArrayList<PortalProfile>(
			Arrays.asList(new PortalProfile[] {ebatesProfile, 
					unitedProfile, 
					marriottProfile,
					upromiseProfile}));
	@Autowired
	private final List<Portal> portalList = new ArrayList<Portal>(
			Arrays.asList(new Portal[] {ebatesPortal, 
					unitedPortal, 
					marriottPortal,
					upromisePortal}));
	
	// Return Portal object for home page
	public HashMap<String, Portal> getPortals() throws IOException {
		HashMap<String, Portal> portalMap = new HashMap<String,Portal>();
		
		for (PortalProfile profile : profileList) {
			for (Portal portal : portalList) {
				if (profile.getProfileKey().equals(portal.getPortalKey())) {
					portalMap.put(profile.getProfileKey(), new Portal(portal.getPortalName(),
							portal.getEquivalentPercentage(),
							merchantService.getEnabledMerchantData(profile.getProfileKey())));
				}
			}
		}
		return portalMap;
	}

	public HashMap<String, List<Merchant>> getStoreMap(String storeKey) {
		HashMap<String, List<Merchant>> storeMap = new HashMap<String, List<Merchant>>();
		
		for (Portal portal : portalList) {
			storeMap.put(portal.getPortalKey(), 
					merchantService.getMerchantData(portal.getPortalKey(), 
							storeKey));
		}
		
		return storeMap;
	}
}
