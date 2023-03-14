Feature: File 클래스 학습하기

  목차
  ---
  1. resource 디렉터리 경로 찾기
  2. 파일 내용 읽기

  웹서버는 사용자가 요청한 html 파일을 제공 할 수 있어야 한다.
  File 클래스를 사용해서 파일을 읽어오고, 사용자에게 전달한다.

  Scenario: 1. resource 디렉터리 경로 찾기
  File 객체를 생성하려면 파일의 경로를 알아야 한다.
  자바 애플리케이션은 resource 디렉터리에 HTML, CSS 같은 정적 파일을 저장한다.
  resource 디렉터리의 경로는 어떻게 알아낼 수 있을까?

    Given 찾을 파일명은 "nextstep.txt"이다.
    When resource 디렉터리에서 파일명으로 파일을 찾아서 File 객체로 만들고
    Then File 객체의 파일명이 "nextstep.txt"인지 검증한다.

  Scenario: 2. 파일 내용 읽기
  읽어온 파일의 내용을 I/O Stream을 사용해서 사용자에게 전달 해야 한다.
  File, Files 클래스를 사용하여 파일의 내용을 읽어보자.

    Given 읽어올 파일명은 "nextstep.txt"이다.
    When resource 디렉터리에 있는 파일을 읽어와서
    Then 파일 내용이 "nextstep"이 맞는지 검증한다.
