package com.drjcfitz.rewardyourself.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

import com.drjcfitz.rewardyourself.cron.PortalTask;
import com.drjcfitz.rewardyourself.model.Merchant;
import com.drjcfitz.rewardyourself.model.Portal;
import com.drjcfitz.rewardyourself.model.Reward;
import com.drjcfitz.rewardyourself.service.MerchantService;
import com.google.gson.Gson;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	@Qualifier("ebatesProcess")
	private Portal ebatesProcess;
	@Autowired
	@Qualifier("unitedProcess")
	private Portal unitedProcess;
	@Autowired
	@Qualifier("marriottProcess")
	private Portal marriottProcess;
	@Autowired
	@Qualifier("upromiseProcess")
	private Portal upromiseProcess;
	
	@Autowired
	private MerchantService merchantService;

	@Autowired
	private List<Portal> portalList = new ArrayList<Portal>(Arrays.asList(new Portal[] {ebatesProcess, unitedProcess, marriottProcess}));
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(Model model) {		
		
		return "home";
	}	
	
	@RequestMapping(value="/portals/{portalName}", method=RequestMethod.GET)
	public String portals(@PathVariable String portalName, Model model) {
		return "portals";
	}
	
	@RequestMapping(value="/stores/{storeName}", method=RequestMethod.GET)
	@ResponseBody
	public String stores(@PathVariable String storeName, Model model) {
		HashMap<String, List<Merchant>> storeDataMap = new HashMap<String, List<Merchant>>();
		
		for (Portal portal : portalList) {
			storeDataMap.put(portal.getProfile(),
					merchantService.getMerchantData(portal.getProfile(),
							storeName));
		}

		Gson gson = new Gson();
		String json = gson.toJson(storeDataMap);
		return json;
	}

	@RequestMapping(value="/d3")
	public String d3graphic(Model model) {
		return "graphic";
	}
}
