package study;

import io.cucumber.java8.En;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

        Given("찾을 파일명은 {string}이다.", (String given) -> {
            state.fileName = given;
        });

        When("resource 디렉터리에서 파일명으로 파일을 찾아서 File 객체로 만들고", () -> {
            state.file = new File("src/test/resources/" + state.fileName);
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

        When("resource 디렉터리에 있는 파일을 읽어와서", () -> {
            final Path path = Paths.get("src/test/resources/" + state.fileName);
            state.actual = Files.readAllLines(path);
        });

        Then("파일 내용이 {string}이 맞는지 검증한다.", (String expected) -> {
            assertThat(state.actual).containsOnly(expected);
        });
    }
}
