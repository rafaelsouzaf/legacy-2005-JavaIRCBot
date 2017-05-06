package br.com.ircmania;

public class Timer implements Runnable {

    Thread thisThread;
    private JavaBot meuBot;

    public Timer(JavaBot meuBot) {

        this.meuBot = meuBot;
        thisThread = new Thread(this);
        thisThread.start();

    }

    public void run() {

        // faz o services News enviar noticias

        try {

            while (true) {

                // inicia contagem do tempo definido pelo usu�rio
                // para que o News envie noticias aos users
                Thread.sleep(20 * 60000);

                // envia notice
                String notice = "operserv global \u00034Aten��o:\u0003 O novoo WEBCHAT j� est� no ar para testes. Visite \u00032http://200.42.38.38 \u0003ou utilizando o seu script de mIRC: \u00037\u001F/server irc.ircmania.com.br\u001F\u0003 Registre seu apelido e sua sala. Teste todos os comandos e novos recursos do chat. Se poss�vel fique sempre online l� tamb�m. Quando mais gente testa-lo, mais r�pido poderemos inaugura-lo.";
                meuBot.ircsend(notice);

                String notice2 = "operserv global \u00034E lembrem-se\u0003. No novo bate-papo, haver� elei��es democr�ticas para IRCop a cada 6 meses. N�o existirao mais IRCops fixos. Os usu�rios ir�o definir seus representantes.";
                meuBot.ircsend(notice2);

            }

        } catch (InterruptedException e) {
        }

    }

}