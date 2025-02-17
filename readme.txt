※powershellで。
※カレントはプロジェクト直下にして下記を実行
java -jar src/main/resources/mybatis-generator-core-1.3.7.jar -configfile src/main/resources/generatorConfig.xml -overwrite

※補足
src/main/resources/generatorConfig.xml で出力先として「resources.mapper_gen」などとしているがこれは良くない。
※本来、大本のパッケジーを変えるのが良い。（例：src/main/resourcesではなくgensrc/main/resources ）
