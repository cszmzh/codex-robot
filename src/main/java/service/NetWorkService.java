package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import config.RobotConfig;
import config.URLConfig;
import entity.Weather;
import okhttp3.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * 网路请求类服务
 *
 * @author 扎克蕉
 */
public class NetWorkService {
    /**
     * 1.获取西青区天气
     *
     * @return 天气实体类
     * @throws IOException IO异常
     */
    public Weather getWeather() throws IOException {
        String url = URLConfig.WEATHER;
        Document document = Jsoup.parse(new URL(url), 30000);
        document.getElementsByTag("city");
        Elements element = document.getElementsByAttributeValueContaining("cityname", "西青");

        Weather weather = new Weather();
        weather.setCityX(element.get(0).attributes().get("cityX"));
        weather.setCityY(element.get(0).attributes().get("cityY"));
        weather.setCityName(element.get(0).attributes().get("cityname"));
        weather.setCenterName(element.get(0).attributes().get("centername"));
        weather.setPyName(element.get(0).attributes().get("pyName"));
        weather.setState1(element.get(0).attributes().get("state1"));
        weather.setState2(element.get(0).attributes().get("state2"));
        weather.setStateDetailed(element.get(0).attributes().get("stateDetailed"));
        weather.setTem1(element.get(0).attributes().get("tem1"));
        weather.setTem2(element.get(0).attributes().get("tem2"));
        weather.setTemNow(element.get(0).attributes().get("temNow"));
        weather.setWindState(element.get(0).attributes().get("windState"));
        weather.setWindDir(element.get(0).attributes().get("windDir"));
        weather.setWindPower(element.get(0).attributes().get("windPower"));
        weather.setHumidity(element.get(0).attributes().get("humidity"));
        weather.setTime(element.get(0).attributes().get("time"));
        weather.setUrl(element.get(0).attributes().get("url"));

        return weather;
    }

    /**
     * 2.聊天接口
     *
     * @param keywords 关键字
     * @param sender   发送者id
     * @return 返回聊天内容
     * @throws IOException IO异常
     */
    public String chat(String keywords, long sender) throws IOException {

        if (keywords.trim().length() == 0) {
            return "请输入内容哦，" + RobotConfig.SERVER_NAME + "洗耳恭听！";
        }

        // 去除字符串中所有空格
        String url = URLConfig.CHAT + "&userid=" + sender + "&spoken=" + keywords.replaceAll(" ", "");

        Connection.Response res = Jsoup.connect(url)
                .timeout(10000).ignoreContentType(true).execute();

        String body = res.body();
        JSONObject obj = JSONObject.parseObject(body);
        String ans = obj.getJSONObject("data").getJSONObject("info").getString("text").replace("小思", RobotConfig.SERVER_NAME);

        return ans;
    }

    /**
     * 3.515code查询文章
     *
     * @param keywords 关键字
     * @return 搜索结果
     * @throws IOException IO异常
     */
    public String getBlogArticle(String keywords) throws IOException {

        String url = URLConfig.BLOG_SEARCH;

        // 创建OkHttp对象
        OkHttpClient client = new OkHttpClient();
        // 定义类型
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        // 创建RequestBody（参数1：数据类型 参数2：JSON字符串）
        RequestBody requestBody = RequestBody.create(type, "{\"requests\":[{\"indexName\":\"515code-search\",\"params\":\"query=" + keywords + "&page=0&facets=%5B%5D&tagFilters=\"}]}");

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        String resData = response.body().string();

        // 获取文章对象
        JSONObject results = JSONObject.parseObject(resData).getJSONArray("results").getJSONObject(0);
        JSONArray hits = results.getJSONArray("hits");

        // 返回字符串
        String ans = "⭐文章搜索结果⭐\n";

        // 限定返回文章条数
        Integer articleSize = hits.size() <= RobotConfig.ARTICLE_SIZE ? hits.size() : RobotConfig.ARTICLE_SIZE;

        for (int i = 0; i < articleSize; i++) {
            JSONObject article = hits.getJSONObject(i);
            ans += i + 1 + ".《" + article.get("title").toString() + "》\n" + "链接: " + article.get("permalink").toString() + " \n";
        }
        ans += "共搜索到" + results.getString("nbHits") + "篇文章，显示" + articleSize + "篇文章，用时：" + results.getString("processingTimeMS") + "毫秒";

        if (hits.size() == 0) {
            ans = "暂时没有搜到你想要的文章，可以换个关键字或者打开 https://www.515code.com/ 看看哦！";
        }

        return ans;
    }

