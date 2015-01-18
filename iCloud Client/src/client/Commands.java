package client;

import icloud.CommonLogic;

import java.util.Map;

public class Commands{

	static TestClient var = new TestClient();
	public enum BaseCommandEnum {

		invalid {
			public void run(Map<String, String> args, Map<String, String> stateMap) {
			}
		},
		
		// userPrefs mode
		connect {
			public void run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				var.openConnection(args.get("username"), args.get("password"), CommonLogic.stringToBoolean(args.get("extendedlogin")), CommonLogic.stringToBoolean(stateMap.get("debugenabled")), CommonLogic.stringToBoolean(stateMap.get("announceconnections")));
			}
		},
		
		validateprevious {
			public void run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				//var.openConnection(args.get("username"), args.get("password"), CommonLogic.stringToBoolean(args.get("extendedlogin")), CommonLogic.stringToBoolean(stateMap.get("debugenabled")), CommonLogic.stringToBoolean(stateMap.get("announceconnections")));
				System.out.println("Unimplemented!!");
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
		
		callnotestartup {
			public void run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				var.createNoteManager();
			}
		},
		
		printnotes {
			public void run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				var.printNotes();
			}
		},
		
		deletenote {
			public void run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				var.deleteNote();
			}
		},
		
		createnote {
			public void run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				var.createNote();
			}
		},
		
		getnotechanges {
			public void run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				var.manualCallNoteChangeset();
			}
		},
		
		help {
			public void run(Map<String, String> args, Map<String, String> stateMap) {
				for(BaseCommandEnum enums : BaseCommandEnum.values()){
					System.out.println(enums.toString());
				}
			}
		},
		
		exit {
			public void run(Map<String, String> args, Map<String, String> stateMap) {
				System.out.println("Exiting current mode");	
			}
		};

		public abstract void run(Map<String, String> args, Map<String, String> stateMap) throws Exception;
	}

	public enum NoteCommandEnum {
		
		
	}
	
	public enum UserCommandEnum {
		
		
	}
}
