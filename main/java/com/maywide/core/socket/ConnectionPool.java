package com.maywide.core.socket;



public class ConnectionPool {

	private static ConnectionPool instance = null;

	private ConnectionPool() {
	}

	public static ConnectionPool getInstance() {
		if (instance == null)
			instance = new ConnectionPool();
		return instance;
	}

	public ClientHandler getConnection(String _ip, int _port)
			throws Exception {
		ClientHandler socket = null;

		try {
			socket = new ClientHandler(_ip, _port);
			return socket;
		} catch (Exception e) {
			throw new Exception("socket连接出错！" + e.getMessage(), e);
		}
	}

	public void release(ClientHandler socket) throws Exception {
		// handler
		if (socket != null) {
			try {
				socket.close();
			} catch (Exception e) {
				throw new Exception("关闭socket连接错误：" + e.getMessage(), e);
			}
		}
	}

	public void handleException(ClientHandler socket) throws Exception {

	}

}
