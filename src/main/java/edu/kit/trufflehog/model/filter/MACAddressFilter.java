package edu.kit.trufflehog.model.filter;

import edu.kit.trufflehog.model.network.INetworkIOPort;
import edu.kit.trufflehog.model.network.InvalidMACAddress;
import edu.kit.trufflehog.model.network.MacAddress;
import edu.kit.trufflehog.model.network.graph.INode;
import edu.kit.trufflehog.model.network.graph.components.node.FilterPropertiesComponent;
import edu.kit.trufflehog.model.network.graph.components.node.NodeInfoComponent;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * <p>
 *
 * </p>
 * @author Mark Giraud
 * @version 0.1
 */
public class MACAddressFilter implements IFilter {

    private final Map<MacAddress, Color> addresses = new HashMap<>();
    private int priority = 0;
    private final INetworkIOPort networkIOPort;

    /**
     * //TODO document
     *
     * @param networkIOPort the network IO port. This is needed to enable this filter to revert the chaanges it made to the nodes.
     * @param filterInput the filter input to input to this filter.
     * @throws InvalidFilterRule this exception is thrown if the filterInput contains invalid rules
     */
    public MACAddressFilter(INetworkIOPort networkIOPort, final FilterInput filterInput) throws InvalidFilterRule {

        if (networkIOPort == null)
            throw new NullPointerException("networkIOPort must not be null!");

        if (filterInput == null)
            throw new NullPointerException("filterInput must not be null!");

        this.networkIOPort = networkIOPort;

        final List<String> rules = filterInput.getRules();

        if (rules == null)
            throw new NullPointerException("the rules list in filterInput must not be null!");

        priority = filterInput.getPriority();

        final List<MacAddress> macAddresses = new LinkedList<>();

        for (String rule : rules) {
            if (!rule.matches("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$"))
                throw new InvalidFilterRule("Mac address doesn't have the correct format");

            final String[] macAddressParts = rule.split(":");

            long macAddress = 0;

            for (int i = 0; i < 6; i++) {
                int b = Integer.parseInt(macAddressParts[i], 16);
                macAddress <<= 8;
                macAddress += b;
            }

            try {
                macAddresses.add(new MacAddress(macAddress));
            } catch (InvalidMACAddress invalidMACAddress) {
                // nothing to do here because we can't pass invalid addresses.
            }
        }

        macAddresses.stream().forEach(address -> addresses.put(address, filterInput.getColor()));
    }

    @Override
    public void clear() {

        networkIOPort.getNetworkNodes().stream().forEach(node -> {
            node.getComponent(FilterPropertiesComponent.class).removeFilterColor(this, node);
        });
    }

    @Override
    public Color getFilterColor(INode node) {
        return addresses.get(node.getComponent(NodeInfoComponent.class).getMacAddress());
    }

    @Override
    public void check(final INode node) {
        if (node == null)
            throw new NullPointerException("node must not be null!");

        //TODO maybe null check?
        final MacAddress address = node.getComponent(NodeInfoComponent.class).getMacAddress();

        if (addresses.containsKey(address)) {
            node.getComponent(FilterPropertiesComponent.class).addFilterColor(this, addresses.get(address));
        }
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(IFilter other) {
        return priority - other.getPriority();
    }
}