#!/bin/bash

cd src/main/resources

# 자바 파일명 고정
javaFileName="Main"

# 자바 파일 컴파일
javac "${javaFileName}.java" -encoding UTF8

# 컴파일된 자바 파일 실행
result=$(java "${javaFileName}" 4 5)
#echo $result
#파일로 저장
echo $result > result.txt
cat result.txt


# 실행이 완료되면 생성된 클래스 파일 및 실행 파일 삭제 (선택사항)
rm "${javaFileName}.class"


