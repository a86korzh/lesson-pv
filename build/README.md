Для запуска сборки 
необходимо в файле hibernate.cfg.xml раскомментировать строку:
<property name="hibernate.connection.url">jdbc:mysql://database:3306/school</property>
иначе будет ошибка соединения с базой данных, как здесь:
https://www.javaer101.com/en/article/3656591.html

далее
собрать war-файл (в idea: build->build artifact->war->build)
и поместить его в ./build/packs/

далее
в ./build/base/ поместить fill_db.sql из src/main/resources/fill_db.sql

далее
docker-compose up -d
docker-compose down

# docker build -t tomcat-img .
# docker run --name tmc -d -p 8088:8080 tomcat-img:latest
# docker exec -it tomcat bash
# docker exec -it mysql bash
# mysql -uroot -p
# use school;
# show tables;
