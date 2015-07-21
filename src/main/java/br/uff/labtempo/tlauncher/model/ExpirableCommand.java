/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.model;

import java.util.Timer;
import java.util.TimerTask;

public class ExpirableCommand implements Command {

    private final ConsoleCommand command;
    private final long timeInMillis;
    private final Timer timer;
    private boolean running;

    public ExpirableCommand(String command, long timeInMillis) {
        this.timeInMillis = timeInMillis;
        this.command = new ConsoleCommand(command);
        this.timer = new Timer("ExpirableCommand", true);
    }

    @Override
    public void abort() {
        if (!running) {
            return;
        }
        command.abort();
    }

    @Override
    public void execute() throws Exception {
        if (running) {
            return;
        }
        TimerTask task = new Task();
        timer.schedule(task, timeInMillis);
        running = true;
        command.execute();
        task.cancel();
        timer.purge();
        running = false;
    }

    private class Task extends TimerTask {

        @Override
        public void run() {
            abort();
        }
    }
}
