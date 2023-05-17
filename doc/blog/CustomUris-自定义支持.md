# 功能作用
    动态生成请求的uri集合

# 使用示例

### 1）实现CustomUris接口
```java
import com.gtop.reda.core.support.CustomUris;

public class MyCustomUris implements CustomUris {
    public String[] getUris() {
        String httpPrefix = "http://localhost:8080/";
        String[] uris = new String[10];
        for (int i = 0; i < uris.length; i++) {
            uris[i] = "meId@" + i + "@" + httpPrefix + "get" + i;
        }
        return uris;
    }
}
```
### 2）@RemoteData注解参数赋值

```java
import com.gtop.reda.core.annotation.RemoteData;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/rd")
public class TestController {

    @RemoteData(customUris = MyCustomUris.class, uris = {"http://localhost:8080/get"})
    @GetMapping("get")
    public Result<MyData> test(ParamDTO dto) {
        return Result.success(new MyData());
    }

}
```
