# xlsx-reader

是一个xlsx解析器，与poi不同的是xlsx-reader只支持row-by-row的方式读取文件中的每行数据，由于不需要将整个DOM树载入到内存，xlsx-reader具有更小的内存占用。

xlsx-reader适用于以较小的内存占用从xlsx格式的文件中读入java对象，具体的使用请见示例。

## 快速开始

对于maven，先引入依赖

```xml
<dependency>
  <groupId>io.github.walterinkitchen</groupId>
  <artifactId>mini-xlsx-reader</artifactId>
  <version>1.0.1</version>
</dependency>
```

我们希望从下面的xlsx文件中读出所有的people对象。

![image-20220913224718431](assets/image-20220913224718431.png)

定义一个我们要从xlsx中读出的类；

```java
@Data
@Sheet
public class People {
    @Column(name = "id")       //映射到id列
    private String id;

    @Column(name = "name")	   //映射到name列
    private String name;

    @Column(name = "country")	 //映射到country列
    private String country;

    @Column(name = "gender")	 //映射到gender列
    private String gender;
}
```

提供一个xlsx文件，一个期望解析的class就可用迭代器解析出实例

```java
public static void main(String[] args) throws URISyntaxException {
  File file = openFile("people.xlsx");
  try (EntityIterator<People> iterator = EntityIteratorFactory.buildXlsxEntityIterator(file, People.class)) {
    while (iterator.hasNext()) {
      People next = iterator.next();
      System.out.println(next);
    }
    long total = Runtime.getRuntime().totalMemory() / (1024 * 1024);
    long max = Runtime.getRuntime().maxMemory() / (1024 * 1024);
    System.out.println("total:" + total + " max:" + max);
  } catch (Exception e) {
    throw new RuntimeException(e);
  }
}

private static File openFile(String srcFile) throws URISyntaxException {
  URL resource = MiniReader.class.getClassLoader().getResource("people.xlsx");
  return new File(resource.toURI());
}
```

