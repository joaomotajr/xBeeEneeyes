package xbee;


import java.math.BigDecimal;

import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBee64BitAddress;
import com.digi.xbee.api.models.XBeeMessage;

import model.Position;
import service.JsonService;
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
	
	private String sinc;
	public String getSinc() {
		return sinc;
	}
	public void setSinc(String sinc) {
		this.sinc = sinc;
	}
	
	JsonService js = new JsonService("targetdeviceStatus.json");

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

		MainApp.logger.info(String.format("MESSAGE Received From MCAdress %s  >> %s ", macAddress, messageReceived));
		
		try {
			
			String nome = value[1];
			String tipo = value[2];			
			String unidade = value[3];
			String id = value[4];			
			String min = value[5];
			String max = value[6];
			String valor = value[7];
			String milliTime = value[8];
						
			if (sinc.equals("rest"))
				new SendRest(host, id, valor);		
			
			Position position = new Position();
			position.setKey("E_GAS");
			position.setNome(nome);
			position.setId(Integer.parseInt(id));
			position.setTipo(tipo);
			position.setUnidade(unidade);
			position.setValue(new BigDecimal(Double.parseDouble(valor)));
			position.setMinValue(Double.parseDouble(min));
			position.setMaxValue(Double.parseDouble(max));			
			position.setMilliTime(new BigDecimal(milliTime));
			js.update(position);
			
		} catch (Exception e) {
			MainApp.logger.error(e);
		}
		
		MainApp.logger.info(String.format("From %s >> %s ", macAddress, messageReceived));
	}

}
