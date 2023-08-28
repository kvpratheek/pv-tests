package com.sap.pvs.saasprovider.demo.util;

import java.util.LinkedHashMap;

import com.sap.xs.env.Credentials;
import com.sap.xs.env.Service;
import com.sap.xs.env.VcapServices;

public class VcapServiceReader {

    private VcapServices vcapServices;

    public VcapServiceReader() {
        if (System.getenv().containsKey(VcapServices.VCAP_SERVICES)) {
            vcapServices = VcapServices.fromEnvironment();
        } else {
            vcapServices = VcapServices.fromSystemProperty();
        }
    }

    public VcapServices getVcapServices() {
        return vcapServices;
    }

    public Credentials getCredentials(final String serviceName) {
        return vcapServices.getCredentials(serviceName);
    }

    /**
     * Given the service label the method returns the first service that matches. If no service matches, returns null.
     *
     * @param serviceLabel
     * @param attribute
     *            the credentials attribute.
     * @return
     */
    public String getAttribute(final String serviceLabel, final String attribute) {
        Service service = vcapServices.findService(serviceLabel, null, null);
        if (service != null) {
            Credentials credentials = service.getCredentials();
            return (String) credentials.get(attribute);
        }
        return null;
    }

    public String getUAAttribute(String serviceLabel, final String attribute) {
        Service service = vcapServices.findService(serviceLabel, null, null);
        if (service != null) {
            Credentials credentials = service.getCredentials();
            LinkedHashMap hashMap = (LinkedHashMap) credentials.get("uaa");
            return (String) hashMap.get(attribute);
        }
        return null;
    }

}
