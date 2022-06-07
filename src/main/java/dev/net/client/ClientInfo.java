package dev.net.client;

public class ClientInfo {
	private String id;
	public ClientInfo() {}
	
	public ClientInfo(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "ClientInfo [id=" + id +"]";
	}
}
