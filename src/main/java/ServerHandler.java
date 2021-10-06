import sofia_kp.SSAP_sparql_response;
import sofia_kp.iKPIC_subscribeHandler2;
import wrapper.SmartSpaceException;
import wrapper.SmartSpaceKPI;
import wrapper.SmartSpaceTriple;

import java.util.Vector;

public class ServerHandler implements iKPIC_subscribeHandler2 {

    SmartSpaceKPI kpi;

    ServerHandler(SmartSpaceKPI kp) {
        kpi = kp;
    }

    @Override
    public void kpic_RDFEventHandler(Vector<Vector<String>> vector, Vector<Vector<String>> vector1, String s, String s1) {
        for (Vector<String> data : vector) {
            if(data.get(2).equals("play")){
                String randomNumber=String.valueOf((int)(Math.random() * (100)));
                try {
                    kpi.insert(new SmartSpaceTriple(data.get(0),"has", randomNumber));
                    kpi.insert(new SmartSpaceTriple(data.get(0),"start", "game"));
                } catch (SmartSpaceException e) {
                    e.printStackTrace();
                }
            }
             if(data.get(1).equals("suppose")){
                try {
                    SmartSpaceTriple secretNumber = kpi.query(new SmartSpaceTriple(data.get(0),"has",null)).get(0);
                    if(Integer.parseInt(data.get(2))>Integer.parseInt(secretNumber.getSubject())){
                        kpi.insert(new SmartSpaceTriple(data.get(0),"gets hint","smaller"));
                    }
                    if(Integer.parseInt(data.get(2))<Integer.parseInt(secretNumber.getSubject())){
                        kpi.insert(new SmartSpaceTriple(data.get(0),"gets hint","bigger"));
                    }
                    if(Integer.parseInt(data.get(2))==Integer.parseInt(secretNumber.getSubject())){
                        kpi.insert(new SmartSpaceTriple(data.get(0),"gets hint","win"));
                    }

                } catch (SmartSpaceException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void kpic_SPARQLEventHandler(SSAP_sparql_response ssap_sparql_response, SSAP_sparql_response ssap_sparql_response1, String s, String s1) {

    }

    @Override
    public void kpic_UnsubscribeEventHandler(String s) {

    }

    @Override
    public void kpic_ExceptionEventHandler(Throwable throwable) {

    }
}
