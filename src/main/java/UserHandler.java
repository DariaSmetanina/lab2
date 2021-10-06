import sofia_kp.SSAP_sparql_response;
import sofia_kp.iKPIC_subscribeHandler2;
import wrapper.SmartSpaceException;
import wrapper.SmartSpaceKPI;
import wrapper.SmartSpaceTriple;

import java.util.Vector;

public class UserHandler implements iKPIC_subscribeHandler2 {

    SmartSpaceKPI kpi;
    int secretNumber;

    String name;

    public String getName() {
        return name;
    }

    UserHandler(SmartSpaceKPI kp, String nm) throws SmartSpaceException {
        kpi=kp;
        name=nm;
        kp.insert(new SmartSpaceTriple(name,"want","play"));
        System.out.println(kpi.query(new SmartSpaceTriple(null,null, null)));
    }

    @Override
    public void kpic_RDFEventHandler(Vector<Vector<String>> vector, Vector<Vector<String>> vector1, String s, String s1) {
        for (Vector<String> data : vector) {
            if(data.get(0).equals(name) && data.get(1).equals("start")){
                try {
                    secretNumber=50;
                    kpi.insert(new SmartSpaceTriple(name,"suppose", String.valueOf(secretNumber)));
                    System.out.println(name+secretNumber);
                } catch (SmartSpaceException e) {
                    e.printStackTrace();
                }
            }
            if(data.get(1).equals("gets hint")){
                if(data.get(1).equals("win")){
                    System.out.println(name+"win, secretNumber was"+secretNumber);
                    try {
                        kpi.unsubscribe(new SmartSpaceTriple(name,null,null),
                                true);
                    } catch (SmartSpaceException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                if(data.get(1).equals("smaller")){
                    secretNumber=(int)secretNumber/2;
                }
                if(data.get(1).equals("bigger")){
                    secretNumber=(int)(secretNumber+secretNumber/2);
                }
                try {
                    kpi.insert(new SmartSpaceTriple(name,"suppose", String.valueOf(secretNumber)));;
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

