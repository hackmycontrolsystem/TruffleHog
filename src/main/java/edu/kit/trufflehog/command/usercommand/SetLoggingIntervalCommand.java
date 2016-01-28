package edu.kit.trufflehog.command.usercommand;

import edu.kit.trufflehog.model.configdata.ConfigDataModel;
import edu.kit.trufflehog.service.datalogging.DataLogger;

/**
 * <p>
 *     Command supposed to change the time interval of taking snapshots by the {@link DataLogger}.
 * </p>
 */
public class SetLoggingIntervalCommand implements IUserCommand {
    private ConfigDataModel configModel;

    /**
     * <p>
     *     Creates new command and provides config object.
     * </p>
     * @param config {@link ConfigDataModel} to manipulate
     */
    SetLoggingIntervalCommand(ConfigDataModel config) {
        configModel = config;
    }

    public void execute() {

    }
}