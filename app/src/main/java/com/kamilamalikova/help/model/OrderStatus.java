package com.kamilamalikova.help.model;

public enum  OrderStatus {
    ALL("Все"),
    CREATED("Создан"),
    CLOSED("Закрыт");

    String ru_name;

    OrderStatus(String ru_name) {
        this.ru_name = ru_name;
    }

    public String getRu_name() {
        return ru_name;
    }
}
