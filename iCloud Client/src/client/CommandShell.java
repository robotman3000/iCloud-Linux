package client;

import icloud.CommonLogic;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import client.Commands.BaseCommandEnum;

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
			try {
			HashMap<String, String> stateMap = new HashMap<String, String>();
			stateMap.put("debugenabled", CommonLogic.booleanToString(debugEnabled));
			stateMap.put("announceconnections", CommonLogic.booleanToString(announceConnections));

			String unreadCommand = local.getCommand();
			HashMap<String, String> commandArgs = null;
			try {
				commandArgs = local.parseCommandArgs(unreadCommand);
			} finally {

			}

			Commands.BaseCommandEnum command = Commands.BaseCommandEnum.invalid;
			try {
				String commandStr = commandArgs.get("command");
				command = Commands.BaseCommandEnum.valueOf(commandStr);
			} catch (IllegalArgumentException | NullPointerException e) {
				System.out.println("icsh: " + commandArgs.get("command") + ":" + " command not found...");
				continue;
			} finally {
				commandArgs.remove("command");
			}

			command.run(commandArgs, stateMap);

			if (command.equals(Commands.BaseCommandEnum.exit)) {
				doExit = true;
			}
			
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	private HashMap<String, String> parseCommandArgs(String unreadCommand) {

		HashMap<String, String> commandArgs = new HashMap<String, String>();

		if (unreadCommand != null) {
			// copy pasted
			String[] kvPairs = unreadCommand.split(";");
			for (String kvPair : kvPairs) {
				String[] kv = kvPair.split("=");
				if (kv.length >= 2) {
					String key = kv[0].trim();
					String value = kv[1].trim();

					// my own
					commandArgs.put(key, value);
				}
			}
		}

		return commandArgs;
	}

	private String getCommand() {
		Scanner in = new Scanner(System.in);
		System.out.print("> ");
		System.out.flush();
		String commandStr = in.nextLine();
		//switch (commandStr) {
/*		case "help":
			Commands.BaseCommandEnum.help.run(commandArgs, stateMap);
			break;

		case "exit":

			break;

		default:
			break;
		}*/
		return commandStr;
	}

}
