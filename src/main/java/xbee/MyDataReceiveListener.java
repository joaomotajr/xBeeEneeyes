package xbee;


import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBee64BitAddress;
import com.digi.xbee.api.models.XBeeMessage;

import service.SendRest;

/**
 * Class to manage the XBee received data that was sent by other modules in the 
 * same network.
 * 
 * <p>Acts as a data listener by implementing the 
 * {@link IDataReceiveListener} interface, and is notified when new 
 * data for the module is received.</p>
 * 
 * @see IDataReceiveListener
 *
 */
public class MyDataReceiveListener implements IDataReceiveListener {
	/*
	 * (non-Javadoc)
	 * @see com.digi.xbee.api.listeners.IDataReceiveListener#dataReceived(com.digi.xbee.api.models.XBeeMessage)
	 */
	
	private String host;
		
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void dataReceived(XBeeMessage xbeeMessage) {
		
		String messageReceived = xbeeMessage.getDataString();
		XBee64BitAddress macAddress = xbeeMessage.getDevice().get64BitAddress();
		
		String value[] = messageReceived.split("\n");
		
		if(value.length > 1 ) {
			MainApp.logger.error("Erro de configuração de Sensores, verifique ::" + value);
			return;
		}
				
		value = messageReceived.split(";");
		
		if(!value[0].contentEquals("EGAS"))
			return;

		try {
			String broker = "177.144.134.145:8090";
			new SendRest(broker, value[2], value[3]);
			
		} catch (Exception e) {
			MainApp.logger.error(e);
		}
		
		MainApp.logger.info(String.format("From %s >> %s ", macAddress, messageReceived));
	}

}
