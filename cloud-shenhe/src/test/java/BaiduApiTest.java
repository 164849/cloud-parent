import com.baidu.aip.contentcensor.AipContentCensor;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class BaiduApiTest {
    private static final String APP_ID = "17540802";
    private static final String API_KEY = "wKE4Iuyd383Wld3GXCKCGNF7";
    private static final String SECRET_KEY = "cUDtMt9n5Ur4xoQRKiLizW5flGLS15AZ";


    @Test
    void text() throws JSONException {
        // 初始化一个AipContentCensor
        AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 检测图片[可以是本地图片也可以是网络图片]
        // String path = "test.jpg";
        // JSONObject res = client.imageCensorUserDefined(path);
        // System.out.println(res.toString(2));

        // 要检测的文本内容
        String text = "毛选";
        JSONObject response = client.textCensorUserDefined(text);
        // toString：可以指定字串的缩进格式
        System.out.println(response.toString(2));
    }

}
