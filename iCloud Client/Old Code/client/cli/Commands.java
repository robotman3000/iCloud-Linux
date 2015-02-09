package client.cli;

import java.util.Map;

import client.TestClient;
import common.CommonLogic;

public class Commands {

	static TestClient var = new TestClient();

	public enum BaseCommandEnum {

		help {
			public Map<String, String> run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				if (args.containsKey("help")) {
					String helpCmd = args.get("help");
					try {
						BaseCommandEnum command = Commands.BaseCommandEnum.valueOf(helpCmd);
						command.help(args, stateMap);
					} catch (java.lang.IllegalArgumentException e) {
						System.out.println("The command: " + helpCmd + ": is not a valid command");
					}
				} else {
					for (BaseCommandEnum enums : BaseCommandEnum.values()) {
						System.out.println(enums.toString());
					}
				}
				return stateMap;
			}

			public void help(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				System.out.println("Usage: help [-help=(a command)]");
				System.out.println("Provides the list of commands or command usage");
			}
		},

		exit {
			public Map<String, String> run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				if (var.isConnected()) {
					var.closeConnection();
				}
				System.out.println("Exiting");
				return stateMap;
			}

			public void help(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				System.out.println("Usage: exit");
				System.out.println("Exits the terminal forcefully");
			}
		},

		// Notes

		callnotestartup {
			public Map<String, String> run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				var.createNoteManager();
				return stateMap;
			}

			public void help(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				System.out.println("Usage: callnotestartup");
				System.out.println("Creates the Note Manager");
			}
		},

		printnotes {
			public Map<String, String> run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				var.printNotes();
				return stateMap;
			}

			public void help(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				System.out.println("Usage: printnotes");
				System.out.println("Prints the users notes. if the note manager has not " + "\n" + "been created this will do nothing");
			}
		},

		deletenote {
			public Map<String, String> run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				// TODO : add arguments
				var.deleteNote();
				return stateMap;
			}

			public void help(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				System.out.println("Usage: deletenote <-delete=(Note ID)>");
				System.out.println("Deletes a note");

			}
		},

		createnote {
			public Map<String, String> run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				var.createNote();
				return stateMap;
			}

			public void help(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				System.out.println("Usage: createnote");
				System.out.println("Creates a note. When run this will start a note editor" + "\n" + "Note: currenty unimplemented and will do nothing");
			}
		},

		getnotechanges {
			public Map<String, String> run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				var.manualCallNoteChangeset();
				return stateMap;
			}

			public void help(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				System.out.println("Usage: getnotechanges");
				System.out.println("Calls /no/changeset");
			}
		},

		// Account
		connect {
			public Map<String, String> run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				boolean extendedLogin = false;
				if (args.containsKey("extendedlogin")) {
					extendedLogin = CommonLogic.stringToBoolean(args.get("extendedlogin"));
				}
				var.openConnection(args.get("username"), args.get("password"), extendedLogin, CommonLogic.stringToBoolean(stateMap.get("debugenabled")), CommonLogic.stringToBoolean(stateMap.get("announceconnections")));
				return stateMap;
			}

			public void help(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				System.out.println("Usage: connect <-username=(username)> <-password=(password)> [-extendedlogin=(boolean value)]");
				System.out.println("Starts the connection to the server");
			}
		},

		validateprevious {
			public Map<String, String> run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				// var.openConnection(args.get("username"),
				// args.get("password"),
				// CommonLogic.stringToBoolean(args.get("extendedlogin")),
				// CommonLogic.stringToBoolean(stateMap.get("debugenabled")),
				// CommonLogic.stringToBoolean(stateMap.get("announceconnections")));
				System.out.println("Unimplemented!!");
				return stateMap;
			}

			public void help(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				System.out.println("Usage: validateprevious");
				System.out.println("Unimplemented");
			}
		},

		printuserprops {
			public Map<String, String> run(Map<String, String> args, Map<String, String> stateMap) {
				var.printUserProps();
				return stateMap;
			}

			public void help(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				System.out.println("Usage: printuserprops");
				System.out.println("Prints the properties of the user that was collected from the server." + "\n" + "This does nothing if you haven't connected to the server yet");
			}
		},

		disconnect {
			public Map<String, String> run(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				var.closeConnection();
				return stateMap;
			}

			public void help(Map<String, String> args, Map<String, String> stateMap) throws Exception {
				System.out.println("Usage: disconnect");
				System.out.println("Ends the connection to the server");
			}
		};
		// public abstract void run(Map<String, String> args, Map<String,
		// String> stateMap) throws Exception;

		public abstract Map<String, String> run(Map<String, String> args, Map<String, String> stateMap) throws Exception;

		public abstract void help(Map<String, String> args, Map<String, String> stateMap) throws Exception;
	}
}
