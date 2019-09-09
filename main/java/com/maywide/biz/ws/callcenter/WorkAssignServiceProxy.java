package com.maywide.biz.ws.callcenter;

public class WorkAssignServiceProxy implements com.maywide.biz.ws.callcenter.WorkAssignService {
  private String _endpoint = null;
  private com.maywide.biz.ws.callcenter.WorkAssignService workAssignService = null;
  
  public WorkAssignServiceProxy() {
    _initWorkAssignServiceProxy();
  }
  
  public WorkAssignServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initWorkAssignServiceProxy();
  }
  
  private void _initWorkAssignServiceProxy() {
    try {
      workAssignService = (new WorkAssignServiceServiceLocator()).getWorkAssignService();
      if (workAssignService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)workAssignService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)workAssignService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (workAssignService != null)
      ((javax.xml.rpc.Stub)workAssignService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.maywide.biz.ws.callcenter.WorkAssignService getWorkAssignService() {
    if (workAssignService == null)
      _initWorkAssignServiceProxy();
    return workAssignService;
  }
  
  public java.lang.String operateOrder(java.lang.String bossRequest) throws java.rmi.RemoteException{
    if (workAssignService == null)
      _initWorkAssignServiceProxy();
    return workAssignService.operateOrder(bossRequest);
  }
  
  
}