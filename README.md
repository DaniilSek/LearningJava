# LearningJava

## 1. Объясните концепцию полиморфизма, абстракции, наследования и инкапсуляции в Java и приведите пример.
**Полиморфизм** - это когда объекты разных классов могут обрабатываться как объекты одного и того же суперкласса, но при этом их поведение будет зависеть от конкретного типа объекта. Например, есть класс **Animal**, от него наследуются классы **Dog** и **Cat**, у данных классов может быть одинаковый с классом **Animal** метод **whatAnimalSay()**, но у **Dog** допустим будет вывод "Гав", а у **Cat** будет вывод "Мяу".
```Java
class Animal {
    public void whatAnimalSay() { System.out.println("..."); }
}

class Dog extends Animal {
    @Override
    public void whatAnimalSay() { System.out.println("Гав"); }
}

class Cat extends Animal {
    @Override
    public void whatAnimalSay() { System.out.println("Мяу"); }
}

// Использование полиморфизма:
Animal animal = new Dog();
animal.whatAnimalSay(); // Выведет "Гав"
```
**Абстракция** - сокрытие деталей реализации класса и предоставление только интерфейса для взаимодействия с ним. Например класс **Shape** скрывает подробности вычисления площади внутри подклассов, предоставляя лишь интерфейс метода **area()**, который реализуется в конкретных формах (например, круге - класс **Circle**). Это позволяет добавлять новые формы без изменения существующего кода.
```Java
abstract class Shape {
    abstract double area();
    
    void displayInfo() {
        System.out.println("Форма");
    }
}
```
```Java
class Circle extends Shape {
    private final double radius;

    Circle(double r) {
        this.radius = r;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}
```
```Java
public class Main {
    public static void main(String[] args) {
        Shape shape = new Circle(5);
        System.out.println(shape.area()); // Вычислит площадь круга
    }
}
```
**Наследование** - повторное использования свойств и методов родительского класса дочерними классами. Например класс **Car** наследуется от базового класса **Vehicle**. Таким образом, автомобиль получает свойства и методы своего родителя, одновременно дополняя их собственными характеристиками.
```Java
class Vehicle {
    int speed;

    void startEngine() {
        System.out.println("Двигатель запущен");
    }
}
```
```Java
class Car extends Vehicle {
    String brand;

    Car(int s, String b) {
        super.speed = s;
        this.brand = b;
    }

    void accelerate() {
        System.out.println("Автомобиль ускоряется");
    }
}
```
```Java
public class Main {
    public static void main(String[] args) {
        Car car = new Car(100, "Toyota");
        car.startEngine(); // унаследованный метод
        car.accelerate();  // собственный метод
    }
}
```
**Инкапсуляция** - защита внутренних данных класса путём ограничения прямого доступа извне и предоставления специальных методов для управления ими. Переменная **balance** скрыта от внешнего мира и доступна только через специальные методы (**deposit**, **withdraw**, **getBalance**), которые контролируют доступ и обеспечивают безопасность операций над счётом.
```Java
class BankAccount {
    private double balance;

    public void deposit(double amount) {
        if(amount > 0)
            balance += amount;
    }

    public boolean withdraw(double amount) {
        if(balance >= amount && amount > 0) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }
}
```
```Java
public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        account.deposit(100);   // Пополнение счёта
        account.withdraw(50);   // Снятие денег
        System.out.println(account.getBalance()); // Получение баланса
    }
}
```

## 2. Что означает ключевое слово final перед объявлением переменной или метода?
**final** перед переменной означает, что она не может быть изменена, например:
```Java
final int x = 10;
x = 20; // тут будет ошибка компиляции
```
**final** перед методом запрещает переопределение метода. Также **final** может стоять перед классом, это означает, что класс нельзя наследовать.

