package edu.kit.trufflehog.service.executor;

import edu.kit.trufflehog.command.ICommand;
import edu.kit.trufflehog.command.queue.CommandQueue;
import edu.kit.trufflehog.command.queue.CommandQueueManager;
import edu.kit.trufflehog.command.queue.ICommandQueue;
import edu.kit.trufflehog.command.trufflecommand.ITruffleCommand;
import edu.kit.trufflehog.command.usercommand.IUserCommand;
import edu.kit.trufflehog.service.packetdataprocessor.profinetdataprocessor.TruffleReceiver;
import edu.kit.trufflehog.util.IListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * <p>
 *     This class supplies a service to execute commands generated by the {@link TruffleReceiver} and the view.
 *     Any incoming commands will always be executed in alternating order and first in first out (round robbin way).
 *     If no command is available the service will block and wait until a new command is available.
 * </p>
 *
 * @author Mark Giraud
 * @version 1.0
 */
public class CommandExecutor implements Runnable {

    private static final Logger logger = LogManager.getLogger(CommandExecutor.class);

    private final CommandQueueManager commandQueueManager = new CommandQueueManager();
    private final ICommandQueue truffleCommandQueue = new CommandQueue(commandQueueManager);
    private final ICommandQueue userCommandQueue = new CommandQueue(commandQueueManager);

    /**
     * <p>
     *     The main method of the CommandExecutor service.
     * </p>
     */
    @Override
    public void run() {

        while (!Thread.interrupted()) {
            try {
                commandQueueManager.getNextQueue().pop().execute();
            } catch (InterruptedException e) {
                logger.debug("Executor thread interrupted: " + Arrays.toString(e.getStackTrace()));
                Thread.currentThread().interrupt();
            }
        }

        logger.debug("Executor thread exited");

    }

    /**
     * <p>
     *     This method returns an {@link IListener} that accepts {@link IUserCommand} commands.
     *     Any command sent to this listener will be executed fairly alternating between user and truffle commands.
     * </p>
     *
     * @return A user command listener.
     */
    public IListener<IUserCommand> asUserCommandListener() {
        return asListener(IUserCommand.class, userCommandQueue);
    }

    /**
     * <p>
     *     This method returns an {@link IListener} that accepts {@link ITruffleCommand} commands.
     *     Any command sent to this listener will be executed fairly alternating between user and truffle commands.
     * </p>
     *
     * @return A truffle command listener.
     */
    public IListener<ITruffleCommand> asTruffleCommandListener() {
        return asListener(ITruffleCommand.class, truffleCommandQueue);
    }

    private <T extends ICommand> IListener<T> asListener(final Class<T>commandQueueType, final ICommandQueue commandQueue) {
        return message -> {
            try {
                if (message == null) {
                    throw new NullPointerException("Message to receive must not be null!");
                }
                commandQueue.push(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }
}
