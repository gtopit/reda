#功能作用
    指定用于接收远程调用返回的结果映射。
    一般在远程调用多接口时使用

#使用示例

---
###示例1（远程多接口请求合并）
####1）定义返回给前端的类

```java
import com.gtop.reda.core.annotation.IsMe;
import lombok.Data;

@Data
public class MyData {
    @IsMe(id = "1")
    private Integer catNumber;
    @IsMe(id = "2")
    private Integer dogNumber;
    private Integer totalNumber;
}
```
####2）多个uri的关联格式，可以直接使用uris，也可实现CustomUris接口，以使用uris为例

```java
import com.gtop.reda.core.annotation.RemoteData;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/rd")
public class TestController {

    @RemoteData(uris = {"meId@1@http://localhost:8080/catNumber", "meId@2@http://localhost:8080/dogNumber"})
    @GetMapping("/get")
    public Result<MyData> test(ParamDTO dto) {
        return Result.success(new MyData());
    }

}
```
####3）注意事项
    这里需要特别注意，由于多个请求是通过CompletableFuture异步完成的，因此需要标识请求时的uri，在异步返回结果后根据标识关联结果。
    因此@IsMe注解的id值和uris中每个uri中两个@中的值需要一致。

---
###示例2（单接口类内部映射）
####1）定义返回给前端的类
```java
import com.gtop.reda.core.annotation.IsMe;
import lombok.Data;

@Data
public class MyRecord {
    @IsMe
    private Integer catNumber;
}
```
####2）uris设置（也可通过实现CustomUris接口）
```java
import com.gtop.reda.core.annotation.RemoteData;

@RestController
@RequestMapping("/test")
public class TestController {

    @RemoteData(uris = {"http://localhost:8080/catNumber"})
    public Result<MyRecord> test(ParamDTO dto) {
        return Result.success(new MyData());
    }

}
```
####3）说明
    由于只是单接口的远程调用，因此关联IsMe的id