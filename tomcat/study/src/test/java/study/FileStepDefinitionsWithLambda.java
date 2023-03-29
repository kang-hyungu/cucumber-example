package study;

import io.cucumber.java8.En;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static io.cucumber.core.internal.com.fasterxml.jackson.databind.cfg.CoercionInputShape.Object;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 장점
 * given, when, then이 한 곳에 모여서 읽기 쉽다.
 * state를 메서드 단위로 관리 할 수 있다.
 * 테스트 케이스를 격리할 수 있다.
 *
 * 단점
 * 생성자에 매번 메서드를 추가해줘야 한다.(자동화 방법을 찾아보자)
 * 동일한 expression을 사용하지 못한다.(명명 규칙을 정하자)
 */
public class FileStepDefinitionsWithLambda implements En {

    public FileStepDefinitionsWithLambda() {
        resource_디렉터리_경로_찾기();
        파일_내용_읽기();
    }

    private void resource_디렉터리_경로_찾기() {
        var state = new Object() {
            String fileName;
            File file;
            String actual;
        };
        //state 라는걸 만드네? 이름이 왜 state일까?
        // 공식 문서의 예시코드에서는 Object 가 생성되진 않았어서 낯설음

        Given("찾을 파일명은 {string}이다.", (String given) -> {
            state.fileName = given;
        });
        //파일을 찾는 내장 함수같은게 있던가??
        //파일 패스를 resource 디렉터리까지 추가로 적어야 되는걸까? "resources"+state.fileName 이런식으로
        When("resource 디렉터리에서 파일명으로 파일을 찾아서 File 객체로 만들고", () -> {
            state.file = new File(state.fileName);
            state.actual = state.file.getName();
        });

        Then("File 객체의 파일명이 {string}인지 검증한다.", (String expected) -> {
            assertThat(state.actual).endsWith(expected);
        });
    }

    private void 파일_내용_읽기() {
        var state = new Object() {
            String fileName;
            List<String> actual;
        };

        Given("읽어올 파일명은 {string}이다.", (String given) -> {
            state.fileName = given;
        });
        // 상대 경로:실패, 절대경로:성공을 하고
        // 경로를 수정해보다가 아래 패스를 지정해서 성공
        // 파일 read 메소드는 검색해 봄
        When("resource 디렉터리에 있는 파일을 읽어와서", () -> {
            final Path path = Path.of("src/test/resources/nextstep.txt");
            state.actual = Files.readAllLines(path);
        });

        Then("파일 내용이 {string}이 맞는지 검증한다.", (String expected) -> {
            assertThat(state.actual).containsOnly(expected);
        });
    }
}