    /**
     * 4.获取名人名言
     *
     * @return 名人名言
     * @throws IOException IO异常
     */
    public String getGoodSaying() throws IOException {
        // 创建OkHttp对象
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        // 发送请求
        Request request = new Request.Builder()
                .url(URLConfig.GOOD_SAYING)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String resData = response.body().string();
        JSONObject data = JSONObject.parseObject(resData).getJSONObject("data");

        return data.getString("content") + "——" + data.getString("author");
    }

    /**
     * 5.获取Zaker热点新闻图片
     *
     * @param qqId QQ号
     * @return 相对存储路径
     * @throws IOException IO异常
     */
    public String getZakerHotNews(long qqId) throws IOException {
        // 截图
        ScreenShotService screenShotService = new ScreenShotService(String.valueOf(qqId));
        String savePath = screenShotService.zakerHotNews();
        return savePath;
    }

    /**
     * 6.获取Zaker互联网新闻图片
     *
     * @param qqId QQ号
     * @return 相对存储路径
     * @throws IOException IO异常
     */
    public String getZakerInternetNews(long qqId) throws IOException {
        // 截图
        ScreenShotService screenShotService = new ScreenShotService(String.valueOf(qqId));
        String savePath = screenShotService.zakerInternetNews();
        return savePath;
    }

    /**
     * 7.四六级查询
     *
     * @param mode   "4"或者"6"，代表四级或六级
     * @param number 准考证号
     * @param name   姓名
     * @return 成绩结果
     * @throws IOException IO异常
     */
    @Deprecated
    public String getCET(String mode, String number, String name) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://cachecloud.neea.cn/cet/query?data=CET" + mode + "_203_DANGCI," + number + "," + name)
                .method("GET", null)
                .addHeader("Accept", " */*")
                .addHeader("Accept-Language", " zh-CN,zh;q=0.9")
                .addHeader("Connection", " keep-alive")
                .addHeader("Host", " cachecloud.neea.cn")
                .addHeader("Referer", " http://cet.neea.cn/")
                .addHeader("User-Agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        String resData = response.body().string();
        return resData;
    }

    /**
     * 8.彩虹屁
     *
     * @return 夸夸语句
     * @throws IOException IO异常
     */
    public String getLove() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(URLConfig.RAINBOW_LOVE)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        String resData = response.body().string();
        return resData;
    }

    /**
     * 9.查英语单词
     *
     * @param keyword 关键字
     * @return 字符串
     */
    public String getBczWord(String keyword) throws IOException {
        String url = URLConfig.BCZ_URL + keyword + "&pageSize=1&pageNum=1";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        JSONArray jsonArray = JSONObject.parseObject(response.body().string()).getJSONArray("data");

        // 返回查询结果
        String ans;
        if (jsonArray.size() == 0) {
            ans = "暂无搜索结果，可以换个关键词或使用微信小程序：香蕉单词~";
        } else {
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String word = jsonObject.getString("word");
            String meanCn = jsonObject.getString("meanCn");
            String tag = jsonObject.getString("tag");
            ans = "查词结果如下：\n单词：" + word + "\n释义：" + meanCn + "\n标签：" + tag + "\n更多详情，请使用微信小程序：香蕉单词~";
        }
        return ans;
    }
}
