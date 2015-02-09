package client.cli;

import java.util.HashMap;
import java.util.Scanner;

import common.CommonLogic;

public class CommandShell {

	public static void main(String[] args) throws Exception {

		boolean debugEnabled = false;
		boolean announceConnections = true;
		boolean doExit = false;

		CommandShell local = new CommandShell();

		System.out.println("Started iCloud-Linux debug command shell" + "\n" + "Type \"help\" for a list of commands." + "\n" + "Type \"help -help=(command name)\" for details on a command");

		while (doExit != true) {
			try {
				HashMap<String, String> stateMap = new HashMap<String, String>();
				stateMap.put("debugenabled", CommonLogic.booleanToString(debugEnabled));
				stateMap.put("announceconnections", CommonLogic.booleanToString(announceConnections));

				String unreadCommand = local.getCommand();
				HashMap<String, String> commandArgs = null;

				commandArgs = local.parseCommandArgs(unreadCommand);

				Commands.BaseCommandEnum command;
				try {
					String commandStr = commandArgs.get("command");
					command = Commands.BaseCommandEnum.valueOf(commandStr);
				} catch (IllegalArgumentException | NullPointerException e) {
					System.out.println("icsh: " + commandArgs.get("command") + ":" + " command not found...");
					continue;
				} finally {
					commandArgs.remove("command");
				}

				stateMap = (HashMap<String, String>) command.run(commandArgs, stateMap);

				if (command.equals(Commands.BaseCommandEnum.exit)) {
					doExit = true;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private HashMap<String, String> parseCommandArgs(String UnreadCommand) {

		HashMap<String, String> commandArgs = new HashMap<String, String>();

		if (UnreadCommand != null) {
			String moddedUnreadCommand = "-command=" + UnreadCommand;
			String[] kvPairs = moddedUnreadCommand.split("-");
			for (String kvPair : kvPairs) {
				String[] kv = kvPair.split("=");
				if (kv.length >= 2) {
					String key = kv[0].trim();
					String value = kv[1].trim();
					commandArgs.put(key, value);
				}
			}
		}

		return commandArgs;
	}

	private String getCommand() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.print("> ");
		System.out.flush();
		String commandStr = in.nextLine();
		return commandStr;
	}

}
