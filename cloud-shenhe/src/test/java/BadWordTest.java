import com.itck.util.BadWordsUtil;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class BadWordTest {

    @Test
    void badWord() throws IOException {
        BadWordsUtil.initbadWordsList();
        List<String> words = BadWordsUtil.searchBadWords("你是真的牛逼");
        if (CollectionUtils.isEmpty(words)) {
            System.out.println("内容合规");
        } else {
            words.forEach(System.out::println);
        }
    }

}
