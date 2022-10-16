#  Дипломный проект профессии "Тестировщик"
___
### Дипломный проект представляет собой комплексное автоматизированное тестирования веб-сервиса по покупке тура.
___

## Запуск SUT, авто-тестов и генерация отчетов

1. Запустить Docker Desktop
2. Открыть проект в IntelliJ IDEA
3. Запустить в терминале IDEA контейнеры:  
   `docker-compose up-d`
4. Запустить приложение:
* Для MySQL:  
  `java -jar ./artifacts/aqa-shop.jar -- spring.datasource.url=jdbc:mysql://localhost:3306/app`
* Для PostgreSQL:  
  `java -jar ./artifacts/aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app`
5. Открыть второй терминал
6. Запустить тесты в новой вкладке терминала в IDEA:
* Для MySQL:  
  `./gradlew clean test -Durl=jdbc:mysql://localhost:3306/app`
* Для PostgreSQL:  
  `./gradlew clean test -Durl=jdbc:postgresql://localhost:5432/app`
7. Создать отчёт Allure  
   `.\gradlew allureServe`
8. Выполнить команду для завершения работы allureServe
   `Ctrl+C`
9. Перейти в первый терминал
10. Остановить приложение
    `Ctrl+C`
11. Выполнить команду для остановки работы контейнеров 
    `docker-compose down`
___

## Документация проекта:
[1. Задание к диплому](https://github.com/Valted-cmd/Diplom/blob/main/files/description.md)  
[2. План автоматизации](https://github.com/Valted-cmd/Diplom/blob/main/files/plan.md)  
[3. Отчет по итогам тестирования](https://github.com/Valted-cmd/Diplom/blob/main/files/report.md)  
[4. Отчет по итогам автоматизации](https://github.com/Valted-cmd/Diplom/blob/main/files/summary.md)
[5. Отчет Allure](https://github.com/Valted-cmd/Diplom/blob/main/files/allure.jpg)
