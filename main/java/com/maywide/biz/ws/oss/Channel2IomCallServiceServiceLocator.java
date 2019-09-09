/**
 * Channel2IomCallServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.maywide.biz.ws.oss;

import java.util.List;

import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.service.PersistentService;

public class Channel2IomCallServiceServiceLocator extends org.apache.axis.client.Service implements com.maywide.biz.ws.oss.Channel2IomCallServiceService {

    public Channel2IomCallServiceServiceLocator() {
    }

    public Channel2IomCallServiceServiceLocator(PersistentService dao) {
    	this.getChannelOrderServiceAddress(dao);
    }
    
    public Channel2IomCallServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Channel2IomCallServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ChannelOrderService
    private java.lang.String ChannelOrderService_address = "http://10.205.153.1:7001/IOMPROJ/services/ChannelOrderService";

    public java.lang.String getChannelOrderServiceAddress() {
        return ChannelOrderService_address;
    }
    
    public java.lang.String getChannelOrderServiceAddress(PersistentService dao) {
    	if(dao != null){
    	List list;
		try {
			list = dao.find("select * from prv_sysparam t where t.gcode = 'WEBSERVICES' and t.mcode = 'OSSWS_URL'",PrvSysparam.class);
		    if(list.size()>0 ){
		    	   PrvSysparam sysparam = (PrvSysparam) list.get(0);
		    	   if(sysparam!=null && !"".equals(sysparam.getData())){
		    		   ChannelOrderService_address =sysparam.getData() ;
		    	   }
		       }
		} catch (Exception e) {
			
		}

       } 
        return ChannelOrderService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ChannelOrderServiceWSDDServiceName = "ChannelOrderService";

    public java.lang.String getChannelOrderServiceWSDDServiceName() {
        return ChannelOrderServiceWSDDServiceName;
    }

    public void setChannelOrderServiceWSDDServiceName(java.lang.String name) {
        ChannelOrderServiceWSDDServiceName = name;
    }

    public com.maywide.biz.ws.oss.Channel2IomCallService getChannelOrderService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ChannelOrderService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getChannelOrderService(endpoint);
    }

    public com.maywide.biz.ws.oss.Channel2IomCallService getChannelOrderService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.maywide.biz.ws.oss.ChannelOrderServiceSoapBindingStub _stub = new com.maywide.biz.ws.oss.ChannelOrderServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getChannelOrderServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setChannelOrderServiceEndpointAddress(java.lang.String address) {
        ChannelOrderService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.maywide.biz.ws.oss.Channel2IomCallService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.maywide.biz.ws.oss.ChannelOrderServiceSoapBindingStub _stub = new com.maywide.biz.ws.oss.ChannelOrderServiceSoapBindingStub(new java.net.URL(ChannelOrderService_address), this);
                _stub.setPortName(getChannelOrderServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ChannelOrderService".equals(inputPortName)) {
            return getChannelOrderService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.205.153.1:7001/IOMPROJ/services/ChannelOrderService", "Channel2IomCallServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://10.205.153.1:7001/IOMPROJ/services/ChannelOrderService", "ChannelOrderService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ChannelOrderService".equals(portName)) {
            setChannelOrderServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
