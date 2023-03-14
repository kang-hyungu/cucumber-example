Feature: I/O Stream 클래스 학습하기

  목차
  ---
  1. OutputStream 학습하기
  2. InputStream 학습하기
  3. FilterStream 학습하기
  4. InputStreamReader 학습하기

  자바는 스트림(Stream)으로부터 I/O를 사용한다.
  입출력(I/O)은 하나의 시스템에서 다른 시스템으로 데이터를 이동 시킬 때 사용한다.

  InputStream은 데이터를 읽고, OutputStream은 데이터를 쓴다.
  FilterStream은 InputStream이나 OutputStream에 연결될 수 있다.
  FilterStream은 읽거나 쓰는 데이터를 수정할 때 사용한다. (e.g. 암호화, 압축, 포맷 변환)

  Stream은 데이터를 바이트로 읽고 쓴다.
  바이트가 아닌 텍스트(문자)를 읽고 쓰려면 Reader와 Writer 클래스를 연결한다.
  Reader, Writer는 다양한 문자 인코딩(e.g. UTF-8)을 처리할 수 있다.

  Rule: 1. OutputStream 학습하기
  자바의 기본 출력 클래스는 java.io.OutputStream이다.
  OutputStream의 write(int b) 메서드가 기반 메서드이다.
  `public abstract void write(int b) throws IOException;`

    Scenario: OutputStream은 데이터를 바이트로 처리한다.
    OutputStream은 다른 매체에 바이트로 데이터를 쓸 때 사용한다.
    OutputStream의 서브 클래스(subclass)는 특정 매체에 데이터를 쓰기 위해 write(int b) 메서드를 사용한다.
    예를 들면, FileOutputStream은 파일로 데이터를 쓸 때,
    또는 DataOutputStream은 자바의 primitive type data를 다른 매체로 데이터를 쓸 때 사용한다.

    write 메서드는 데이터를 바이트로 출력하기 때문에 비효율적이다.
    `write(byte[] data)`와 `write(byte b[], int off, int len)` 메서드를 사용하면
    1바이트 이상을 한 번에 전송 할 수 있어 훨씬 효율적이다.

      Given write 테스트로 사용할 "110, 101, 120, 116, 115, 116, 101, 112" 데이터를 byte 배열로 준비한다.
      When OutputStream 객체의 write 메서드를 사용해서 테스트를 통과시킨다.
      Then OutputStream 객체로 출력한 데이터가 "nextstep"이 맞는지 검증한다.

    Scenario: BufferedOutputStream을 사용하면 버퍼링이 가능하다.
    효율적인 전송을 위해 스트림에서 버퍼링을 사용할 수 있다.
    BufferedOutputStream 필터를 연결하면 버퍼링이 가능하다.

    버퍼링을 사용한다면 OutputStream의 flush() 메서드를 사용하자.
    flush() 메서드는 버퍼가 아직 가득 차지 않은 상황에서 강제로 버퍼의 내용을 전송한다.
    Stream은 동기(synchronous)로 동작하기 때문에 버퍼가 찰 때까지 기다리면
    데드락(deadlock) 상태가 되기 때문에 flush로 해제해야 한다.

      Given BufferedOutputStream 클래스를 mocking한다.
      When OutputStream 객체의 flush 메서드를 호출한다.
      Then flush 메서드가 호출됐는지 검증한다.

    Scenario: OutputStream을 사용하고 나서 close() 메서드를 실행한다.
    스트림 사용이 끝나면 항상 close() 메서드를 호출하여 스트림을 닫는다.
    장시간 스트림을 닫지 않으면 파일, 포트 등 다양한 리소스에서 누수(leak)가 발생한다.

      Given OutputStream 클래스를 mocking한다.
      When try-with-resources를 사용하여 OutputStream이 자동으로 스트림이 닫히도록 하자.
      Then OutputStream의 close 메서드가 호출됐는지 검증한다.

  Rule: 2. InputStream 학습하기
  자바의 기본 입력 클래스는 java.io.InputStream이다.
  InputStream은 다른 매체로부터 바이트로 데이터를 읽을 때 사용한다.
  InputStream의 read() 메서드는 기반 메서드이다.
  `public abstract int read() throws IOException;`

  InputStream의 서브 클래스(subclass)는 특정 매체에 데이터를 읽기 위해 read() 메서드를 사용한다.

    Scenario: InputStream은 데이터를 바이트로 읽는다.
    read() 메서드는 매체로부터 단일 바이트를 읽는데, 0부터 255 사이의 값을 int 타입으로 반환한다.
    int 값을 byte 타입으로 변환하면 -128부터 127 사이의 값으로 변환된다.
    그리고 Stream 끝에 도달하면 -1을 반환한다.

      Given read 테스트로 사용할 "-16, -97, -92, -87" 데이터를 byte 배열로 준비한다.
      When InputStream의 read 메서드를 사용해서 바이트를 문자열로 변환한다.
      Then InputStream으로 읽은 데이터가 "🤩"가 맞는지 검증한다.

    Scenario: InputStream을 사용하고 나서 close 메서드를 실행한다.
    스트림 사용이 끝나면 항상 close() 메서드를 호출하여 스트림을 닫는다.
    장시간 스트림을 닫지 않으면 파일, 포트 등 다양한 리소스에서 누수(leak)가 발생한다.

      Given InputStream 클래스를 mocking한다.
      When try-with-resources를 사용하여 InputStream이 자동으로 스트림이 닫히도록 하자.
      Then InputStream의 close 메서드가 호출됐는지 검증한다.

  Rule: 3. FilterStream 학습하기
  필터는 필터 스트림, reader, writer로 나뉜다.
  필터는 바이트를 다른 데이터 형식으로 변환 할 때 사용한다.
  reader, writer는 UTF-8, ISO 8859-1 같은 형식으로 인코딩된 텍스트를 처리하는 데 사용된다.

    Scenario: 필터인 BufferedInputStream을 사용해보자
    BufferedInputStream은 데이터 처리 속도를 높이기 위해 데이터를 버퍼에 저장한다.
    InputStream 객체를 생성하고 필터 생성자에 전달하면 필터에 연결된다.
    버퍼 크기를 지정하지 않으면 버퍼의 기본 사이즈는 얼마일까?

      Given "필터에 연결해보자." 문자열로 ByteArrayInputStream 객체를 생성한다.
      And BufferedInputStream 객체를 생성한다.
      When BufferedInputStream 객체로 byte 배열을 반환한다.
      Then BufferedInputStream 객체가 FilterInputStream 타입이 맞는지 검증하고
      And "필터에 연결해보자." 문자열을 byte 배열로 반환했는지 검증한다.

  Rule: 4. InputStreamReader 학습하기
  자바의 기본 문자열은 UTF-16 유니코드 인코딩을 사용한다.
  문자열이 아닌 바이트 단위로 처리하려니 불편하다.
  그리고 바이트를 문자(char)로 처리하려면 인코딩을 신경 써야 한다.
  reader, writer를 사용하면 입출력 스트림을 바이트가 아닌 문자 단위로 데이터를 처리하게 된다.
  그리고 InputStreamReader를 사용하면 지정된 인코딩에 따라 유니코드 문자로 변환할 수 있다.

    Scenario: BufferedReader를 사용하여 문자열을 읽어온다.
      InputStreamReader를 사용해서 바이트를 문자(char)로 읽어온다.
      읽어온 문자(char)를 문자열(String)로 처리하자.
      필터인 BufferedReader를 사용하면 readLine 메서드를 사용해서 문자열(String)을 한 줄씩 읽어올 수 있다.

      Given emoji 문자열로 ByteArrayInputStream 객체를 만든다.
      """
      😀😃😄😁😆😅😂🤣🥲☺️😊
      😇🙂🙃😉😌😍🥰😘😗😙😚
      😋😛😝😜🤪🤨🧐🤓😎🥸🤩
      """
      When BufferedReader 객체를 만들어서 문자열을 한 줄씩 읽는다.
      Then BufferedReader 객체로 읽은 emoji 문자열을 올바르게 읽었는지 검증한다.
