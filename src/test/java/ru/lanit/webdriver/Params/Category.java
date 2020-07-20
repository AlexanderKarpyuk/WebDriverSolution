package ru.lanit.webdriver.Params;

public enum Category {
    оргтехника("Оргтехника и расходники", 99),
    телефон("Телефоны", 84);

    public String name;
    public int id;

    Category(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
