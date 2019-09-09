package com.maywide.core.socket;

import java.io.IOException;

public abstract interface Connection
{
  public abstract void open()
    throws IOException;
  
  public abstract void close()
    throws IOException;
  
  public abstract byte[] read()
    throws IOException, InterruptedException;
  
  public abstract byte[] read(String paramString)
    throws IOException, InterruptedException;
  
  public abstract void write(byte[] paramArrayOfByte)
    throws IOException;
  
  public abstract void write(String paramString)
    throws IOException;
}
