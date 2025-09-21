## Финальный проект JavaRush университета

Доска задач по типу Jira или Trello

Технологии
- Spring Boot
- Spring JPA
- Hibernate
- PostgreSQL
- Liquibase
- Spring Security
- Spring MVC
- Thymeleaf
- jQuery
- Swagger
- Caffeine
- Lombok
- Mapstruct
- Spring Test
- JUnit
- Docker

Твои задачи на выбор

1) Ознакомиться с проектом
2) Удалить социальные сети: vk, yandex
3) Вынести чувствительную информацию в отдельный проперти файл
4) Переделать тесты так, чтоб во время тестов использовалась in memory БД (H2), а не PostgreSQL
5) Написать тесты для всех публичных методов контроллера ProfileRestController
6) Сделать рефакторинг метода com.javarush.jira.bugtracking.attachment.FileUtil#upload чтоб он использовал современный подход для работы с файловой системой
7) Добавить новый функционал: добавления тегов к задаче (REST API + реализация на сервисе). Фронт делать необязательно. Таблица task_tag уже создана
8) Добавить подсчет времени сколько задача находилась в работе и тестировании.
9) Написать Dockerfile для основного сервера 
10) Написать docker-compose файл для запуска контейнера сервера вместе с БД и nginx
11) Добавить локализацию минимум на двух языках для шаблонов писем (mails) и стартовой страницы index.html
12) Переделать механизм распознавания «свой-чужой» между фронтом и беком с JSESSIONID на JWT

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
    docker-compose создавалось два бина с одинаковым типом и Spring ругался.
    Сделал List<HandlerExceptionResolver> resolvers и тогда все заработало
    2. для того, чтобы при запуске через docker-compose база заполнялась стартовыми значениями, добавил строку-ссылку
    в changelog.sql для запуска data.sql после создания таблиц. Для этого data.sql должна быть в папке resources
    (скопировал data.sql)
    
11) \+, добавил два класса конфигурации: LocaleConfig и LocaleInterceptorConfig, а также 3 файла messages.properties.
    Локализация устанавливается за счет параметров запроса ?lang=ru/en.
    После добавления локализации посыпался метод createProblemDetail в классе 
    com.javarush.jira.common.internal.web.RestExceptionHandler (доработал).
    

P.S. Регистрация работает при настройке рабочей почты для отправки и соответствующем внешнем пароле
(кто-то решил в группе, проверил, но не стал выкладывать свой пароль, оставил как было)

