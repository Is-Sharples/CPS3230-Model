import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SUT {



    public static boolean PostApiCall(boolean successMarker){
        Gson gson = new Gson();
        boolean success = false;
        ArrayList<CustomAlert> alerts = new ArrayList<CustomAlert>();

        if(successMarker){
            alerts.add(new CustomAlert("https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth","00de4d33-5d10-4151-ad8f-39dca960ddce","Jumper Windows 11 Laptop","Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD","https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg","00de4d33-5d10-4151-ad8f-39dca960ddce",24999,"2022-12-26T12:35:32.3242361Z",6));
            alerts.add(new CustomAlert("https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth","00de4d33-5d10-4151-ad8f-39dca960ddce","Jumper Windows 11 Laptop","Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD","https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg","00de4d33-5d10-4151-ad8f-39dca960ddce",24999,"2022-12-26T12:35:32.3242361Z",6));
        }else {
            alerts.add(new CustomAlert("","","","","","", 0,"",0));
        }

        for (CustomAlert alert : alerts) {
            String json = gson.toJson(alert);
            try {
                URL url = new URL("https://api.marketalertum.com/Alert");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type","application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);

                try(OutputStream os = con.getOutputStream()){
                    byte[] input = json.getBytes("utf-8");
                    os.write(input,0,input.length);
                }

                try(BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(),"utf-8"))){
                    StringBuilder response = new StringBuilder();
                    String respondLine = null;
                    while ((respondLine = br.readLine()) != null){
                        response.append(respondLine.trim());
                    }
                    System.out.println(response.toString());
                    success = true;
                }catch(IOException e){
                    return false;
                }

            } catch (MalformedURLException e) {
                success = false;
                throw new RuntimeException(e);
            } catch (IOException e) {
                success = false;
                throw new RuntimeException(e);
            }
        }
        return success;
    }

    public static int DeleteApiCall(String userID){
        try {
            URL url = new URL("https://api.marketalertum.com/Alert?userId="+userID);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded" );
            con.connect();
            return con.getResponseCode();
        }catch (Exception e){
        }
        return 0;
    }

    public static boolean LoginFunction(boolean successMarker){
        WebDriver driver = new ChromeDriver();
        String res = "";
        System.setProperty("webdriver.chrome.driver", "C:/Users/Drew/Desktop/chromedriver.exe");
        driver.get("https://www.marketalertum.com/");
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        List<WebElement> list = driver.findElements(By.className("nav-item"));
        WebElement temp = list.get(list.size()-1);
        temp.click();
        temp = driver.findElement(By.name("UserId"));
        if(successMarker){
            temp.sendKeys("00de4d33-5d10-4151-ad8f-39dca960ddce");
        }else {
            temp.sendKeys("00de4d33-5d10ddce");
        }
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        temp.submit();try{
            res = driver.findElement(By.tagName("h1")).getText();
        }catch(Exception e){
            driver.quit();
            return false;
        }

        driver.quit();
        if(res.contains("Sharples")){
            return true;
        }
        return false;
    }

    public static boolean LogoutFunction(){
        WebDriver driver = new ChromeDriver();
        String res = "";
        System.setProperty("webdriver.chrome.driver", "C:/Users/Drew/Desktop/chromedriver.exe");
        driver.get("https://www.marketalertum.com/");
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        List<WebElement> list = driver.findElements(By.className("nav-item"));
        WebElement temp = list.get(list.size()-1);
        temp.click();
        temp = driver.findElement(By.name("UserId"));
        temp.sendKeys("00de4d33-5d10-4151-ad8f-39dca960ddce");
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        temp.submit();
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().click();
        String title = driver.getTitle();
        driver.quit();
        if(title.contains("Home Page")){
            return true;
        }

        return false;
    }
}
