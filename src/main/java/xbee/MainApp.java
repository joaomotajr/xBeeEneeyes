package xbee;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Copyright 2017, Digi International Inc.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES 
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR 
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES 
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN 
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF 
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;

import service.SendRest;

/**
 * XBee Java Library Receive Data sample application.
 * 
 * <p>This example registers a listener to manage the received data.</p>
 * 
 * <p>For a complete description on the example, refer to the 'ReadMe.txt' file
 * included in the root directory.</p>
 * E-GAS - API :: 177.144.134.145:8090
 */
public class MainApp {
	
	/* Constants */
	 
	// TODO Replace with the baud rate of you receiver module.
	private static final int BAUD_RATE = 9600;
	
	public static final Logger logger = LogManager.getLogger(MainApp.class);
	
	/**
	 * Application main method.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		BasicConfigurator.configure();
		 
		System.out.println(" +-----------------------------------------+");
		System.out.println(" |  XBee ENEEYES => To EGas - Direct       |");
		System.out.println(" +-----------------------------------------+\n");
		
		if (args.length < 2 || args[0].isEmpty() ) {
			throw new IllegalArgumentException("Você deve especificar: IP:Porta do Broker e Porta COM");
		}		
		
		logger.info("Initialing Integrator XBee -> E-Gas");
		String host = args[0];
		String comPort = args[1];
		
		logger.info("E-Gas API IP :: " + host + "|| Virtual Usb Port :: " + comPort);
		
		XBeeDevice myDevice = new XBeeDevice(comPort, BAUD_RATE);
		
		try {
			
			SendRest sendRest = new SendRest();

			if (sendRest.TestRest(host)) {			
				myDevice.open();
				
				MyDataReceiveListener dataReceiveListener = new MyDataReceiveListener();
				dataReceiveListener.setHost(host);
				myDevice.addDataListener(dataReceiveListener);
				
				System.out.println("\n>> Waiting for data...");
			}
			
		} catch (XBeeException e) {
			
			logger.error("Sorry, something wrong!", e);
			System.exit(1);
		}
	}
}