## 3. Что такое перегрузка (overloading) и переопределение (overriding)? Приведите примеры каждого случая.
1. **Перегрузка (overloading)** - наличие в одном классе нескольких методов с одним именем, но различными параметрами (различные сигнатуры).
```Java
public class OverloadExample {

    public void display(String message) {
        System.out.println(message);
    }

    public void display(int number) {
        System.out.println(number);
    }
}
```
2. **Переопределение (overriding)** - подкласс заменяет реализацию метода суперкласса своей собственной версией (сигнатуры совпадают).
```Java
class Parent {
    public void greeting() {
        System.out.println("Hello from parent!");
    }
}
```
```Java
class Child extends Parent {
    @Override
    public void greeting() {
        System.out.println("Hello from child!");
    }
}
```
## 4. Какие ключевые особенности многопоточности в Java вам известны?
У каждого потока свой стек, но общая память: локальные переменные потокобезопасны, но объекты в **heap** могут быть общими и требуют синхронизации. Если два потока одновременно изменяют один объект, то возможна гонка за данные (**Race Condition**).  
Механизм **synchronized**, нужен для синхронизации. Например:
```Java
public synchronized void doWork() {
    System.out.println("synchronized");
}
```
только один поток сможет войти в этот метод при одном экземпляре объекта. 
Ключевое слово **volatile** - используется для примитивных типов данных, чтобы гарантировать видимость изменений среди потоков. Гарантирует атомичность чтения и записи значений, но не обеспечивает защиту сложных операций над ними.

## 5. Опишите разницу между методами equals() и hashCode(). Почему важно соблюдать связь между этими двумя методами?
Метод **equals()** используется для сравнения двух объектов по содержимому (логическому равенству).
Метод **hashCode()** вычисляет уникальный числовой идентификатор объекта. 
Если два объекта равны согласно **equals()**, то их **hashCode()** обязательно должен возвращать одно и то же значение. Когда переопределяешь для какого-то класса **equals()**, то и **hashCode()** необходимо переопределить.
```Java
public class Person {
    private String name;
    private int age;
    
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Person)) return false;
        
        Person other = (Person) obj;
        return Objects.equals(name, other.name) && age == other.age;
    }

    public int hashCode() {
        return Objects.hash(name, age);
    }
}

HashMap<Person, Integer> map = new HashMap<>();
Person p1 = new Person("Alice", 30);
Person p2 = new Person("Alice", 30);

map.put(p1, 1);
System.out.println(map.get(p2)); // 1
System.out.println(p1.equals(p2)); // true
```

## 6. Перечислите преимущества использования Collection Framework в Java.
**Collection Framework** предоставляет единый набор удобных интерфейсов **List, Set, Map, Queue**.
Легко менять реализацию без изменения логики: например, **ArrayList → LinkedList**, **HashMap -> LinkedHashMap**.
Все представленные типы данных имеют реализации с добавлением и удалением объектов внутри себя, а также поиск элементов.

## 7. Назовите три способа обработки исключений в Java.
1. Обработка через **try-catch** блок, где исключение перехватывается и обрабатывается внутри метода;
2. Проброс исключения через **throws**, в этом случае исключение не обрабатывается внутри текущего метода, оно отправляется коду, вызвавшему этот метод;
3. Использование **try-with-resources**, позволяет автоматически закрывать ресурсы, реализующие интерфейс **AutoCloseable**. Например при открытии текстового файла.

## 8. Что такое класс Object в Java и почему он важен?
Все классы **Java** наследуют от класса **Object** методы **equals()** и **hashCode()**. Данные методы помогают проверять равенство объектов и осуществлять поиск по хэшкоду объекта. Если переопределяется метод **equals()**, то и **hashCode()** следует переопределить, чтобы не было коллизий. Также часто используемый метод **toString()** для представления объекта в виде строки, который тоже можно переопределить для конкретного объекта.

