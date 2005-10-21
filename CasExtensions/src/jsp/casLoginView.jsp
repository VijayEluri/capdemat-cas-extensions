<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<!-- $Id: casLoginView.jsp,v 1.10 2005/05/24 18:57:18 sbattaglia Exp $ -->
	
		<!-- DOCUMENT TITLE: CHANGE TO NEW TITLE -->
		<title>Service d'Authentification du Conseil Général du Val d'Oise</title>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	
		<!-- KEYWORDS AND DESCRIPTIONS GO INTO THIS SECTION -->
	
		<meta name="keywords" content="Conseil Général, Val d'Oise, Central Authentication Service, JA-SIG, CAS" />
		<meta name="description" content="Page de login au service d'authentification du Conseil Général du Val d'Oise" />
		<meta name="author" content="Bart Grebowiec, Scott Battaglia" />
		
	
		<!-- THIS CODE PROVIDES THE FORMATTING FOR THE TEXT - PLEASE LEAVE INTACT -->
		<link rel="stylesheet" href="<spring:theme code="css" />" type="text/css" media="all" />
		<script src="js/common.js" type="text/javascript"></script>
	</head>


  <body onload="init();">
	<!-- HEADER -->
	<div id="header">
		<h1>Service d'Authentification du Conseil Général du Val d'Oise</h1>
	</div>
	<!-- END HEADER -->

	<!-- CONTENT -->
	<div id="content">

		<div class="dataset clear" style="position: relative;">
			<h2 style="margin-bottom:0;">Connexion</h2>

			<p style="margin-top:-.5em;border:1px solid #ccc;background-color:#ffc;color:#000;padding:5px;">
                          Pour des raisons de sécurité, n'oubliez pas de vous
			  déconnecter et de fermer votre navigateur Web quand
			  vous avez fini d'utiliser les services nécessitant une
			  authentification !
			</p>

			<form method="post" action="">
				<spring:hasBindErrors name="credentials">
				  <c:forEach var="error" items="${errors.allErrors}">
				      <br /><spring:message code="${error.code}" />
				  </c:forEach>
				</spring:hasBindErrors>
				<fieldset>
					<legend><strong>Entrez votre nom d'utilisateur et votre mot de passe</strong></legend>
					<div style="margin-left:25%;">
						<div>

							<p>
								<label for="username"><span class="accesskey">N</span>om d'utilisateur :</label><br />
								<input class="required" id="username" name="username" size="32" tabindex="1" accesskey="n" />
							</p>

							<p>
								<label for="password"><span class="accesskey">M</span>ot de passe :</label><br />

								<input class="required" type="password" id="password" name="password" size="32" tabindex="2" accesskey="p" />
							</p>

							<p><input style="width:1.5em;border:0;padding:0;margin:0;" type="checkbox" id="warn" name="warn" value="true" tabindex="3" /> 
							   <label for="warn" accesskey="w"><span class="accesskey">M</span>e prévenir avant de me connecter à d'autres sites.</label></p>

							<input type="hidden" name="lt" value="${flowExecutionId}" />
  							<input type="hidden" name="_currentStateId" value="${currentStateId}" />
							<input type="hidden" name="_eventId" value="submit" />

							<p><input type="submit" class="button" accesskey="l" value="LOGIN" tabindex="4" /></p>
						</div>

					</div>
				</fieldset>
			</form>
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

