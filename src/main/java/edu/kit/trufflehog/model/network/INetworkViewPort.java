package edu.kit.trufflehog.model.network;

import edu.kit.trufflehog.model.jung.layout.ObservableLayout;
import edu.kit.trufflehog.model.network.graph.IConnection;
import edu.kit.trufflehog.model.network.graph.INode;
import edu.kit.trufflehog.model.network.recording.NetworkViewCopy;
import edu.kit.trufflehog.util.DeepCopyable;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.event.GraphEventListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import org.apache.commons.collections15.Transformer;

import java.util.Collection;

/**
 * A Networkview port provides all functionality that has to be accessed by the network view. Meaning it also
 * has to give access to specific Metadata of the Network
 */
public interface INetworkViewPort extends Layout<INode, IConnection>, DeepCopyable<NetworkViewCopy> {

    /**
     * Returns the maximum number of connections that
     * @return The maximum number of connections
     */
    int getMaxConnectionSize();

    void setMaxConnectionSize(int size);

    ObservableLayout<INode, IConnection> getDelegate();

    /**
     * Returns the maximum number of connections that
     * @return The maximum number of connections
     */
    IntegerProperty getMaxConnectionSizeProperty();

    /**
     * Returns the maximum throughput of any network node in the current network
     * @return
     */
    int getMaxThroughput();

    void setMaxThroughput(int size);

    /**
     * Returns the maximum throughput of any network node in the current network
     * @return
     */
    IntegerProperty getMaxThroughputProperty();

    /**
     * Returns the time the currently active viewport is displaying (e.g. if it is a recording it will not be the
     * live time) in millis since 1970... bla bla
     * @return the current time of the view port
     */
    long getViewTime();

    void setViewTime(long time);

    /**
     * Returns the time the currently active viewport is displaying (e.g. if it is a recording it will not be the
     * live time) in millis since 1970... bla bla
     * @return the current time of the view port
     */
    LongProperty getViewTimeProperty();

    void setPopulation(int value);

    int getPopulation();

    IntegerProperty getPopulationProperty();

    void setThroughput(double value);

    double getThroughput();

    DoubleProperty getThroughputProperty();

    /**
     * Refreshes the Layout of the underlying graph, by reapplying a new instance of an
     * {@link Layout}. New instances are created by the layout factory that is
     * set for this instance.
     */
    void refreshLayout();

    /**
     * Specifies a new layout factory for the underlying graph. The type of layout factory determines
     * which layout will be reinstantiated to refresh the graph layout.
     *
     * @param layoutFactory the layout factory for instantiating new layouts
     *                         of the given type
     */
    void setLayoutFactory(Transformer<Graph<INode, IConnection>, Layout<INode, IConnection>> layoutFactory);


    /**
     * Intersects the underlying graph of this viewport with the given graph. Meaning only common node and
     * connections with the given graph will remain in the underlying graph.
     * @param graph
     */
    void graphIntersection(Graph<INode, IConnection> graph);

    void graphIntersection(Collection<INode> vertices, Collection<IConnection> edges);

    /**
     * Adds {@code l} as a listener to this graph.
     */
    void addGraphEventListener(GraphEventListener<INode , IConnection> l);

    /**
     * Removes {@code l} as a listener to this graph.
     */
    void removeGraphEventListener(GraphEventListener<INode, IConnection> l);
}
