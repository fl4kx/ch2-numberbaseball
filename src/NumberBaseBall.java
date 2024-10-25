import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class NumberBaseBall {

    /**
     * 플레이어가 맞춰야 하는 숫자
     */
    private final List<Integer> numberOfAnswer = new ArrayList<>();
    /**
     * 플레이어가 제출한 숫자
     */
    private final List<Integer> numberOfUser = new ArrayList<>();
    /**
     * 유저가 입력한 답의 점수판
     */
    private final Map<String, Integer> scoreBoard = new HashMap<>();

    /**
     * 한 게임에서 입력한 횟수
     */
    private int countRound;

    /**
     * 플레이한 게임 수
     */
    private int playTime = 0;

    /**
     * 게임 클리어 히스토리
     */
    private final Map<Integer, Integer> gameHistory = new HashMap<>();

    /**
     * {@code scoreBoard}를 초기화하는 메서드입니다.
     */
    private void initScoreBoard() {
        scoreBoard.put("strike", 0);
        scoreBoard.put("ball", 0);
        scoreBoard.put("out", 3);
    }

    /**
     * 숫자야구 게임의 메인 메뉴입니다.
     * 1. 게임을 시작
     * 2. 기록을 출력
     * 3. 게임을 종료
     * 유저가 만족할 때까지 즐길 수 있습니다.
     */
    public void gameController() {
        Scanner sc = new Scanner(System.in);

        int selectMenu;

        do {
            System.out.println("환영합니다! 원하시는 번호를 입력해주세요");
            System.out.print("1. 게임 시작하기" + "  ");
            System.out.print("2. 게임 기록 보기" + "  ");
            System.out.println("3. 종료하기");

            selectMenu = sc.nextInt();

            switch (selectMenu) {
                case 1:
                    System.out.println("< 게임을 시작합니다 >");
                    gameStart();
                    break;
                case 2:
                    System.out.println(" < 게임 기록 보기 >");
                    getGameHistory();
                    break;
                case 3:
                    clearGameHistory();
                    System.out.println("게임을 종료합니다.");
                    break;
                default:
                    System.out.println("올바른 번호를 입력해주세요.");
            }

        } while (selectMenu != 3);

    }

    /**
     * 숫자야구 게임의 로직입니다. <br>
     * {@code numberGenerator()}를 호출해 임의의 숫자를 생성,
     * {@code initScoreBoard()} 점수판을 초기화합니다.
     * {@code getUserInput()}으로 사용자 입력을 받아
     * {@code answerComparator()}으로 정답을 체크합니다.
     * 점수판의 {@code strike}가 3점이 되면 게임이 종료됩니다.
     */
    private void gameStart() {
        increasePlayTime();
        numberGenerator();

        do {
            initScoreBoard();
            System.out.println("숫자를 입력해 주세요!");

            if (!getUserInput()) {
                System.out.println("올바르지 않은 입력값입니다.");
            } else {
                String result = answerComparator();
                System.out.println(result);
            }

        } while (scoreBoard.get("strike") != 3);

        System.out.println("정답 : " + numberOfAnswer + "\nYou Win\n");
        setGameHistory(playTime, countRound);

        initRound();
    }

    /**
     * 정답으로 사용될 임의의 세 자리 수를 생성해 {@code numberOfAnswer}에 저장합니다.
     */
    private void numberGenerator() {
        Random random = new Random();
        Set<Integer> randomNumberSet = new LinkedHashSet<>();

        while (randomNumberSet.size() < 3) {
            randomNumberSet.add(random.nextInt(9) + 1);
        }

        numberOfAnswer.addAll(randomNumberSet);
    }

    /**
     * {@code numberOfUser}에 숫자를 추가하는 메서드입니다.
     *
     * @param num 유저가 입력한 숫자
     */
    private void addNumberOfUser(int num) {
        numberOfUser.add(num);
    }

    /**
     * {@code numberOfUser}의 요소를 전부 제거하는 메서드입니다.
     */
    private void clearNumberOfUser() {
        numberOfUser.clear();
    }

    /**
     * 사용자로부터 숫자를 입력을 받고 분석 가능한 형태로 변환후 {@code numberOfUser}에 저장하고 {@code true}를 반환합니다.
     * 정상적인 값이 아닐 경우 {@link InputMismatchException} 예외를 발생시켜
     * {@code numberOfUser}를 비운 다음 {@code false}를 반환합니다.
     *
     * @return 문자열 유효성 검사 결과 {@code true} or {@code false}
     */
    private boolean getUserInput() throws InputMismatchException {
        Scanner sc = new Scanner(System.in);

        try {
            int userInput = sc.nextInt();

            while (userInput > 0) {
                addNumberOfUser(userInput % 10);
                userInput /= 10;
            }

            Set<Integer> checkException = new HashSet<>(numberOfUser);

            if (numberOfUser.size() != 3 || checkException.size() != 3) {
                throw new InputMismatchException();
            } else {
                checkException.clear();
            }

            Collections.reverse(numberOfUser);
            increaseRound();
            return true;

        } catch (InputMismatchException e) {
            clearNumberOfUser();
            return false;

        }
    }

    /**
     * {@code numberOfAnswer}와 {@code numberOfUser}의 요소를 비교해
     * {@code scoreBoard}에 결과를 기록하고 힌트를 반환합니다.
     * 비교가 끝나면 {@code numberOfUser}를 비웁니다
     *
     * @return 힌트를 포함한 문자열
     */
    private String answerComparator() {
        if (!numberOfAnswer.isEmpty()) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (numberOfAnswer.get(i).equals(numberOfUser.get(j)) && i == j) {
                        scoreBoard.merge("strike", 1, Integer::sum);
                    } else if (numberOfAnswer.get(i).equals(numberOfUser.get(j))) {
                        scoreBoard.merge("ball", 1, Integer::sum);
                    }
                }
            }

            clearNumberOfUser();
            scoreBoard.put("out", scoreBoard.get("out") - (scoreBoard.get("strike") + scoreBoard.get("ball")));
        }
        return String.format("\u001B[34m" + "S : " + scoreBoard.get("strike") + "  " + "\u001B[33m" + " B : " + scoreBoard.get("ball") + "  " + "\u001B[31m " + "OUT : " + scoreBoard.get("out") + "\u001B[0m");

    }

    /**
     * {@code countRound}를 1 증가시키는 메서드입니다.
     */
    private void increaseRound() {
        countRound++;
    }

    /**
     * 카운트한 라운드 횟수를 초기화하는 메서드입니다.
     */
    private void initRound() {
        countRound = 0;
    }

    /**
     * 게임 플레이 횟수를 1 증가시키는 메서드입니다.
     */
    private void increasePlayTime() {
        playTime++;
    }

    /**
     * 플레이 회차와 해당 회차에 시도한 라운드 횟수를 게임 히스토리에 저장하는 메서드입니다.
     *
     * @param playTime 플레이 회차
     * @param countTry 시도한 라운드 횟수
     */
    private void setGameHistory(int playTime, int countTry) {
        gameHistory.put(playTime, countTry);
    }

    /**
     * 저장된 게임 히스토리를 전부 출력하는 메서드입니다.
     */
    private void getGameHistory() {
        if (!gameHistory.isEmpty()) {
            for (int i = 1; i <= gameHistory.size(); i++) {
                System.out.println(i + "번째 게임 : 시도 횟수 - " + gameHistory.get(i));
            }
        } else {
            System.out.println("--- 기록이 없습니다 ---\n");
        }
    }

    /**
     * 저장된 게임 히스토리를 전부 제거하는 메서드입니다.
     */
    private void clearGameHistory() {
        gameHistory.clear();
    }
}