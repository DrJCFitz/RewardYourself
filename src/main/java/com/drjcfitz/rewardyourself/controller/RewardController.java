package com.drjcfitz.rewardyourself.controller;

import java.io.IOException;
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

/**
 * Handles requests for the application home page.
 */
@Controller
public class RewardController {
	
	@Autowired
	private PortalService portalService;
	@Autowired
	private MerchantService merchantService;
	
	// Bind Portal object to mobile page
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String showMobile(Model model) throws IOException {
		List<Portal> portalList= portalService.getPortals();
		List<Merchant> orderedMerchant = merchantService.getAllMerchantNamesKeys();
		
		model.addAttribute("portalList", portalList);
		model.addAttribute("orderedMerchant", orderedMerchant);
		
		return "mobile";
	}	
	
	@RequestMapping(value="/panels", method=RequestMethod.GET)
	public String showPanels(Model model) {
		return "panels";
	}
}
