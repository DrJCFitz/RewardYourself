package com.drjcfitz.rewardyourself.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.drjcfitz.rewardyourself.model.Merchant;
import com.drjcfitz.rewardyourself.model.Reward;

@Component("merchantDao")
public class MerchantDao {
	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	@Transactional
	public void createMerchant(List<Merchant> merchant, String site) {
		List<SqlParameterSource> params = new ArrayList<SqlParameterSource>();
		for (Merchant merch : merchant) {
			params.add(new BeanPropertySqlParameterSource(merch));
		}

		jdbc.batchUpdate("insert ignore into merchant (name) values (:name)",
					params.toArray(new SqlParameterSource[0]));
		jdbc.batchUpdate("update " + site + " set enabled=false where " + 
					"(LOWER(name)=LOWER(:name) and (rewardValue<>:rewardValue or rewardUnit<>:rewardUnit))", 
					params.toArray(new SqlParameterSource[0]));
		jdbc.batchUpdate("insert ignore into " + site + 
				" (name, storeLink, rewardValue, rewardUnit, rewardRate, enabled, refDate)" +
				" values (:name, :storeLink, :rewardValue, :rewardUnit, :rewardRate, :enabled, :refDate)", 
				params.toArray(new SqlParameterSource[0]));
	}

	public List<Merchant> getAllMerchants() {
		return jdbc.query(
				"select * from merchant as a, ebates as b where a.mkey=b.mkey",
				new RowMapper<Merchant>() {
					public Merchant mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Merchant merch = new Merchant(rs.getString("name"), 
								rs.getString("storeLink"), 
								new Reward(rs.getDouble("rewards"), rs.getString("rewardType")),
								rs.getLong("refDate"), 
								rs.getBoolean("enabled"));
						return merch;
					}
				});
	}

	public List<Merchant> getMerchantData(String portal, String storeName) {
		// Using RowMapper over BeanPropertyRowMapper since Reward bean set methods throwing nullpointer exception
		return jdbc.query("select merchant.name, storeLink, rewardValue, rewardRate, rewardUnit, "+
					"refDate, enabled "+
					"from merchant inner join "+portal+" on merchant.name="+portal+".name where merchant.name=\""+storeName+"\"",
						new RowMapper<Merchant>(){

							public Merchant mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								Reward reward = new Reward(rs.getDouble("rewardValue"), 
										rs.getString("rewardRate"),
										rs.getString("rewardUnit"));
								
								return new Merchant(rs.getString("name"),
										rs.getString("storeLink"),
										reward,
										rs.getLong("refDate"),
										rs.getBoolean("enabled"));
							}});
	}
	
	public List<Merchant> getActiveMerchants(String site) {
		return jdbc
				.query("select name, enabled, refDate, rewards, rewardType, storeLink from merchant as a,"
						+ site + " as b where a.mkey=b.mkey and b.enabled=true",
						new BeanPropertyRowMapper<Merchant>());/*   {
							public Merchant mapRow(ResultSet rs, int rowNum)
									throws SQLException {

								Merchant merch = new Merchant();
								merch.setName(rs.getString("name"));
								merch.setEnabled(rs.getBoolean("enabled"));
								merch.setReward(new Reward(rs.getDouble("rewards"),
										rs.getString("rewardType")));
								merch.setStoreLink(rs.getString("storeLink"));
								merch.setRefDate(rs.getLong("refDate"));

								return merch;
							}
						});*/
	}

	public boolean merchExistsGlobal(Merchant merchant) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("mkey", merchant.getName().toLowerCase());
		return jdbc.queryForObject(
				"select count(*) from merchant where mkey=:mkey", params,
				Integer.class) > 0;
	}

	public boolean merchExistsSite(Merchant merchant, String site) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("mkey", merchant.getName().toLowerCase());
		return jdbc.queryForObject("select count(*) from " + site
				+ " where mkey=:mkey", params, Integer.class) > 0;
	}

}
