package com.pesados.purplepoint.api.exception;

public class DeviceNotFoundException extends RuntimeException{
	/**
	 *
	 */
       private static final long serialVersionUID = 1L;

        public DeviceNotFoundException(Long id) {
            super("Could not find device with id: " + id);
        }

		public DeviceNotFoundException(String firebaseToken) {
			super("Could not find device with token: " + firebaseToken);		
		}
}
