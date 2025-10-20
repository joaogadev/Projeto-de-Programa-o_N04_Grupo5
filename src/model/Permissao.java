package model;

public enum Permissao { // impedir erros de digitação em acessos/ usuario.getPermissao() == Permissao.ADMIN
    ADMIN,
    USUARIO,
    VISUALIZADOR
}
