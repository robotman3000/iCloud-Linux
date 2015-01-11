package client;

import icloud.CommonLogic;

import java.util.Map;

public class Commands{

	static TestClient var = new TestClient();
	public enum CommandEnum {

		help {
			public void run(Map<String, String> args, Map<String, String> stateMap) {
				for(CommandEnum enums : CommandEnum.values()){
					System.out.println(enums.toString());
				}
				
			}
		},
		
		// userPrefs mode
		connect {
			public void run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				var.openConnection(args.get("username"), args.get("password"), CommonLogic.stringToBoolean(args.get("extendedlogin")), CommonLogic.stringToBoolean(stateMap.get("debugenabled")), CommonLogic.stringToBoolean(stateMap.get("announceconnections")));
			}
		},
		
		printuserprops {
			public void run(Map<String, String> args, Map<String, String> stateMap) {
				var.printUserProps();
			}
		},
		
		disconnect {
			public void run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				var.closeConnection();
			}
		},
		
/*		listmodes {
			public void run(Map<String, String> args) {
				System.out.println("You ran listmodes");
			}
		},
		
		setmode {
			public void run(Map<String, String> args) {
				System.out.println("You ran setmode");
			}
		},
		
		getmode {
			public void run(Map<String, String> args) {
				System.out.println("You ran getmode");
			}
		},*/

		exit {
			public void run(Map<String, String> args, Map<String, String> stateMap) {
				System.out.println("Exiting current mode");	
			}
		};

		public abstract void run(Map<String, String> args, Map<String, String> stateMap) throws Exception;
	}
}
