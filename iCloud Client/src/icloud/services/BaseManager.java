package icloud.services;

import java.net.URL;

import common.SystemLogger;
import common.SystemLogger.LoggingVerbosity;

public abstract class BaseManager {

	// TODO: Add Exception throwing; Add Exception handling; Add Javadoc
	// TODO: add a method generate the default headers
	protected boolean isInitialized = false;
	protected SystemLogger logger;
	
	protected BaseManager(SystemLogger logger){
		this();
		setLogger(logger);
	}
	
	protected BaseManager(){
		this.isInitialized = true;
	}
	
	protected SystemLogger getLogger(){
		return logger;
	}
	
	protected void setLogger(SystemLogger logger){
		if (logger != null){
			this.logger = logger;
		} else {
			this.logger = new SystemLogger().setSystemLogLevel(SystemLogger.LoggingVerbosity.ERROR);
		}
	}
	
	protected void setLogLevel(LoggingVerbosity logLevel){
		if (logLevel != null){
			this.logger.setSystemLogLevel(logLevel);
		}
	}
	
	protected LoggingVerbosity getLogLevel(){
		return this.logger.getSystemLogLevel();
	}
}
