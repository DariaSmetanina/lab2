import sofia_kp.SSAP_sparql_response;
import sofia_kp.iKPIC_subscribeHandler2;
import wrapper.SmartSpaceException;
import wrapper.SmartSpaceKPI;
import wrapper.SmartSpaceTriple;

import java.util.Vector;

import static java.lang.Thread.sleep;

public class ServerHandler implements iKPIC_subscribeHandler2 {

    SmartSpaceKPI kpi;

    ServerHandler(SmartSpaceKPI kp) {
        kpi = kp;
    }

    @Override
    public void kpic_RDFEventHandler(Vector<Vector<String>> vector, Vector<Vector<String>> vector1, String s, String s1) {
        for (Vector<String> data : vector) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(data.get(2).equals("play")){
                String randomNumber=String.valueOf((int)(Math.random() * (100)));
                randomNumber = "66";
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
                    int suggestion=Integer.parseInt(data.get(2));
                    int secret=Integer.parseInt(secretNumber.getObject());
                    if(suggestion>secret){
                        SmartSpaceTriple tr = new SmartSpaceTriple(data.get(0),"gets hint","smaller");
                        System.out.println(tr);
                        kpi.insert(tr);
                        System.out.println(kpi.query(new SmartSpaceTriple(null, null,null)));
                    }
                    if(suggestion<secret){
                        kpi.insert(new SmartSpaceTriple(data.get(0),"gets hint","bigger"));
                    }
                    if(suggestion==secret){
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
