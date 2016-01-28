package edu.kit.trufflehog.model.graph;

import edu.uci.ics.jung.algorithms.layout.KKLayout;

/**
 * <p>
 *     Uses the Kamada-Kawai-algorithm from the jung library to present the {@link AbstractNetworkGraph}.
 * </p>
*/
public class KamadaKawaiLayout extends KKLayout<INode, IConnection> implements INetworkGraphLayout<INode, IConnection> {

    /**
     * <p>
     *     Creates a new layout to present a given INetworkGraph.
     * </p>
     *
     * @param graph {@link AbstractNetworkGraph} to be drawn.
     */
    public KamadaKawaiLayout(AbstractNetworkGraph graph) {
        super(graph);
    }
}