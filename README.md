# Cinema (Ticket sales service)

<a name="menu"></a>
<ul>
    <li>
        <a href="#about">О проекте</a>
        <ul>
            <li><a href="#description">Описание</a></li>
            <li><a href="#technologies">Технологии</a></li>
            <li><a href="#scheme">Схема БД</a></li>
        </ul>
    </li>
    <li>
        <a href="#build">Сборка</a>
    </li>
    <li>
        <a href="#usage">Использование</a>
        <ul>
            <li><a href="#authorization">Авторизация</a></li>
            <li><a href="#authentication">Аутентификация</a></li>
            <li><a href="#buy">Покупка билетов</a></li>
            <li><a href="#validation">Валидация Данных</a></li>
        </ul>
    </li>
    <li>
        <a href="#contact">Контакты</a>
    </li>
</ul>

<h2><a name="about">О проекте</a>&nbsp;&nbsp;<a href="#menu">&#9650;</a></h2>
<h4><a name="description">Описание</a>&nbsp;&nbsp;<a href="#menu">&#9650;</a></h4>

<p>
    Это приложение - часть сервиса по онлайн бронированию билетов в кинотеатр.<br>
    Войдя на сайт пользователь может посмотреть актуальные сеансы и подобрать удобные места и время сеанса.
    Для покупки билета пользователь должен войти на сайт под своим логином и паролем или пройти регистрацию.
</p>

<h4><a name="technologies">Технологии</a>&nbsp;&nbsp;<a href="#menu">&#9650;</a></h4>
<ul>
    <li>Java 17</li>
    <li>Spring boot</li>
    <li>PostgreSQL, Sql2o, Liquibase</li>
    <li>JUnit, Mockito</li>
    <li>Maven, Tomcat</li>
    <li>Thymeleaf, Bootstrap</li>
</ul>

<h4><a name="scheme">Db schema</a>&nbsp;&nbsp;<a href="#menu">&#9650;</a></h4>
<p align="center">
  <img src="images/" height="400" title="Db-schema">
</p>

<h2><a name="build">Сборка</a>&nbsp;&nbsp;<a href="#menu">&#9650;</a></h2>
<ol>
    <li>
        Для успешной сборки и работы проекта на вашем компьютере должны быть установлены:
        <ol>
            <li>JDK 17(+)</li>
            <li>Maven</li>
            <li>PostgreSQL</li>
            <li>Tomcat</li>
        </ol>
    </li>
    <li>
        В PostgreSQL создайте базу с именем "job4j_cinema"
    </li>
    <li>
        Скачайте проект к себе на компьютер с помощью команды<br>
        <code>git clone https://github.com/KvardakovMax/job4j_cinema.git</code><br>
    </li>
    <li>
``` 
Todo db connection 
```
</li>
    <li>
        Выполните команду <code>mvn install</code> в корне проекта для его сборки<br>
        С помощью Liquibase произайдёт миграция БД.<br>
        В случае успешной сборки появится файл <code>target/job4j_cinema-&#60;version&#62;.war</code><br>
        переименуйте его в <code>cinema.war</code>
    </li>
    <li>
        Для запуска веб-приложения вам нужно скопировать <code>cinema.war</code> в папку <code>webapps</code> вашего Tomcat
    </li>
    <li>
        После запуска сервера приложение будет доступно по адресу<br>
        <a href="http://localhost:8080/cinema/">http://localhost:8080/cinema/</a>
    </li>
</ol>

<h2><a name="usage">Использование</a>&nbsp;&nbsp;<a href="#menu">&#9650;</a></h2>
<h3><a name="authorization">Authorization</a>&nbsp;&nbsp;<a href="#menu">&#9650;</a></h3>
<p>
    -----
</p>
<p align="center">
  <img src="images/" height="400" title="Authorization">
</p>

<h3><a name="authentication">Authentication</a>&nbsp;&nbsp;<a href="#menu">&#9650;</a></h3>
<p>
    -----
</p>
<p align="center">
  <img src="images/" height="400" title="Authentication">
</p>

<h3><a name="buy">Buying a ticket</a>&nbsp;&nbsp;<a href="#menu">&#9650;</a></h3>
<p>
    -----
</p>
<p align="center">
  <img src="images/" height="400" title="Buying">
</p>

<h3><a name="validation">Data validation</a>&nbsp;&nbsp;<a href="#menu">&#9650;</a></h3>
<p>
    ------
</p>
<p align="center">
  <img src="images/"  height="400" title="Data validation">
</p>

<h2><a name="contact">Контакты</a>&nbsp;&nbsp;<a href="#menu">&#9650;</a></h2>
