<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<title>RewardYour$elf</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="static/css/jquery.mobile-1.4.1.min.css" />
	<link rel="stylesheet" type="text/css" href="static/css/rewardyourself.css" />
	<script type="text/javascript" src="static/js/jquery-min.js"></script>
	<script type="text/javascript" src="static/js/jquery.mobile-1.4.1.min.js"></script>
	<script type="text/javascript" src="static/js/d3.v3.min.js"></script>
	<script type="text/javascript" src="static/js/rewardyourself.js"></script>
</head>
<body>
	<div data-role="panel" id="leftpanel3" data-position="left" data-display="overlay" data-theme="a">
		<a href="#leftpanel3" rel-data="close" class='ui-btn ui-icon-delete ui-btn-icon-right'>Close</a>
        <a href="#trends" class='ui-btn ui-icon-arrow-u-r ui-btn-icon-left'>Trends</a>
        <p>Track reward patterns<br/>Determine the best time to buy</p>
        <a href="#stores" class='ui-btn ui-icon-shop ui-btn-icon-left'>Stores</a>
        <p>Find current offers by store</p>
        <a href="#products" class='ui-btn ui-icon-tag ui-btn-icon-left'>Products</a>
        <p>Search and track product pricing</p>
        <a href="#portals" class='ui-btn ui-icon-home ui-btn-icon-left'>Portals</a>
        <p>Browse or search offers by shopping portal</p>
	</div><!-- /leftpanel3 -->


	<div id="stores" data-role="page">

		<div data-role="header">
			<a href="#leftpanel3" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-bars">Menu</a>
			
			<h1>Stores</h1>

		</div><!-- /header -->
		<div role="main" class="ui-content">
			<ul id="storeList" data-role="listview" data-filter="true" data-filter-placeholder="Search fruits..." data-inset="true" data-filter-reveal="false">
			    <li>
			    	<div data-role="collapsible" data-inset="false" data-collapsed-icon="arrow-r" data-expanded-icon="arrow-d">
			    		<h4>Apple</h4>
			    		<p>What kind of apple?</p>
			    	</div>
			    </li>
			    <li><a href="#">Banana</a></li>
			    <li><a href="#">Cherry</a></li>
			    <li><a href="#">Cranberry</a></li>
			    <li><a href="#">Grape</a></li>
			    <li><a href="#">Orange</a></li>
			</ul>
		</div><!-- /content -->

		<div data-role="footer">
			<h4>Page Footer</h4>
		</div><!-- /footer -->
	</div><!-- /page -->

	<div id="trends" data-role="page">

		<div data-role="header">
			<a href="#leftpanel3" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-bars">Default panel</a>
			<h1>Trends</h1>
		</div><!-- /header -->
		<div role="main" class="ui-content">
				<div data-role="fieldcontain">
				<select name="select-choice-1" id="select-choice-1" data-native-menu="false">
					<option data-placeholder='true'>Choose a store:</option>
					<option value="dick's">Dick's</option>
					<option value="1-800-flowers">1-800-Flowers</option>
				</select>
			</div>
		</div><!-- /content -->

		<div data-role="footer">
			<h4>Page Footer</h4>
		</div><!-- /footer -->
	</div><!-- /page -->

	<div id="products" data-role="page">

		<div data-role="header">
			<a href="#leftpanel3" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-bars">Default panel</a>
			<h1>Products</h1>
		</div><!-- /header -->
		<div role="main" class="ui-content">
		</div><!-- /content -->

		<div data-role="footer">
			<h4>Page Footer</h4>
		</div><!-- /footer -->
	</div><!-- /page -->

	<div id="portals" data-role="page">

		<div data-role="header">
			<a href="#leftpanel3" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-bars">Default panel</a>
			<h1>Portals</h1>
		</div><!-- /header -->
		<div role="main" class="ui-content">
			<ul id="portalList" data-role="listview" data-filter="true" data-filter-placeholder="Search portals..." data-inset="true" data-filter-reveal="false">
			    <li>
			    	<div data-role="collapsible" data-inset="false" data-collapsed-icon="arrow-r" data-expanded-icon="arrow-d">
			    		<h4>Ebates</h4>
			    		<p>What kind of discount?</p>
			    	</div>
			    </li>
			</ul>
		</div><!-- /content -->

		<div data-role="footer">
			<h4>Page Footer</h4>
		</div><!-- /footer -->
	</div><!-- /page -->	
</body>
</html>