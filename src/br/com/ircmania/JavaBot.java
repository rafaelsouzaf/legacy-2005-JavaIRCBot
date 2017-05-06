package br.com.ircmania;

import java.io.*;
import java.net.Socket;

public class JavaBot {

    private static BufferedReader IRCir;
    private static BufferedWriter IRCor;
    public boolean running;
    private Socket IRCServerS;
    private String botName;
    private String botDescription;


    public JavaBot(String botName, String botDescription) {
        // set the bot's nickname and description
        this.botName = botName;
        this.botDescription = botDescription;
    }

    public static boolean ircsend(String message) {

        try {
            IRCor.write(message);
            IRCor.newLine();
            IRCor.flush();
        } catch (IOException e) {
            return false;
        }

        return true;

    }

    public static void logoff() {
        BufferedReader br = IRCir;
        BufferedWriter bw = IRCor;

        try {
            if (!ircsend("quit terminating")) ;
            bw.write("quit terminating");
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            System.out.println("logoff error: " + e);
            System.exit(0);
        }
    }

    public void connect(String serverHostname, int serverPort) {

        try {
            IRCServerS = new Socket(serverHostname, serverPort);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // get input and output streams from the IRC server
        InputStream IRCis = null;
        OutputStream IRCos = null;

        try {
            IRCis = IRCServerS.getInputStream();
            IRCos = IRCServerS.getOutputStream();
        } catch (Exception e) {
            System.err.println("error opening streams to IRC server");
            e.printStackTrace();
            System.exit(0);
        }

        IRCir = new BufferedReader(new InputStreamReader(IRCis));
        IRCor = new BufferedWriter(new OutputStreamWriter(IRCos));

    }

    public void disconnect() {
        try {
            IRCir.close();
            IRCor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logon() {
        BufferedReader br = IRCir;
        BufferedWriter bw = IRCor;

        try {
            // send user info
            bw.write("user " + botName + " ware2 irc :" + botDescription);
            bw.newLine();
            bw.write("nick " + botName);
            bw.newLine();
            bw.write("join #Brasil_Chat");
            bw.newLine();
            bw.write("nickserv identify passwordHere");
            bw.newLine();
            bw.write("oper vircio001 passwordHere");
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            System.out.println("logon error: " + e);
            System.exit(0);
        }

        return;
    }

    public boolean pingpong(String msg) throws IOException {

        if (msg.substring(0, 4).equalsIgnoreCase("ping")) {
            // send a pong back
            String pongmsg = "pong " + msg.substring(5);
            IRCor.write(pongmsg);
            IRCor.newLine();
            IRCor.flush();
            return true;
        }

        return false;
    }

    public void send_notice(String username, String message) {

        String command = "notice " + username + " :" + message;
        ircsend(command);

    }

    public void service() {

        try {
            // see if there's some input
            if (IRCir.ready()) {
                String msg = IRCir.readLine();

                // deal with pings
                if (!pingpong(msg)) {
                    // check for a recognisable command
                    String prefix = null;
                    String command = null;
                    String params = null;

                    // check for the prefix
                    if (msg.substring(0, 1).equals(":")) {
                        prefix = msg.substring(1, msg.indexOf(' '));
                        msg = msg.substring(msg.indexOf(' ') + 1);
                    }

                    // extract the command
                    command = msg.substring(0, msg.indexOf(' '));

                    // get the parameters (the rest of the message)
                    params = msg.substring(msg.indexOf(' ') + 1);

                    if ((msg.contains("[200.42.38.150]")) && (msg.contains("Client connecting:"))) {

                        try {

                            String nick = msg.substring(msg.indexOf("connecting:") + 12, msg.indexOf("(") - 1);
                            System.out.println("=" + nick + "=");
                            IRCor.write("operserv raw svsjoin " + nick + " #Ubbi");
                            IRCor.newLine();
                            IRCor.flush();

                            /// operserv raw svsjoin nick canal

                        } catch (Exception e) {
                        }

                    }

                }

            } else {
                // sleep for a bit
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }

            }
        } catch (IOException e) {
            System.out.println("error: " + e);
            System.exit(0);
        }
    }

}
