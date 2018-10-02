package xbee;

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
						
		logger.info("Initialing Integrator XBee -> E-Gas (API)");
		
		System.out.println(" +-----------------------------------------+");
		System.out.println(" |  XBee ENEEYES => To EGas - Direct       |");
		System.out.println(" +-----------------------------------------+\n");
		
		if (args.length != 3) {
			logger.error("Você deve especificar: Integração[none, mqtt],  IP:Porta do Broker e Porta COM do xBee");			
			System.exit(1);
		}		
		
		logger.info("Checking Parameters...");
		String sinc = args[0].toLowerCase();
		String host = args[1];
		String comPort = args[2];
				
		if(sinc.equals("none"))		
			logger.info("Integração com E-Gas Desabilitada..");
		else if(sinc.equals("rest"))
			logger.info("E-Gas API IP :: " + host + " || Virtual Usb Port / TX :: " + comPort + "/" + BAUD_RATE);		
		else 
			logger.info("Parâmetro Integração Inválido");
				
		try {
		
			logger.info("E-Gas API IP :: " + host + " - Iniciando Comunicação...");
			SendRest sendRest = new SendRest();

			if (sendRest.TestRest(host)) {
				
				logger.info("E-Gas API IP :: " + host + " Conectado.");				
				logger.info("Checando xBee :: Virtual Usb Port / TX :: " + comPort + "/" + BAUD_RATE);
				
				XBeeDevice myDevice = new XBeeDevice(comPort, BAUD_RATE);
				myDevice.open();
				
				logger.info("XBee :: Virtual Usb Port / TX :: " + comPort + "/" + BAUD_RATE + " OK");
				
				MyDataReceiveListener dataReceiveListener = new MyDataReceiveListener();
				dataReceiveListener.setHost(host);
				myDevice.addDataListener(dataReceiveListener);
				
				System.out.println("\n>> Esperando por Dados dos Routers...");
			}
			
		} catch (XBeeException e) {
			
			logger.error("Ops! Há Algo errado, verifique\n", e);
			System.exit(1);
		}
	}
}
