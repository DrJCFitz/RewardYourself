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
	public HashMap<String, Portal> getPortalData() throws IOException {
		HashMap<String, Portal> portalMap = new HashMap<String,Portal>();
		
		for (PortalProfile profile : profileList) {
			for (Portal portal : portalList) {
				if (profile.getProfileKey().equals(portal.getPortalKey())) {
					portalMap.put(profile.getProfileKey(), new Portal(portal.getPortalName(),
							portal.getEquivalentPercentage(),
							merchantService.getCurrentPortalData(profile.getProfileKey())));
				}
			}
		}
		return portalMap;
	}

	// Return list of portals
	// note that a merchantList is not set/included in the Portal object
	public List<Portal> getPortals() throws IOException {
		return portalList;
	}

	public HashMap<String, List<Merchant>> getAllStoreData(String storeKey) {
		HashMap<String, List<Merchant>> storeMap = new HashMap<String, List<Merchant>>();	
		for (Portal portal : portalList) {
			storeMap.put(portal.getPortalKey(), 
					merchantService.getMerchantData(portal.getPortalKey(), 
							storeKey));
		}
		return storeMap;
	}
	
	public HashMap<String, Portal> getCurrentMerchantData(String storeKey) {
		HashMap<String, Portal> currData = new HashMap<String, Portal>();	
		for (Portal portal : portalList) {
			currData.put(portal.getPortalKey(), 
					new Portal(portal.getPortalName(),
							portal.getEquivalentPercentage(),
							merchantService.getCurrentMerchantData(portal.getPortalKey(), storeKey)));
		}
		return currData;
	}
	
	public HashMap<String, List<Merchant>> processPortals() throws IOException {
		HashMap<String, List<Merchant>> processedPortals = new HashMap<String, List<Merchant>>();
		ProcessMerchant processMerchant = new ProcessMerchant();
		for (PortalProfile profile : profileList) {
			processedPortals.put(profile.getProfileKey(), 
					processMerchant.listStores(profile));
		}
		return processedPortals;
	}

	public HashMap<String, Portal> getCurrentPortalData(String portalName) {
		HashMap<String, Portal> currData = new HashMap<String, Portal>();	
		for (Portal portal : portalList) {
			if (portal.getPortalKey().equals(portalName)) {
				currData.put(portal.getPortalKey(), 
						new Portal(portal.getPortalName(),
								portal.getEquivalentPercentage(),
								merchantService.getCurrentPortalData(portal.getPortalKey())));
			}
		}
		return currData;
	}
}
