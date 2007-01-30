<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- Minimal Web pages, starting point for Web Designers -->

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Service d'Authentification de CapWebCT</title>
	<meta name="keywords"
		content="Conseil Général, Val d'Oise, Central Authentication Service, JA-SIG, CAS" />
	<meta name="description"
		content="Page de login du service d'authentification de CapWebCT" />
	<meta name="author" content="Benoit Orihuela" />

	<!-- THIS CODE PROVIDES THE FORMATTING FOR THE TEXT - PLEASE LEAVE INTACT -->
	<link rel="stylesheet" href="<spring:theme code="css" />"
		type="text/css" media="all" />
	<script src="js/common.js" type="text/javascript"></script>
  </head>
  <body>
    <img src="images/cvq_bandeau_cg95.jpg" />
	<div id="header">
		<h1>Service d'Authentification de CapWebCT</h1>
	</div>

	<!-- CONTENT -->
	<div id="content">

	  <div class="dataset clear" style="position: relative;">
		<h2 style="margin-bottom:0;">Connexion</h2>

		<p style="margin-top:-.5em;border:1px solid #ccc;background-color:#ffc;color:#000;padding:5px;">
			Pour des raisons de sécurité, n'oubliez pas de vous déconnecter et de
			fermer votre navigateur Web quand vous avez fini d'utiliser les services
			nécessitant une authentification !
		</p>

		<form method="post" action="">

			<!-- Begin error message generating Server-Side tags -->
			<spring:hasBindErrors name="credentials">
				<c:forEach var="error" items="${errors.allErrors}">
					<br />
					<spring:message code="${error.code}" text="${error.defaultMessage}" />
				</c:forEach>
			</spring:hasBindErrors>
			<!-- End error message generating Server-Side tags -->

			<fieldset>
            	<legend><strong>Entrez votre nom d'utilisateur et votre mot de passe</strong></legend>
                <div style="margin-left:25%;">
                	<div>
						<p>Identifiant <input id="username" name="username" size="32" tabindex="1" accesskey="n" /></p>

						<p>Mot de passe <input type="password" id="password" name="password" size="32" tabindex="2" accesskey="p" /></p>

						<p>Collectivité 
							<select name="authority">
								<c:forEach var="localAuthority" items="${localAuthorities}">
									<option value="${localAuthority.key}">${localAuthority.value}</option>
								</c:forEach>
							</select>
						</p>

						<p>
							<input type="checkbox" id="warn" name="warn" value="false" tabindex="3" />
								Me prévenir avant de me connecter à d'autres sites
								<!-- The following hidden field must be part of the submitted Form -->
							<input type="hidden" name="lt" value="${flowExecutionId}" /> 
							<input type="hidden" name="_currentStateId" value="${currentStateId}" /> 
							<input type="hidden" name="_eventId" value="submit" />
						</p>

						<p>
							<input type="submit" class="button" accesskey="l" value="LOGIN" tabindex="4" />
						</p>
					</div>
				</div>
			</fieldset>
		</form>
	  </div>
	</div>
	
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

