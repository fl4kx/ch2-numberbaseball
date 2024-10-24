import java.util.LinkedHashSet;
import java.util.Random;

public class NumberBaseBall {

    /**
     * 플레이어가 맞춰야 하는 숫자
     */
    private final LinkedHashSet<Integer> numberOfAnswer = new LinkedHashSet<>();

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

    private void getUserInput() {

    }

    private void answerHandler() {
    }
}
