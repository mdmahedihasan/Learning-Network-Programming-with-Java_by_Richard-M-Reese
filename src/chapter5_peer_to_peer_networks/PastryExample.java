package chapter5_peer_to_peer_networks;

import rice.environment.Environment;
import rice.pastry.NodeHandle;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNode;
import rice.pastry.PastryNodeFactory;
import rice.pastry.leafset.LeafSet;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Collection;

public class PastryExample {

    public PastryExample(int bindPort, InetSocketAddress bootAddress, Environment environment) throws Exception {
        NodeIdFactory nodeIdFactory = new RandomNodeIdFactory(environment);
        PastryNodeFactory pastryNodeFactory = new SocketPastryNodeFactory(nodeIdFactory, bindPort, environment);
        PastryNode pastryNode = pastryNodeFactory.newNode();

        FreePastryApplication application = new FreePastryApplication(pastryNode);
        pastryNode.boot(bootAddress);

        System.out.println("Node " + pastryNode.getId().toString() + " created");
        System.out.println("Node " + pastryNode.getId().toStringFull() + " created");
        environment.getTimeSource().sleep(10000);

        LeafSet leafSet = pastryNode.getLeafSet();
        Collection<NodeHandle> collection = leafSet.getUniqueSet();
        for (NodeHandle nodeHandle : collection) {
            application.routeMessageDirect(nodeHandle);
            environment.getTimeSource().sleep(1000);
        }
    }

    public static void main(String[] args) throws Exception {
        Environment environment = new Environment();
        environment.getParameters().setString("nat_search_policy", "never");

        try {
            int bindPort = 9001;
            int bootPort = 9001;

            InetAddress bootInetAddress = InetAddress.getByName("192.168.1.9");
            InetSocketAddress bootAddress = new InetSocketAddress(bootInetAddress, bootPort);
            System.out.println("InetAddress : " + bootInetAddress);

            PastryExample pastryExample = new PastryExample(bindPort, bootAddress, environment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
