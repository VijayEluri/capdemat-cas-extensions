package fr.cg95.sso.cas;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

import org.jasig.cas.services.*;

import org.apache.log4j.Logger;

/**
 * @author bor@zenexity.fr
 */
public final class CvqServiceRegistry implements ServiceRegistry, 
						 ServiceRegistryManager {

    /** Logging instance. */
    private static Logger log = Logger.getLogger(CvqServiceRegistry.class);

    /** The map containing the services. */
    private final Map services = new HashMap();

    public boolean serviceExists(final String serviceId) {
//         return this.services.containsKey(serviceId);
	if (getService(serviceId) != null)
	    return true;
	else
	    return false;
    }

    public Collection getServices() {
        return this.services.values();
    }

    public void addService(final RegisteredService service) {
        log
            .debug("Adding service [" + service.getId()
                + "] to serviceRegistry");
        this.services.put(service.getId(), service);
    }

    public boolean deleteService(final String serviceId) {
        log.debug("Deleting service[" + serviceId + "] from Service Registry.");
        return this.services.remove(serviceId) != null;
    }

    public RegisteredService getService(final String serviceId) {

	log.debug("Attempting to retrieve service [" + serviceId
		  + "] from Service Registry");

	Set servicesList = this.services.keySet();
	Iterator servicesIt = servicesList.iterator();
	RegisteredService authenticatedService = null;
	while (servicesIt.hasNext()) {
	    String tempServiceId = (String) servicesIt.next();
	    if (serviceId.startsWith(tempServiceId)) {
		authenticatedService = (RegisteredService) this.services.get(tempServiceId);
	    }
	}

        if (authenticatedService != null) {
            log.debug("Successfully retrieved service [" + serviceId
                + "] from Service Registry.");
        }
        return authenticatedService;
    }

    public void clear() {
        log.debug("Clearing all entries from Service Registry");
        this.services.clear();
    }
}
