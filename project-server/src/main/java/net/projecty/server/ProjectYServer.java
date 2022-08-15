package net.projecty.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProjectYServer {

	private static final Logger logger = LogManager.getLogger(ProjectYServer.class);
	public static void main(String[] args) {
		new ProjectYServer();
	}

	private ProjectYServer() {
		logger.debug("ProjectYServer - START");
	}
}