<%@ page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<!-- $Id: serviceErrorView.jsp,v 1.1 2005/04/01 03:55:24 sbattaglia Exp $ -->
	
		<!-- DOCUMENT TITLE: CHANGE TO NEW TITLE -->
		<title>Service d'Authentification du Conseil G�n�ral du Val d'Oise</title>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	
		<!-- KEYWORDS AND DESCRIPTIONS GO INTO THIS SECTION -->
	
		<meta name="keywords" content="Conseil G�n�ral, Val d'Oise, Central Authentication Service,JA-SIG,CAS" />
	
		<!-- THIS CODE PROVIDES THE FORMATTING FOR THE TEXT - PLEASE LEAVE INTACT -->
		<link rel="stylesheet" href="<spring:theme code="css"/>" type="text/css" media="all" />
		<script src="js/common.js" type="text/javascript"></script>
	</head>


  <body onload="init();">
	<!-- HEADER -->
	<div id="header">
		<h1>Service d'Authentification du Conseil G�n�ral du Val d'Oise</h1>
	</div>
	<!-- END HEADER -->

	<!-- CONTENT -->
	<!-- $Id: serviceErrorView.jsp,v 1.1 2005/04/01 03:55:24 sbattaglia Exp $ -->
	<div id="content">

		<div class="dataset clear" style="position: relative;">
			<h2 style="margin-bottom:0;">Please Log In</h2>

			<p style="margin-top:-.5em;border:1px solid #ccc;background-color:#ffc;color:#000;padding:5px;">
				Le service auquel vous essayez d'acc�der n'est
				pas autoris� � utiliser le service
				d'authentification CAS.<br /><br />
			</p>
		</div>
	</div><!-- END CONTENT -->

	<!-- FOOTER -->
   	<div id="footer">
		<hr />
		<p style="margin-top:1em;">
			Copyright &copy; 2004 JA-SIG.  All rights reserved.
		</p>
	</div>
	<!-- END FOOTER -->
</body>
</html>

