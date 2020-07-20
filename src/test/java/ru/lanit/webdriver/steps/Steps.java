package ru.lanit.webdriver.steps;

import com.codeborne.selenide.ElementsCollection;
import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import ru.lanit.webdriver.params.Category;
import ru.lanit.webdriver.params.Price;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.*;

/**
 * Шаги для сценария GetPrinters.
 */
public class Steps {
    @ParameterType(".*")
    public Category category(String name) {
        return Category.valueOf(name);
    }

    @ParameterType(".*")
    public Price price(String name) {
        return Price.valueOf(name);
    }

    /**
     * Открытие сайта
     */
    @Пусть("открыт ресурс авито")
    public static void openSite() {
        open("https://www.avito.ru/");
    }

    /**
     * Выбор категории.
     * @param category - type registry Category.
     */
    @И("в выпадающем списке категорий выбрана {category}")
    public void selectCategory(Category category) {
        $(By.xpath("//select[@id='category']/option[@value=" + category.id + "]")).click();
    }

    /**
     * Заполнение поля поиска.
     * @param arg - строка.
     */
    @И("в поле поиска введено значение принтер {string}")
    public void typeSearch(String arg) {
        $(By.xpath("//input[@id='search']")).sendKeys(arg);

    }

    /**
     * Выбор региона.
     */
    @Тогда("кликнуть по выпадающему списку региона")
    public void clickRegion() {
        $(By.xpath("//div[@data-marker='search-form/region']")).click();

    }

    /**
     * Ввод города в поиск.
     * @param arg - строка.
     */
    @Тогда("в поле регион введено значение {string}")
    public void selectCity(String arg) {
        $(By.xpath("//input[@data-marker='popup-location/region/input']"))
                .sendKeys(arg);
        sleep(1000);
        $(By.xpath("//li[@data-marker='suggest(0)']")).click();
    }

    /**
     * Нажание кнопки поиск по городу.
     */
    @И("нажата кнопка показать объявления")
    public void clickFindCity() {
        $(By.xpath("//button[@data-marker='popup-location/save-button']")).click();
    }

    /**
     * Проверка на открытие нужной страницы.
     * Сравнивает текст внутри титульного заголовка с ожидаемой строкой.
     * @param arg - строка для сравнения.
     */
    @Тогда("открылась страница результаты по запросу {string}")
    public void checkTitle(String arg) {
        String result = $(By.xpath("//div[@data-marker='page-title']/div/h1")).getText();
        Pattern pattern = Pattern.compile("«(.*)»");
        Matcher matcher = pattern.matcher(result);
        if (matcher.find()) {
            result = matcher.group();
            result = result.substring(1, result.length() - 1);
        }
        Assert.assertEquals(result, arg);
    }

    /**
     * Активация чекбокса "только с фото".
     */
    @И("активирован чекбокс только с фотографией")
    public void enabledCheckbox() {
        WebElement elementFilter = $(By.xpath("//div[@class='filters-root-3Q1ZY']/label[starts-with(@class,'checkbox')]/span"));
        if (!elementFilter.isSelected()) {
            $(By.xpath("//div[@class='filters-root-3Q1ZY']/label[starts-with(@class,'checkbox')]/span")).click();
        }
        $(By.xpath("//button[@data-marker='search-filters/submit-button']")).click();
    }

    /**
     * Фильтр отобращения результатов.
     * @param price type registry Price.
     */
    @И("в выпадающем списке сортировка выбрано значение {price}")
    public void selectExpensive(Price price) {
        $(By.xpath("//option[text()='" + price.name + "']")).click();
    }

    /**
     * Вывод в консоль название/цена товара.
     * @param arg - кол-во выводимого в консоль товара.
     */
    @И("И в консоль выведено значение названия и цены {int} первых товаров")
    public void printPrinters(int arg) {
        ElementsCollection printers = $$(By.xpath("//div[@class='item_table-wrapper']"));

        for (int i = 0; i < arg; i++) {
            System.out.println("Принтер №" + (i + 1));
            System.out.println(printers.get(i).$(By.xpath("./div/div[@class='snippet-title-row']/h3/a")).getText());
            System.out.println(printers.get(i).$(By.xpath("./div/div[@class='snippet-price-row']/span[@class='snippet-price ']")).getText());
            System.out.println("-------------------------");
        }
    }
}
