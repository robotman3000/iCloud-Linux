package common;

public class SystemLogger {

	private LoggingVerbosity systemLogLevel = LoggingVerbosity.NONE;
	private final String seperator = "================================================================================";
	//TODO: make these use their ordinal
	public enum LoggingVerbosity{
		
		NONE(), 
		INFO(), 
		WARNING(), 
		ERROR(), 
		DEVELOPER(),
		EXTREME();
		
		protected Void logError(String Message, String Sender){
			System.err.println("[" + this.toString() + "] " + Sender + ": " + Message);
			return null;
		}
		
		protected Void logMessage(String Message, String Sender) {
			System.out.println("[" + this.toString() + "] " + Sender + ": " + Message);
			return null;	
		}
	}
	
	public SystemLogger(){
		
	}
	
	public SystemLogger(LoggingVerbosity logLevel) {
		this.systemLogLevel = logLevel;
	}

	public void log(String logMessage, String sender, LoggingVerbosity logLevel){
		boolean logErr = false;
		if (logLevel == LoggingVerbosity.ERROR){
			logErr = true;
		}
		if(systemLogLevel.ordinal() >= logLevel.ordinal()){
			@SuppressWarnings("unused")
			Void nullVar = (logErr ? logLevel.logError(logMessage, sender) : logLevel.logMessage(logMessage, sender));
		}
	}

	public LoggingVerbosity getSystemLogLevel() {
		return systemLogLevel;
	}

	public SystemLogger setSystemLogLevel(LoggingVerbosity systemLogLevel) {
		this.systemLogLevel = systemLogLevel;
		return this;
	}
	
	public String getSeperator(){
		return seperator;
	}
}
