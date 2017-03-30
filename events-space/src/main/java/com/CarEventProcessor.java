import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;

import com.magic.insightedge.model.CarEvent;


@EventDriven @Polling
public class CarEventProcessor {

    @EventTemplate
    CarEvent unprocessedData() {
        CarEvent template = new CarEvent();
        template.setIsSentByHttp(false);
        return template;
    }

    @SpaceDataEvent
    public void eventListener(CarEvent event) {
        // Call external HTTP
    	System.out.println("Calling HTTP client for car event: " + event.getID());
    	try {
    		URL url = new URL("http://localhost:8091/");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            writer.write(event.getRECHNERBEZ());
            writer.flush();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
              System.out.println(line);
            }
            writer.close();
            reader.close();
    	} catch(Exception e) {
    		
    	}
    	
    	
    }
}