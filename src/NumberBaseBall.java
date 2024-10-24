import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class NumberBaseBall {

    /**
     * 플레이어가 맞춰야 하는 숫자
     */
    private final LinkedHashSet<Integer> numberOfAnswer = new LinkedHashSet<>();
    /**
     * 플레이어가 제출한 숫자
     */
    private final List<Integer> numberOfUser = new ArrayList<>();

    public void controller() {
    }

    /**
     * 정답으로 사용될 임의의 세 자리 수를 생성합니다.
     */
    private void numberGenerator() {
        Random random = new Random();

        while (numberOfAnswer.size() < 3) {
            numberOfAnswer.add(random.nextInt(9) + 1);
        }
    }

    /**
     * 사용자로부터 숫자를 입력을 받고 분석 가능한 형태로 가공합니다.
     */
    private void getUserInput() {
        Scanner sc = new Scanner(System.in);
        int userInput = sc.nextInt();

        while (userInput > 0) {
            numberOfUser.add( userInput % 10);
            userInput /= 10;
        }
        Collections.reverse(numberOfUser);
    }

    private void answerHandler() {
    }
}
