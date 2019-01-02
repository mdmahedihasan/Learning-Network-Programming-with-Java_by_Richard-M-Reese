package chapter5_peer_to_peer_networks;

import org.kth.dks.JDHT;
import org.kth.dks.dks_exceptions.DKSIdentifierAlreadyTaken;
import org.kth.dks.dks_exceptions.DKSRefNoResponse;
import org.kth.dks.dks_exceptions.DKSTooManyRestartJoins;

import java.io.IOException;

public class JDHTClient {

    public static void main(String[] args) {
        try {
            JDHT jdht = new JDHT(5550, "dksref://127.0.1.1:4440/0/708058828/0/609274177169371340");
            String value = (String) jdht.get("Java SE API");
            System.out.println(value);
            jdht.close();
        } catch (IOException | DKSTooManyRestartJoins | DKSIdentifierAlreadyTaken | DKSRefNoResponse e) {
            e.printStackTrace();
        }
    }
}
