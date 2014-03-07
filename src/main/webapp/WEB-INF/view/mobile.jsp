<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>RewardYour$elf</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css"
	href="static/css/jquery.mobile-1.4.2.min.css" /> <!--  -->
<link rel="stylesheet" type="text/css"
	href="static/css/rewardyourself.css" />
<link rel="stylesheet" type="text/css"
	href="static/css/rewardyourselfD3.css" />
<script type="text/javascript"
	src="static/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="static/js/jquery.mobile-1.4.2.min.js"></script>
<script type="text/javascript" src="static/js/d3.v3.min.js"></script>
<script type="text/javascript" src="static/js/rewardyourself.js"></script>
<script type="text/javascript" src="static/js/rewardyourselfD3.js"></script>
</head>
<body>

	<div id="stores" data-role="page">
		<div data-role="header">
			<a href="#leftmenu-stores" data-role="button" data-inline="true"
				data-icon="bars">
				Menu 
			</a>
			<h1>Stores</h1>
		</div>
		<div role="main" class="ui-content">
			<ul id="storeList" data-role="listview" data-filter="true"
				data-filter-placeholder="Search stores..." data-inset="true"
				data-filter-reveal="false">
				<c:forEach var="merchant" items="${orderedMerchant}">
					<li>
					 <div data-role="collapsible" data-inset="false" data-collapsed-icon="arrow-r" data-expanded-icon="arrow-d" 
					 class="rwys-store" mkey="<c:out value="${merchant.mkey}"/>">
					 	<h4><c:out value="${merchant.name}"/></h4>
					 </div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div data-role="footer">
		</div><!-- /footer -->		
		<div data-role="panel" id="leftmenu-stores">
			<div class="panel-content">
				<a href="#leftmenu-stores" rel-data="close" class='ui-btn ui-icon-delete ui-btn-icon-right'>Close</a>
			    <a href="#trends" class='ui-btn ui-icon-arrow-u-r ui-btn-icon-left'>Trends</a>
			    <p>Track reward patterns<br/>Determine the best time to buy</p>
			    <!-- <a href="#stores" class='ui-btn ui-icon-shop ui-btn-icon-left'>Stores</a>
			    <p>Find current offers by store</p> -->
			    <!-- <a href="#products" class='ui-btn ui-icon-tag ui-btn-icon-left'>Products</a>
			    <p>Search and track product pricing</p> -->
			    <a href="#portals" class='ui-btn ui-icon-home ui-btn-icon-left'>Portals</a>
			    <p>Browse or search offers by shopping portal</p>
			</div>
		</div>
	</div><!-- end stores page -->


	<div id="trends" data-role="page">
		<div data-role="header">
			<a href="#leftmenu-trends" data-role="button" data-inline="true"
				data-icon="bars">
				Menu 
			</a>
			<h1>Trends</h1>
		</div>
		<div role="main" class="ui-content">
			<div data-role="fieldcontain">
				<select name="trendSelector" id="trendSelector"
					data-native-menu="false">
					<option data-placeholder='true'>Choose a store:</option>
					<c:forEach var="merchant" items="${orderedMerchant}">
						<option value="<c:out value="${merchant.mkey}" />">
						   <c:out value="${merchant.name}"/>
						</option>
					</c:forEach>
				</select>
			</div>
			<div id="trendPlot"></div>
		</div>
		<div data-role="footer">
		</div><!-- /footer -->		
		<div data-role="panel" id="leftmenu-trends">
			<div class="panel-content">
				<a href="#leftmenu-trends" rel-data="close" class='ui-btn ui-icon-delete ui-btn-icon-right'>Close</a>
			    <!-- <a href="#trends" class='ui-btn ui-icon-arrow-u-r ui-btn-icon-left'>Trends</a>
			    <p>Track reward patterns<br/>Determine the best time to buy</p> -->
			    <a href="#stores" class='ui-btn ui-icon-shop ui-btn-icon-left'>Stores</a>
			    <p>Find current offers by store</p>
			    <!-- <a href="#products" class='ui-btn ui-icon-tag ui-btn-icon-left'>Products</a>
			    <p>Search and track product pricing</p> -->
			    <a href="#portals" class='ui-btn ui-icon-home ui-btn-icon-left'>Portals</a>
			    <p>Browse or search offers by shopping portal</p>
			</div>
		</div>
	</div>
	<!-- end trends page -->

	<div id="portals" data-role="page">
		<div data-role="header">
			<a href="#leftmenu-portals" data-role="button" data-inline="true"
				data-icon="bars">
				Menu 
			</a>
			<h1>Portals</h1>
		</div>
		<!-- /header -->
		<div role="main" class="ui-content">
			<ul id="portalList" data-role="listview" data-filter="false"
				data-inset="true" data-filter-reveal="false">
			<c:forEach var="portal" items="${portalList}">
				<li>
					<div data-role="collapsible" data-inset="false" data-iconpos="right"
							data-collapsed-icon="arrow-r" data-expanded-icon="arrow-d"
							data-heading=".portal-heading" pkey="<c:out value="${portal.portalKey}"/>">
						<div class="portal-heading">
							<img src=<c:out value="static/img/${portal.portalKey}.png"/> />
							<h1><c:out value="${portal.portalName}"/></h1>
						</div>
						<div class="rwys-portal">
						</div>
				 	</div>
				</li>
			</c:forEach> 
			</ul>
		</div>	
		<div data-role="footer">
		</div><!-- /footer -->		
		<div data-role="panel" id="leftmenu-portals">
			<div class="panel-content">
				<a href="#leftmenu-portals" rel-data="close" class='ui-btn ui-icon-delete ui-btn-icon-right'>Close</a>
			    <a href="#trends" class='ui-btn ui-icon-arrow-u-r ui-btn-icon-left'>Trends</a>
			    <p>Track reward patterns<br/>Determine the best time to buy</p>
			    <a href="#stores" class='ui-btn ui-icon-shop ui-btn-icon-left'>Stores</a>
			    <p>Find current offers by store</p>
			    <!-- <a href="#products" class='ui-btn ui-icon-tag ui-btn-icon-left'>Products</a>
			    <p>Search and track product pricing</p> -->
			    <!-- <a href="#portals" class='ui-btn ui-icon-home ui-btn-icon-left'>Portals</a>
			    <p>Browse or search offers by shopping portal</p>-->
			</div>
		</div>
	</div> <!-- end portal page -->
</body>
</html>