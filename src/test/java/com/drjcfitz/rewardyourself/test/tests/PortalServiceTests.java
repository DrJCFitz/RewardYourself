package com.drjcfitz.rewardyourself.test.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.drjcfitz.rewardyourself.model.Merchant;
import com.drjcfitz.rewardyourself.model.Portal;
import com.drjcfitz.rewardyourself.model.PortalProfile;
import com.drjcfitz.rewardyourself.process.ProcessMerchant;
import com.drjcfitz.rewardyourself.service.MerchantService;
import com.drjcfitz.rewardyourself.service.PortalService;

@ContextConfiguration(locations = {
		"classpath:com/drjcfitz/rewardyourself/config/portalProfile-config.xml",
		"classpath:com/drjcfitz/rewardyourself/config/portal-config.xml",
		"classpath:com/drjcfitz/rewardyourself/test/config/local-db.xml",
		"classpath:com/drjcfitz/rewardyourself/config/dao-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class PortalServiceTests {
	@Autowired
	private DataSource dataSource;

	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private PortalService portalService;

	@Autowired
	private PortalProfile ebatesProfile;
	@Autowired
	private PortalProfile unitedProfile;
	@Autowired
	private PortalProfile marriottProfile;
	@Autowired
	private PortalProfile upromiseProfile;

	@Autowired
	private List<PortalProfile> portalList = new ArrayList<PortalProfile>(Arrays.asList(new PortalProfile[] {ebatesProfile, unitedProfile, marriottProfile}));
	
	//final ProcessMerchant procMerch = new ProcessMerchant();

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
	public void testProcess() throws IOException {
		ProcessMerchant procMerchant = new ProcessMerchant();
		procMerchant.listStores(ebatesProfile);
	}
	
	@Test 
	public void testPortalService() throws IOException {
		HashMap<String,List<Merchant>> portalMap = portalService.processPortals();
		for (Map.Entry<String, List<Merchant>> entry : portalMap.entrySet()) {
			merchantService.create(entry.getValue(), entry.getKey());
		}
	}
	
}
