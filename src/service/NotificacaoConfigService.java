package service;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class NotificacaoConfigService {
    private static final String arquivoConfiguracao = "config/notificacoes.properties";

    public NotificacaoConfigService(){
    }

    // verificar ativação de notificações
    public boolean estadoNotificacao(){
        Properties properties = new Properties();
        try(FileInputStream file = new FileInputStream(arquivoConfiguracao)){
            properties.load(file);
            return Boolean.parseBoolean(properties.getProperty("ativada", "desativada"));
        }catch (IOException e){
            return false; // desativado
        }
    }

    // salvar escolha do usuário
    public void setNotificacaoAtivada(boolean ativada){
        Properties properties = new Properties();
        properties.setProperty("ativada", String.valueOf(ativada));

        try(FileOutputStream fileO = new FileOutputStream(arquivoConfiguracao)){
            properties.store(fileO, "Configuração das Notificações");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
