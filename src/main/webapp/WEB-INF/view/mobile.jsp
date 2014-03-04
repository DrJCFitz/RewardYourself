<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
	<title>RewardYour$elf</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="static/css/jquery.mobile-1.4.1.min.css" />
	<link rel="stylesheet" type="text/css" href="static/css/rewardyourself.css" />
	<link rel="stylesheet" type="text/css" href="static/css/rewardyourselfD3.css" />
	<script type="text/javascript" src="static/js/jquery-min.js"></script>
	<script type="text/javascript" src="static/js/jquery.mobile-1.4.1.min.js"></script>
	<script type="text/javascript" src="static/js/d3.v3.min.js"></script>
	<script type="text/javascript" src="static/js/rewardyourself.js"></script>

	<script>
		$(document).ready(function(){
			$('#trendSelector').change(function(){
				d3.json('<c:url value="stores"></c:url>'+'/'+$('#trendSelector').val(), function(error, json) {
					  if (error) return console.warn(error);
					  update(json);
					});
			});
		});
	</script>
	<script id="panel-init">
		$(function() {
			$( "body>[data-role='panel']").panel();
		});
	</script>
</head>
<body>
	<div data-role="panel" id="leftmenupanel" data-position="left" data-display="overlay" data-theme="a">
		<a href="#leftmenupanel" rel-data="close" class='ui-btn ui-icon-delete ui-btn-icon-right'>Close</a>
        <a href="#trends" class='ui-btn ui-icon-arrow-u-r ui-btn-icon-left'>Trends</a>
        <p>Track reward patterns<br/>Determine the best time to buy</p>
        <a href="#stores" class='ui-btn ui-icon-shop ui-btn-icon-left'>Stores</a>
        <p>Find current offers by store</p>
        <!-- <a href="#products" class='ui-btn ui-icon-tag ui-btn-icon-left'>Products</a>
        <p>Search and track product pricing</p>  -->
        <a href="#portals" class='ui-btn ui-icon-home ui-btn-icon-left'>Portals</a>
        <p>Browse or search offers by shopping portal</p>
	</div> <!-- end menu panel -->


	<div id="stores" data-role="page">
		<div data-role="header">
			<a href="#leftmenupanel" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-bars">
				Menu
			</a>
			<h1>Stores</h1>
		</div>
		<div role="main" class="ui-content">
			<ul id="storeList" data-role="listview" data-filter="true" 
				data-filter-placeholder="Search stores..." data-inset="true" data-filter-reveal="false">
				<c:forEach var="merchant" items="${orderedMerchant}">
			    <li>
			    	<div data-role="collapsible" data-inset="false" data-collapsed-icon="arrow-r" data-expanded-icon="arrow-d">
			    		<h4><c:out value="${merchant.name}"/></h4>
					<c:forEach var="portal" items="${portalMap}">
			    		<div>
			    			<c:forEach var="portalMerch" items="${portal.value.merchantList}" >
			    				<c:if test="${merchant.mkey==portalMerch.mkey}">
					    			<p><c:out value="${portal.value.portalName}"/></p>
			    					<p>${portalMerch.rewardValue} ${portalMerch.rewardUnit} ${portalMerch.rewardRate}</p>
			    				</c:if>
			    			</c:forEach>
						</div>
					</c:forEach>
			    	</div>
			    </li>
				</c:forEach>
			</ul>
		</div> <!-- end stores page -->

	<div id="trends" data-role="page">
		<div data-role="header">
			<a href="#leftmenupanel" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-bars">
				Menu
			</a>
			<h1>Trends</h1>
		</div><!-- /header -->
		<div role="main" class="ui-content">
			<div data-role="fieldcontain">
				<select name="trendSelector" id="trendSelector" data-native-menu="false">
					<option data-placeholder='true'>Choose a store:</option>
					<c:forEach var="merchant" items="${orderedMerchant}">
						<option value="<c:out value="${merchant.mkey}" />">
							<c:out value="${merchant.name}"/>
						</option>
					</c:forEach>
				</select>
			</div>
			<div id="trendPlot">
			</div>
			<script type="text/javascript" src="static/js/rewardyourselfD3.js"></script>
		</div>
	</div><!-- end trends page -->

	<div id="portals" data-role="page">

		<div data-role="header">
			<!-- <a href="#leftmenupanel" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-bars">
				Menu
			</a> -->
			<h1>Portals</h1>
		</div><!-- /header -->
		<div role="main" class="ui-content">
			<ul id="portalList" data-role="listview" data-filter="false" data-inset="true" data-filter-reveal="false">
			    <li>
			    	<div data-role="collapsible" data-inset="false" data-collapsed-icon="arrow-r" data-expanded-icon="arrow-d">
					<c:forEach var="portal" items="${portalMap}">
			    		<h4>${portal.value.portalName}</h4>
		    			<c:forEach var="portalMerch" items="${portal.value.merchantList}" >
	    					<p>
	    						<a href="<c:url value="${portalMerch.storeLink}"/>">
	    							${portalMerch.name}
	    						</a>
	    					${portalMerch.rewardValue} ${portalMerch.rewardUnit} ${portalMerch.rewardRate}</p>
		    			</c:forEach>
					</c:forEach>
			    	</div>
			    </li>
			</ul>
		</div>
	</div><!-- end portal page -->	
	
</body>
</html>