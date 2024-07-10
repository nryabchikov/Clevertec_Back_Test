Первым шагом необходимо выполнить: gradlew fatJar. После этого будет создан jar файл с именем clevertec-check-all.jar в директории build/libs. Далее можем запускать наше приложение 
с различными значениями параметров с помощью команды:  
java -jar build/libs/clevertec-check-all.jar 3-1 2-5 5-1 discountCard=1111 balanceDebitCard=100 saveToFile=result.csv datasource.url=jdbc:postgresql://localhost:5432/clevertec_db datasource.username=nikitaryabchikov datasource.password=123
