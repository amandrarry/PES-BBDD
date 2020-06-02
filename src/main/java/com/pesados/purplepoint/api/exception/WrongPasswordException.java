package com.pesados.purplepoint.api.exception;

public class WrongPasswordException extends Exception {
	  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public WrongPasswordException(String pwd) {
			super("Wrong username or password!");
		}

}
