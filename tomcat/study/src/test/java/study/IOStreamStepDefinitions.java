package study;

import io.cucumber.docstring.DocString;
import io.cucumber.java8.En;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class IOStreamStepDefinitions implements En {

    public IOStreamStepDefinitions() {
        // OutputStream 학습하기
        OutputStream은_데이터를_바이트로_처리한다();
        BufferedOutputStream을_사용하면_버퍼링이_가능하다();
        OutputStream을_사용하고_나서_close_메서드를_실행한다();

        // InputStream 학습하기
        InputStream은_데이터를_바이트로_읽는다();
        InputStream을_사용하고_나서_close_메서드를_실행한다();

        // FilterStream 학습하기
        필터인_BufferedInputStream을_사용해보자();

        // InputStreamReader 학습하기
        BufferedReader를_사용하여_문자열을_읽어온다();
    }

    private void OutputStream은_데이터를_바이트로_처리한다() {
        var state = new Object() {
            byte[] bytes;
            String actual;
        };

        Given("write 테스트로 사용할 {string} 데이터를 byte 배열로 준비한다.", (String given) -> {
            // gherkin으로 작성한 문서는 byte 타입을 만들 수 없어서 문자열을 바이트로 변환함
            state.bytes = convertStringToByteArray(given);
        });

        When("OutputStream 객체의 write 메서드를 사용해서 테스트를 통과시킨다.", () -> {
            final OutputStream outputStream = new ByteArrayOutputStream(state.bytes.length);

            // todo
            // OutputStream 객체의 write 메서드를 사용해서 테스트를 통과시킨다
            outputStream.write(state.bytes);
            state.actual = outputStream.toString();
            outputStream.close();
        });

        Then("OutputStream 객체로 출력한 데이터가 {string}이 맞는지 검증한다.", (String expected) -> {
            assertThat(state.actual).isEqualTo(expected);
        });
    }

    private void BufferedOutputStream을_사용하면_버퍼링이_가능하다() {
        var state = new Object() {
            OutputStream outputStream;
        };

        Given("BufferedOutputStream 클래스를 mocking한다.", () -> {
            state.outputStream = mock(BufferedOutputStream.class);
        });

        When("OutputStream 객체의 flush 메서드를 호출한다.", () -> {
            /**
             * todo
             * flush를 사용해서 테스트를 통과시킨다.
             * ByteArrayOutputStream과 어떤 차이가 있을까?
             */
            state.outputStream.flush();
        });

        Then("flush 메서드가 호출됐는지 검증한다.", () -> {
            verify(state.outputStream, atLeastOnce()).flush();
        });
    }

    private void OutputStream을_사용하고_나서_close_메서드를_실행한다() {
        var state = new Object() {
            OutputStream outputStream;
        };

        Given("OutputStream 클래스를 mocking한다.", () -> {
            state.outputStream = mock(OutputStream.class);
        });

        When("try-with-resources를 사용하여 OutputStream이 자동으로 스트림이 닫히도록 하자.", () -> {
            /**
             * todo
             * try-with-resources를 사용한다.
             * java 9 이상에서는 변수를 try-with-resources로 처리할 수 있다.
             */
            final OutputStream 변수 = null;
        });

        Then("OutputStream의 close 메서드가 호출됐는지 검증한다.", () -> {
            verify(state.outputStream, atLeastOnce()).close();
        });
    }

    private void InputStream은_데이터를_바이트로_읽는다() {
        var state = new Object() {
            byte[] bytes;
            InputStream inputStream;
            String actual;
        };

        Given("read 테스트로 사용할 {string} 데이터를 byte 배열로 준비한다.", (String given) -> {
            // gherkin으로 작성한 문서는 byte 타입을 만들 수 없어서 문자열을 바이트로 변환함
            state.bytes = convertStringToByteArray(given);
            state.inputStream = new ByteArrayInputStream(state.bytes);
        });

        When("InputStream의 read 메서드를 사용해서 바이트를 문자열로 변환한다.", () -> {
            /**
             * todo
             * inputStream에서 바이트로 반환한 값을 문자열로 어떻게 바꿀까?
             */
            state.actual = "";
        });

        Then("InputStream으로 읽은 데이터가 {string}가 맞는지 검증한다.", (String expected) -> {
            assertThat(state.actual).isEqualTo("🤩");
            assertThat(state.inputStream.read()).isEqualTo(-1);
            state.inputStream.close();
        });
    }

    private void InputStream을_사용하고_나서_close_메서드를_실행한다() {
        var state = new Object() {
            InputStream inputStream;
        };

        Given("InputStream 클래스를 mocking한다.", () -> {
            state.inputStream = mock(InputStream.class);
        });

        When("try-with-resources를 사용하여 InputStream이 자동으로 스트림이 닫히도록 하자.", () -> {
            /**
             * todo
             * try-with-resources를 사용한다.
             * java 9 이상에서는 변수를 try-with-resources로 처리할 수 있다.
             */
            final InputStream 변수 = null;
        });

        Then("InputStream의 close 메서드가 호출됐는지 검증한다.", () -> {
            verify(state.inputStream, atLeastOnce()).close();
        });
    }

    private void 필터인_BufferedInputStream을_사용해보자() {
        var state = new Object() {
            InputStream inputStream;
            InputStream bufferedInputStream;
            byte[] actual;
        };

        Given("{string} 문자열로 ByteArrayInputStream 객체를 생성한다.", (String given) -> {
            state.inputStream = new ByteArrayInputStream(given.getBytes());
        });
        And("BufferedInputStream 객체를 생성한다.", () -> {
            state.bufferedInputStream = null;
        });

        When("BufferedInputStream 객체로 byte 배열을 반환한다.", () -> {
            state.actual = null;
        });

        Then("BufferedInputStream 객체가 FilterInputStream 타입이 맞는지 검증하고", () -> {
            assertThat(state.bufferedInputStream).isInstanceOf(FilterInputStream.class);
        });
        And("{string} 문자열을 byte 배열로 반환했는지 검증한다.", (String expected) -> {
            assertThat(state.actual).isEqualTo(expected.getBytes());
        });
    }

    private void BufferedReader를_사용하여_문자열을_읽어온다() {
        var state = new Object() {
            String emoji;
            InputStream inputStream;
            StringBuilder actual;
        };

        Given("emoji 문자열로 ByteArrayInputStream 객체를 만든다.", (DocString given) -> {
            state.emoji = given.getContent();
            state.inputStream = new ByteArrayInputStream(state.emoji.getBytes());
        });

        When("BufferedReader 객체를 만들어서 문자열을 한 줄씩 읽는다.", () -> {
            state.actual = null;
        });

        Then("BufferedReader 객체로 읽은 emoji 문자열을 올바르게 읽었는지 검증한다.", () -> {
            assertThat(state.actual).hasToString(state.emoji);
        });
    }

    private static byte[] convertStringToByteArray(final String given) {
        final var bytes = Stream.of(given.split(", "))
                .map(Byte::parseByte)
                .toArray(Byte[]::new);
        return ArrayUtils.toPrimitive(bytes);
    }
}
