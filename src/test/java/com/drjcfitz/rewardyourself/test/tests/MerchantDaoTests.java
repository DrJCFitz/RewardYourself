package com.drjcfitz.rewardyourself.test.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.drjcfitz.rewardyourself.dao.MerchantDao;
import com.drjcfitz.rewardyourself.model.Merchant;
import com.drjcfitz.rewardyourself.model.PortalProfile;
import com.drjcfitz.rewardyourself.process.ProcessMerchant;
import com.drjcfitz.rewardyourself.service.MerchantService;
import com.drjcfitz.rewardyourself.service.PortalService;

@ContextConfiguration(locations = {
		"classpath:com/drjcfitz/rewardyourself/config/portalProfile-config.xml",
		"classpath:com/drjcfitz/rewardyourself/config/portal-config.xml",
		"classpath:com/drjcfitz/rewardyourself/test/config/local-db.xml",
		"classpath:com/drjcfitz/rewardyourself/config/dao-context.xml",
		"classpath:com/drjcfitz/rewardyourself/config/service-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MerchantDaoTests {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private MerchantService merchantService;

	final long date = new GregorianCalendar().getTimeInMillis();

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		jdbc.execute("delete from marriott");
		jdbc.execute("delete from ebates");
		jdbc.execute("delete from upromise");
		jdbc.execute("delete from united");
		jdbc.execute("delete from merchant");
	}

	//@Test
	public void testPortalToDb() throws IOException {
		Document doc = Jsoup.connect(upromiseProcess.getAllStoresUrl()).get();
		List<Merchant> list = procMerch.listStores(doc,upromiseProcess); 
		merchantService.create(list, upromiseProcess.getProfileName());
	}
	
	//@Test
	public void updateDbData() {
		String portal = "united";
		List<Merchant> origList = merchantService.getAllMerchantDbData(portal);
		List<Merchant> newList = procMerch.modifyDbPortal(origList, portal);
		merchantService.updateDbMerchant(newList, portal);
	}
	
	//@Test
	public void testPortalData() {
		
	}
	
	//@Test
	public void testGetPortalData() {
		HashMap<String, List<Merchant>> storeDataMap = new HashMap<String, List<Merchant>>();
		String storeName = "Abebooks";
		for (PortalProfile portal : portalList) {
			storeDataMap.put(portal.getProfileName(),
					merchantService.getMerchantData(portal.getProfileName(), storeName));
		}
		
		for (Map.Entry<String, List<Merchant>> entry : storeDataMap.entrySet()) {
			for (Merchant merch : entry.getValue()) {
				System.out.println(entry.getKey() + " : " + merch);
			}
		}
	}
	
	//@Test
	public void testAllMerchantNamesKeys() {
		List<Merchant> merchList = merchantService.getAllMerchantNamesKeys();
		for (Merchant merch : merchList) {
			System.out.println(merch);
		}
	}
	
	//@Test
	public void testEnabledMerchant() {
		List<Merchant> merchList = merchantService.getEnabledMerchantData("ebates");
		for (Merchant merch : merchList) {
			System.out.println(merch);
		}
	}

}
