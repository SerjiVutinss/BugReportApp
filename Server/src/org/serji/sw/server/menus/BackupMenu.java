package org.serji.sw.server.menus;

import org.serji.sw.server.client.RequestHandler;
import org.serji.sw.server.data.DataBackup;

/**
 * Class containing menu logic for saving and reading of data from the backup
 * files. A RequestHandler must be passed as an argument to each method.
 * 
 * @param handler the RequestHanlder object to run this method with, will be
 *                different for each thread calling this method
 * 
 * @author Justin
 *
 */
public abstract class BackupMenu {

	/**
	 * 
	 * @param handler
	 */
	public static void saveBackupData(RequestHandler handler) {

		StringBuilder sb;
		sb = new StringBuilder();
		sb.append("\nAre you sure you want to back up in-memory data to file? ");
		sb.append("\nEnter 'Y' or 'y' to continue or anything else to cancel: ");
		handler.sendMessage(sb);
		String response = handler.getMessage();

		if (response.toLowerCase().equals("y")) {
			handler.sendMessage("Backing up data: " + response);
			DataBackup.writeData();
			handler.sendMessage("Backup Complete: " + response);

		} else {
			return;
		}
	}

	public static void readBackupData(RequestHandler handler) {

		StringBuilder sb;
		sb = new StringBuilder();
		sb.append("\nAre you sure you want to load data from file? ");
		sb.append("\nEnter 'Y' or 'y' to continue or anything else to cancel: ");
		handler.sendMessage(sb);
		String response = handler.getMessage();

		if (response.toLowerCase().equals("y")) {
			handler.sendMessage("Loading data: " + response);
			DataBackup.readData();
			handler.sendMessage("Load Complete: " + response);

		} else {
			return;
		}
	}
}
