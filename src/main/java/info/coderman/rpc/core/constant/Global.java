package info.coderman.rpc.core.constant;

public interface Global {
	 String ZK_REGISTRY_PATH="/coderman-rpc";
	 	 
	 int ZK_SESSION_TIMEOUT=5000;
	 
	 int ZK_CONNECTION_TIMEOUT=5000;
	 
	 int PROCESSORS=Runtime.getRuntime().availableProcessors();
}
