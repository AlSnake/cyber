package com.github.alsnake.cyber.core;

import org.slf4j.LoggerFactory;

public class Logger {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Logger.class);

	public static org.slf4j.Logger getLogger() {
		return LOGGER;
	}
}
