package com.maywide.core.socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.maywide.core.util.PubUtil;

public class ClientHandler
{
  protected static Log logger = LogFactory.getLog(ClientHandler.class);
  protected Connection connection;
  public volatile boolean isRunning;
  private static String host;
  private static int port;
  
  public static String getHost()
  {
    return host;
  }
  
  public static void setHost(String host)
  {
    host = host;
  }
  
  public static int getPort()
  {
    return port;
  }
  
  public static void setPort(int port)
  {
    port = port;
  }
  
  public ClientHandler(Connection connection)
  {
    this.connection = connection;
  }
  
  public ClientHandler(String host, int port)
    throws Exception
  {
    this.connection = new TCPIPConnection(host, port);
  }
  
  public Connection getConnection()
  {
    return this.connection;
  }
  
  public void setConnection(Connection connection)
  {
    this.connection = connection;
  }
  
  public void close()
    throws Exception
  {
    this.isRunning = false;
    try
    {
      this.connection.close();
    }
    catch (Exception ex)
    {
      throw ex;
    }
  }
  
  public String send(String cmd)
    throws Exception
  {
    return send(cmd, null);
  }
  
  public String send(String cmd, String separator)
    throws Exception
  {
    String retval = null;
    try
    {
      this.connection.write(cmd);
      byte[] buffer = PubUtil.isEmpty(separator) ? this.connection.read() : 
        this.connection.read(separator);
      if (buffer != null) {
        retval = new String(buffer);
      }
    }
    catch (Exception ex)
    {
      close();
      throw ex;
    }
    return retval;
  }
  
  public byte[] sendBytes(byte[] bytes)
    throws Exception
  {
    byte[] ret = (byte[])null;
    try
    {
      this.connection.write(bytes);
      ret = this.connection.read();
    }
    catch (Exception e)
    {
      close();
      throw e;
    }
    return ret;
  }
  
  public byte[] read()
    throws Exception
  {
    byte[] ret = (byte[])null;
    try
    {
      ret = this.connection.read();
    }
    catch (Exception e)
    {
      close();
      throw e;
    }
    return ret;
  }
}
