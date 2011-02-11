package grutils.network

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Logger;

/**
 * Run ssh commands for a given server/user
 * 
 * <br /> requires at least: compile 'org.apache.ant:ant-jsch:1.8.1', 'com.jcraft:jsch:0.1.42'
 */
class SshCommand {
	String remoteHost
	String remoteUser
	String remoteKey
	
	SshCommand(final String remoteHost, final String remoteUser, final String remoteKey){
		this.remoteHost = remoteHost
		this.remoteUser = remoteUser
		this.remoteKey = remoteKey
		
		setupLoggingForJSch()
	}
	
	String execute(String command){
		def ant = new AntBuilder()
		
		ant.sshexec(host:remoteHost,
				 keyfile:remoteKey,
				 username:remoteUser,
				 outputproperty:"result",
				 trust:true,
				 command:command)
		
		ant.getProject().getProperty("result")
	}

	protected static void setupLoggingForJSch(){
		public final Logger SLF4J_LOGGER = new Logger(){
			
			public boolean isEnabled(int level){
				return true;
			}
			
			public void log(int level, String message){
				getLogger().info(message)
			}
		  };
		
		if(!jschLoggingSetup){
			JSch.setLogger(SLF4J_LOGGER)
			jschLoggingSetup = true
		}
	}	
	
	private static boolean jschLoggingSetup = false
	private static org.slf4j.Logger logger
	
	protected static org.slf4j.Logger getLogger(){
		if(!logger){
			logger = LoggerFactory.getLogger("grutils.network.SshCommand")
		}
		return logger
	}

}