## 9. Объясните разницу между ==, equals() и compareTo() методами в Java. Когда какой из них следует использовать?
1. Оператор **==**: Проверяет, ссылаются ли две переменные на один и тот же объект в памяти или сравнивает примитивные типы.
2. Метод **equals()**: Используется для семантического сравнения двух объектов, проверяя, равны ли их состояния. Необходимо переопределять этот метод в собственных классах, если нужно сравнивать содержимое объектов.
3. Метод **compareTo()**: Применяется в интерфейсе **Comparable**. Этот метод возвращает отрицательное число, ноль или положительное число, показывая порядок следования элементов при сортировке.

## 10. Что означает термин “исключение” (exception) в Java? Какие бывают виды исключений и какова разница между ними?
Исключение — это событие, которое нарушает нормальный ход выполнения программы. Все исключения происходят их класса **java.lang.Throwable**:
```Java
Throwable
├── Error → системные фатальные ошибки (не нужно и не стоит ловить)
└── Exception
    ├── RuntimeException → непроверяемые исключения (Unchecked Exception)
    └── Checked Exceptions → проверяемые исключения
```
1. **Checked Exceptions** - должны быть объявлены или обработаны с помощью **try-catch** или **throws**. Компилятор проверяет их наличие и выдаст предупреждение, что такая ошибка может возникнуть при выполнении программы и ее стоит обработать.<br>
Примеры:<br>
**IOException**<br>
**SQLException**<br>
**URISyntaxException**<br>
**ClassNotFoundException**
3. **Unchecked Exception** - не требуют обязательной проверки, могут возникнуть в любом контексте (ошибки логики программы).<br>
Примеры:<br>
**NullPointerException**<br>
**IllegalArgumentException**<br>
**ArrayIndexOutOfBoundsException**<br>
**ArithmeticException** (например, деление на 0)
3. **Error** - фатальные проблемы с которыми приложение не может бороться, не требуют перехвата.<br>
Примеры:<br>
**OutOfMemoryError** — закончилась память<br>
**StackOverflowError** — бесконечная рекурсия<br>
**NoClassDefFoundError** — класс был, но не найден при запуске<br>

