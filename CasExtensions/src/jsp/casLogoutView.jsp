<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<!-- $Id: casLogoutView.jsp,v 1.5 2005/06/09 20:43:51 sbattaglia Exp $ -->
	
		<!-- DOCUMENT TITLE: CHANGE TO NEW TITLE -->
		<title>Service d'Authentification de CapWebCT</title>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	
		<!-- KEYWORDS AND DESCRIPTIONS GO INTO THIS SECTION -->
	
		<meta name="keywords" content="Conseil G�n�ral, Val d'Oise, CapWebCT, Central Authentication Service,JA-SIG,CAS" />
	
		<!-- THIS CODE PROVIDES THE FORMATTING FOR THE TEXT - PLEASE LEAVE INTACT -->
		<link rel="stylesheet" href="<spring:theme code="css"/>" type="text/css" media="all" />
	</head>


  <body>
	<!-- HEADER -->
    <img src="images/cvq_bandeau_cg95.jpg" />
	<div id="header">
		<h1>Service d'Authentification de CapWebCT</h1>
	</div>
	<!-- END HEADER -->

	<!-- CONTENT -->
	<!-- $Id: casLogoutView.jsp,v 1.5 2005/06/09 20:43:51 sbattaglia Exp $ -->
	<div id="content">

		<div class="dataset clear" style="position: relative;">
			<h2 style="margin-bottom:0;">D�connexion r�ussie</h2>

			<p style="margin-top:-.5em;border:1px solid #ccc;background-color:#ffc;color:#000;padding:5px;">
              Vous avez �t� d�connect� du service d'authentification de CapWebCT.
              Pour des raisons de s�curit�, quittez aussi votre navigateur Web.
			</p>
			
			<%--
			 Implementation of support for the "url" parameter to logout as recommended in CAS spec section 2.3.1.
			 A service sending a user to CAS for logout can specify this parameter to suggest that we offer
			 the user a particular link out from the logout UI once logout is completed.  We do that here.
			--%>
			<c:if test="${not empty url}">
			<p style="margin-top:-.5em;border:1px solid #ccc;background-color:#ffc;color:#000;padding:5px;">
				The service from which you arrived has supplied a 
				<a href="${url}">link you may follow by clicking here</a>.<br /><br />
			  ({$url})
			</p>
			</c:if>
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

