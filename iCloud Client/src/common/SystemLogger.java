package common;

public class SystemLogger {

	private LoggingVerboseity systemLogLevel = LoggingVerboseity.NONE;
	private final String seperator = "================================================================================";
	
	public enum LoggingVerboseity{
		
		NONE(){
			@SuppressWarnings("unused")
			int logNumber = 0;
			protected int getLogNumber() {
				return logNumber;
			}
		},
		
		INFO(){
			@SuppressWarnings("unused")
			int logNumber = 1;
			protected int getLogNumber() {
				return logNumber;
			}
		}, 
		
		WARNING(){
			@SuppressWarnings("unused")
			int logNumber = 2;
			protected int getLogNumber() {
				return logNumber;
			}
		}, 
		
		ERROR(){
			@SuppressWarnings("unused")
			int logNumber = 3;
			protected int getLogNumber() {
				return logNumber;
			}
		}, 
		
		DEVELOPER(){
			@SuppressWarnings("unused")
			int logNumber = 4;
			protected int getLogNumber() {
				return logNumber;
			}
		};
		
		//CUSTOM(){
			
		//}
		protected void logMessage(String Message, String Sender) {
			System.out.println("[" + this.toString() + "] " + Sender + ": " + Message);	
		}
		
		protected abstract int getLogNumber();
		
		private int logNumber;
	}
	
	public SystemLogger(){
		
	}
	
	public SystemLogger(LoggingVerboseity logLevel) {
		this.systemLogLevel = logLevel;
	}

	public void log(String logMessage, String sender, LoggingVerboseity logLevel){
		if(systemLogLevel.getLogNumber() <= logLevel.getLogNumber()){
			logLevel.logMessage(logMessage, sender);
		}
	}

	public LoggingVerboseity getSystemLogLevel() {
		return systemLogLevel;
	}

	public SystemLogger setSystemLogLevel(LoggingVerboseity systemLogLevel) {
		this.systemLogLevel = systemLogLevel;
		return this;
	}
	
	public String getSeperator(){
		return seperator;
	}
}
