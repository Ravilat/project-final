## [REST API](http://localhost:8080/doc)

## Концепция:

- Spring Modulith
    - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

- Есть 2 общие таблицы, на которых не fk
    - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
    - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем
      проверять

## Аналоги

- https://java-source.net/open-source/issue-trackers

## Тестирование

- https://habr.com/ru/articles/259055/

# Список выполненных задач:

2) \+, не убрано из .css файлов

3) \+

4) +/-, переделать и запустить тесты смог, но часть тестов все равно не проходят (в Postgres все проходят)
Оставил закомментированные настройки для тестов в H2 в application-test.yaml

5) \+, через H2 не работает, в Postgres работает

6) \+

7) \+, добавил методы setTag, deleteTag, getAllTagsFromTask, getTasksByTag в TaskController, с соответствующей реализацией
сервис методов в TaskService. Сущность Tag не делал, т.к. в Task - поле tags с аннотацией @CollectionTable.

8) \+, добавил в TaskService два метода, getTimeTaskAtWorking(Task task) и getTimeTaskAtTesting(Task task), которые 
через таблицу activity вычисляют время работы и тестирования задачи по дате изменения статуса. Не добавлял записи в activity
через changelog.sql, т.к. записи можно добавить в процессе работы с приложением, последовательно изменяя статус задачи.
Непонятно, что дальше с этими методами делать, в описании не сказано.

9) \+

10) \+ docker-compose завелся, но есть нюансы
    1. была ошибка в com.javarush.jira.common.internal.config.RestAuthenticationEntryPoint, при запуске через
    docker-compose создавалось два бина с одинаковым типом и Spring ругался. ИИ подсказал сделать через
    List<HandlerExceptionResolver> resolvers и тогда все заработало
    2. для того, чтобы при запуске через docker-compose база заполнялась стартовыми значениями, добавил строку-ссылку
    в changelog.sql для запуска data.sql после создания таблиц. Для этого data.sql должна быть в папке resources
    (скопировал data.sql)
    
11) \+, добавил два класса конфигурации: LocaleConfig и LocaleInterceptorConfig, а также 3 файла messages.properties.
    Локализация устанавливается за счет параметров запроса ?lang=ru/en.
    После добавления локализации посыпался метод createProblemDetail в классе 
    com.javarush.jira.common.internal.web.RestExceptionHandler (доработал).
    

P.S. Регистрация работает при настройке рабочей почты для отправки и соответствующем внешнем пароле
(кто-то решил в группе, проверил, но не стал выкладывать свой пароль, оставил как было)

