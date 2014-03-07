package com.drjcfitz.rewardyourself.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.drjcfitz.rewardyourself.model.Merchant;
import com.drjcfitz.rewardyourself.model.Portal;
import com.drjcfitz.rewardyourself.model.PortalProfile;
import com.drjcfitz.rewardyourself.model.Reward;
import com.drjcfitz.rewardyourself.service.MerchantService;
import com.drjcfitz.rewardyourself.service.PortalService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Controller
public class RewardServer {
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private PortalService portalService;
	
	// Return JSON containing store data by portal
	@RequestMapping(value="/portals/{portalName}", method=RequestMethod.GET)
	@ResponseBody
	public String portals(@PathVariable String portalName, Model model) {
		HashMap<String, Portal> portalMerchMap = portalService.getCurrentPortalData(portalName);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(portalMerchMap);
		return json;
	}
	
	// Return JSON containing store data by portal
	@RequestMapping(value="/trends/{storeName}", method=RequestMethod.GET)
	@ResponseBody
	public String trends(@PathVariable String storeName, Model model) {
		HashMap<String, List<Merchant>> storeDataMap = portalService.getAllStoreData(storeName);
		Gson gson = new Gson();
		String json = gson.toJson(storeDataMap);
		return json;
	}

	// Return JSON containing store data by portal
	@RequestMapping(value="/stores/{storeName}", method=RequestMethod.GET)
	@ResponseBody
	public String stores(@PathVariable String storeName, Model model) {
		HashMap<String, Portal> portalMerchMap = portalService.getCurrentMerchantData(storeName);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(portalMerchMap);
		return json;
	}	
}
