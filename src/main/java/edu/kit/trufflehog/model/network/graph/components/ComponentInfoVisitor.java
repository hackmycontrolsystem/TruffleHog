/*
 * This file is part of TruffleHog.
 *
 * TruffleHog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TruffleHog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with TruffleHog.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.kit.trufflehog.model.network.graph.components;

import edu.kit.trufflehog.Main;
import edu.kit.trufflehog.model.network.graph.NetworkConnection;
import edu.kit.trufflehog.model.network.graph.NetworkNode;
import edu.kit.trufflehog.model.network.graph.NetworkNodeTester;
import edu.kit.trufflehog.model.network.graph.components.edge.EdgeStatisticsComponent;
import edu.kit.trufflehog.model.network.graph.components.node.FilterPropertiesComponent;
import edu.kit.trufflehog.model.network.graph.components.node.NodeInfoComponent;
import edu.kit.trufflehog.model.network.graph.components.node.NodeStatisticsComponent;
import edu.kit.trufflehog.model.network.graph.components.node.PacketDataLoggingComponent;
import edu.kit.trufflehog.viewmodel.StatisticsViewModel;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;

import java.text.DecimalFormat;

/**
 * \brief
 * \details
 * \date 15.03.16
 * \copyright GNU Public License
 *
 * @author Jan Hermes
 * @version 0.0.1
 */
public class ComponentInfoVisitor implements IComponentVisitor<TreeItem<StatisticsViewModel.IEntry<StringProperty, ? extends Property>>> {

    @Override
    public TreeItem<StatisticsViewModel.IEntry<StringProperty, ? extends Property>> visit(ViewComponent component) {

        return new TreeItem<>(new StatisticsViewModel.StringEntry<>(component.name(), ""));
    }

    @Override
    public TreeItem<StatisticsViewModel.IEntry<StringProperty, ? extends Property>> visit(EdgeStatisticsComponent component) {

        final TreeItem<StatisticsViewModel.IEntry<StringProperty, ? extends Property>> root = new TreeItem<>(new StatisticsViewModel.StringEntry<>(component.name(), ""));

        root.getChildren().add(new TreeItem<>(new StatisticsViewModel.StringEntry<>("Connection Traffic", component.getTrafficProperty())));

        return root;
    }

    @Override
    public TreeItem<StatisticsViewModel.IEntry<StringProperty, ? extends Property>> visit(NodeStatisticsComponent component) {

        final TreeItem<StatisticsViewModel.IEntry<StringProperty, ? extends Property>> root = new TreeItem<>(new StatisticsViewModel.StringEntry<>(component.name(), ""));

        StringProperty doubleStringProperty = new SimpleStringProperty("");
        doubleStringProperty.bindBidirectional(component.getThroughputProperty(), new DecimalFormat("0.00"));

        root.getChildren().add(new TreeItem<>(new StatisticsViewModel.StringEntry<>("In/out packages", component.getCommunicationCountProperty())));
        root.getChildren().add(new TreeItem<>(new StatisticsViewModel.StringEntry<>("Packages per second", doubleStringProperty)));
        root.getChildren().add(new TreeItem<>(new StatisticsViewModel.StringEntry<>("Outgoing", component.outgoingCountProperty())));
        root.getChildren().add(new TreeItem<>(new StatisticsViewModel.StringEntry<>("Incoming", component.ingoingCountProperty())));

        return root;
    }

    @Override
    public TreeItem<StatisticsViewModel.IEntry<StringProperty, ? extends Property>> visit(NodeInfoComponent component) {

        final TreeItem<StatisticsViewModel.IEntry<StringProperty, ? extends Property>> root = new TreeItem<>(new StatisticsViewModel.StringEntry<>(component.name(), ""));

        root.getChildren().add(new TreeItem<>(new StatisticsViewModel.StringEntry<>("Device name", component.getDeviceNameProperty())));
        root.getChildren().add(new TreeItem<>(new StatisticsViewModel.StringEntry<>("IP Address", component.getIpAddressProperty())));
        //TODO maybe use the read only property rather than wrapping the actual value in a temporary property
        root.getChildren().add(new TreeItem<>(new StatisticsViewModel.StringEntry<>("Mac Address", new SimpleObjectProperty<>(component.getMacAddress()))));

        return root;
    }

    @Override
    public TreeItem<StatisticsViewModel.IEntry<StringProperty, ? extends Property>> visit(FilterPropertiesComponent component) {

        final TreeItem<StatisticsViewModel.IEntry<StringProperty, ? extends Property>> root = new TreeItem<>(new StatisticsViewModel.StringEntry<>(component.name(), ""));
        root.getChildren().add(new TreeItem<>(new StatisticsViewModel.StringEntry<>("Status", new SimpleListProperty<>(FXCollections.observableArrayList(component.getFilterColors().values())))));
        return root;
    }

    @Override
    public TreeItem<StatisticsViewModel.IEntry<StringProperty, ? extends Property>> visit(PacketDataLoggingComponent component) {

        final TreeItem<StatisticsViewModel.IEntry<StringProperty, ? extends Property>> root = new TreeItem<>(new StatisticsViewModel.StringEntry<>(component.name(), ""));

/*        final Popup popup = new Popup();
        popup.setX(300);
        popup.setY(200);
        popup.getContent().addAll(new Circle(25, 25, 50, Color.AQUAMARINE));

        root.expandedProperty().

        root.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            popup.show(Main.pr);
        });*/

        final TreeItem<StatisticsViewModel.IEntry<StringProperty, ? extends Property>> info = new TreeItem<>(new StatisticsViewModel.StringEntry<>("Packets", component.getObservablePacketsProperty()));
        root.getChildren().add(info);

        return root;
    }

    @Override
    public TreeItem<StatisticsViewModel.IEntry<StringProperty, ? extends Property>> visit(NetworkNode component) {
        return new TreeItem<>(new StatisticsViewModel.StringEntry<>(component.name(), ""));
    }

    @Override
    public TreeItem<StatisticsViewModel.IEntry<StringProperty, ? extends Property>> visit(NetworkConnection component) {
        return new TreeItem<>(new StatisticsViewModel.StringEntry<>(component.name(), ""));
    }

    // TODO DELTE
    @Override
    public TreeItem<StatisticsViewModel.IEntry<StringProperty, ? extends Property>> visit(NetworkNodeTester iComponents) {
        throw new UnsupportedOperationException("Operation not implemented yet");
    }
}