## 11. Опишите принцип работы механизма сборки мусора (GC) в Java. Какие существуют стратегии GC и как выбрать подходящую стратегию для приложения?
Принцип работы **Garbage Collector**<br>
**Garbage Collector**/**GC** (сборщик мусора) - это компонент виртуальной машины **Java (JVM)**, который автоматически освобождает память, он периодически проверяет наличие ссылок на объект. Объекты, на которые больше нет активных ссылок, считаются мусором и подлежат удалению.
1. Когда программа создает новый объект, память выделяется в куче (**heap**).
2. После завершения работы с объектом ссылка на него теряется.
3. Через некоторое время сборщик мусора определяет, что этот объект никому не нужен, и освобождает занимаемую им память.<br>

**Heap** делится на поколения:
1. **Young Generation** (Молодое поколение): Новые объекты попадают сюда изначально. Эта область быстро освобождается, поскольку большинство новых объектов умирают вскоре после своего создания.
2. **Old Generation** (Старое поколение): Здесь хранятся долгоживущие объекты, которые пережили очистку **Young Generation**.
3. **Permanent Generation** (Перманентное поколение): Содержит метаданные классов и структуры **JVM** (до Java 8).

Сборщик работает следующим образом:
1. **Маркировка живых объектов**: Объекты, достижимые из корней (локальных переменных, стека вызовов), помечаются как "живые".
2. **Удаление мертвых объектов**: Недостижимые объекты собираются и уничтожаются.
3. **Компактирование**: Оставшиеся объекты перемещаются, уплотняя память.

**Основные алгоритмы GC**:<br>
**Serial Collector** - Простой последовательный алгоритм. Подходит для небольших приложений и однопроцессорных машин.<br>
**Parallel Collector** - Выполняет сборку мусора параллельно, улучшая производительность на многоядерных системах.<br>
**CMS (Concurrent Mark-Sweep)** - Работает одновременно с приложением, уменьшая паузы остановки.<br>
**G1 (Garbarge First)** - Современный алгоритм, используемый по умолчанию в большинстве случаев. Ориентирован на минимизацию задержек и позволяет задать цели производительности.<br>
Выбор подходящей стратегии зависит от требований приложения:<br>
Для серверных приложений предпочтителен **G1** или **CMS**.<br>
Для небольших десктоп-приложений подойдет **Serial** или **Parallel**.

## 12. Объясните разницу между интерфейсами Collection, List и Set. Приведите пример ситуации, когда лучше использовать каждый из них
Эти три интерфейса являются частью **Java Collection Framework** и связаны между собой по наследованию.
```Java
Collection (интерфейс)
├── List (интерфейс)
│   ├── ArrayList
│   ├── LinkedList
│   └── Vector
└── Set (интерфейс)
    ├── HashSet
    ├── LinkedHashSet
    └── TreeSet
```
```
List<E>
```
Интерфейс **List** представляет упорядоченную коллекцию элементов, допускающую дублирование. Каждый элемент имеет индекс, доступ к нему осуществляется по этому индексу. Это полезно, когда порядок важен и нужен быстрый доступ к элементам по индексу. Например, список товаров в корзине покупателя.
```
Set<E>
```
Интерфейс **Set** гарантирует уникальность элементов внутри коллекции. Порядок хранения не гарантируется (если не используется **SortedSet**). Полезно, когда важно отсутствие повторяющихся значений. Например, хранение уникальных идентификаторов пользователей.

## 13. Что такое дженерики (Generics) в Java и зачем они нужны?
Дженерики (**Generics**) — это механизм параметризации типов, он позволяет писать классы, интерфейсы и методы, которые работают с произвольными типами, при этом сохраняя типобезопасность на этапе компиляции. 
```Иными словами - Дженерики позволяют создавать "шаблоны", где тип указывается при использовании, а не при написании кода.``` 
Все коллекции (**List, Set, Map**) используют дженерики.
```Java
List<String> list = new ArrayList<>();
list.add("Привет");
// list.add(123); → Ошибка компиляции
String s = list.get(0); // Не нужно приведение типов
```
```Java
public class Box<T> {
    private T content;
    
    public void setContent(T t) { this.content = t; }
    public T getContent() { return content; }
}

Box<Integer> boxInt = new Box<>(); // Хранит целые числа
boxInt.setContent(10);
Integer value = boxInt.getContent();
```

## 14. Расскажите, как устроено наследование в Java. Что значит ключевое слово final применительно к классу и методу? Можете привести пример переопределения метода?
Наследование классов в **Java**:
Один класс может наследоваться от другого, перенимая или переопределяя под себя родительские методы. Наследоваться (**extends**) можно только от одного класса, но если класс является интерфейсом, то можно имплементировать (**implements**) множественное кол-во интерфейсов.
Пример наследование с переопределением метода:
```Java
public class Animal {
    public void animalSound() {
        System.out.println("");
    }
}

public class Dog extends Animal {
    @Override
    public void animalSound() {
        System.out.println("Гав");
    }
}
```
**final** - означает неизменность. Если применить его к классу, то от такого класса нельзя будет наследоваться, если применить его к методу, то такой метод нельзя будет переопределить.

## 15. Опишите принцип работы многопоточности в Java. Чем отличается создание потока через реализацию интерфейса Runnable от расширения класса Thread? Какие проблемы возникают при работе с потоками и как их избежать?
Многопоточность — это способность **JVM** выполнять несколько потоков одновременно, что позволяет эффективно использовать ресурсы процессора, особенно при работе с сетью, файлами, асинхронными задачами.
1. Каждый поток — это отдельная последовательность выполнения (**thread of execution**);
2. Все потоки разделяют одну память (**heap**), но имеют собственный стек;
3. Управление потоками осуществляет **JVM** и операционная система.
Интерфейс **Runnable** определяет контракт для запуска процесса в отдельном потоке. Чтобы создать поток таким способом, нужно создать класс, реализующий интерфейс **Runnable**, а затем передать экземпляр этого класса конструктору класса **Thread**.
```Java
class MyRunnable implements Runnable {
    @Override
    public void run() {
        for(int i=0; i<5; i++) {
            System.out.println(Thread.currentThread().getName());
        }
    }
}

MyRunnable r = new MyRunnable();
Thread t1 = new Thread(r);
t1.start();
```
Когда создаётся поток путём расширения класса **Thread**, создается новый подкласс класса **Thread**. Каждый экземпляр такого подкласса представляет отдельный поток исполнения.
```Java
class MyThread extends Thread {
    @Override
    public void run() {
        for(int i=0; i<5; i++) {
            System.out.println(Thread.currentThread().getName());
        }
    }
}

MyThread t1 = new MyThread();
t1.start();
```
Итог: использовать реализацию интерфейса **Runnable** предпочтительнее, поскольку это способствует лучшей структуре программы и сохраняет её открытость для повторного использования и расширения.
**Проблемы многопоточности:**
1. **Deadlock:** взаимоблокировка двух или более потоков, ожидающих друг друга.
2. **Race condition:** непредсказуемый результат операций, выполняемых несколькими потоками параллельно.
3. **Starvation:** ситуация, когда поток бесконечно долго ожидает ресурс.
**Решение проблем:**
1. Синхронизация методов или блоков через ключевые слова **synchronized, ReentrantLock**.
2. Использование механизма синхронизации из пакета **java.util.concurrent** (например: **CountDownLatch, CyclicBarrier**).
3. Применение паттернов проектирования, таких как **Producer-Consumer**.

## 16. Что такое сериализация объектов в Java и для чего она применяется? Какой интерфейс реализует класс, поддерживающий сериализацию?
Сериализация объектов - это преобразования объекта в поток байтов, который можно:
1. Сохранить в файл;
2. Передать по сети;
3. Хранить в базе данных;
4. Отправить через REST API.
Для этого требуется интерфейс **java.io.Serializable**
1. Процесс восстановления объекта из сохранённого состояния называется десериализацией.
2. Для контроля процесса сериализации/десериализации можно реализовать специальные методы **writeObject** и **readObject**.
3. Интерфейс **Serializable** сам по себе пустой (**marker interface**), никаких обязательных методов не требует.
4. Переменные, объявленные как **transient**, игнорируются при сериализации.
Пример (класс **Person** реализует интерфейс **Serializable**):
```Java
public static void main(String[] args) throws IOException, ClassNotFoundException {
        Person person = new Person("Иван", 30);

        // Сериализация
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("person.ser"))) {
            out.writeObject(person);
            System.out.println("Объект сериализован");
        }

        // Десериализация
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("person.ser"))) {
            Person restored = (Person) in.readObject();
            System.out.println("Восстановлен: " + restored.getName() + ", " + restored.getAge());
        }
    }
```

## 17. Что такое аннотации в Java? Зачем они используются? Приведите пару примеров стандартных аннотаций и расскажите, как их применять.
Аннотации — это специальные метки, которые можно ставить на классы, методы, поля, параметры и другие элементы кода, это способ "дать указания" коду без изменения его структуры.
Примеры:
1. Из стандартного пакета **Java**:
```Java
@Override - переопределения метода.
@Deprecated - метод или класс устарели и не рекомендуются к использованию.
@FunctionalInterface - функциональный интерфейс (интерфейс с одним методом).
```
3. Из пакета **Lombok**:
```Java
 @Getter - генерирует геттеры.
 @Setter - генерирует сеттеры.
 @AllArgsConstructor - конструктор со всеми полями.
 @NoArgsConstructor - пустой конструктор.
 @Data - комбинирует несколько аннотаций (@Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor).
```

## 18. Какой цикл управляет жизненным циклом сервлетов в веб-приложениях Java EE? Перечислите этапы жизненного цикла сервлета и объясните их назначение.
В веб-приложениях Java EE жизненным циклом сервлетов управляет сервлет-контейнер, он полностью создаёт, управляет и уничтожает экземпляры сервлетов. Каждый сервлет проходит через следующие стадии:
1. Инициализация (init()) - при первом запросе к сервлету или при запуске приложения. Контейнер вызывает метод init(ServletConfig config). Создаётся один экземпляр сервлета (по умолчанию — один на весь контекст). Сервлет может инициализировать ресурсы: подключение к БД, кэш, конфигурацию.
```Java
public class MyServlet extends HttpServlet { 
    private String appConfig;
    
    @Override
public void init() throws ServletException { 
        ServletConfig config = getServletConfig(); 
        appConfig = config.getInitParameter("config"); 
        System.out.println("Сервлет инициализирован с: " + appConfig); 
    }
}
```
2. Обработка запросов (service()) - при каждом HTTP-запросе к сервлету (GET, POST, PUT). Контейнер вызывает метод service(HttpServletRequest req, HttpServletResponse resp). Метод service() автоматически определяет тип запроса и перенаправляет его, например:doGet() → для GET, doPost() → для POST.
```Java
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/html");
    PrintWriter out = resp.getWriter();
    out.println("<h1>Hello from Servlet!</h1>");
}
```
3. Уничтожение (destroy()) - перед тем как контейнер выгрузит сервлет из памяти, обычно при остановке или обновлении приложения. Вызывается метод destroy(). Здесь нужно освободить ресурсы: закрыть соединения с БД, остановить потоки, сохранить состояние.
```Java
@Override
public void destroy() {
    System.out.println("Сервлет уничтожается. Освобождаем ресурсы...");    
    // Например: dbConnection.close();
}
```

Диаграмма жизненного цикла:
[Загрузка сервлета]
↓ init() → один раз
↓ service() → многократно (на каждый запрос)
↓ destroy() → один раз (при остановке)

Пример полного сервлета:
```Java
public class HelloServlet extends HttpServlet {
    private String message;

    @Override
    public void init() throws ServletException {
        message = "Привет от сервлета!";
        System.out.println("Инициализация завершена");
}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    @Override
    public void destroy() {
        System.out.println("Сервлет уничтожен.");
        message = null;
    }
}
```

## 19. Расскажите про Stream API.
Это способ обработки данных в Java. Он позволяет описывать, что нужно сделать с данными (например, «отфильтровать и посчитать»), а не как это делать. Весь процесс работы делется на промежуточные операции и терминальные операции.  
**Промежуточные операции** - сами по себе они ничего не делают, они просто настраивают логику. Их главная особенность — **ленивость** (Laziness). Код не выполнится, пока не будет вызвана терминальная операция.  
Примеры промежуточных операций:  
```.filter()``` - фильтрация. Пропускает только те элементы, которые удовлетворяют условию;  
```.map()``` - преобразование. Берёт элемент и превращает его в другой объект;  
```.flatMap()``` - преобразование со «сплющиванием». Берёт элемент, превращает его в поток, и объединяет все эти потоки в один;  
```.sorted()``` - сортировка;  
```.distinct()``` - удаление дубликатов;  
```.peek()``` -  для отладки. Позволяет посмотреть на элемент, не меняя его.  
**Терминальные операции** - запускают всю цепочку промежуточных операций, обрабатывают данные и возвращают конечный результат или побочный эффект (например, вывод на экран). После терминальной операции поток считается **«использованным»** и закрывается.  
Примеры терминальных операций:  
```.forEach()``` -  перебор элементов (например, вывод в консоль);  
```.collect()``` - сбор результата в коллекцию (List, Set, Map) или строку;  
```.count()``` - подсчёт количества элементов;  
```.findFirst()/.findAny()``` - поиск элемента;  
```.reduce()``` - свёртка элементов в одно значение (например, сумма);  
```.anyMatch()/.allMatch()/.noneMatch()``` - проверка условий на всём потоке.  

Примеры:  
```Java
List<String> words = List.of("Привет", "мир", "Java");

// Получить список длин этих слов
// map берёт одно слово и превращает его в одно число (длину)
List<Integer> lengths = words.stream()
    .map(String::length) // "Привет" -> 6, "мир" -> 3, "Java" -> 4
    .collect(Collectors.toList());
// Результат: [6, 3, 4]

// Получить список всех букв из всех слов
// flatMap берёт слово, превращает его в поток букв, и объединяет все буквы в один общий поток
List<String> letters = words.stream()
    .flatMap(word -> Arrays.stream(word.split(""))) // "Привет" -> ["П","р","и","в","е","т"]
    .collect(Collectors.toList());
// Результат: ["П", "р", "и", "в", "е", "т", "м", "и", "р", "J", "a", "v", "a"]
```

```Java
class User {
    String name;
    int age;
    public User(String name, int age) { this.name = name; this.age = age; }
    public String getName() { return name; }
    public int getAge() { return age; }
}
List<User> users = List.of(
    new User("Иван", 25),
    new User("Анна", 30),
    new User("Петр", 25)
);

// Собрать всех пользователей в List
List<String> names = users.stream()
    .map(User::getName) // Преобразуем User -> String (имя)
    .collect(Collectors.toList());
// names -> ["Иван", "Анна", "Петр"]

// Собрать всех пользователей в Map
// ключ — имя, а значение — возраст
// если имена повторяются - оставляем старое значение
Map<String, Integer> nameToAge = users.stream()
    .collect(Collectors.toMap(
        User::getName,      // Ключ: имя
        User::getAge,       // Значение: возраст
        (existingValue, newValue) -> existingValue // Если ключи совпали, оставить старое
    ));
// Результат: {"Иван"=25, "Анна"=30, "Петр"=25}

// Вывод в консоль пользователей старше 25 лет
users.stream()
    .filter(user -> user.getAge() > 25) // Промежуточная операция: фильтруем по возрасту
    .forEach(user -> System.out.println(user.getName())); // Терминальная: выводим имя
// В консоль выведется:
// Анна
```

# Практика.

## 1. Напишите программу, которая находит максимальное число в массиве целых чисел.
Решение:
```Java
import java.util.Arrays;

class Main {
    public static int findMax(int[] arr) {
        if (arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }
        
        int maxValue = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > maxValue) {
                maxValue = arr[i];
            }
        }
        return maxValue;
    }

    public static void main(String[] args) {
        int[] numbers = {3, 5, 1, 8, 2};
        System.out.println(findMax(numbers)); // Ответ: 8
    }
}
```

## 2. Реализуйте метод, который возвращает отсортированный список строк в алфавитном порядке.
Решение:
```Java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Main {
    public static List<String> sortStrings(List<String> strings) {
        Collections.sort(strings);
        return strings;
    }

    public static void main(String[] args) {
        List<String> words = new ArrayList<>(Arrays.asList("apple", "banana", "cherry"));
        System.out.println(sortStrings(words)); // Output: ["apple", "banana", "cherry"]
    }
}
```
## 3. Вам дано приложение электронной библиотеки, где пользователи могут искать книги по названию и авторам. Необходимо написать метод, который ищет книгу по фрагменту названия или фамилии автора. Библиотека представлена списком книг (класс Book), каждая книга имеет название (title) и автора (author).
Требуется реализовать метод поиска с учётом следующего поведения:
Поиск нечувствителен к регистру символов.
Пользователь вводит фрагмент текста, который может содержать части названия или имени автора.
Результат должен включать все книги, содержащие указанный фрагмент в названии или фамилии автора.
Класс Book:
```Java
public class Book {
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
```
Решение:
```Java
    public List<Book> searchBooksByFragment(List<Book> books, String fragment) {
        // Реализуйте метод поиска здесь
        if (books == null || books.isEmpty()){
            return Collections.emptyList();
        }

        List<Book> resultList = new ArrayList<>();
        for (Book book : books) {

            String searchIn = (book.getTitle() + book.getAuthor()).toLowerCase().replaceAll("\\s+", "");
            fragment = fragment.toLowerCase().replaceAll("\\s+", "");

            if (searchIn.contains(fragment)) {
                resultList.add(book);
            }
        }
        return resultList;
    }
```

## 4. Реализуйте метод, который группирует студентов по факультетам и выводит средний балл успеваемости каждого студента в рамках своего факультета. Студенты представлены в списке объектов класса Student, содержащего следующую структуру:
Класс Student:
```Java
public class Student {
    private String fullName;
    private String faculty;
    private double averageScore;

    public Student(String fullName, String faculty, double averageScore) {
        this.fullName = fullName;
        this.faculty = faculty;
        this.averageScore = averageScore;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFaculty() {
        return faculty;
    }

    public double getAverageScore() {
        return averageScore;
    }

    @Override
    public String toString() {
        return "Student{" +
                "fullName='" + fullName + '\'' +
                ", faculty='" + faculty + '\'' +
                ", averageScore=" + averageScore +
                '}';
    }
}
```
Решение:
```Java
    public Map<String, Double> getAvgScoreByFaculty(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return Collections.emptyMap();
        }

        return students.stream()
                .collect(Collectors.groupingBy(
                        Student::getFaculty,
                        Collectors.averagingDouble(Student::getAverageScore)));

    }
```

## 5. Необходимо разработать метод, который вычисляет суммарную стоимость заказов, находящихся в статусе "Ожидает оплаты" ("WAITING_FOR_PAYMENT"). Заказ представлен объектом класса Order, который содержит следующую структуру:
Класс Orders:
```Java
public class Order {
    private Long id;
    private BigDecimal totalAmount; // Общая сумма заказа
    private Status status;          // Текущий статус заказа

    public enum Status {
        WAITING_FOR_PAYMENT, SHIPPED, DELIVERED, RETURNED
    }

    public Order(Long id, BigDecimal totalAmount, Status status) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                '}';
    }
}
```
Решение:
```Java
    // Для подсчета суммы только по статусу WAITING_FOR_PAYMENT
    public BigDecimal getWaitingForPaymentSum(List<Order> orders) {

        if (orders == null || orders.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return orders.stream()
                .filter(order -> order.getStatus().equals(Order.Status.WAITING_FOR_PAYMENT))
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    // Для подсчета сумм в разрезе всех статусов и вывода в Map
    public Map<Order.Status, BigDecimal> getOrdersByStatusSum(List<Order> orders) {

        if (orders == null || orders.isEmpty()) {
            return Collections.emptyMap();
        }

        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getStatus,
                        Collectors.collectingAndThen(
                                Collectors.reducing(BigDecimal.ZERO, Order::getTotalAmount, BigDecimal::add),
                                sum -> sum.setScale(2, RoundingMode.HALF_UP)
                        )));
    }
```

## 5. Разработайте метод, который берет список записей банковских транзакций и вычисляет баланс счета клиента. Каждая запись транзакции представлена объектом класса Transaction, имеющим следующую структуру:
Класс Transaction:
```Java
public class Transaction {
    private LocalDateTime timestamp; // Время транзакции
    private BigDecimal amount;       // Сумма транзакции (+/-)
    private Type type;               // Тип транзакции (DEPOSIT или WITHDRAWAL)

    enum Type {
        DEPOSIT, WITHDRAWAL
    }

    public Transaction(LocalDateTime timestamp, BigDecimal amount, Type type) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "timestamp=" + timestamp +
                ", amount=" + amount +
                ", type=" + type +
                '}';
    }
}
```
Решение:
```Java
    public BigDecimal getBalance(List<Transaction> transactions) {

        if (transactions == null || transactions.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return transactions.stream()
                .map(transaction -> transaction.getType() == Transaction.Type.DEPOSIT ? transaction.getAmount() : transaction.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

    }
```
