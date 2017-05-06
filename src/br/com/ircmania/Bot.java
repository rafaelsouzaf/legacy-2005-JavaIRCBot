package br.com.ircmania;

public class Bot {

    public static void main(java.lang.String[] args) {

        JavaBot meuBot = new JavaBot("null", "ircmania");
        meuBot.connect("127.0.0.1", 6667);
        meuBot.logon();
        meuBot.running = true;
        //new br.com.ircmania.Timer(meuBot);
        while (meuBot.running) {
            meuBot.service();
        }

        JavaBot.logoff();
        meuBot.disconnect();

    }

}
