package ru.lanit.webdriver.params;

public enum Price {
    Дороже("Дороже",2),
    Дешевле("Дешевле", 1);

    public String name;
    public int id;

    Price(String name, int id) {
        this.name = name;
        this.id = id;
    }


}
