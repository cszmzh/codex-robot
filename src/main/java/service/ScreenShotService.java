package service;

import config.RobotConfig;
import config.URLConfig;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenShotService {

    private WebDriver driver;

    private String fileName;

    ScreenShotService(String fileName) {
        // 指定文件名
        this.fileName = fileName;
        // 设置系统参数
        File file = new File(RobotConfig.IMAGE_LOCAL_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        System.setProperty("webdriver.chrome.driver", RobotConfig.CHROME_DRIVER_DIR_PI);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // 无窗口模式
        options.addArguments("--disable-infobars"); // 禁言消息条（就上面经常一条黄色的那个）
        options.addArguments("--disable-extensions"); // 禁用插件
        //options.addArguments("--disable-gpu"); // 禁用GPU
        options.addArguments("--no-sandbox"); // 禁用沙盒模式否则会报错
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--hide-scrollbars"); // 隐藏滚动条
        options.addArguments("--window-size=1920, 1080"); // 设置开启游览器时的分辨率
        // 启动chrome实例
        this.driver = new ChromeDriver(options);
    }

    public WebDriver getChromeDriver() {
        return this.driver;
    }

    /**
     * 1.对指定URL全屏截图
     */
    public void fullScreenShot(String url) throws IOException {
        //访问网站
        this.driver.get(url);
        //指定了OutputType.FILE做为参数传递给getScreenshotAs()方法，其含义是将截取的屏幕以文件形式返回。
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        //利用FileUtils工具类的copyFile()方法保存getScreenshotAs()返回的文件对象。
        FileUtils.copyFile(srcFile, new File(RobotConfig.IMAGE_LOCAL_DIR + fileName + ".png"));
        //关闭浏览器
        driver.quit();
    }

    /**
     * 2.根据Element截图指定区域方法
     *
     * @param element 截图区域
     * @throws Exception
     */
    public File elementSnapshot(WebElement element) throws IOException {
        //创建全屏截图
        File screen = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);

        BufferedImage image = ImageIO.read(screen);

        //获取元素的高度、宽度
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();

        //创建一个矩形使用上面的高度，和宽度
        Rectangle rect = new Rectangle(width, height);
        //元素坐标
        Point p = element.getLocation();


        //对前面的矩形进行操作
        //TODO 使用可以截全图的方法（滚动条），暂未找到方式
        int w = rect.width; //指定矩形区域的宽度
        int h = rect.height;//指定矩形区域的高度
        int x = p.getX(); //指定矩形区域左上角的X坐标
        int y = p.getY(); //指定矩形区域左上角的Y坐标

        //driver的分辨率，这里设置1920*1080
        int w_driver = 1920;
        int h_driver = 1080;

        System.out.println("width:" + w);
        System.out.println("height:" + h);
        System.out.println("x:" + x);
        System.out.println("y:" + y);

        System.out.println("y+height:" + (y + h));
        System.out.println("x+width:" + (x + w));

        /**
         * 如果Element的Y坐标值加上高度超过driver的高度
         * 就会报错(y + height) is outside or not
         * 退而求其次，调整图片的宽度和高度, 调整到适合driver的分辨率
         * 此时会截图driver可见的元素区域快照
         * TODO 如果能找到跨滚动条截图的方式，可以不用裁剪
         */
        try {
            if (y + h > h_driver) {
                h = h - (y + h - h_driver); //

                System.out.println("修改后的height:" + h);
                System.out.println("修改后的y+height：" + (y + h));
            }
            //(x + width) is outside or not
            if (x + w > w_driver) {
                w = x - (x + w - w_driver);

                System.out.println("修改后的width：" + w);
                System.out.println("修改后的x+width:" + (x + w));
            }

            BufferedImage img = image.getSubimage(x, y, w, h);
            ImageIO.write(img, "png", screen);
            System.out.println("Screenshot By element success");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return screen;
    }

    /**
     * 3.青年大学习截图
     *
     * @param url 请求地址
     * @return 相对存储路径
     * @throws IOException IO异常
     */
    public String youngStudy(String url) throws IOException {

        this.driver.get(url);

        WebElement element = driver.findElement(By.className("pyqBox"));
        String savePath = RobotConfig.IMAGE_LOCAL_DIR + fileName + ".png";
        FileUtils.copyFile(elementSnapshot(element), new File(savePath));

        driver.quit();
        return savePath;
    }

    /**
     * 4.Zaker热点新闻
     *
     * @return 相对存储路径
     * @throws IOException IO异常
     */
    public String zakerHotNews() throws IOException {

        this.driver.get(URLConfig.ZAKER_HOT_NEWS);

        WebElement element = driver.findElement(By.className("main-inner"));
        String savePath = RobotConfig.IMAGE_LOCAL_DIR + "HotNews.png";
        FileUtils.copyFile(elementSnapshot(element), new File(savePath));

        driver.quit();
        return savePath;
    }

    /**
     * 5.Zaker互联网新闻
     *
     * @throws IOException IO异常
     */
    public String zakerInternetNews() throws IOException {

        this.driver.get(URLConfig.ZAKER_INTERNET_NEWS);

        WebElement element = driver.findElement(By.id("contentList")).findElements(By.className("article-wrap")).get((int) (Math.random() * 2));
        String savePath = RobotConfig.IMAGE_LOCAL_DIR + "InternetNews.png";
        FileUtils.copyFile(elementSnapshot(element), new File(savePath));

        driver.quit();
        return savePath;
    }
}
