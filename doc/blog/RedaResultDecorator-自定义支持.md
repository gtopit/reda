#功能作用
    远程调用结束后，对得到的返回数据在返回前端前做最后的加工。

#使用示例
###1）实现RedaResultDecorator接口

```java
import com.gtop.reda.core.support.RedaResultDecorator;

// MyData参考 IsMe注解.md（去掉了@IsMe注解）
public class MyDecorator implements RedaResultDecorator<MyData> {
    @Override
    public MyData decorate(MyData result) {
        result.setTotalNumber(result.getCatNumber() + result.getDogNumber());
        return result;
    }
}
```
###2）@RemoteData注解参数赋值

```java
import com.gtop.reda.core.annotation.RemoteData;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/rd")
public class TestController {

    //这里假设远程调用返回了catNumber和dogNumber，我们需要计算totalNumber
    @RemoteData(resultDecorator = MyDecorator.class, uris = "http://localhost:8080/getCatAndDog")
    @GetMapping("/get")
    public Result<MyData> test(ParamDTO dto) {
        return Result.success(new MyData());
    }

}
```


