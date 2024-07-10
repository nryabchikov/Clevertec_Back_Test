Первым шагом необходимо скомпилировать все .java файлы из директории src и сохранить результаты компиляции в директорию out. Для этого используем следующую команду в терминале:
javac -d out $(find src -name "*.java")
После компиляции исходного кода можно запускать приложение с различными параметрами. Например:
java -cp out ru.clevertec.check.CheckRunner 1-1 1-2 2-1 discountCard=1111 balanceDebitCard=120 pathToFile=src/main/resources/prod.csv saveToFile=result1.csv

