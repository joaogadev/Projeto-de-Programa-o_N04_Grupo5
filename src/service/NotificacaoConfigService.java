package service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class NotificacaoConfigService {
    private static final String arquivoConfiguracao = "config/notificacoes.properties";
    private final Properties properties = new Properties();

    public NotificacaoConfigService(){
    }

    private void carregarConfig() {
        try {
            File file = new File(arquivoConfiguracao);
            if (!file.exists()) {
                // Se não existir, cria pasta e arquivo
                file.getParentFile().mkdirs();
                file.createNewFile();

                // valor padrão
                properties.setProperty("notificacoes", "false");
                salvarConfig();
            } else {
                FileInputStream fis = new FileInputStream(file);
                properties.load(fis);
                fis.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void salvarConfig() {
        try {

            File file = new File(arquivoConfiguracao);

            // garante que a pasta "config" exista
            file.getParentFile().mkdirs();

            FileOutputStream fos = new FileOutputStream(file);
            properties.store(fos, "Configuração de notificações");
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
