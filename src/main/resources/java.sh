#!/bin/bash

## 사용자로부터 자바 파일명 입력 받기
#echo "Enter the Java file name (without the .java extension):"
#read javaFileName

cd src
cd main
cd resources

ls
pwd
# 자바 파일명 고정
javaFileName="Main"

# 자바 파일 컴파일
javac "${javaFileName}.java" -encoding UTF8

# 컴파일된 자바 파일 실행
java "${javaFileName}"

java Main 4 5
# 실행이 완료되면 생성된 클래스 파일 및 실행 파일 삭제 (선택사항)
#rm "${javaFileName}.class"


