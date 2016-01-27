package edu.kit.trufflehog.service.executor;

import edu.kit.trufflehog.command.queue.CommandQueue;
import edu.kit.trufflehog.command.queue.CommandQueueManager;
import edu.kit.trufflehog.command.queue.ICommandQueue;
import edu.kit.trufflehog.command.trufflecommand.ITruffleCommand;
import edu.kit.trufflehog.command.usercommand.IUserCommand;
import edu.kit.trufflehog.service.packetdataprocessor.profinetdataprocessor.TruffleReceiver;
import edu.kit.trufflehog.util.IListener;

/**
 * This class supplies a service to execute commands generated by the {@link TruffleReceiver} and the view.
 * Any incoming commands will always be executed in alternating order and first in first out.
 * If no command is available the service will block and wait until a new command is available.
 *
 * @author Mr. X
 * @version 0.0
 */
public class CommandExecutor implements Runnable {

    private final CommandQueueManager commandQueueManager = new CommandQueueManager();
    private final ICommandQueue truffleCommandQueue = new CommandQueue(commandQueueManager);
    private final ICommandQueue userCommandQueue = new CommandQueue(commandQueueManager);

    /**
     * The main method of the CommandExecutor service.
     */
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    /**
     * This method returns an {@link IListener} that accepts {@link IUserCommand} commands.
     * Any command sent to this listener will be executed fairly alternating between user and truffle commands.
     * @return A user command listener.
     */
    public IListener<IUserCommand> asUserCommandListener() {
        return message -> {
            try {
                userCommandQueue.push(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * This method returns an {@link IListener} that accepts {@link ITruffleCommand} commands.
     * Any command sent to this listener will be executed fairly alternating between user and truffle commands.
     * @return A truffle command listener.
     */
    public IListener<IUserCommand> asTruffleCommandListener() {
        return message -> {
            try {
                truffleCommandQueue.push(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }
}
