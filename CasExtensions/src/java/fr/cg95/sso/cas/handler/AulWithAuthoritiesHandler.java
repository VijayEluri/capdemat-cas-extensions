package fr.cg95.sso.cas.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.AuthenticationHandler;
import org.jasig.cas.authentication.principal.Credentials;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import fr.cg95.sso.cas.principal.UsernamePasswordAuthorityCredentials;

public class AulWithAuthoritiesHandler implements AuthenticationHandler {

    private static Logger log = 
        Logger.getLogger(AulWithAuthoritiesHandler.class);

    private static final Class SUPPORTED_CLASS = UsernamePasswordAuthorityCredentials.class;

    // La branche qui contiens les users.
    private String userProvider="ou=people";
    
    // Branche qui contiens les groups
    private String groupProvider="ou=groups";
    
    // Admin DN
    private String adminDn = "cn=admin,dc=localdomain";
    
    // Admin password
    private String adminPassword="aqw";
    
    // @ du serveur ldap
    private String ldapUrl = "ldap://localhost:389/";

    private String ditRoot = "dc=localdomain";

    private boolean authenticateUser(String cn, String uid ,String password, String localAuthority) {
        Hashtable env = new Hashtable();
        String userDn = cn + "," + userProvider + ",dc=" + localAuthority + "," + ditRoot;
        env.put(Context.SECURITY_PRINCIPAL, userDn);
        env.put(Context.SECURITY_CREDENTIALS,password);
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.PROVIDER_URL, ldapUrl);
        
