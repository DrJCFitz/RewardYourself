package com.drjcfitz.rewardyourself.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.drjcfitz.rewardyourself.cron.PortalTask;
import com.drjcfitz.rewardyourself.dao.MerchantDao;
import com.drjcfitz.rewardyourself.model.Merchant;
import com.drjcfitz.rewardyourself.model.Portal;
import com.drjcfitz.rewardyourself.model.Reward;
import com.drjcfitz.rewardyourself.process.ProcessMerchant;
import com.drjcfitz.rewardyourself.service.MerchantService;

@ActiveProfiles("dev")
@ContextConfiguration(locations = {
		"classpath:com/drjcfitz/rewardyourself/process/portalData-config.xml",
		"classpath:com/drjcfitz/rewardyourself/test/config/local-db.xml",
		"classpath:com/drjcfitz/rewardyourself/config/dao-context.xml",
		"classpath:com/drjcfitz/rewardyourself/config/service-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MerchantDaoTests {

	@Autowired
	private MerchantDao merchantDao;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private Portal ebatesProcess;
	@Autowired
	private Portal unitedProcess;
	@Autowired
	private Portal marriottProcess;

	@Autowired
	private List<Portal> portalList = new ArrayList<Portal>(Arrays.asList(new Portal[] {ebatesProcess, unitedProcess, marriottProcess}));
	

	final long date = new GregorianCalendar().getTimeInMillis();
	private Merchant merch0 = new Merchant("Zeroth Store", "href store0",
			new Reward(5.0, "$"), date, true);
	private Merchant merch1 = new Merchant("First Store", "href store1",
			new Reward(5.0, "%"), date, true);
	private Merchant merch2 = new Merchant("Zeroth Store", "href store",
			new Reward(7.0, "$"), date, true);

	final ProcessMerchant procMerch = new ProcessMerchant();

	// @Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		jdbc.execute("delete from marriott");
		jdbc.execute("delete from ebates");
		jdbc.execute("delete from merchant");
	}

	// @Test
	public void testCreateRetrieve() {
		// Check that the store key for merch0 is not in db
		assertFalse(
				"One merchant matching \"zeroth store\" does not exist in Global Db",
				merchantDao.merchExistsGlobal(merch0));
		assertFalse(
				"One merchant matching \"zeroth store\" does not exist in Ebates Db",
				merchantDao.merchExistsSite(merch0, "ebates"));
		// Create it a listing for merch0
		// merchantDao.create(merch0, "ebates");
		// Query for merch0 and compare
		List<Merchant> merchants = merchantDao.getAllMerchants();
		assertEquals("The one merchant's data should match", merch0,
				merchants.get(0));
		// Confirm that merch0 exists
		assertTrue("One merchant matching \"first store\" exists in Global db",
				merchantDao.merchExistsGlobal(merch0));
		assertTrue("One merchant matching \"first store\" exists in Ebates db",
				merchantDao.merchExistsSite(merch0, "ebates")); // Attempt to
		// create a duplicate entry
		// merchantDao.create(merch0, "ebates");
		// Create a second [distinct] entry
		// merchantDao.create(merch1, "ebates");
		// Update rewards field
		// merchantDao.create(merch2, "ebates");
		// merchantService.create(merch2, "ebates");
		// Get active merchants
		List<Merchant> merchants1 = merchantDao.getActiveMerchants("ebates");
		assertEquals("Match \"second store\" ", merch1, merchants1.get(0));
		assertEquals("Matching \"first store\" ", merch2, merchants1.get(1));

	}

	// @Test
	public void testProcessStores() {

		Document doc;
		try {
			doc = Jsoup.connect(ebatesProcess.getStoresUrl()).get();
			List<Merchant> list = procMerch.listStores(doc, ebatesProcess);
			merchantService.create(list, ebatesProcess.getProfile());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testPortalData() {
		HashMap<String, List<Merchant>> storeDataMap = new HashMap<String, List<Merchant>>();
		PortalTask portalTask = new PortalTask(); 
		String storeName = "Abebooks";
		for (Portal portal : portalList) {
			//storeDataMap = portalTask.getStoreData(storeName, portalList);
			storeDataMap.put(portal.getProfile(),
					merchantService.getMerchantData(portal.getProfile(),
							storeName));
		}

		for (Map.Entry<String, List<Merchant>> entry : storeDataMap.entrySet()) {
			for (Merchant merch : entry.getValue()) {
				System.out.println(entry.getKey() + " : " + merch);
			}
		}
	}
	
	//@Test
	public void testGetPortalData() {
		HashMap<String, List<Merchant>> storeDataMap = new HashMap<String, List<Merchant>>();
		String storeName = "Abebooks";
		for (Portal portal : portalList) {
			storeDataMap.put(portal.getProfile(),
					merchantService.getMerchantData(portal.getProfile(), storeName));
		}
		
		for (Map.Entry<String, List<Merchant>> entry : storeDataMap.entrySet()) {
			for (Merchant merch : entry.getValue()) {
				System.out.println(entry.getKey() + " : " + merch);
			}
		}
	}

}
