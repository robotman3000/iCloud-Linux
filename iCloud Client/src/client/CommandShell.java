package client;

import icloud.CommonLogic;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import client.Commands.CommandEnum;

public class CommandShell {

	public static void main(String[] args) throws Exception {
		boolean debugEnabled = true;
		boolean announceConnections = true;
		boolean doExit = false;

		if (args.length != 2) {
			System.out.println("Must provide username and password!");
			System.exit(1);
		}
		String username = args[0];
		String password = args[1];

		CommandShell local = new CommandShell();

		System.out.println("Started iCloud-Linux debug command shell" + "\n"
				+ "Type \"help\" for a list of commands");

		while (doExit != true) {
			String unreadCommand = local.getCommand();
			HashMap<String, String> commandArgs = local.parseCommandArgs(unreadCommand);

			Commands.CommandEnum command = Commands.CommandEnum.valueOf(commandArgs.get("command"));
			commandArgs.remove("command");

			HashMap<String, String> stateMap = new HashMap<String, String>();
			stateMap.put("debugenabled", CommonLogic.booleanToString(debugEnabled));
			stateMap.put("announceconnections", CommonLogic.booleanToString(announceConnections));
			
			command.run(commandArgs, stateMap);
			
			if (command.equals(Commands.CommandEnum.exit)) {
				doExit = true;
			}
		}
	}

	private HashMap<String, String> parseCommandArgs(String unreadCommand) {
		
		HashMap<String, String> commandArgs = new HashMap<String, String>();
		
		// copy pasted
		String[] kvPairs = unreadCommand.split(";");
		unreadCommand.
		for(String kvPair: kvPairs) {
			   String[] kv = kvPair.split("=");
			   String key = kv[0];
			   String value = kv[1];
			   
			   // my own
			   commandArgs.put(key, value);
		}
		
		return commandArgs;
	}

	private String getCommand() {
		Scanner in = new Scanner(System.in);
		System.out.print("> ");
		System.out.flush();
		String commandStr = in.nextLine();
		return commandStr;
	}

}
