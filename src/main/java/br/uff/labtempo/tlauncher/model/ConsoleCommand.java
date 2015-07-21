/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.model;

/**
 *
 * @author Felipe Santos <live.proto at hotmail.com>
 */
public class ConsoleCommand implements Command {

    private final String command;
    private final Printer printer;
    private Process process;    

    public ConsoleCommand(String command) {
        this.command = command;
        this.printer = new ConsolePrinter();
    }

    public ConsoleCommand(String command, Printer printer) {
        this.command = command;
        this.printer = printer;
    }

    @Override
    public void execute() throws Exception {
        process = Runtime.getRuntime().exec(command);
        printer.setInputStream(process.getInputStream());
        process.waitFor();
        process = null;
    }

    @Override
    public void abort() {
        if (isAlive(process)) {
            process.destroy();
        }
    }

    private boolean isAlive(Process process) {
        if (process == null) {
            return false;
        }
        try {
            process.exitValue();
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
