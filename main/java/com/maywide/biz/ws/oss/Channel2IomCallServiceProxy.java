package com.maywide.biz.ws.oss;

public class Channel2IomCallServiceProxy implements com.maywide.biz.ws.oss.Channel2IomCallService {
  private String _endpoint = null;
  private com.maywide.biz.ws.oss.Channel2IomCallService channel2IomCallService = null;
  
  public Channel2IomCallServiceProxy() {
    _initChannel2IomCallServiceProxy();
  }
  
  public Channel2IomCallServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initChannel2IomCallServiceProxy();
  }
  
  private void _initChannel2IomCallServiceProxy() {
    try {
      channel2IomCallService = (new com.maywide.biz.ws.oss.Channel2IomCallServiceServiceLocator()).getChannelOrderService();
      if (channel2IomCallService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)channel2IomCallService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)channel2IomCallService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (channel2IomCallService != null)
      ((javax.xml.rpc.Stub)channel2IomCallService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.maywide.biz.ws.oss.Channel2IomCallService getChannel2IomCallService() {
    if (channel2IomCallService == null)
      _initChannel2IomCallServiceProxy();
    return channel2IomCallService;
  }
  
  public java.lang.String queryOrderFromChannel(java.lang.String requestXml) throws java.rmi.RemoteException{
    if (channel2IomCallService == null)
      _initChannel2IomCallServiceProxy();
    return channel2IomCallService.queryOrderFromChannel(requestXml);
  }
  
  
}