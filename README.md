# StartFromZero-JavaCore

# Java8 新特性

## 一、Lambda 表达式

Lambda表达式是一个匿名函数，一段可传递的java代码。

### 1. Lambda表达式基本语法

Java8中新增操作符`->` 

- 操作符左侧：表达式的参数列表；

- 操作符右侧：表达式执行的功能，称为Lambda体。

接口中仅包含一个抽象方法的接口称为**函数式接口**，可以使用`@FunctionalInterface`注解修饰，编译器可检验函数式接口的合法性。Lambda表达式需要只能用在函数式接口中使用。

### 2. 语法格式

- 无入参，无返回值：`() -> Lambda体`
- 有入参，有返回值，且Lambda体包含多条语句：`(参数1, 参数2, 参数3, ... 参数N) -> {语句1;语句2; return x;}`
  - 参数类型可以不指定，编译器可根据上下文推断
  - 仅包含一个入参，`()`可以省略
  - Lambda体仅包含一条语句，`return`和`{}`可以省略

### 3. Java8 四大核心函数式接口

- `Consumer<T>` ：消费型接口，`void accept(T t);`
- `Supplier<T>`：供给类接口，`T get();`
- `Function<T, R>`：函数型接口，`R apply(T t);`
- `Predicate<T>`：匹配型接口，`boolean test(T t);`

### 4. 方法引用

若Lambda体中的内容有方法已经实现了，可以使用方法引用语法。

- 对象::实例方法名
- 类::静态方法名
- 类::实例方法名，第一个入参是实例方法的调用者，后续入参是实力方法的入参时，可使用，例如:`String::equals`

注意，Lambda体中调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的参数列表和返回值类型保持一致。

### 5. 构造器引用

类型::new

### 6. 数组引用

类型[]::new

## 二、Stream API

Stream是Java8中处理集合的关键抽象概念，能够对集合执行复杂的查找、过滤、映射等操作。集合讲的是数据，流讲得是计算。

- Stream自己不会存储元素
- Stream不会改变源对象，只会生成一个新的Stream
- Stream操作是延迟执行的，在需要结果的时候执行

### 1. Stream的三个操作步骤

- 创建Stream
  - Collection集合的`stream()`（串行流）或`parallelStream()`（并行流）创建流
  - Arrays.stream()创建流
  - Stream.of()方法创建流
  - 无限流
    - Stream.iterate()
    - Stream.generate()
- 中间操作
  - filter()：过滤元素
  - distinct()：去重
  - map()：映射
  - flatMap()：拉平映射
  - sort()：排序
- 终止操作
  - allMatch()
  - anyMath()
  - noneMatch()
  - findFirst()
  - findAny()
  - count()
  - max()
  - min()
  - reduce()：归约，将流中元素反复结合起来，返回一个值
  - collect()：收集，将流转化为其他形式

### 2 并行流

Fork/Join框架：将一个大任务拆分成若干小人物，再将一个个的小任务运算的结果进行Join汇总。

- parallel()：并行流

## 三、Optional类

Optional代表一个容器类，表示存在或不存在。

## 四、接口中的默认方法和静态方法

接口中使用default修饰提供具体实现的默认方法。

接口中使用static修饰提供具体实现的方法。

## 五、新的日期API

java.time包

- LocalDate
- LocalTime
- LocalDateTime