        InitialContext context=null;
        try {
            context = new InitialContext(env);
            // if context successfully initiated, that's ok
            return true;
        } catch (NamingException e) {
            log.error("Authentication failed for user "+cn+" : ",e);
        } finally {
            if (context!=null)
                try {
                    context.close();
                } catch (NamingException e) {
                    log.error("Erreur de fermeture de la conneciton LDAP",e);
                }
        }
        return false;
    }
    
    private void fetchGroups (String cn, String uid, String password, String localAuthority,
            Map properties) {
        Hashtable env = new Hashtable();
        env.put(Context.SECURITY_PRINCIPAL,cn);
        env.put(Context.SECURITY_CREDENTIALS,password);
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.PROVIDER_URL, ldapUrl);
        
        InitialContext context=null;
        try {
            context = new InitialContext(env);
            String groupBaseBranch = groupProvider + ",dc=" + localAuthority + "," + ditRoot;
            DirContext ctx = (DirContext) context.lookup(groupBaseBranch);
            SearchControls searchControl = new SearchControls();
            NamingEnumeration namingEnumeration = ctx.search("","(memberUid="+uid+")",searchControl);
            while (namingEnumeration.hasMore()) {
                SearchResult result = (SearchResult) namingEnumeration.next();
                String group = result.getAttributes().get("cn").get().toString();
                if (properties.get("group") == null)
                    properties.put("group",new ArrayList());
                ((List) properties.get("group")).add(group);
            } 
        } catch (NamingException e) {
            log.error("Erreur de récupération des groupes de l'utilisateur "+cn+" : ",e);
        } finally {
            if (context!=null)
                try {
                    context.close();
                } catch (NamingException e) {
                    log.error("Erreur de fermeture de la conneciton LDAP",e);
                }
        }
    }
    
    private String fetchDn(String uid, String localAuthority) {
        Hashtable env = new Hashtable();
        env.put(Context.SECURITY_PRINCIPAL,adminDn);
        env.put(Context.SECURITY_CREDENTIALS,adminPassword);
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.PROVIDER_URL, ldapUrl);
        
        InitialContext context = null;
        try {
            context = new InitialContext(env);
            String userBaseBranch = userProvider + ",dc=" + localAuthority + "," + ditRoot;
            DirContext ctx = (DirContext) context.lookup(userBaseBranch);
            SearchControls searchControl = new SearchControls();
            searchControl.setSearchScope(SearchControls.SUBTREE_SCOPE); // Recursif.
            NamingEnumeration namingEnumeration = ctx.search("","(uid="+uid+")",searchControl);
            if (namingEnumeration.hasMore()) {
                SearchResult result = (SearchResult) namingEnumeration.next();
                // FIXME : maybe a problem here
                return (result.getName());

            } else {
                log.error("l'utilisateur uid "+uid+" n'existe pas");
            }
        } catch (NamingException e) {
            log.error("Erreur lors de la requete service. Vérifiez les propriétés adminDn et adminPassword",e);
        } finally {
            if (context!=null)
                try {
                    context.close();
                } catch (NamingException e) {
                    log.error("Erreur de fermeture de la connexion LDAP",e);
                }
        }
        return null;
    }
    
    public boolean authenticate(Credentials credentials) throws AuthenticationException {
        UsernamePasswordAuthorityCredentials myCredentials =
            (UsernamePasswordAuthorityCredentials) credentials;
        String dn = fetchDn(myCredentials.getUsername(), myCredentials.getAuthority());
        if (dn == null)
            return false;
        Map userProperties = new HashMap();
        if (!authenticateUser(dn, myCredentials.getUsername(), myCredentials.getPassword(),
                myCredentials.getAuthority()))
            return false;
        fetchGroups(dn, myCredentials.getUsername(), myCredentials.getPassword(),
                myCredentials.getAuthority(), userProperties);
        String newCredentials = "username=" + myCredentials.getUsername() 
            + ";localAuthority=" + myCredentials.getAuthority();
        Iterator userPropertiesIt = userProperties.keySet().iterator();
        while (userPropertiesIt.hasNext()) {
            String key = (String) userPropertiesIt.next();
            List values = (List) userProperties.get(key);
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    newCredentials += ";" + key + "=" + ((String) values.get(i));
                }
            }
        }
        myCredentials.setUsername(newCredentials);
        return true;
    }

    public boolean supports(Credentials credentials) {

        return credentials != null
            && (SUPPORTED_CLASS.equals(credentials.getClass())
                    || (SUPPORTED_CLASS.isAssignableFrom(credentials.getClass())));
    }

    public Map getAllLocalAuthorities() throws Exception {

        Map resultMap = new HashMap();

        Hashtable env = new Hashtable();
        env.put(Context.SECURITY_PRINCIPAL,adminDn);
        env.put(Context.SECURITY_CREDENTIALS,adminPassword);
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.PROVIDER_URL, ldapUrl);
        
        InitialContext context = null;
        try {
            context = new InitialContext(env);
            DirContext ctx = (DirContext) context.lookup(ditRoot);
            SearchControls searchControl = new SearchControls();
            searchControl.setSearchScope(SearchControls.ONELEVEL_SCOPE); // Recursif.
            NamingEnumeration localAuthoritiesEnumeration = 
                ctx.search("", "(objectClass=organization)", searchControl);

            while (localAuthoritiesEnumeration.hasMore()) {
                SearchResult result = (SearchResult) localAuthoritiesEnumeration.next();
                resultMap.put((String) result.getAttributes().get("dc").get(),
                        (String) result.getAttributes().get("o").get());
                log.info("getAllLocalAuthorities() Got object name : " + result.getName());
            }
        } catch (NamingException ne) {
            ne.printStackTrace();
            throw new Exception("Error while retrieving local authorities");
        }

        return resultMap;
    }

    public void setAdminDn(String adminDn) {
        this.adminDn = adminDn;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public void setGroupProvider(String groupProvider) {
        this.groupProvider = groupProvider;
    }

    public void setLdapUrl(String ldapUrl) {
        this.ldapUrl = ldapUrl;
    }

    public void setUserProvider(String userProvider) {
        this.userProvider = userProvider;
    }

    public void setDitRoot(String ditRoot) {
        this.ditRoot = ditRoot;
    }
}
