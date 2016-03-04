package edu.kit.trufflehog.service.packetdataprocessor.profinetdataprocessor;

import com.sun.istack.internal.NotNull;
import edu.kit.trufflehog.command.trufflecommand.AddPacketDataCommand;
import edu.kit.trufflehog.model.filter.Filter;
import edu.kit.trufflehog.model.network.INetworkWritingPort;

import java.util.List;

/**
 * Created by Hoehler on 04.03.2016.
 */
public class TruffleCrook extends TruffleReceiver {
    private final INetworkWritingPort networkWritingPort;
    private final List<Filter> filters;
    private long lastCreation = 0;

    private long[] addresses;
    private int maxAddresses = 15;

    public TruffleCrook(@NotNull INetworkWritingPort writingPort, @NotNull List<Filter> filterList) {
        networkWritingPort = writingPort;
        filters = filterList;
        init();
    }

    @Override
    public void connect() {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            synchronized (this) {
                try {
                    Truffle truffle = null;
                    if (System.currentTimeMillis() - lastCreation > 1000) {
                        truffle = getTruffle();
                        lastCreation = System.currentTimeMillis();
                        System.out.println("Truffle generated");
                    }

                    if (truffle != null) {
                        notifyListeners(new AddPacketDataCommand(networkWritingPort, truffle, filters));
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private Truffle getTruffle() {
        int a1 = (int)(Math.random()*maxAddresses);
        int a2 = (int)(Math.random()*maxAddresses);
        Truffle truffle = new TruffleV2(addresses[a1], addresses[a2]);

        return truffle;
    }

    private void init() {
        addresses = new long[maxAddresses];
        for (int i = 0; i < maxAddresses; i++) {
            addresses[i] = (long)(Math.random()*10000000);
        }
    }
}
