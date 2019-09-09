/**
 * WorkAssignServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.maywide.biz.ws.callcenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.service.PersistentService;

public class WorkAssignServiceServiceLocator extends org.apache.axis.client.Service implements com.maywide.biz.ws.callcenter.WorkAssignServiceService {


	@Autowired
	private PersistentService dao;	
    public WorkAssignServiceServiceLocator() {
    	this.getWorkAssignServiceAddress();
    }

    public WorkAssignServiceServiceLocator(PersistentService dao) {
    	this.getWorkAssignServiceAddress(dao);
    }    


    public WorkAssignServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WorkAssignServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WorkAssignService
    private java.lang.String WorkAssignService_address = "http://10.202.253.56:8080/cc-server/services/WorkAssignService";

    public java.lang.String getWorkAssignServiceAddress() {
        return WorkAssignService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WorkAssignServiceWSDDServiceName = "WorkAssignService";

    public java.lang.String getWorkAssignServiceWSDDServiceName() {
        return WorkAssignServiceWSDDServiceName;
    }

    public void setWorkAssignServiceWSDDServiceName(java.lang.String name) {
        WorkAssignServiceWSDDServiceName = name;
    }

    public java.lang.String getWorkAssignServiceAddress(PersistentService dao) {
    	if(dao != null){
    	List list;
		try {
			list = dao.find("select * from prv_sysparam t where t.gcode = 'WEBSERVICES' and t.mcode = 'CALLCENTER_URL'",PrvSysparam.class);
		    if(list.size()>0 ){
		    	   PrvSysparam sysparam = (PrvSysparam) list.get(0);
		    	   if(sysparam!=null && !"".equals(sysparam.getData())){
		    		   WorkAssignService_address =sysparam.getData() ;
		    	   }
		       }
		} catch (Exception e) {
			
		}

       }
    	return WorkAssignService_address;
    }      
    public com.maywide.biz.ws.callcenter.WorkAssignService getWorkAssignService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WorkAssignService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWorkAssignService(endpoint);
    }

    public com.maywide.biz.ws.callcenter.WorkAssignService getWorkAssignService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.maywide.biz.ws.callcenter.WorkAssignServiceSoapBindingStub _stub = new com.maywide.biz.ws.callcenter.WorkAssignServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getWorkAssignServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWorkAssignServiceEndpointAddress(java.lang.String address) {
        WorkAssignService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.maywide.biz.ws.callcenter.WorkAssignService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.maywide.biz.ws.callcenter.WorkAssignServiceSoapBindingStub _stub = new com.maywide.biz.ws.callcenter.WorkAssignServiceSoapBindingStub(new java.net.URL(WorkAssignService_address), this);
                _stub.setPortName(getWorkAssignServiceWSDDServiceName());
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
        if ("WorkAssignService".equals(inputPortName)) {
            return getWorkAssignService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.server.callcenter.com", "WorkAssignServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.server.callcenter.com", "WorkAssignService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WorkAssignService".equals(portName)) {
            setWorkAssignServiceEndpointAddress(address);
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
