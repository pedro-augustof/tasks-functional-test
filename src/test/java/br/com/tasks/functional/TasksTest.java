package br.com.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    public WebDriver acessarAplicacao() throws MalformedURLException {
        System.setProperty("webdriver.chrome.driver", "E:\\devops\\seleniumDrivers\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.121:4444/wd/hub"), cap);
        driver.navigate().to("http://192.168.0.121:8001/tasks/");
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws InterruptedException, MalformedURLException {
        WebDriver driver = acessarAplicacao();

        try {
            driver.findElement(By.id("addTodo")).click();

            driver.findElement(By.id("task")).sendKeys("Teste via selenium");

            driver.findElement(By.id("dueDate")).sendKeys("10/10/2023");

            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Success!", message);
        } finally {
            driver.quit();
        }

    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() throws InterruptedException, MalformedURLException {
        WebDriver driver = acessarAplicacao();

        try {
            driver.findElement(By.id("addTodo")).click();

            driver.findElement(By.id("dueDate")).sendKeys("10/10/2023");

            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Fill the task description", message);
        } finally {
            driver.quit();
        }

    }

    @Test
    public void naoDeveSalvarTarefaSemData() throws InterruptedException, MalformedURLException {
        WebDriver driver = acessarAplicacao();

        try {
            driver.findElement(By.id("addTodo")).click();

            driver.findElement(By.id("task")).sendKeys("Teste via selenium");

            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Fill the due date", message);
        } finally {
            driver.quit();
        }

    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() throws InterruptedException, MalformedURLException {
        WebDriver driver = acessarAplicacao();

        try {
            driver.findElement(By.id("addTodo")).click();

            driver.findElement(By.id("task")).sendKeys("Teste via selenium");

            driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");

            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Due date must not be in past", message);
        } finally {
            driver.quit();
        }

    }
}
