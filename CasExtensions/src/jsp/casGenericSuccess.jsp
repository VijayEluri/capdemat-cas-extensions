<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<!-- $Id: casGenericSuccess.jsp,v 1.4 2005/06/06 22:10:18 dmazurek Exp $ -->
	
		<!-- DOCUMENT TITLE: CHANGE TO NEW TITLE -->
		<title>Service d'authentification du Conseil Général du Val d'Oise</title>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	
		<!-- KEYWORDS AND DESCRIPTIONS GO INTO THIS SECTION -->
	
		<meta name="keywords" content="Central Authentication Service,JA-SIG,CAS" />
	
		<!-- THIS CODE PROVIDES THE FORMATTING FOR THE TEXT - PLEASE LEAVE INTACT -->
		<link rel="stylesheet" href="<spring:theme code="css" />" type="text/css" media="all" />
	</head>


  <body>
	<!-- HEADER -->
	<div id="header">
		<a id="top">Java Architecture Special Interest Group</a>
		<h1>Service d'authentification du Conseil Général du Val d'Oise</h1>
	</div>
	<!-- END HEADER -->

	<!-- CONTENT -->
	<!-- $Id: casGenericSuccess.jsp,v 1.4 2005/06/06 22:10:18 dmazurek Exp $ -->
	<div id="content">

		<div class="dataset clear" style="position: relative;">
			<h2 style="margin-bottom:0;">Connexion réussie</h2>

			<p style="margin-top:-.5em;border:1px solid #ccc;background-color:#ffc;color:#000;padding:5px;">
				Vous vous êtes connecté avec succès au service d'authentification du Conseil
				Général du Val d'Oise.<br/><br/>
				Pour des raisons de sécurité, déconnectez-vous et fermez votre navigateur lorsque vous
				voulez terminer votre session !
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

