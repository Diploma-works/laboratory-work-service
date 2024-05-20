# Сервис лабораторных работ по дисциплине "Высокопроизводительные системы"

Для обеспечения полноценной работы веб-приложения необходим запуск трех модулей системы:
1. [База данных](#база-данных)
2. [Back-end](#back-end)
3. [Front-end](#front-end)

## База данных

В качестве базы данных следует использовать PostgreSQL локально, либо же воспользоваться `docker-compose`, расположенным в [этой](back-end) папке. По умолчанию база данных использует порт `5432`.

Для работы веб-приложения необходимо создать таблицы и добавить функцию случайного выбора заданий для генерации вариантов заданий на лабораторные работы. Скрипты создания расположены в [этой](SQL) папке.

## Back-end

Для запуска модуля back-end необходима запущенная база данных, содержащая все необходимые таблицы и функцию из приложенных скриптов. Кроме того, необходимо добавить файл `application.properties` по пути `/src/main/resources`, который содержит:

```properties
# Пример содержимого application.properties
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.url=jdbc:postgresql://localhost:5432/${database-name}
spring.datasource.username=${username}
spring.datasource.password=${postgres}
spring.jpa.hibernate.ddl-auto=validate

jwt.secret=${jwt-secret}
jwt.lifetime=${jwt-lifetime}

spring.servlet.multipart.max-file-size=${max-file-size}
spring.servlet.multipart.max-request-size=${max-file-size}
```

Описание переменных:
- database-name - название базы данных
- username - имя пользователя СУБД
- password - пароль СУБД
- jwt-secret - Секретный ключ JWT
- jwt-lifetime - Время жизни JWT
- max-file-size - Максимальный размер файла, который можно будет получать запросом и хранить в приложении

## Front-end

Для запуска модуля front-end выполните следующие шаги:

1. Убедитесь, что у вас установлен Node.js и npm (Node Package Manager). Вы можете скачать и установить их с [официального сайта Node.js](https://nodejs.org/).

2. Перейдите в директорию с front-end проектом.

3. Установите необходимые зависимости, выполнив команду:
    ```sh
    npm install
    ```

4. Запустите локальный сервер разработки:
    ```sh
    npm start
    ```

5. Откройте браузер и перейдите по адресу:
    ```
    http://localhost:3000
    ```
   По умолчанию front-end сервер работает на порту 3000.
